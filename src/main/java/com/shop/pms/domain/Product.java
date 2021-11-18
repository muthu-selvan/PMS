package com.shop.pms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.shop.pms.parser.CSVCell;


@Entity
@javax.persistence.Table(name="PRODUCTS")
@IdClass(Product.class)
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PRODUCT_ID", unique = true)
	@CSVCell(label = "Product ID")
	private int productId;
	
	@Column(name = "PRODUCT_NAME")
	@CSVCell(label = "Product Name")
	private String productName;
	
	@Column(name = "QUANTITY")
	@CSVCell(label = "Quantity")
	private int quantity;
	
	@Column(name = "PRICE")
	@CSVCell(label = "Price")
	private int price;
	
	public Product() {}
	
	public Product(int productId, String productName, int quantity, int price) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
	
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}
}
