package com.example.cccoffeebackendfromatoz.controller.api;

import com.example.cccoffeebackendfromatoz.model.product.Category;
import com.example.cccoffeebackendfromatoz.model.product.Product;
import com.example.cccoffeebackendfromatoz.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductRestController {
	private final ProductService service;

	public ProductRestController(ProductService productService) {
		this.service = productService;
	}

	@GetMapping("/api/v1/products")
	public List<Product> productList(@RequestParam Optional<Category> category,
	                                 @RequestParam Optional<Long> priceMin,
	                                 @RequestParam Optional<Long> priceMax,
	                                 @RequestParam(defaultValue = "1970-01-01") String createdDateFrom,
	                                 @RequestParam(defaultValue = "9999-12-31") String createdDateTo,
	                                 @RequestParam Optional<String> productName,
	                                 @RequestParam Optional<UUID> productId) {
		// search with created date range condition
		Timestamp createdDateTimeFrom = Timestamp.valueOf(createdDateFrom + "00:00:00");
		Timestamp createdDateTimeTo = Timestamp.valueOf(createdDateTo + "23:59:59");
		List<Product> products = service.getProductsCreatedBetween(createdDateTimeFrom, createdDateTimeTo);

		// search with product name
		if (productName.isPresent()) {
			products = products.stream().filter(product -> product.getProductName().equals(productName.get())).toList();
		}

		// search with product ID
		if (productId.isPresent()) {
			products = products.stream().filter(product -> product.getProductId().equals(productId.get())).toList();
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
}
