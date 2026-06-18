package com.ecommerce.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("project.image.path")
    private String imagePath;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                     .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        
        boolean isProductPresent = true;
        List<Product> products = category.getProduct();
        for (Product value: products) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductPresent = false;
                break;
            }
        }
        
        if(!isProductPresent) 
            throw new APIException("Product already exists!!");

        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("deafult.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() - 
                    ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageDetail = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageDetail);
        
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOs = products.stream()
            .map(product -> modelMapper.map(product, ProductDTO.class))
            .toList();

        return new ProductResponse(
            productDTOs,
            productPage.getPageable().getPageNumber(),
            productPage.getSize(),
            productPage.getTotalElements(),
            productPage.getTotalPages()
        );
    }    

    @Override
    public ProductResponse searchProductByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("category", "categoryID", categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        return productResponse;
    }

    @Override 
    public ProductResponse searchProductByKeyword(String keyword) {
        List<Product> products = productRepository.findProductByProductNameLikeIgnoreCase("%" + keyword + "%");
        List<ProductDTO> productDtos = products.stream()
            .map(product -> modelMapper.map(product, ProductDTO.class))
            .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productBody) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("product", "productID", productId));
        modelMapper.map(productBody, product);
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("product", "productID", productId));
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productRepository.delete(product);
        return productDTO;
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile imageFile) throws IOException {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("product", "productID", productId));

        // file upload
        String filename = fileService.uploadImage(imagePath, imageFile);

        product.setImage(filename);
        Product updateProduct = productRepository.save(product);
        return modelMapper.map(updateProduct, ProductDTO.class);
    }
}
