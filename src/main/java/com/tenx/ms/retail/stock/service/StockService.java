package com.tenx.ms.retail.stock.service;

import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.StockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public void updateStock(StockDTO stock) {
        stockRepository.save(toStockEntity(stock));
    }

    private StockEntity toStockEntity(StockDTO stock) {
        return new StockEntity()
            .setProductId(stock.getProductId())
            .setStoreId(stock.getStoreId())
            .setCount(stock.getCount());
    }
}
