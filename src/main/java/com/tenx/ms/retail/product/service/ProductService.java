package com.tenx.ms.retail.product.service;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.rest.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Optional<ProductDTO> getProductById(Long ProductId) {
        return productRepository.findOneByProductId(ProductId).map(this::toProductDTO);
    }

    public Optional<ProductDTO> getProductByName(String ProductName) {
        return productRepository.findOneByName(ProductName).map(this::toProductDTO);
    }

    public Paginated<ProductDTO> getAllProducts(Pageable pageable, String baseLinkPath) {
        Page<ProductEntity> page = productRepository.findAll(pageable);

        List<ProductDTO> Products = page.getContent().stream()
            .map(this::toProductDTO)
            .collect(Collectors.toList());

        return Paginated.wrap(page, Products, baseLinkPath);
    }

    public Long addProduct(ProductDTO Product) {
        return productRepository.save(toProductEntity(Product)).getProductId();
    }

    private ProductDTO toProductDTO(ProductEntity Product) {
        return new ProductDTO().setProductId(Product.getProductId()).
            setName(Product.getName());
    }

    private ProductEntity toProductEntity(ProductDTO product) {
        return new ProductEntity()
            .setProductId(product.getProductId())
            .setStoreId(product.getStoreId())
            .setName(product.getName())
            .setDescription(product.getDescription())
            .setSku(product.getSku())
            .setPrice(product.getPrice());
    }
}
