import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.lang.Math;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Exception;

public class MainMethodV2 {

	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";


	private static final String COLUMN_TAB_DELIMITER = "\t\t";
	private static final int COLUMN_TAB_DELIMITER_SIZE = 8;
	private static final String TABLE_BORDER = "-";
	private static final String DATA_ROW_DELIMITER = ";;";
	private static final int ID_COLUMN = 0;

	private static void prettyPrintData(Map<String, Map> data) {
		System.out.println();
		for (Map d : data.values()) {
			for (Object key : d.keySet()) {
				System.out.println(key + ": " + d.get(key));
			}
		}
	}

	private static void prettyPrintDataDBStyle(Map<String, Map> data, String tableName) {
		System.out.println();
		// Assuming all data is clean (same columns)

		// Print Table Name
		if (tableName != null) {
			System.out.println("\t" + ANSI_GREEN + tableName + ANSI_RESET);
			System.out.println();
		}

		
		// Print Columns
		int tableBorderSize = 0;
		Set columns = ((Map)(data.values().toArray()[0])).keySet();
		String columnsString = "";
		Object[] columnsArray = columns.toArray();
		for (int index = 0; index < columnsArray.length; index++) {
			columnsString += ANSI_CYAN + columnsArray[index] + ANSI_RESET + COLUMN_TAB_DELIMITER;

			if (index < columnsArray.length - 1) { 
				// Gathering table-border-size data for any column except the last
				tableBorderSize += 
				(
					Math.ceil(
						(    
							1.0 *                                     // Allow double math to keep track of important decimal numbers
							columnsArray[index].toString().length()   // \ # of tab delimiters that fit
							/ COLUMN_TAB_DELIMITER_SIZE               // /
						)
					)
					* COLUMN_TAB_DELIMITER_SIZE                       // Add number of tab sizes that fit
				)
				+ COLUMN_TAB_DELIMITER_SIZE;                          // Add trailing tab delimiter size
			} else {
				// Gathering table-border-size data for the last column
				tableBorderSize += columnsArray[index].toString().length();
			}
			
		}
		System.out.println(columnsString);

		
		// Print table upper border
		printTableBorder(TABLE_BORDER, tableBorderSize);
		System.out.println();


		for (Map row : data.values()) {
			for (Object key : row.keySet()) {
				System.out.print(row.get(key) + COLUMN_TAB_DELIMITER);
			}
			System.out.println();
		}


		// Print table lower border
		printTableBorder(TABLE_BORDER, tableBorderSize);
		System.out.println();	
	}

	private static void printTableBorder(String border, int size) {
		for (int index = 0; index < size; index++) {
			System.out.print(border);
		}
	}

	private static void prettyPrintDataDBStyle(Map<String, Map> data) {
		prettyPrintDataDBStyle(data, null);
	}

	private static Map newCustomer(String id, String name) {
		Map customer = new HashMap();
		customer.put("Id", id != null ? id : "null");
		customer.put("Name", name != null ? name : "null");

		return customer;
	}

	private static Map<String, Map> loadCustomers() {
		Map customers = new HashMap<Object, Map>();
		
		customers.put("cust123", newCustomer("cust123", "Joe"));
		customers.put("cust456", newCustomer("cust456", "Adam"));
		customers.put("cust789", newCustomer("cust789", "Bill"));
		customers.put("cust001", newCustomer("cust001", "Allison"));
		customers.put("cust002", newCustomer("cust002", "Betty"));

		return customers;
	}

	private static Map newProduct(String id, String name) {
		Map product = new HashMap<Object, Map>();
		product.put("Id", id != null ? id : "null");
		product.put("Name", name != null ? name : "null");

		return product;
	}

	private static Map<String, Map> loadProducts() {
		Map products = new HashMap<Object, Map>();
		
		products.put("prod123", newProduct("prod123", "Hammer"));
		products.put("prod456", newProduct("prod456", "Laptop"));
		products.put("prod789", newProduct("prod789", "Shoe"));
		products.put("prod001", newProduct("prod001", "Keyboard"));
		products.put("prod002", newProduct("prod002", "Mouse"));

		return products;
	}

