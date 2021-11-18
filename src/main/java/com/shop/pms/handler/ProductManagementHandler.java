/**
 * 
 */
package com.shop.pms.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.pms.domain.NewStock;
import com.shop.pms.domain.Product;
import com.shop.pms.domain.PurchasedItem;
import com.shop.pms.services.ProductManagementService;

/**
 * @author muthu
 *
 */

@Service
public class ProductManagementHandler {
	
	@Autowired
	ProductManagementService productManagementService;
	
	/**
	 * Method to print all products.
	 */
	public void printProducts() {
		final List<Product> products = productManagementService.getAllProducts();
		if(products != null && !products.isEmpty()) {
			System.out.println("*** All Products: ");
			products.forEach(product -> {
				System.out.println(product.getProductName());
			});
		} else {
			System.out.println("*** Product list is Empty");
		}
	}
	
	/**
	 * Method to print all purchased items.
	 */
	public void printPurchasedItems() {
		final List<PurchasedItem> purchasedItems = productManagementService.getAllPurchasedItems();
		if(purchasedItems != null && !purchasedItems.isEmpty()) {
			System.out.println("*** Purchased Products");
			for(final PurchasedItem purchasedProduct : purchasedItems) {
				System.out.println(purchasedProduct.getProductName());
			}
		} else {
			System.out.println("*** Purchased Product is Empty");
		}
	}
	
	/**
	 * Method to print stock list.
	 */
	public void printStockList() {
		final List<NewStock> stockList = productManagementService.getAllStocks();
		if(stockList != null && !stockList.isEmpty()) {
			System.out.println("*** New Stock list");
			for(final NewStock stock : stockList) {
				System.out.println(stock.getProductName());
			}
		} else {
			System.out.println("*** New Stock is Empty");
		}
	}
	
	/**
	 *  Method to delete the purchased product.
	 *  The following actions are performed.
	 *    1. Delete the item from PURCHASED_ITEMS table.
	 *    2. Updating the quantity of the product in PRODUCTS which maintains Product details.
	 *    3. Since the purchased item is removed. The stock list is also removed from NEW_STOCKS table.
	 */
	public void deletePurchasedItem(final int productId) {
		
		PurchasedItem purchasedItem = productManagementService.getPurchasedItemByProductId(productId);
		
		if(purchasedItem == null) {
			System.out.println("*** The Item to be removed is not purchased yet..");
		} else {
			// Deleting purchased item
			productManagementService.deletePurchasedItem(productId);
			
			final Product product = productManagementService.getProductById(productId);
			if(product != null) {
				// Updating the quantity of the product
				int purchasedQuantity = purchasedItem.getQuantity();
				product.setQuantity(product.getQuantity()+purchasedQuantity);
				productManagementService.updateProduct(product);
				
				// Since the purchased item is removed. The stock list is also removed in NEW_STOCKS
				productManagementService.deleteStockByProductId(productId);
			}
		}
	}
	
	/**
	 * Method to buy a Item.
	 * The following actions are performed.
	 *    1. Check the product which has available quantity. If no error message will be printed.
	 *    2. Then update the quantity for the product in PRODUCTS table.
	 *    3. Check the item already purchased or not. If already purchased update the quantity of product in PURCHASED_ITEMS table else create new one.
	 *    4. If available quantity is 0 then put item into NEW_STOCKS table.
	 */
	public void buyItem(final int productId, final int quantity) {
		final Product product = productManagementService.getProductById(productId);
		
		if(product != null) {
			
			System.out.println("*** Selected Item : "+product.getProductName());
			int productQuantity = product.getQuantity();
			
			if(productQuantity <= 0) {
				System.out.println("*** Item not available");
			} else if(quantity > productQuantity) {
				System.out.println("*** Quantity is higher than Available");
			} else {
				productQuantity-= quantity;
				
				if(productQuantity <= 0) {
					productQuantity = 0;
				}
				product.setQuantity(productQuantity);
				productManagementService.updateProduct(product);
				
				PurchasedItem purchasedItem = productManagementService.getPurchasedItemByProductId(productId);
				
				if(purchasedItem == null) {
					purchasedItem = new PurchasedItem(productId, product.getProductName(), 1);
					purchasedItem.setPrice(product.getPrice());
					productManagementService.addPurchasedItem(purchasedItem);
					System.out.println("*** New Purchased Item added.");
				} else {
					purchasedItem.setQuantity(purchasedItem.getQuantity()+1);
					productManagementService.updatePurchasedItem(purchasedItem);
					System.out.println("*** Purchased Item updated.");
				}
				
				if(productQuantity == 0) {
					NewStock newStock = new NewStock(productId, product.getProductName(), 0);
					productManagementService.addNewStock(newStock);
				}
			}
		}
	}

	/**
	 * Method to end Purchase,
	 * The following actions are performed.
	 * 		1. Price of all purchased items will be printed.
	 *      2. Clear entries in PURCHASED_ITEMS table.
	 */
	public void endPurchase() {
		int amount = 0;
		final List<PurchasedItem> purchasedItems = productManagementService.getAllPurchasedItems();
		if(purchasedItems != null && !purchasedItems.isEmpty()) {
			System.out.println("*** Purchased Items:");
			for(PurchasedItem item : purchasedItems) {
				System.out.println(item.getProductName());
				amount += item.getPrice();
			}
		}
		
		System.out.println("*** Price: $"+amount);
		productManagementService.deleteAllPurchasedItems();
	}
}
