package com.example.cccoffeebackendfromatoz.controller.api;

import com.example.cccoffeebackendfromatoz.controller.dto.CreateProductRequest;
import com.example.cccoffeebackendfromatoz.model.product.Category;
import com.example.cccoffeebackendfromatoz.model.product.Product;
import com.example.cccoffeebackendfromatoz.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class ProductRestController {
	private final ProductService service;

	public ProductRestController(ProductService productService) {
		this.service = productService;
	}

	// Get product list to show.
	@GetMapping("/api/v1/products")
	public List<Product> productList(@RequestParam Optional<Category> category,
	                                 @RequestParam Optional<Long> priceMin,
	                                 @RequestParam Optional<Long> priceMax,
	                                 @RequestParam(defaultValue = "1970-01-01") String createdDateFrom,
	                                 @RequestParam(defaultValue = "9999-12-31") String createdDateTo,
	                                 @RequestParam Optional<String> productName,
	                                 @RequestParam Optional<UUID> productId) {
		// search with created date range condition
		Timestamp createdDateTimeFrom = Timestamp.valueOf(createdDateFrom + " 00:00:00");
		Timestamp createdDateTimeTo = Timestamp.valueOf(createdDateTo + " 23:59:59");
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


	// Get a certain product information by product ID
	@GetMapping("/api/v1/products/{productId}")
	public Map<String, String> getCertainProduct(@PathVariable("productId") UUID productId) {
		Map<String, String> result = new HashMap<>();

		List<Product> productData = service.getProductById(productId);
		if (productData.isEmpty()) {
			return result;
		}

		Product product = productData.get(0);
		result.put("productId", product.getProductId().toString());
		result.put("productName", product.getProductName());
		result.put("category", product.getCategory().toString());
		result.put("price", String.valueOf(product.getPrice()));
		result.put("description", product.getDescription());
		result.put("createdAt", product.getCreatedAt().toString());
		result.put("lastModifiedAt", product.getLastModifiedAt().toString());

		return result;
	}

	// Create a product
	@PostMapping("/api/v1/products")
	public Product createProduct(@ModelAttribute("createProductRequest") CreateProductRequest createProductRequest) {
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
}