	private static Map newPurchase(String id, String customerId, String productId) {
		Map purchase = new HashMap();
		purchase.put("Id", id != null ? id : "null");
		purchase.put("cId", customerId != null ? customerId : "null");
		purchase.put("pId", productId != null ? productId : "null");

		return purchase;
	}

	private static Map<String, Map> loadPurchases() {
		Map purchases = new HashMap();

		purchases.put("p001", newPurchase("p001", "cust123", "prod123"));
		purchases.put("p002", newPurchase("p002", "cust456", "prod123"));
		purchases.put("p003", newPurchase("p003", "cust123", "prod456"));
		purchases.put("p004", newPurchase("p004", "cust789", "prod789"));
		purchases.put("p005", newPurchase("p005", "cust001", "prod123"));
		purchases.put("p006", newPurchase("p006", "cust001", "prod002"));
		purchases.put("p007", newPurchase("p007", "cust001", "prod002"));
		purchases.put("p008", newPurchase("p008", "cust001", "prod002"));
		purchases.put("p009", newPurchase("p009", "cust001", "prod002"));
		purchases.put("p010", newPurchase("p010", "cust001", "prod002"));

		return purchases;		
	}

	private static void log(Object message) {
		System.out.println(ANSI_PURPLE + message + ANSI_RESET);
	}

	private static Map<String, Map> loadDataFromFile(String filename) {
		Map<String, Map> returnData = null;

		File fileInfo = new File(filename);

		if (fileInfo != null) {
			if (fileInfo != null && fileInfo.exists()) {
				try {
					BufferedReader fileReader = new BufferedReader(new FileReader(filename));

					if (fileReader != null) {
						List<String> columns = new ArrayList<String>();
						String columnsLine = fileReader.readLine();
						if (columnsLine != null) {
							for (String column : columnsLine.split(DATA_ROW_DELIMITER)) {
								columns.add(column);
							}

							if (columns.size() > 0) {
								returnData = new HashMap<String, Map>();

								
								// Starting from the second line (first line is column headers),
								// load all the data into memory
								for (int row = 1; row < lines.size(); row++) {

									// Split the row data by ";;"
									String[] rowValues = lines.get(row).split(DATA_ROW_DELIMITER);

									if (rowValues != null && rowValues.length > 0) {
										Map obj = new HashMap();
										for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
											obj.put(columns.get(columnIndex), rowValues[columnIndex]);
										}
										returnData.put(
											(String)obj.get(columns.get(ID_COLUMN)),  // Id
											obj);                                     // Object Map
									}
								}

								return returnData;
							}
						}
					}
				} catch(Exception e) {
					System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
					return returnData;
				}
			}
		}

