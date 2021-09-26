package com.example.cccoffeebackendfromatoz.product.service;

import com.example.cccoffeebackendfromatoz.product.model.Category;
import com.example.cccoffeebackendfromatoz.product.model.Product;
import com.example.cccoffeebackendfromatoz.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository repository;

	public ProductServiceImpl(ProductRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Product> getAllProduct() {
		return repository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(Category category) {
		return repository.findByCategory(category);
	}

	@Override
	public List<Product> getProductsPriceBetween(long priceMin, long priceMax) {
		return repository.findByPrice(priceMin, priceMax);
	}

	@Override
	public List<Product> getProductsCreatedBetween(Timestamp from, Timestamp to) {
		return repository.findByCreatedTime(from, to);
	}

	@Override
	public List<Product> getProductsByName(String productName) {
		return repository.findByName(productName);
	}

	@Override
	public List<Product> getProductById(UUID productId) {
		return repository.findById(productId);
	}

	@Override
	public Product createProduct(String productName, Category category, long price) {
		Product product = new Product.Builder(UUID.randomUUID(), LocalDateTime.now())
				.productName(productName)
				.category(category)
				.price(price)
				.build();
		return repository.insert(product);
	}

	@Override
	public Product createProduct(String productName, Category category, long price, String description) {
		Product product = new Product.Builder(UUID.randomUUID(), LocalDateTime.now())
				.productName(productName)
				.category(category)
				.price(price)
				.description(description)
				.build();
		return repository.insert(product);
	}

	@Override
	public Optional<Product> changeName(Product product, String newName) {
		return repository.updateName(product, newName);
	}

	@Override
	public Optional<Product> changePrice(Product product, long newPrice) {
		return repository.updatePrice(product, newPrice);
	}

	@Override
	public Optional<Product> changeDescription(Product product, String newDescription) {
		return repository.updateDescription(product, newDescription);
	}

	@Override
	public void deleteProduct(UUID productId) {
		repository.deleteProduct(productId);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
}
