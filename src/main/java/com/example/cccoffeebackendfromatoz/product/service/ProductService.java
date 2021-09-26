package com.example.cccoffeebackendfromatoz.product.service;

import com.example.cccoffeebackendfromatoz.product.model.Category;
import com.example.cccoffeebackendfromatoz.product.model.Product;

import java.sql.Timestamp;
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
		return getProductsCreatedBetween(from, Timestamp.valueOf("9999-12-31 23:59:59"));
	}
	default List<Product> getProductsCreatedBefore(Timestamp to) {
		return getProductsCreatedBetween(Timestamp.valueOf("1970-01-01 00:00:00"), to);
	}
	List<Product> getProductsByName(String productName);
	List<Product> getProductById(UUID productId); // Optional vs List, 일단 편의상 List

	// create a product
	Product createProduct(String productName, Category category, long price);
	Product createProduct(String productName, Category category, long price, String description);

	// change product info
	Optional<Product> changeName(Product product, String newName);
	default Optional<Product> changeName(UUID productId, String newName) {
		try {
			Product product = getProductById(productId).get(0);
			return changeName(product, newName);
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException("No Such product exists. Please check the product ID. (Given: %s)".formatted(productId.toString()));
		}
	}
	Optional<Product> changePrice(Product product, long newPrice);
	default Optional<Product> changePrice(UUID productId, long newPrice) {
		try {
			Product product = getProductById(productId).get(0);
			return changePrice(product, newPrice);
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException("No Such product exists. Please check the product ID. (Given: %s)".formatted(productId.toString()));
		}
	}
	Optional<Product> changeDescription(Product product, String newDescription);
	default Optional<Product> changeDescription(UUID productId, String newDescription) {
		try {
			Product product = getProductById(productId).get(0);
			return changeDescription(product, newDescription);
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException("No Such product exists. Please check the product ID. (Given: %s)".formatted(productId.toString()));
		}
	}

	// delete a product
	default void deleteProduct(Product product) {
		deleteProduct(product.getProductId());
	}
	void deleteProduct(UUID productId);
	void deleteAll();
}
