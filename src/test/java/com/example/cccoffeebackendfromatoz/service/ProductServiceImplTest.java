package com.example.cccoffeebackendfromatoz.service;

import com.example.cccoffeebackendfromatoz.model.Category;
import com.example.cccoffeebackendfromatoz.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {
	// TODO: Test용 DB 별도로 구성하고, 해당 DB에서 테스트 로직이 돌도록 수정

	private final ProductService service;

	private static List<Product> testProducts;

	@Autowired
	ProductServiceImplTest(ProductService productService) {
		service = productService;
	}

	@BeforeAll
	static void setup() {
		testProducts = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			Product testProduct = new Product.Builder(UUID.randomUUID(), LocalDateTime.now())
					.category(Category.COFFEE_BEAN_PACKAGE)
					.productName("Test Product - 00" + i)
					.price(i * 1000)
					.description("Product for test")
					.updatedAt(LocalDateTime.now())
					.build();
			testProducts.add(testProduct);
		}
	}

	@AfterEach
	void clean() {
		service.deleteAll();
	}

	@Test
	@DisplayName("전체 Product 조회")
	void getAllProduct() {
		Product product1 = service.createProduct("Test Product 1", Category.COFFEE_BEAN_PACKAGE, 1, "Test Product 1");
		Product product2 = service.createProduct("Test Product 2", Category.COFFEE_BEAN_PACKAGE, 22, "Test Product 2");
		Product product3 = service.createProduct("Test Product 3", Category.COFFEE_BEAN_PACKAGE, 333, "Test Product 3");

		assertThat(service.getAllProduct()).contains(product1, product2, product3);
	}

	@Test
	void getProductsByCategory() {
		Product product1 = service.createProduct("Test Product 1", Category.COFFEE_BEAN_PACKAGE, 1, "Test Product 1");
		Product product2 = service.createProduct("Test Product 2", Category.COFFEE_BEAN_PACKAGE, 22, "Test Product 2");
		Product product3 = service.createProduct("Test Product 3", Category.UNCATEGORIZED, 333, "Test Product 3");

		assertThat(service.getProductsByCategory(Category.COFFEE_BEAN_PACKAGE)).contains(product1, product2);
		assertThat(service.getProductsByCategory(Category.COFFEE_BEAN_PACKAGE)).doesNotContain(product3);
		assertThat(service.getProductsByCategory(Category.UNCATEGORIZED)).contains(product3);
		assertThat(service.getProductsByCategory(Category.UNCATEGORIZED)).doesNotContain(product1, product2);
	}

	@Test
	void getProductsPriceBetween() {
		Product product1 = service.createProduct("Test Product 1", Category.COFFEE_BEAN_PACKAGE, 1000, "Test Product 1");

		assertThat(service.getProductsPriceBetween(500, 2000)).contains(product1);
		assertThat(service.getProductsPriceBetween(2000, 5000)).doesNotContain(product1);
	}

	@Test
	void getProductsCreatedBetween() {
		Product product1 = service.createProduct("Test Product 1", Category.COFFEE_BEAN_PACKAGE, 1, "Test Product 1");

		assertThat(service.getProductsCreatedBetween(Timestamp.valueOf("1999-01-01 00:00:00"), Timestamp.valueOf("9999-12-31 00:00:00"))).contains(product1);
		assertThat(service.getProductsCreatedBetween(Timestamp.valueOf("1999-01-01 00:00:00"),
				Timestamp.valueOf("2000-01-01 00:00:00"))).doesNotContain(product1);
	}

	@Test
	void getProductsByName() {
		Product product1 = service.createProduct("Test Product 1", Category.COFFEE_BEAN_PACKAGE, 1, "Test Product 1");

		assertThat(service.getProductsByName("Test Product 1")).contains(product1);
		assertThat(service.getProductsByName("Test Product 2")).doesNotContain(product1);
	}

	@Test
	void getProductById() {
		Product product1 = service.createProduct("Test Product 1", Category.COFFEE_BEAN_PACKAGE, 1, "Test Product 1");

		assertThat(service.getProductById(product1.getProductId())).contains(product1);
		assertThat(service.getProductById(UUID.randomUUID())).isEmpty();
	}
}