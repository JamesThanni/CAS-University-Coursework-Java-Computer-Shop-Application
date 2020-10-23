import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class customer {
	public static int userID;
	public String username;
	public static String postcode; 
	private int houseNum;  
	public String fname;
	private String city; 
	private String role;
	public static String[] columnNames;
	public static ArrayList<String> productDetailsCols;
	public static ArrayList<String> productsAsStrings = new ArrayList<String>();
	
	
	
	public static void userSearch(List<product> data, JTable productTable) {
		try {
			BufferedReader fileBr = new BufferedReader (new FileReader("stock.txt"));
			ArrayList<String> columnNames = new ArrayList<String> (Arrays.asList(product.productDetailNames));
			columnNames.remove(7);
			String[] customerCols = columnNames.toArray(new String[0]);
			
			DefaultTableModel searchResults = new DefaultTableModel(new Object[][] {}, customerCols);		
			Object rowData[]= new Object[10];
			
			   for(int i=0; i< data.size(); i++){
			        rowData[0]= Integer.toString(data.get(i).getBarcode());
			        rowData[1]=data.get(i).getDeviceName();
			        rowData[2]=data.get(i).getDeviceType();
			        rowData[3]=data.get(i).getBrand();
			        rowData[4]=data.get(i).getColour();
			        rowData[5]=data.get(i).getConnectivity();
			        rowData[6]=Integer.toString(data.get(i).getStockQuant());
			        rowData[7]=Double.toString(data.get(i).getMsrp());
			        rowData[8]=data.get(i).getExtraInfo();		     		      
			        searchResults.addRow(rowData);
			   }
			      
			fileBr.close();
			productTable.setModel(searchResults);
		} catch (IOException e) {
			shop.optionResult.setText("Error: " + e);
		}
	}
	/** 
	 * Reads the text file, identifies lines in a file containing the search term as 
	 * a substring, removes the original price from the line and appends the line to an array list to be output. 
	 * @param term - the input search term by the customer.
	 * @return productsAsStrings - the list of products that include the search term as a substring.
	 */
	/**
	 * Fills a specific JTable with entries from the products input into the method.
	 * @param table - a specified Jtable, either the basketTable or productTable.
	 * @param tableContent - products from the text file in a string array; either all the products or search matches.
	 * @param tableTitle - name of the table displayed in GUI.
	 */
	
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		customer.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public int getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		customer.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	
}
