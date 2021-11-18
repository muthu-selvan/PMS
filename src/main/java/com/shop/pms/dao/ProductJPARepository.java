package com.shop.pms.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.pms.domain.Product;

@Repository
public interface ProductJPARepository extends JpaRepository<Product, Integer> {
	
	public Optional<Product> findByProductId(int productId);

}