package com.tenx.ms.retail.product.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.ProductDTO;
import com.tenx.ms.retail.product.service.ProductService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "products", description = "Product Entity Management API")
@RestController("ProductControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/products")
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Add a Product")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of Product details"),
        @ApiResponse(code = 412, message = "Invalid ProductEntity"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated addProduct(@Validated @RequestBody
    ProductDTO Product) {

        LOGGER.debug("Add new Product: {}", Product);

        return new ResourceCreated<>(productService.addProduct(Product));
    }

//TODO:    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @ApiOperation(value = "Finds all Products", authorizations = { @Authorization("ROLE_ADMIN")})
    @ApiOperation(value = "Finds all products")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of products"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(method = RequestMethod.GET)
    public Paginated<ProductDTO> getAllProducts(Pageable pageable) {

        LOGGER.debug("Fetching all products: {}", pageable);

        return productService.getAllProducts(pageable, RestConstants.VERSION_ONE + "/products");
    }

    @ApiOperation(value = "Find Product by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of product"),
        @ApiResponse(code = 404, message = "Product can't be found by id"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(value = {"/{productId:\\d+}"}, method = RequestMethod.GET)
    public ProductDTO getProductById(
        @ApiParam(name = "productId", value = "The id of the product to get its details")
        @PathVariable() long productId) {

        LOGGER.debug("Fetching product by product id: {}", productId);

        return productService.getProductById(productId).get();
    }

    @ApiOperation(value = "Find Product by name")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of Product"),
        @ApiResponse(code = 404, message = "Product can't be found by name"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(value = {"/{productName:.*[a-zA-Z].*}"}, method = RequestMethod.GET)
    public ProductDTO getProductByName(
        @ApiParam(name = "productName", value = "The name of the Product to get its details")
        @PathVariable String productName) {

        LOGGER.debug("Fetching Product by Product name: {}", productName);

        return productService.getProductByName(productName).get();
    }
}
