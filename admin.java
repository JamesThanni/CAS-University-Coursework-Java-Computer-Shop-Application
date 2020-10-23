import java.io.*;
import java.util.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class admin {
	public static ArrayList<String> productsAsStrings = new ArrayList<String>();
	public static ArrayList<String> inputProductDetails = new ArrayList<String>();
	
	public static void adminSearch(List<product> data, JTable productTable) {
		String[] columnNames = product.productDetailNames;
		
		DefaultTableModel searchResults = new DefaultTableModel(new Object[][] {}, columnNames);		
		Object rowData[]= new Object[10];
		
		   for(int i=0; i< data.size(); i++){
		        rowData[0]= Integer.toString(data.get(i).getBarcode());
		        rowData[1]=data.get(i).getDeviceName();
		        rowData[2]=data.get(i).getDeviceType();
		        rowData[3]=data.get(i).getBrand();
		        rowData[4]=data.get(i).getColour();
		        rowData[5]=data.get(i).getConnectivity();
		        rowData[6]=Integer.toString(data.get(i).getStockQuant());
		        rowData[7]=Double.toString(data.get(i).getRrp());
		        rowData[8]=Double.toString(data.get(i).getMsrp());
		        rowData[9]=data.get(i).getExtraInfo();		     		      
		        searchResults.addRow(rowData);
		   }
		productTable.setModel(searchResults);
		
	}
	/** 
	 * Reads the text file, identifies lines in a file containing the search term as 
	 * a substring and appends the line to an array list to be output. 
	 * @param term - the input search term by the admin.
	 * @return productsAsStrings - the list of products that include the search term as a substring.
	 */
	/**
	 * Fills a specific JTable with entries from the products input into the method.
	 * @param table - a specified Jtable, either the basketTable or productTable.
	 * @param tableContent - products from the text file in a string array; either all the products or search matches.
	 * @param tableTitle - name of the table displayed in GUI.
	 */
	
	public static void addNewProduct(ArrayList<String> newDetailsInput) throws IOException {
		String newProduct = String.join(", ", newDetailsInput);
		
		try {
			BufferedWriter newItemBr = new BufferedWriter(new FileWriter("stock.txt", true));			
			newItemBr.append("\n" + newProduct);
			newItemBr.close();
		} catch (IOException e) {
			shop.optionResult.setText("Error: " + e);
		}
	}
	/**
	 * Iterates through the list of product details entered by the admin and outputs the new 
	 * product to the stock text file as a new line.
	 * @param newDetailsInput - the list of individual product details input by the admin.
	 * @throws IOException - outputs an error if there is an issue in accessing the stock file.
	 */

}
