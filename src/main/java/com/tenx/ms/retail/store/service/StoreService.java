package com.tenx.ms.retail.store.service;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public Optional<Store> getStoreById(Long storeId) {
        return storeRepository.findOneByStoreId(storeId).map(this::toStoreDTO);
    }

    public Optional<Store> getStoreByName(String storeName) {
        return storeRepository.findOneByName(storeName).map(this::toStoreDTO);
    }

    public Paginated<Store> getAllStores(Pageable pageable, String baseLinkPath) {
        Page<StoreEntity> page = storeRepository.findAll(pageable);

        List<Store> stores = page.getContent().stream()
            .map(this::toStoreDTO)
            .collect(Collectors.toList());

        return Paginated.wrap(page, stores, baseLinkPath);
    }

    public Long addStore(Store store) {
        //TODO: throw exception if something wrong
        return storeRepository.save(toStoreEntity(store)).getStoreId();
    }

    private Store toStoreDTO(StoreEntity store) {
        return new Store().setStoreId(store.getStoreId()).
            setName(store.getName());
    }

    private StoreEntity toStoreEntity(Store store) {
        return new StoreEntity().setStoreId(store.getStoreId()).
            setName(store.getName());
    }
}
