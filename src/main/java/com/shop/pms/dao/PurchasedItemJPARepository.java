/**
 * 
 */
package com.shop.pms.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.pms.domain.PurchasedItem;

/**
 * @author muthu
 *
 */

@Repository
public interface PurchasedItemJPARepository extends JpaRepository<PurchasedItem, Integer> {

	public Optional<PurchasedItem> findByProductId(int productId);
}
