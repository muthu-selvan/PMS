/**
 * 
 */
package com.shop.pms.services;

import java.util.List;

import com.shop.pms.domain.NewStock;
import com.shop.pms.domain.Product;
import com.shop.pms.domain.PurchasedItem;
import com.shop.pms.domain.ReturnResult;

/**
 * @author muthu
 *
 */
public interface ProductManagementService {
	
	public List<Product> getAllProducts();
	
	public ReturnResult deleteAllProducts();
	
	public Product getProductById(int productId);
	
	public ReturnResult updateProduct(Product product);
	
	public ReturnResult addProduct(Product product);
	
	Product getProductByProductId(int productId);
	
	public ReturnResult addPurchasedItem(PurchasedItem purchasedItem);
	
	public PurchasedItem getPurchasedItemByProductId(int productId);
	
	public ReturnResult updatePurchasedItem(PurchasedItem purchasedItem);
	
	public ReturnResult deletePurchasedItem(int productId);

	public List<PurchasedItem> getAllPurchasedItems();
	
	public List<NewStock> getAllStocks();
	
	ReturnResult deleteStockByProductId(int productId);
	
	public ReturnResult addNewStock(NewStock newStock);
	
	public void deleteAllPurchasedItems();
	
	public void deleteAllProductStocks();
}