		return returnData;
	}

	public static void main(String[] args) {

		if (                             // Fail if...
				args == null         ||  // args is null or...
				args.length == 0     ||  // args is empty or...
				args[0] == null      ||  // first args is null or...
				args[0].length() == 0    // first args is empty.
			) {
			System.out.println();
			System.out.println(ANSI_RED + "No Product ID provided via command-line args. Please try again." + ANSI_RESET);
			
		} else {
			System.out.println(ANSI_YELLOW + "Starting Program: MainMethod" + ANSI_RESET);

			String p = args[0];


			// Assumptive map storage

			// customers
			// {
			// 	"cust123":
			// 	{ 
			// 		"Id": "cust123",
			// 		"Name": "Joe"
			// 	},
			// 	"cust456":
			// 	{ 
			// 		"Id": "cust456",
			// 		"Name": "Adam"
			// 	},
			// 	"cust789":
			// 	{ 
			// 		"Id": "cust789",
			// 		"Name": "Bill"
			// 	}
			// }

			// products
			// {
			// 	"prod123":
			// 	{
			//		"Id": "prod123",
			// 		"Name": "Hammer"
			// 	},
			// 	"prod456":
			// 	{
			//		"Id": "prod456",
			// 		"Name": "Laptop"
			// 	},
			// 	"prod789":
			// 	{
			//		"Id": "prod789",
			// 		"Name": "Shoe"
			// 	},
			// }

			// purchases
			// {
			// 	"purch001":
			// 	{
			// 		"Id": "purch001",
			//		"cId": "cust123",
			//		"pId": "prod123"
			// 	},
			// 	"purch002":
			// 	{
			// 		"Id": "purch002",
			//		"cId": "cust123",
			//		"pId": "prod456"
			// 	},
			// 	"purch003":
			// 	{
			// 		"Id": "purch003",
			//		"cId": "cust456",
			//		"pId": "prod123"
			// 	}
			// }

			Map<String, Map> customers = loadDataFromFile("customers.data"); //loadCustomers();
			System.out.println(ANSI_GREEN + "DEBUG: " + customers);
			prettyPrintDataDBStyle(customers, "Customers");

			Map<String, Map> products = loadProducts();
			prettyPrintDataDBStyle(products, "Products");

			Map<String, Map> purchases = loadPurchases();
			prettyPrintDataDBStyle(purchases, "Purchases");

			// Assume data is loaded


			// Grab Customer Ids who bought product P
			Map<String, Map> CustomersIdsWhoBoughtProductP = new HashMap<String, Map>();
			for (Map purchase : purchases.values()) {
				// Grab only where product p
				if (purchase.get("pId").equals(p)) {
					CustomersIdsWhoBoughtProductP.put((String)purchase.get("cId"), customers.get(purchase.get("cId")));
				}
			}

			// Print Customers who bought product P
			System.out.println();
			System.out.println();
			System.out.println(ANSI_YELLOW + "Customers who bought product P:" + ANSI_RESET);
			for (Map customer : CustomersIdsWhoBoughtProductP.values()) {
				System.out.println(
					String.format("%s (%s)", 
						customer.get("Name"), 
						customer.get("Id")));
			}


			// Grab all products purchased by Customers who bought product P
			Map<String, Integer> productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount = new HashMap<String, Integer>();
			for (Map purchase : purchases.values()) {

				// Grab only where customers who bought product p... grab the products
				if (CustomersIdsWhoBoughtProductP.containsKey(purchase.get("cId"))) {
					if (!productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount.containsKey(purchase.get("pId"))) {
						// If doesn't have product added yet, add product to map with count = 1
						productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount.put((String)purchase.get("pId"), 1);
					} else {
						// Else if product is added to map, then increment the count
						productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount
							.put(
								(String)purchase.get("pId"), 
								(int)productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount
									.get(purchase.get("pId")) + 1);
					}
				}
			}

			// DEBUG: System.out.println(ANSI_RED + "productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount: " + productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount + ANSI_RESET);


			System.out.println();
			System.out.println();
			System.out.println(ANSI_YELLOW + "Products bought by Customers who bought Product: " + p + ANSI_RESET);
			for (Object productId : productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount.keySet()) {
				System.out.println(
					String.format("%s (%s)", 
						((Map)products.get(productId)).get("Name"), 
						((Map)products.get(productId)).get("Id")));
			}


			// Order products of purchases involving customers who bought product P to create recommendations
			// Using insertion sort
			List orderedRecommendations = new ArrayList();
			for (Object keyToAdd : productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount.keySet()) {
				boolean isAdded = false;

				for (int orderedIndex = 0; orderedIndex < orderedRecommendations.size(); orderedIndex++) {
					int keyToAddCount = (int)productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount
							.get(keyToAdd);
					int recommendationCompareCount = (int)productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount
							.get(orderedRecommendations.get(orderedIndex));
					if (keyToAddCount
							>
						recommendationCompareCount) {
						orderedRecommendations.add(orderedIndex, keyToAdd);
						isAdded = true;
						break;
					}
				}

				if (!isAdded) {
					orderedRecommendations.add(keyToAdd);
				}
			}


			// Print recommendations in order of popularity
			System.out.println();
			System.out.println();
			System.out.println(String.format(ANSI_YELLOW + "Based on our data of other Customers who purchased product %s, %nwe recommend these products (in order of popularity):" + ANSI_RESET, p));
			for (int index = 0; index < orderedRecommendations.size(); index++) {
				for (Map product : products.values()) {
					if (orderedRecommendations.get(index).equals(product.get("Id"))) {
						System.out.println(String.format("%s (%s)", product.get("Name"), product.get("Id")));
					}
				}
			}
		}

		System.out.println();
	}
}