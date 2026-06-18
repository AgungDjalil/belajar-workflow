package com.ecommerce.project.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            categoryRepository.deleteAll();
            productRepository.deleteAll();

            Category electronics = new Category(null, "Electronics", List.of());
            Category clothing = new Category(null, "Clothing", List.of());
            Category books = new Category(null, "Books", List.of());
            Category home = new Category(null, "Home & Kitchen", List.of());
            Category sports = new Category(null, "Sports", List.of());

            categoryRepository.saveAll(List.of(electronics, clothing, books, home, sports));

            List<Category> allCategories = categoryRepository.findAll();

            List<Product> products = List.of(
                new Product(null, "Wireless Bluetooth Headphones", "High quality wireless headphones with noise cancellation and 30 hour battery life", "headphones.jpg", 50, 15, 2999.00, 2549.15, electronics),
                new Product(null, "Smartphone 128GB", "Latest smartphone with 6.5 inch display, triple camera, and fast charging", "smartphone.jpg", 30, 10, 12999.00, 11699.10, electronics),
                new Product(null, "Laptop 15 inch", "Powerful laptop with i7 processor, 16GB RAM, 512GB SSD for professionals", "laptop.jpg", 20, 20, 45999.00, 36799.20, electronics),
                new Product(null, "Smart Watch", "Fitness tracking smartwatch with heart rate monitor and GPS", "smartwatch.jpg", 40, 12, 8999.00, 7919.12, electronics),
                new Product(null, "Cotton T-Shirt", "Comfortable 100% cotton t-shirt available in multiple colors and sizes", "tshirt.jpg", 100, 5, 599.00, 569.05, clothing),
                new Product(null, "Jeans Slim Fit", "Premium denim slim fit jeans with stretch comfort", "jeans.jpg", 75, 8, 1899.00, 1747.08, clothing),
                new Product(null, "Running Shoes", "Lightweight running shoes with cushioned sole and breathable mesh", "shoes.jpg", 60, 18, 3499.00, 2869.18, clothing),
                new Product(null, "Java Programming", "Complete guide to Java programming for beginners and advanced developers", "java-book.jpg", 150, 10, 799.00, 719.10, books),
                new Product(null, "Data Structures in C", "Comprehensive textbook on data structures and algorithms with examples", "ds-book.jpg", 100, 15, 649.00, 551.65, books),
                new Product(null, "Non-Stick Cookware Set", "10-piece non-stick cookware set with stainless steel handles", "cookware.jpg", 35, 25, 4999.00, 3749.25, home),
                new Product(null, "Blender 700W", "Powerful 700W blender with 3 speed settings and pulse function", "blender.jpg", 45, 10, 2499.00, 2249.10, home),
                new Product(null, "Yoga Mat", "Anti-slip exercise yoga mat 6mm thick with carrying strap", "yoga-mat.jpg", 80, 5, 899.00, 854.05, sports),
                new Product(null, "Cricket Bat", "English willow cricket bat with full grain face and lightweight design", "cricket-bat.jpg", 25, 12, 3999.00, 3519.12, sports),
                new Product(null, "Wireless Mouse", "Ergonomic wireless mouse with adjustable DPI and silent click", "mouse.jpg", 90, 8, 999.00, 919.08, electronics),
                new Product(null, "Winter Jacket", "Warm winter jacket with waterproof outer shell and fleece lining", "jacket.jpg", 40, 20, 4599.00, 3679.20, clothing)
            );

            productRepository.saveAll(products);

            System.out.println("=======================================");
            System.out.println(" Dummy data loaded successfully!");
            System.out.println(" Categories: " + categoryRepository.count());
            System.out.println(" Products: " + productRepository.count());
            System.out.println("=======================================");
        };
    }
}
