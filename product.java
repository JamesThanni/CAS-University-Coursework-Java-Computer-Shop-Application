import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class product implements Serializable, Comparable<Number>{
	public int barcode;
	public String deviceType; 
	public String typeCategory; 
	public String brand;
	public String colour; 
	public String connectivity; 
	public int stockQuantity; 
	public double rrp;
	public double msrp; 
	public String extraInfo;
	public static String[] productDetailNames = {"Barcode", "Device", "Category", "Brand", "Colour", 
				"Connectivity", "Quantity", "RRP (£)", "MSRP (£)", "Desc" };
	private static List<product> productsAsObjects= new LinkedList<>();
	public static ArrayList<String> productsAsStrings = new ArrayList<String>();
	public static String[] columnNames;
	public static ArrayList<String> productDetailsCols;
	
	
		
		
		
	
	
		public product(int barcode, String deviceName, String deviceType, String brand, String colour,
				String connectivity, int stockQuant, double rrp, double msrp, String extraInfo) {
			this.barcode = barcode;
			this.deviceType = deviceName;
			this.typeCategory = deviceType;
			this.brand = brand;
			this.colour = colour;
			this.connectivity = connectivity;
			this.stockQuantity = stockQuant;
			this.rrp = rrp;
			this.msrp = msrp;
			this.extraInfo = extraInfo;
		}
		
		public static List<product>  readStockData(String term) {
			productsAsObjects.clear();
			ArrayList<String> contents = new ArrayList<String>();
			
			try {
				BufferedReader fileBr = new BufferedReader (new FileReader("stock.txt"));
				String stockInfo = fileBr.readLine();
				while (stockInfo != null) {
					if (stockInfo.toLowerCase().contains(term.toLowerCase())) {
						contents.add(stockInfo.toLowerCase());
					}
					stockInfo = fileBr.readLine();
				}
				fileBr.close();
			} catch (IOException e) {
				shop.optionResult.setText("Error: " + e);
			}
			
			for (String s: contents) {
				s.replaceAll("\\s+","");
				ArrayList<String> details = new ArrayList<String>(Arrays.asList(s.split(",")));
				product prd = new product(Integer.parseInt(details.get(0)), details.get(1),  
						details.get(2).trim(),  details.get(3).trim(),  details.get(4).trim(),  details.get(5).trim(), 
						Integer.parseInt((details.get(6).trim())), Double.parseDouble(details.get(7)), 
						Double.parseDouble(details.get(8)),  details.get(9).trim());
				productsAsObjects.add(prd);	
			}
			Collections.sort(productsAsObjects, product.ProductQuantitySort.quantity);
			return productsAsObjects;
		}
		
		
		public static class ProductQuantitySort {
	        public static Comparator<product> quantity = new Comparator<product>() {
	            @Override
	            public int compare(product firstProduct, product secondProduct) {
	                int i = Integer.compare(firstProduct.getStockQuant(), secondProduct.getStockQuant());
	                if (i == 0) {
	                    i = firstProduct.stockQuantity - secondProduct.stockQuantity;
	                }
	                return i;
	            }
	        };
	    }
		/**
		 * Compares the quantity of frequency of products in the selected products list to 
		 * count up the quantity added to basket
		 *
		 */
		
		public int getBarcode() {
			return barcode;
		}


		public void setBarcode(int barcode) {	
			this.barcode = barcode;
		}


		public String getDeviceName() {
			return deviceType;
		}


		public void setDeviceName(String deviceName) {
			this.deviceType = deviceName;
		}


		public String getDeviceType() {
			return typeCategory;
		}


		public void setDeviceType(String deviceType) {
			this.typeCategory = deviceType;
		}


		public String getBrand() {
			return brand;
		}


		public void setBrand(String brand) {
			this.brand = brand;
		}


		public String getColour() {
			return colour;
		}


		public void setColour(String colour) {
			this.colour = colour;
		}


		public String getConnectivity() {
			return connectivity;
		}


		public void setConnectivity(String connectivity) {
			this.connectivity = connectivity;
		}


		public int getStockQuant() {
			return stockQuantity;
		}


		public void setStockQuant(int stockQuant) {
			this.stockQuantity = stockQuant;
		}


		public double getRrp() {
			return rrp;
		}


		public void setRrp(double rrp) {
			this.rrp = rrp;
		}


		public double getMsrp() {
			return msrp;
		}


		public void setMsrp(double msrp) {
			this.msrp = msrp;
		}


		public String getExtraInfo() {
			return extraInfo;
		}


		public void setExtraInfo(String extraInfo) {
			this.extraInfo = extraInfo;
		}


		public String objectText () {
			String output = this.barcode + ", " + this.deviceType + ", " + this.typeCategory + ", " + 	
							this.brand + ", " + this.colour + ", " + this.connectivity + ", " + 
							this.stockQuantity + ", " + this.rrp + ", " + this.msrp + ", " + this.extraInfo;
			return output;
		}


		/**
		 *
		 */
		@Override
		public int compareTo(Number o) {
			// TODO Auto-generated method stub
			return 0;
		}
		
			
 	

}
