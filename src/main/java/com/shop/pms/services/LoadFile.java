/**
 * 
 */
package com.shop.pms.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import com.shop.pms.constants.PMSConstants;
import com.shop.pms.domain.Product;
import com.shop.pms.parser.CSVParser;

/**
 * @author muthu
 *
 */

@Repository
@ComponentScan(basePackages = {"com.shop.pms.*"})
@PropertySource("classpath:application.properties")
public class LoadFile {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ProductManagementService productManagementService;

	
	@Autowired
	private Environment env;
	
	public LoadFile() {}
	
	@PostConstruct
	private void init() {
		boolean isRestore = false;
		
		try {
			isRestore = Boolean.parseBoolean(env.getProperty(PMSConstants.RESTORE.getValue()));
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Restore value : "+isRestore);
			
			if(!isRestore) {
				return;
			}
			
//			Product
			Resource resource = resourceLoader.getResource("classpath:"+"assets/csv/Product.csv");
			List<Product> productList = CSVParser.readCSVFile(Product.class, resource.getFile().getAbsolutePath());
			System.out.println("******** Product List *********");
			
			productManagementService.deleteAllProducts();
			productManagementService.deleteAllProductStocks();
			productManagementService.deleteAllPurchasedItems();
			
			productList.forEach(product -> 
			{
				productManagementService.addProduct(product);
				});
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
