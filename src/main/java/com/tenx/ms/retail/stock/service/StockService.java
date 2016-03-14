package com.tenx.ms.retail.stock.service;

import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.StockDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private StockRepository stockRepository;

    public boolean isAvailableStock(StockDTO stock) {
        //TODO: Implement batch processing
        return stockRepository.findOneByStoreIdAndProductId(stock.getStoreId(), stock.getProductId()).get().getCount() >= stock.getCount();
    }

    public void updateStock(StockDTO stock) {
        stockRepository.save(toStockEntity(stock));
    }

    public void increaseCount(StockDTO stock) {
        int updatedRecords = stockRepository.increaseCount(stock.getStoreId(), stock.getProductId(), stock.getCount());
        if (updatedRecords <=0) {
            LOGGER.error("increaseCount updated zero records for storeId: {} and productId: {}", stock.getStoreId(), stock.getProductId());
            throw new NoSuchElementException("Stock can't be found");
        }
    }

    private StockEntity toStockEntity(StockDTO stock) {
        return new StockEntity()
            .setProductId(stock.getProductId())
            .setStoreId(stock.getStoreId())
            .setCount(stock.getCount());
    }
}
