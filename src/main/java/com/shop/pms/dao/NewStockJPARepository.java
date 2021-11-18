/**
 * 
 */
package com.shop.pms.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.pms.domain.NewStock;
import com.shop.pms.domain.PurchasedItem;

/**
 * @author muthu
 *
 */

@Repository
public interface NewStockJPARepository extends JpaRepository<NewStock, Integer> {
	public Optional<NewStock> findByProductId(int productId);
}
