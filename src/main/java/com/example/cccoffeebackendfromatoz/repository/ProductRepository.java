package com.example.cccoffeebackendfromatoz.repository;

import com.example.cccoffeebackendfromatoz.model.Category;
import com.example.cccoffeebackendfromatoz.model.Product;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
	// find product(s) from DB
	List<Product> findAll();
	List<Product> findByCategory(Category category);
	List<Product> findByPrice(long priceMin, long priceMax);
	List<Product> findByCreatedTime(Timestamp from, Timestamp to);
	List<Product> findByName(String productName);
	Optional<Product> findById(UUID productId);

	// create product
	Product insert(Product product);

	// delete product
	void deleteProduct(UUID productId);
	default void deleteProduct(Product product) {
		deleteProduct(product.getProductId());
	}
	void deleteAll();
}
