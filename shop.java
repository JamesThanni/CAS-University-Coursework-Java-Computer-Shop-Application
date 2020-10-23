import java.util.*;


import java.awt.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class shop extends JFrame {
	private JPanel contentPane;
	public JTable productTable;
	public static JTable basketTable;
	private JTextField paymentInfoInput;
	private JTextField secCodeInput;
	
	public static int totalBasketCost = 0;
	public int productDetailNumber = 0;
	public static String[] categories = product.productDetailNames;
	public static String accountLoggedIn = cas.selectedUser;
	public static ArrayList<String> selectedProducts = new ArrayList<String>();
	private static ArrayList<JButton> allBtns = new ArrayList<JButton>();
	private static ArrayList<String> newProductDetails = new ArrayList<String>();
	private static ArrayList<String> newLogEntries = new ArrayList<String>();
	public static JLabel optionResult = new JLabel("");
	String[] basketColumns = {product.productDetailNames[0], product.productDetailNames[1], 
			product.productDetailNames[3], product.productDetailNames[6],
			product.productDetailNames[8]};
	DefaultTableModel basketModel = new DefaultTableModel(new Object[][] {}, basketColumns);
	public static ArrayList<String> updatedProductDetails = new ArrayList<String>();
			
	public shop() {
		JLabel searchHint = new JLabel("Search here:");
		JTextField searchInput = new JTextField();
		JButton searchBtn = new JButton("Search");
		JButton addToBasketBtn = new JButton("Add to Basket");
		JButton saveBtn = new JButton("Save for Later");
		JLabel inputBG = new JLabel("  <CAS>");
		inputBG.setFont(new Font("Consolas", Font.PLAIN, 15));
		inputBG.setForeground(Color.GREEN);
		
		JTable basketTable = new JTable();
		basketTable.setForeground(Color.WHITE);
		JTableHeader basketTH = basketTable.getTableHeader();
		JScrollPane basketSP = new JScrollPane();
		
		JTable productTable = new JTable();
		JTableHeader productTH = productTable.getTableHeader();
		JScrollPane productSP = new JScrollPane();
		
		JButton clearBtn = new JButton("Cancel & Clear Basket");
		JLabel payOptionMsg = new JLabel("");
		JLabel securityCodeMsg = new JLabel("Enter security code:");
		JButton submitPaymentBtn = new JButton("Pay");
		
		JButton paypalBtn = new JButton("Pay via PayPal");
		JButton cardBtn = new JButton("Pay via Credit Card\r\n");
		
		setTitle("CAS - Shop");
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(25, 25, 25));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		searchHint.setFont(new Font("Candara", Font.PLAIN, 12));
		searchHint.setForeground(Color.WHITE);
		searchHint.setBounds(262, 10, 70, 20);
		contentPane.add(searchHint);
		
		searchInput.setFont(new Font("Candara", Font.BOLD, 12));
		searchInput.setForeground(Color.WHITE);
		searchInput.setBounds(345, 10, 545, 20);
		searchInput.setOpaque(true);
		searchInput.setBackground(new Color(45, 45, 45));
		contentPane.add(searchInput);
		searchInput.setColumns(10);
		searchInput.setBorder(BorderFactory.createEmptyBorder());
				
		searchBtn.setBounds(905, 10, 110, 20);
		contentPane.add(searchBtn);
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String searchTerm = searchInput.getText().toLowerCase();
				
				if (!cas.isAdminUser && searchTerm.matches("[0-9]+")) {
					optionResult.setText("Invalid search!");
					productSP.setVisible(false);
					productTH.setVisible(false);
				} else {
					productSP.setVisible(true);
					productTH.setVisible(true);
					
					if (cas.isAdminUser) {
						admin.adminSearch(product.readStockData(searchTerm), productTable);
						optionResult.setText(String.valueOf(productTable.getRowCount()) + " result(s) found!");
					} else {
						customer.userSearch(product.readStockData(searchTerm), productTable);
						optionResult.setText(String.valueOf(productTable.getRowCount()) + " result(s) found!");
					}
					
				}
			}
		});
				
		saveBtn.setBounds(1025, 10, 110, 20);
		contentPane.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (basketTable.getRowCount() >=1 ) {
					try {
						logActivity("saved", " ");
					} catch (IOException logError) {
						optionResult.setText("Error: " + logError);
					}
					
					optionResult.setText("Basket items saved for later.");
					basketModel.setRowCount(0);
					basketTable.setModel(basketModel);
					selectedProducts.clear();
				}
				
			}	
		});
		 
		
		productTable.setBounds(510, 61, 430, 262);
		productTable.setDefaultEditor(Object.class, null);
		productTable.setBackground(new Color(25, 25, 25));
		productTable.setForeground(Color.WHITE);
		productTable.setFont(new Font("Candara", Font.PLAIN, 14));
		productTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Please search for items!"}) {});
		productTable.setShowGrid(false);
		
		productTH.setFont(new Font("Candara", Font.BOLD, 14));
		productTH.setBorder(BorderFactory.createEmptyBorder());
		productTH.setBackground(new Color(25, 25, 25));
		productTH.setForeground(new Color(119, 221, 119));
		productTH.setVisible(true);
		
		productSP.setBounds(345, 50, 910, 409);
		productSP.setBorder(BorderFactory.createEmptyBorder());
		productSP.getViewport().setBackground(new Color(25, 25, 25));
		productSP.setViewportView(productTable);
		contentPane.add(productSP);
		productSP.setVisible(true);
		
			
		basketTable.setBounds(0, 0, 1, 1);
		basketTable.setDefaultEditor(Object.class, null);
		basketTable.setBackground(Color.DARK_GRAY);
		basketTable.setFont(new Font("Candara", Font.PLAIN, 12));
		basketTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Added Items go here!"}));
		
		basketTH.setFont(new Font("Candara", Font.BOLD, 14));
		basketTH.setBorder(BorderFactory.createEmptyBorder());
		basketTH.setBackground(new Color(15, 15, 15));
		basketTH.setForeground(new Color(119, 221, 119));
		basketSP.setBounds(10, 50, 325, 320);
		basketSP.setBorder(BorderFactory.createEmptyBorder());
		basketSP.setViewportView(basketTable);
		basketSP.getViewport().setBackground(new Color(25, 25, 25));
		contentPane.add(basketSP);	
		
		
		addToBasketBtn.setBounds(1145, 10, 110, 20);
		contentPane.add(addToBasketBtn);
		addToBasketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				if (productTable.getRowCount() >= 1) {
			
				int selectedRow = productTable.getSelectedRow();
					Object[] rowData = new Object[5];
					rowData[0] = productTable.getModel().getValueAt(selectedRow, 0);
			        rowData[1]=productTable.getModel().getValueAt(selectedRow, 1);
			        rowData[2]=productTable.getModel().getValueAt(selectedRow, 3);
			        rowData[3]="1";
			        rowData[4]=productTable.getModel().getValueAt(selectedRow, 7);
			        
			        int stock = Integer.parseInt(productTable.getModel().getValueAt(selectedRow, 6).toString());
			        
			        if (stock == 0) {
			        	optionResult.setText(rowData[0].toString() + " out of stock!");
			        } else {
				        int itemsAdded = 0;
				        for(String word : selectedProducts){
				            if(word.contains(rowData[0].toString())) 
				                itemsAdded++;
				        }
				        
				        if (itemsAdded < stock) {
				        	 basketModel.addRow(rowData);
				        	 basketModel.setColumnIdentifiers(basketColumns);
					        basketTable.setModel(basketModel);
					        String[] details = Arrays.copyOf(rowData, rowData.length, String[].class);
					        selectedProducts.add(String.join(",", details));
				        } else {
				        	optionResult.setText("Not enough stock of product " + rowData[0]);
				        }
			        }
		        	       
				}
				
			}
		});
		optionResult.setHorizontalAlignment(SwingConstants.RIGHT);

		optionResult.setForeground(Color.WHITE);
		optionResult.setFont(new Font("Candara", Font.PLAIN, 16));
		optionResult.setBounds(853, 470, 400, 20);
		contentPane.add(optionResult);
		
		
		payOptionMsg.setVisible(false);
		payOptionMsg.setForeground(Color.WHITE);
		payOptionMsg.setFont(new Font("Candara", Font.PLAIN, 12));
		payOptionMsg.setVisible(false);
		payOptionMsg.setBounds(10, 470, 107, 20);
		contentPane.add(payOptionMsg);
		
		
		securityCodeMsg.setVisible(false);
		securityCodeMsg.setForeground(Color.WHITE);
		securityCodeMsg.setFont(new Font("Candara", Font.PLAIN, 12));
		securityCodeMsg.setBounds(345, 470, 110, 20);
		contentPane.add(securityCodeMsg);
		
		paymentInfoInput = new JTextField();
		paymentInfoInput.setVisible(false);
		paymentInfoInput.setOpaque(true);
		paymentInfoInput.setForeground(Color.WHITE);
		paymentInfoInput.setFont(new Font("Candara", Font.BOLD, 12));
		paymentInfoInput.setColumns(10);
		paymentInfoInput.setBorder(BorderFactory.createEmptyBorder());
		paymentInfoInput.setBackground(new Color(45, 45, 45));
		paymentInfoInput.setBounds(125, 470, 210, 20);
		contentPane.add(paymentInfoInput);
		
		secCodeInput = new JTextField();
		secCodeInput.setVisible(false);
		secCodeInput.setOpaque(true);
		secCodeInput.setForeground(Color.WHITE);
		secCodeInput.setFont(new Font("Candara", Font.BOLD, 12));
		secCodeInput.setColumns(10);
		secCodeInput.setBorder(BorderFactory.createEmptyBorder());
		secCodeInput.setBackground(new Color(45, 45, 45));
		secCodeInput.setBounds(465, 470, 210, 20);	
		contentPane.add(secCodeInput);
		
		submitPaymentBtn.setBounds(685, 470, 150, 20);
		submitPaymentBtn.setVisible(false);
		contentPane.add(submitPaymentBtn);
		submitPaymentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				submitPayment( payOptionMsg,  securityCodeMsg, submitPaymentBtn);
			}
		});
		
		
		paypalBtn.setBounds(10, 439, 325, 20);
		contentPane.add(paypalBtn);
		allBtns.add(paypalBtn);
		paypalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (basketTable.getRowCount() >= 1) {
					payOptionMsg.setVisible(true);
					paymentInfoInput.setText("");
					payOptionMsg.setText("Enter email:");
					paymentInfoInput.setVisible(true);
					submitPaymentBtn.setVisible(true);
					securityCodeMsg.setVisible(false);
					secCodeInput.setVisible(false);	
				}
			}
		});
		
		cardBtn.setBounds(10, 410, 325, 20);
		contentPane.add(cardBtn);
		allBtns.add(cardBtn);
		cardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (basketTable.getRowCount() >= 1) {
					payOptionMsg.setVisible(true);
					paymentInfoInput.setText("");
					secCodeInput.setText("");
					payOptionMsg.setText("Enter card number:");
					paymentInfoInput.setVisible(true);
					securityCodeMsg.setVisible(true);
					secCodeInput.setVisible(true);	
					submitPaymentBtn.setVisible(true);
				}
				}
			});
		
		
		clearBtn.setBounds(10, 380, 325, 20);
		contentPane.add(clearBtn);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logActivity("cancelled", " ");
				} catch (IOException logError) {
					optionResult.setText("Error: " + logError);
				}
				payOptionMsg.setVisible(false);
				securityCodeMsg.setVisible(false);
				paymentInfoInput.setVisible(false);
				secCodeInput.setVisible(false);	
				submitPaymentBtn.setVisible(false);
				basketModel.setRowCount(0);
				basketTable.setModel(basketModel);
				selectedProducts.clear();				
				optionResult.setText("Basket cleared");
				
			}
		});
		
		
		if (cas.isAdminUser) {
			admin.adminSearch(product.readStockData(""),  productTable);
			productTH.setVisible(true);
			productSP.setVisible(true);
			basketSP.setVisible(false);
			paypalBtn.setVisible(false);
			cardBtn.setVisible(false);
			clearBtn.setVisible(false);
			addToBasketBtn.setVisible(false);
			saveBtn.setVisible(false);
			
			JPanel adminCtrls = new JPanel();
			JButton addItemBtn = new JButton("Add Item");
			JLabel addHintLbl = new JLabel();
			JTextField detailInput = new JTextField();
			JLabel itemAddedMsg = new JLabel("Item Added!");
			JButton nextFieldBtn = new JButton("Next Information Category");
			
			adminCtrls.setBounds(50, 50, 200, 217);
			adminCtrls.setBackground(new Color(25, 25, 25));
			contentPane.add(adminCtrls);
			adminCtrls.setLayout(null);
						
			
			addHintLbl.setForeground(Color.WHITE);
			addHintLbl.setFont(new Font("Candara", Font.PLAIN, 11));
			addHintLbl.setBounds(0, 79, 200, 20);
			adminCtrls.add(addHintLbl);
			addHintLbl.setVisible(false);
			
			
			detailInput.setOpaque(true);
			detailInput.setForeground(Color.WHITE);
			detailInput.setFont(new Font("Candara", Font.BOLD, 12));
			detailInput.setColumns(10);
			detailInput.setBorder(BorderFactory.createEmptyBorder());
			detailInput.setBackground(new Color(45, 45, 45));
			detailInput.setBounds(0, 109, 200, 20);
			adminCtrls.add(detailInput);
			detailInput.setVisible(false);
			
			
			itemAddedMsg.setForeground(Color.GREEN);
			itemAddedMsg.setFont(new Font("Candara", Font.PLAIN, 12));
			itemAddedMsg.setBounds(58, 30, 200, 20);
			adminCtrls.add(itemAddedMsg);
			itemAddedMsg.setVisible(false);
			
			
			nextFieldBtn.setBounds(0, 139, 200, 20);
			nextFieldBtn.setVisible(false);
			adminCtrls.add(nextFieldBtn);
			nextFieldBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inputNextDetail(detailInput,  addHintLbl,
							 nextFieldBtn,  itemAddedMsg,  addItemBtn);
				}	
			});
			
			
			
			addItemBtn.setBounds(0, 0, 200, 20);
			adminCtrls.add(addItemBtn);
			addItemBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					detailInput.setText("");
					itemAddedMsg.setVisible(false);
					addHintLbl.setVisible(true);
					detailInput.setVisible(true);
					nextFieldBtn.setVisible(true);
					addHintLbl.setText("To add an item please input barcode:");
					productDetailNumber = 1;
					addItemBtn.setVisible(false);
				}
			});
			
			allBtns.add(addItemBtn);
			allBtns.add(nextFieldBtn);				
		}
		
		allBtns.add(searchBtn);
		allBtns.add(addToBasketBtn);
		allBtns.add(clearBtn);
		allBtns.add(submitPaymentBtn);
		allBtns.add(saveBtn);
		
		
		inputBG.setBounds(0, 0, 1264, 45);
		inputBG.setOpaque(true);
		inputBG.setBackground(new Color(15, 15, 15));
		inputBG.setIcon(null);
		contentPane.add(inputBG);
		
		for (JButton btn: allBtns) {
			styleBtns(btn);
		}
		
	} 
	
	
	
	public void inputNextDetail(JTextField detailInput, JLabel addHintLbl, JButton nextFieldBtn, JLabel itemAddedMsg, JButton addItemBtn) {

		newProductDetails.add(detailInput.getText());
		detailInput.setText("");
	 	if (productDetailNumber != categories.length) {
			addHintLbl.setText("Please input " + categories[productDetailNumber] + ":");
			productDetailNumber++;
		} else  { 
			try {
				admin.addNewProduct(newProductDetails);
				addHintLbl.setVisible(false);
				detailInput.setVisible(false);
				nextFieldBtn.setVisible(false);
				itemAddedMsg.setVisible(true);
				addItemBtn.setVisible(true);
				
			} catch (IOException detailInputError) {
				optionResult.setText("Error: " + detailInputError);
			}
			
		}
			
		}

	public static void runShop() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					shop frame = new shop();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Runs the CAS shop window using the shop frame and its components.
	 */
	
	
	
	
	public static void styleBtns(JButton btn) {
		btn.setOpaque(true);
		btn.setFont(new Font("Candara", Font.PLAIN, 14));
		btn.setForeground(Color.BLACK);
		btn.setBackground(new Color(119, 221, 119));
		btn.setBorder(BorderFactory.createEmptyBorder());		
	}
	/**
	 * Styles all buttons of the CAS shop window simultaneously.
	 * @param payOptionMsg 
	 * @param securityCodeMsg 
	 * @param submitPaymentBtn 
	 * @param btn - any button which will be styled.
	 */
	
	public void submitPayment(JLabel payOptionMsg, JLabel securityCodeMsg, JButton submitPaymentBtn) {
		Boolean cardNoCheck =  paymentInfoInput.getText().matches("[0-9]+") && paymentInfoInput.getText().length() == 16;
		Boolean secNoCheck = secCodeInput.isVisible() && secCodeInput.getText().matches("[0-9]+") && secCodeInput.getText().length() == 3;
		Boolean card = cardNoCheck && secNoCheck;
		
		Boolean paypal = paymentInfoInput.getText().matches("^(.+)@(.+)$");
						
		if (paypal) {								
			payOptionMsg.setVisible(false);
			securityCodeMsg.setVisible(false);
			paymentInfoInput.setVisible(false);
			secCodeInput.setVisible(false);
			submitPaymentBtn.setVisible(false);
			
			try {
				logActivity("purchased", "PayPal");
			} catch (IOException logError) {
				optionResult.setText("Error: " + logError);
			}
			
			optionResult.setText("£" + String.valueOf(totalBasketCost) + " paid using PayPal.");
			basketModel.setRowCount(0);
			basketTable.setModel(basketModel);
			selectedProducts.clear();
		} else if (card){
			payOptionMsg.setVisible(false);
			securityCodeMsg.setVisible(false);
			paymentInfoInput.setVisible(false);
			secCodeInput.setVisible(false);
			submitPaymentBtn.setVisible(false);
			
			
			try {
				logActivity("purchased", "Credit Card");
			} catch (IOException logError) {
				optionResult.setText("Error: " + logError);
			}
			optionResult.setText("£" + String.valueOf(totalBasketCost) + " paid using Credit Card.");
			basketModel.setRowCount(0);
			basketTable.setModel(basketModel);
			selectedProducts.clear();
		} else {
			optionResult.setText("Invalid details, please retry: ");
		}
	}
	
	

	public static void updateStock(String barcode, int amount) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("stock.txt"));
	    BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"));
	    String removeID = barcode;
	    String currentLine;
	    while((currentLine = br.readLine()) != null){
	        String trimmedLine = currentLine.trim();
	        updatedProductDetails.clear();
	        if(trimmedLine.contains(removeID)){
	            ArrayList<String> updatedProductDetails = new ArrayList<String>(Arrays.asList(trimmedLine.split(", ")));
	            int newQuantity = Integer.parseInt(updatedProductDetails.get(6)) - amount;
	            updatedProductDetails.set(6, Integer.toString(newQuantity));
	        	currentLine = String.join(", ", updatedProductDetails);
	        }
	        bw.write(currentLine + System.getProperty("line.separator"));

	    }
	    bw.close();
	    br.close();
	    
	    
	    	
	    File f1 = new File("stock.txt");
	    boolean isDeleted = f1.delete();
	    if (!isDeleted) {
	    	optionResult.setText("Error! Stock file not updated.");
	    }
	    File f2 = new File("temp.txt");
	    File f3 = new File("stock.txt");
	    boolean isRenamed = f2.renameTo(f3);
	    if (!isRenamed) {
	    	optionResult.setText("Error! Stock file not updated.");
	    }
	    
	    
	}
	
	private static void logActivity(String action, String payMethod) throws IOException {
			totalBasketCost = 0;
			String date = String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			
			ArrayList<String> setOfProducts = new ArrayList<String>();
			Set<String> set = new HashSet<>(selectedProducts);
			setOfProducts.addAll(set);
			
			for (String prd: setOfProducts) {	
				int numberOfSameItem = Collections.frequency(selectedProducts, prd);
				String quantityBought = String.valueOf(numberOfSameItem);
				String[] purchaseDetails = prd.split(",");
				String barcode = purchaseDetails[0];
				String price = purchaseDetails[purchaseDetails.length - 1];
				totalBasketCost+= Double.parseDouble(price) * Double.parseDouble(quantityBought.trim());
				Double individualCost = Double.parseDouble(price) * Double.parseDouble(quantityBought.trim());
				
				
				ArrayList<String> activity = new ArrayList<String>();
				activity.add(String.valueOf(customer.userID));
				activity.add(customer.postcode);
				activity.add(barcode);
				activity.add(String.valueOf(individualCost));
				activity.add(quantityBought);
				activity.add(action);
				activity.add(payMethod);
				activity.add(date);
				
				newLogEntries.add(String.join(", ", activity));
				activity.clear();
				
				if (action == "purchased") {
					updateStock(barcode, numberOfSameItem);
				}
			}
			
			try {
				BufferedWriter logBr = new BufferedWriter(new FileWriter("activitylog.txt", true));
				for (String entry: newLogEntries) {
					logBr.append("\n" + entry);
				}
				logBr.close();
			} catch (IOException e) {
				optionResult.setText("Error: " + e);
			}

	}
	
	/**
	 * Logs whatever activity is run by the user concerning the basket in the activitylog text file. 
	 * @param action - the specified activity; either saving for later, purchasing an order or cancelling an order.
	 * @param payMethod - the specified method of payment for products; either paypal, credit card or no method for saving and cancelling.
	 * @throws IOException - in the event that the activity log does not exists, it will output the error.
	 */
}


	



