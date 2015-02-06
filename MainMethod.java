import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.lang.Math;

public class MainMethod {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";


	private static final String COLUMN_TAB_DELIMITER = "\t\t";
	private static final int COLUMN_TAB_DELIMITER_SIZE = 8;
	private static final String TABLE_BORDER = "-";

	public static void prettyPrintData(List<Map> data) {
		System.out.println();
		for (Map d : data) {
			for (Object key : d.keySet()) {
				System.out.println(key + ": " + d.get(key));
			}
		}
	}

	public static void prettyPrintDataDBStyle(List<Map> data, String tableName) {
		System.out.println();
		// Assuming all data is clean (same columns)

		// Print Table Name
		if (tableName != null) {
			System.out.println("\t" + ANSI_GREEN + tableName + ANSI_RESET);
			System.out.println();
		}

		
		// Print Columns
		int tableBorderSize = 0;
		Set columns = data.get(0).keySet();
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


		for (Map row : data) {
			for (Object key : row.keySet()) {
				System.out.print(row.get(key) + COLUMN_TAB_DELIMITER);
			}
			System.out.println();
		}


		// Print table lower border
		printTableBorder(TABLE_BORDER, tableBorderSize);
		System.out.println();	
	}

	public static void printTableBorder(String border, int size) {
		for (int index = 0; index < size; index++) {
			System.out.print(border);
		}
	}

	public static void prettyPrintDataDBStyle(List<Map> data) {
		prettyPrintDataDBStyle(data, null);
	}

	private static Map newCustomer(String id, String name) {
		Map customer = new HashMap();
		customer.put("Id", id != null ? id : "null");
		customer.put("Name", name != null ? name : "null");

		return customer;
	}

	public static List<Map> loadCustomers() {
		List<Map> customers = new ArrayList<Map>();
		
		customers.add(newCustomer("cust123", "Joe"));
		customers.add(newCustomer("cust456", "Adam"));
		customers.add(newCustomer("cust789", "Bill"));
		customers.add(newCustomer("cust001", "Allison"));
		customers.add(newCustomer("cust002", "Betty"));

		return customers;
	}

	private static Map newProduct(String id, String name) {
		Map product = new HashMap();
		product.put("Id", id != null ? id : "null");
		product.put("Name", name != null ? name : "null");

		return product;
	}

	public static List<Map> loadProducts() {
		List<Map> products = new ArrayList<Map>();
		
		products.add(newProduct("prod123", "Hammer"));
		products.add(newProduct("prod456", "Laptop"));
		products.add(newProduct("prod789", "Shoe"));
		products.add(newProduct("prod001", "Keyboard"));
		products.add(newProduct("prod002", "Mouse"));

		return products;
	}

	private static Map newPurchase(String id, String customerId, String productId) {
		Map purchase = new HashMap();
		purchase.put("Id", id != null ? id : "null");
		purchase.put("cId", customerId != null ? customerId : "null");
		purchase.put("pId", productId != null ? productId : "null");

		return purchase;
	}

	public static List<Map> loadPurchases() {
		List<Map> purchases = new ArrayList<Map>();

		purchases.add(newPurchase("p001", "cust123", "prod123"));
		purchases.add(newPurchase("p002", "cust456", "prod123"));
		purchases.add(newPurchase("p003", "cust123", "prod456"));
		purchases.add(newPurchase("p004", "cust789", "prod789"));
		purchases.add(newPurchase("p005", "cust001", "prod123"));
		purchases.add(newPurchase("p006", "cust001", "prod002"));
		purchases.add(newPurchase("p007", "cust001", "prod002"));
		purchases.add(newPurchase("p008", "cust001", "prod002"));
		purchases.add(newPurchase("p009", "cust001", "prod002"));
		purchases.add(newPurchase("p010", "cust001", "prod002"));

		return purchases;		
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
			// [
			// 	{ 
			// 		"Id": "cust123",
			// 		"Name": "Joe"
			// 	},
			// 	{ 
			// 		"Id": "cust456",
			// 		"Name": "Adam"
			// 	},
			// 	{ 
			// 		"Id": "cust789",
			// 		"Name": "Bill"
			// 	}
			// ]

			// products
			// [
			// 	{
			//		"Id": "prod123",
			// 		"Name": "Hammer"
			// 	},
			// 	{
			//		"Id": "prod456",
			// 		"Name": "Laptop"
			// 	},
			// 	{
			//		"Id": "prod789",
			// 		"Name": "Shoe"
			// 	},
			// ]

			// purchases
			// [
			// 	{
			// 		"Id": "purch001",
			//		"cId": "cust123",
			//		"pId": "prod123"
			// 	},
			// 	{
			// 		"Id": "purch002",
			//		"cId": "cust123",
			//		"pId": "prod456"
			// 	},
			// 	{
			// 		"Id": "purch003",
			//		"cId": "cust456",
			//		"pId": "prod123"
			// 	}
			// ]

			List<Map> customers = loadCustomers();
			prettyPrintDataDBStyle(customers, "Customers");

			List<Map> products = loadProducts();
			prettyPrintDataDBStyle(products, "Products");

			List<Map> purchases = loadPurchases();
			prettyPrintDataDBStyle(purchases, "Purchases");

			// Assume data is loaded


			// Grab Customer Ids who bought product P
			List<String> CustomersIdsWhoBoughtProductP = new ArrayList<String>();
			for (Map purchase : purchases) {
				// Grab only where product p
				if (purchase.get("pId").equals(p)) {
					CustomersIdsWhoBoughtProductP.add((String)purchase.get("cId"));
				}
			}

			// Print Customers who bought product P
			System.out.println();
			System.out.println();
			System.out.println(ANSI_YELLOW + "Customers who bought product P:" + ANSI_RESET);
			for (Map customer : customers) {
				if (CustomersIdsWhoBoughtProductP.contains((String)customer.get("Id"))) {
					System.out.println(
						String.format("%s (%s)", 
							customer.get("Name"), 
							customer.get("Id")));
				}
			}


			// Grab all products purchased by Customers who bought product P
			Map productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount = new HashMap();
			for (Map purchase : purchases) {
				// Grab only where customers who bought product p... grab the products
				if (CustomersIdsWhoBoughtProductP.contains(purchase.get("cId"))) {
					if (!productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount.containsKey(purchase.get("pId"))) {
						// If doesn't have product added yet, add product to map with count = 1
						productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount.put(purchase.get("pId"), 1);
					} else {
						// Else if product is added to map, then increment the count
						productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount
							.put(
								purchase.get("pId"), 
								(int)productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount
									.get(purchase.get("pId")) + 1);
					}
				}
			}

			System.out.println(ANSI_RED + "productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount: " + productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount + ANSI_RESET);


			System.out.println();
			System.out.println();
			System.out.println(ANSI_YELLOW + "Products bought by Customers who bought Product: " + p + ANSI_RESET);
			for (Map product : products) {
				if (productsOfPurchasesInvolvingCustomersWhoBoughtProductPAndCount.containsKey(product.get("Id"))) {
					System.out.println(
						String.format("%s (%s)", 
							product.get("Name"), 
							product.get("Id")));
				}
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
				for (Map product : products) {
					if (orderedRecommendations.get(index).equals(product.get("Id"))) {
						System.out.println(String.format("%s (%s)", product.get("Name"), product.get("Id")));
					}
				}
			}
		}

		System.out.println();
	}
}