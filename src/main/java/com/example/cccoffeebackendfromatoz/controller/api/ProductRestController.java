package com.example.cccoffeebackendfromatoz.controller.api;

import com.example.cccoffeebackendfromatoz.controller.dto.CreateProductRequest;
import com.example.cccoffeebackendfromatoz.product.model.Category;
import com.example.cccoffeebackendfromatoz.product.model.Product;
import com.example.cccoffeebackendfromatoz.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class ProductRestController {
	// TODO: Admin 페이지용으로 변경하기

	private final ProductService service;

	public ProductRestController(ProductService productService) {
		this.service = productService;
	}

	// Get product list to show.
	@GetMapping("/api/v1/products")
	public List<Product> productList(@RequestParam Optional<Category> category,
	                                 @RequestParam Optional<Long> priceMin,
	                                 @RequestParam Optional<Long> priceMax,
	                                 @RequestParam Optional<String> productName,
	                                 @RequestParam(defaultValue = "1970-01-01") String createdDateFrom,
	                                 @RequestParam(defaultValue = "9999-12-31") String createdDateTo) {
		// search with created date range condition
		Timestamp createdDateTimeFrom = Timestamp.valueOf(createdDateFrom + " 00:00:00");
		Timestamp createdDateTimeTo = Timestamp.valueOf(createdDateTo + " 23:59:59");
		List<Product> products = service.getProductsCreatedBetween(createdDateTimeFrom, createdDateTimeTo);

		// search with product name
		if (productName.isPresent()) {
			products = products.stream().filter(product -> product.getProductName().equals(productName.get())).toList();
		}

		// search with category condition
		if (category.isPresent()) {
			products = products.stream().filter(product -> product.getCategory().equals(category.get())).toList();
		}

		// search with price range condition
		long minPrice = priceMin.orElse(Long.MIN_VALUE);
		long maxPrice = priceMax.orElse(Long.MAX_VALUE);
		if (minPrice != Long.MIN_VALUE && maxPrice != Long.MAX_VALUE) {
			products = products.stream().filter(product -> minPrice <= product.getPrice() && product.getPrice() <= maxPrice).toList();
		}

		return products;
	}


	// Get a certain product information by product ID
	@GetMapping("/api/v1/products/{productId}")
	public List<Product> getCertainProduct(@PathVariable("productId") UUID productId) {
		return service.getProductById(productId); // empty if no such product exist.
	}

	// Create a product
	@PostMapping("/api/v1/products")
	public Product createProduct(@RequestBody CreateProductRequest createProductRequest) {
		return service.createProduct(createProductRequest.productName(),
				createProductRequest.category(),
				createProductRequest.price(),
				createProductRequest.description());
	}

	// Delete a product
	@DeleteMapping("/api/v1/products/{productId}")
	public void deleteProduct(@PathVariable("productId") UUID productId) {
		service.deleteProduct(productId);
	}

	// Delete all product
	@DeleteMapping("/api/v1/products")
	public void deleteAll() {
		service.deleteAll();
	}
}
