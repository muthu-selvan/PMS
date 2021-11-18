/**
 * 
 */
package com.shop.pms.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.pms.constants.ReturnResultStatus;
import com.shop.pms.domain.NewStock;
import com.shop.pms.domain.Product;
import com.shop.pms.domain.PurchasedItem;
import com.shop.pms.domain.ReturnResult;
import com.shop.pms.services.ProductManagementService;

/**
 * @author muthu
 *
 */
@Component
public class ProductManagementServiceImpl implements ProductManagementService {
	
	@Autowired ProductJPARepository productRepository;
	@Autowired PurchasedItemJPARepository purchasedItemRepository;
	@Autowired NewStockJPARepository newStockRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public ReturnResult addPurchasedItem(final PurchasedItem purchasedItem) {
		ReturnResult result = null;
		
		try {
			purchasedItemRepository.save(purchasedItem);
			result = new ReturnResult(ReturnResultStatus.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ReturnResult(ReturnResultStatus.ERROR, "Internal Server Error");
		}
		return result;
	}

	@Override
	public ReturnResult updatePurchasedItem(final PurchasedItem purchasedItem) {
		ReturnResult result = null;
		try {
			result = deletePurchasedItem(purchasedItem.getProductId());
			if(!result.getStatus().equals(ReturnResultStatus.SUCCESS)) {
				return result;
			}
			result = addPurchasedItem(purchasedItem);
			if(!result.getStatus().equals(ReturnResultStatus.SUCCESS)) {
				return result;
			}
			result = new ReturnResult(ReturnResultStatus.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ReturnResult(ReturnResultStatus.ERROR, "Internal Server Error");
		}
		return result;
	}

	@Override
	public ReturnResult deletePurchasedItem(final int productId) {
		ReturnResult result = null;
		try {
			PurchasedItem purchasedItem = getPurchasedItemByProductId(productId);
			if(purchasedItem == null) {
				result = new ReturnResult(ReturnResultStatus.ERROR, "Product Not Found for Id "+productId);
				return result;
			}
			purchasedItemRepository.delete(purchasedItem);
			result = new ReturnResult(ReturnResultStatus.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ReturnResult(ReturnResultStatus.ERROR, "Internal Server Error");
		}
		return result;
	}

	@Override
	public List<PurchasedItem> getAllPurchasedItems() {
		return purchasedItemRepository.findAll();
	}

	@Override
	public ReturnResult addProduct(final Product product) {
		ReturnResult result = null;
		
		try {
			productRepository.save(product);
			result = new ReturnResult(ReturnResultStatus.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ReturnResult(ReturnResultStatus.ERROR, "Internal Server Error");
		}
		return result;
	}

	@Override
	public Product getProductById(final int productId) {
		final Optional<Product> productOptional = productRepository.findByProductId(productId);
		if(productOptional != null && productOptional.isPresent()) {
			return productOptional.get();
		}
		return null;
	}

	@Override
	public ReturnResult updateProduct(final Product product) {
		ReturnResult result = null;
		try {
			
			final Product productToDelete = getProductByProductId(product.getProductId());
			if(productToDelete != null) {
				productRepository.delete(productToDelete);
			}
			
			productRepository.save(product);
			result = new ReturnResult(ReturnResultStatus.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ReturnResult(ReturnResultStatus.ERROR, "Internal Server Error");
		}
		return result;
	}
	
	@Override
	public Product getProductByProductId(int productId) {
		Optional<Product> productOptional = productRepository.findByProductId(productId);
		if(productOptional != null && productOptional.isPresent()) {
			return productOptional.get();
		}
		return null;
	}

	@Override
	public PurchasedItem getPurchasedItemByProductId(int productId) {
		final Optional<PurchasedItem> purchasedItemOptional = purchasedItemRepository.findByProductId(productId);
		if(purchasedItemOptional != null && purchasedItemOptional.isPresent()) {
			return purchasedItemOptional.get();
		}
		return null;
	}

	@Override
	public List<NewStock> getAllStocks() {
		return newStockRepository.findAll();
	}
	
	@Override
	public ReturnResult deleteStockByProductId(int productId) {
		ReturnResult result = null;
		try {
			
			Optional<NewStock> stockProduct = newStockRepository.findByProductId(productId);
			
			if(stockProduct != null && stockProduct.isPresent()) {
				NewStock product = stockProduct.get();
				newStockRepository.delete(product);
			}
			result = new ReturnResult(ReturnResultStatus.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ReturnResult(ReturnResultStatus.ERROR, "Internal Server Error");
		}
		return result;
	}

	@Override
	public ReturnResult addNewStock(NewStock newStock) {
		ReturnResult result = null;
		try {
			newStockRepository.save(newStock);
			result = new ReturnResult(ReturnResultStatus.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ReturnResult(ReturnResultStatus.ERROR, "Internal Server Error");
		}
		return result;
	}

	@Override
	public void deleteAllPurchasedItems() {
		
		final List<PurchasedItem> purchasedItems = getAllPurchasedItems();
		
		if(purchasedItems != null) {
			purchasedItems.forEach(purchasedItem -> {
				deletePurchasedItem(purchasedItem.getProductId());
			});
		}
	}

	@Override
	public ReturnResult deleteAllProducts() {
		ReturnResult result = null;
		try {
			List<Product> products = getAllProducts();
			if(products != null) {
				for(Product product : products) {
					productRepository.delete(product);
				}
			}
			
			result = new ReturnResult(ReturnResultStatus.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ReturnResult(ReturnResultStatus.ERROR, "Internal Server Error");
		}
		return result;
	}

	@Override
	public void deleteAllProductStocks() {
		final List<NewStock> allProductStocks = newStockRepository.findAll();
		
		if(allProductStocks != null) {
			
			allProductStocks.forEach(stock -> {
				newStockRepository.delete(stock);
			});
		}
		
	}
	
	

}
