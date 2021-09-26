package com.example.cccoffeebackendfromatoz.product.repository;

import com.example.cccoffeebackendfromatoz.product.model.Category;
import com.example.cccoffeebackendfromatoz.product.model.Product;

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
	List<Product> findById(UUID productId);

	// create a product
	Product insert(Product product);

	// update product info
	Optional<Product> updateName(Product product, String newName);
	Optional<Product> updatePrice(Product product, long newPrice);
	Optional<Product> updateDescription(Product product, String newDescription);

	// delete product
	void deleteProduct(UUID productId);
	default void deleteProduct(Product product) {
		deleteProduct(product.getProductId());
	}
	void deleteAll();
}
