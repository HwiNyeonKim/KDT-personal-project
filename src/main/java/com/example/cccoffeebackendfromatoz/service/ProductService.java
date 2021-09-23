package com.example.cccoffeebackendfromatoz.service;

import com.example.cccoffeebackendfromatoz.model.Category;
import com.example.cccoffeebackendfromatoz.model.Product;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
	// find product(s)
	List<Product> getAllProduct();
	List<Product> getProductsByCategory(Category category);
	List<Product> getProductsPriceBetween(long priceMin, long priceMax);
	default List<Product> getProductsPriceLowerThan(long priceMax) {
		return getProductsPriceBetween(0, priceMax);
	}
	default List<Product> getProductsPriceHigherThan(long priceMin) {
		return getProductsPriceBetween(priceMin, Long.MAX_VALUE);
	}
	List<Product> getProductsCreatedBetween(Timestamp from, Timestamp to);
	default List<Product> getProductsCreatedAfter(Timestamp from) {
		return getProductsCreatedBetween(from, Timestamp.valueOf(LocalDateTime.MAX));
	}
	default List<Product> getProductsCreatedBefore(Timestamp to) {
		return getProductsCreatedBetween(Timestamp.valueOf(LocalDateTime.MIN), to);
	}
	List<Product> getProductsByName(String productName);
	Optional<Product> getProductById(UUID productId);

	// create a product
	Product createProduct(String productName, Category category, long price);
	default Product createProduct(String productName, Category category, long price, String description) {
		Product product = createProduct(productName, category, price);
		product.setDescription(description);
		return product;
	}

}
