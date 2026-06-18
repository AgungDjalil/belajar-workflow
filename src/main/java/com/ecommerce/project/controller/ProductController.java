package com.ecommerce.project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product, 
                                                  @PathVariable Long categoryId) {
        ProductDTO productDTO = productService.addProduct(categoryId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }                                 

    @GetMapping("public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam(value = "pageNumber", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER )int pageNumber,
                                                          @RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int pageSize,
                                                          @RequestParam(value = "sortOrder", required = false, defaultValue = AppConstant.DEFAULT_SORT_ORDER) String sortOrder,
                                                          @RequestParam(value = "sortBy", required = false, defaultValue = AppConstant.DEFAULT_SORT_BY_PRODUCT) String sortBy) {
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    } 

    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> searchProductsByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.searchProductByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("admin/product/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId,
                                                    @RequestBody ProductDTO product) {
        ProductDTO productDTO = productService.updateProduct(productId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @DeleteMapping("admin/product/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PatchMapping("admin/product/image/{productId}")
    public ResponseEntity<ProductDTO> updateImage(@PathVariable Long productId,
                                                  @RequestParam("image")MultipartFile imageFile) throws IOException {
        ProductDTO productDTO = productService.updateProductImage(productId, imageFile);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
