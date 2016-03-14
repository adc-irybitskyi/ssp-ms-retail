package com.tenx.ms.retail.stock.repository;

import com.tenx.ms.retail.stock.domain.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
    Optional<StockEntity> findOneByStoreIdAndProductId(final Long storeId, final Long productId);

    @Modifying
    @Query("update StockEntity set available_count = available_count + ?3 where store_id = ?1 and product_id = ?2")
    int increaseCount(final Long storeId, final Long productId, final Long count);
}
