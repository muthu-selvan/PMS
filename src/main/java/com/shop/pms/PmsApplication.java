package com.shop.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shop.pms.handler.ProductManagementHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class PmsApplication implements CommandLineRunner {
	
	private static final Map<Integer,String> ACTION_MAP = new HashMap<Integer, String>();

	private static final Map<Integer,Integer> PRODUCT_ID_MAP = new HashMap<Integer, Integer>(); 
	
	static {
		ACTION_MAP.put(1,"PURCHASED_ITEMS");
		ACTION_MAP.put(2,"PRINT_STOCK_LIST");
		ACTION_MAP.put(3,"DELETE_ITEM");
		ACTION_MAP.put(4,"BUY_ITEM");
		ACTION_MAP.put(5,"END_PURCHASE");
		
		PRODUCT_ID_MAP.put(1, 1);
		PRODUCT_ID_MAP.put(2, 2);
		PRODUCT_ID_MAP.put(3, 3);
		PRODUCT_ID_MAP.put(4, 4);
	}
	
	@Autowired
	ProductManagementHandler handler;

	public static void main(String[] args) {
		   SpringApplication app = new SpringApplication(PmsApplication.class);
	        app.setBannerMode(Banner.Mode.OFF);
	        app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		  handler.printProducts();
		  
		  
		  
		  do {
			  
			  System.out.println("Permitted Operations");
			  System.out.println("	1. Purchased Item");
			  System.out.println("	2. Print Stock list");
			  System.out.println("	3. Delete purchased item");
			  System.out.println("	4. Purchase item");
			  System.out.println("	5. End Purchase");
			  System.out.println("	6. Exit");
			  
			  int input = new Scanner(System.in).nextInt();
			  String selectedOption = ACTION_MAP.get(input);
				
			  if(input >= 6) {
				  System.out.println("Exiting !!!..Bye");
				  break;
			  }
			  
			  if(selectedOption == null || selectedOption.isEmpty()) {
				  System.out.println("Invalid option");
			  } else {
				  
				  switch(selectedOption) {
				  	case "PURCHASED_ITEMS":
				  		handler.printPurchasedItems();
				  		break;
				  	case "PRINT_STOCK_LIST":
				  		handler.printStockList();
				  		break;
				  	case "DELETE_ITEM":
				  	{
				  		int productId = getProductIdFromUser();
				  		handler.deletePurchasedItem(productId);
				  	}
				  		break;
				  	case "END_PURCHASE":
				  		handler.endPurchase();
				  		break;
				  	case "BUY_ITEM":
				  		int productId = getProductIdFromUser();
				  		System.out.println("Enter Quantity");
				  		int quantity = new Scanner(System.in).nextInt();
				  		handler.buyItem(productId,quantity);
				  		break;
				  	default:
				  		break;
				  }
			  }
			  
		  } while(true);
	}

	private int getProductIdFromUser() {
		int productId = 0;
		do {
			System.out.println("Enter valid Product:");
			  System.out.println("	1. Soda");
			  System.out.println("	2. Pepsi");
			  System.out.println("	3. Cheese");
			  System.out.println("	4. Pen");
			  
			  int option = new Scanner(System.in).nextInt();
			  
			  productId = PRODUCT_ID_MAP.get(option);
			  
			  if(productId != 0) {
				  break;
			  }
			
		} while(true);
		
		return productId;
	}
}
