package com.example.cccoffeebackendfromatoz.repository;

import com.example.cccoffeebackendfromatoz.model.Category;
import com.example.cccoffeebackendfromatoz.model.Product;
import com.example.cccoffeebackendfromatoz.utils.TimeUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

import static com.example.cccoffeebackendfromatoz.repository.CommonSQL.*;
import static com.example.cccoffeebackendfromatoz.utils.UuidUitls.toUUID;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String productTable;

	public ProductRepositoryImpl(DataSource dataSource, String productTable) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.productTable = productTable;
	}

	@Override
	public List<Product> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL.formatted(productTable), productRowMapper);
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return jdbcTemplate.query(SQL_SELECT_BY_CONDITION.formatted(productTable, "category", "category"),
				Collections.singletonMap("category", category.toString()),
				productRowMapper);
	}

	@Override
	public List<Product> findByPrice(long priceMin, long priceMax) {
		return jdbcTemplate.query(SQL_SELECT_BY_RANGE_CONDITION.formatted(productTable, "price", "priceMin", "priceMax"),
				Map.of(
					"priceMin", priceMin,
					"priceMax", priceMax
				),
				productRowMapper);
	}

	@Override
	public List<Product> findByCreatedTime(Timestamp from, Timestamp to) {
		return jdbcTemplate.query(SQL_SELECT_BY_RANGE_CONDITION.formatted(productTable, "created_at", "from", "to"),
				Map.of(
						"from", from,
						"to", to
				),
				productRowMapper);
	}

	@Override
	public List<Product> findByName(String productName) {
		return jdbcTemplate.query(SQL_SELECT_BY_CONDITION.formatted(productTable, "product_name", "productName"),
				Collections.singletonMap("productName", productName),
				productRowMapper);
	}

	@Override
	public List<Product> findById(UUID productId) {
		return jdbcTemplate.query(SQL_SELECT_BY_ID.formatted(productTable, "product_id", "productId"),
				Collections.singletonMap("productId", productId.toString().getBytes()),
				productRowMapper);
	}

	@Override
	public Product insert(Product product) {
		int affectedRecordsNumber = jdbcTemplate.update(SQL_INSERT_PRODUCT.formatted(productTable), toParamMap(product));

		if (affectedRecordsNumber != 1) {
			throw new RuntimeException("Something went wrong during inserting a new product");
		}

		return product;
	}

	@Override
	public void deleteProduct(UUID productId) {
		jdbcTemplate.update(SQL_DELETE_BY_ID.formatted(productTable, "product_id", "productId"),
				Collections.singletonMap("productId", productId.toString().getBytes()));
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(SQL_DELETE_ALL.formatted(productTable), Collections.emptyMap());
	}

	private final RowMapper<Product> productRowMapper = (resultSet, i) -> {
		UUID productId = toUUID(resultSet.getBytes("product_id"));
		String productName = resultSet.getString("product_name");
		String category = resultSet.getString("category");
		long price = resultSet.getLong("price");
		String description = resultSet.getString("description");
		Timestamp createdAt = resultSet.getTimestamp("created_at");
		Timestamp updatedAt = resultSet.getTimestamp("updated_at");
		return new Product.Builder(productId, TimeUtils.toLocalDateTime(createdAt))
				.productName(productName)
				.category(Category.valueOf(category))
				.price(price)
				.description(description)
				.updatedAt(TimeUtils.toLocalDateTime(updatedAt))
				.build();
	};

	private Map<String, Object> toParamMap(Product product) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("productId", product.getProductId().toString().getBytes());
		paramMap.put("productName", product.getProductName());
		paramMap.put("category", product.getCategory().toString());
		paramMap.put("price", product.getPrice());
		paramMap.put("description", product.getDescription());
		paramMap.put("createdAt", product.getCreatedAt());
		paramMap.put("updatedAt", product.getLastModifiedAt());
		return paramMap;
	}
}
