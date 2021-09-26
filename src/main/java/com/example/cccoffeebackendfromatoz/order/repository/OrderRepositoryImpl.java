package com.example.cccoffeebackendfromatoz.order.repository;

import com.example.cccoffeebackendfromatoz.model.product.Category;
import com.example.cccoffeebackendfromatoz.model.order.Order;
import com.example.cccoffeebackendfromatoz.model.order.OrderItem;
import com.example.cccoffeebackendfromatoz.model.order.OrderStatus;
import com.example.cccoffeebackendfromatoz.utils.TimeUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.cccoffeebackendfromatoz.utils.CommonSQL.*;
import static com.example.cccoffeebackendfromatoz.utils.UuidUitls.toUUID;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	private NamedParameterJdbcTemplate jdbcTemplate;
	private final String orderTable;
	private String orderItemTable;

	public OrderRepositoryImpl(DataSource dataSource, String orderTable, String orderItemTable) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.orderTable = orderTable;
		this.orderItemTable = orderItemTable;
	}

	@Override
	public List<Order> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL.formatted(orderTable), orderRowMapper);
	}

	@Override
	public List<Order> findById(UUID orderId) {
		return jdbcTemplate.query(SQL_SELECT_BY_ID.formatted(orderTable, "order_id", "orderId"),
				Collections.singletonMap("orderId", orderId.toString().getBytes()),
				orderRowMapper);
	}

	@Override
	public List<Order> findByEmail(String email) {
		return jdbcTemplate.query(SQL_SELECT_BY_CONDITION.formatted(orderTable, "email", "email"),
				Collections.singletonMap("email", email),
				orderRowMapper);
	}

	@Override
	public List<Order> findByAddress(String address) {
		return jdbcTemplate.query(SQL_SELECT_BY_CONDITION.formatted(orderTable, "address", "address"),
				Collections.singletonMap("address", address),
				orderRowMapper);
	}

	@Override
	public List<Order> findByCreatedTime(Timestamp from, Timestamp to) {
		return jdbcTemplate.query(SQL_SELECT_BY_RANGE_CONDITION.formatted(orderTable, "created_at", "from", "to"),
				Map.of(
					"from", from,
					"to", to
				),
				orderRowMapper);
	}

	@Override
	public List<Order> findByOrderStatus(OrderStatus orderStatus) {
		return jdbcTemplate.query(SQL_SELECT_BY_CONDITION.formatted(orderTable, "order_status", "orderStatus"),
				Collections.singletonMap("orderStatus", orderStatus),
				orderRowMapper);
	}

	@Override
	public Order insert(Order order) {
		int affectedRowNumber = jdbcTemplate.update(SQL_INSERT_ORDER.formatted(orderTable), toOrderParamMap(order));

		if (affectedRowNumber != 1) {
			throw new RuntimeException("Something went wrong during inserting a new order");
		}

		return order;
	}

	@Override
	public Order updateStatus(Order changedOrder) {
		jdbcTemplate.update(SQL_UPDATE_BY_ID.formatted(orderTable, "order_status", "orderStatus", "order_id", "orderId"),
				Map.of(
						"orderStatus", changedOrder.getOrderStatus().toString(),
						"orderId", changedOrder.getOrderId().toString().getBytes()
				));
		return findById(changedOrder.getOrderId()).stream().findAny().get();
	}

	@Override
	public void deleteOrder(UUID orderId) {
		jdbcTemplate.update(SQL_DELETE_BY_ID.formatted(orderTable, "order_id", "orderId"),
				Collections.singletonMap("orderId", orderId.toString().getBytes()));
		jdbcTemplate.update(SQL_DELETE_BY_ID.formatted(orderItemTable, "order_id", "orderId"),
				Collections.singletonMap("orderId", orderId.toString().getBytes()));
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(SQL_DELETE_ALL.formatted(orderItemTable), Collections.emptyMap());
		jdbcTemplate.update(SQL_DELETE_ALL.formatted(orderTable), Collections.emptyMap());
	}

	private RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) -> {
		UUID productId = toUUID(resultSet.getBytes("product_id"));
		Category category = Category.valueOf(resultSet.getString("category"));
		long price = resultSet.getLong("price");
		int quantity = resultSet.getInt("quantity");
		return new OrderItem(productId, category, price, quantity);
	};

	private RowMapper<Order> orderRowMapper = (resultSet, i) -> {
		UUID orderId = toUUID(resultSet.getBytes("order_id"));
		String email = resultSet.getString("email");
		String address = resultSet.getString("address");
		List<OrderItem> orderItems = jdbcTemplate.query(SQL_SELECT_BY_CONDITION.formatted(orderItemTable, "order_id", "orderId"),
				Collections.singletonMap("orderId", orderId.toString().getBytes()),
				orderItemRowMapper);
		String postcode = resultSet.getString("postcode");
		OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
		LocalDateTime createdAt = TimeUtils.toLocalDateTime(resultSet.getTimestamp("created_at"));
		LocalDateTime lastModifiedAt = TimeUtils.toLocalDateTime(resultSet.getTimestamp("updated_at"));
		return new Order.Builder(orderId, email, orderItems, createdAt)
				.address(address)
				.postcode(postcode)
				.orderStatus(orderStatus)
				.lastModifiedAt(lastModifiedAt)
				.build();
	};

	private Map<String, Object> toOrderParamMap(Order order) {
		HashMap<String, Object> orderParamMap = new HashMap<>();
		orderParamMap.put("orderId", order.getOrderId().toString().getBytes());
		orderParamMap.put("email", order.getEmail());
		orderParamMap.put("address", order.getAddress());
		orderParamMap.put("postcode", order.getPostcode());
		orderParamMap.put("orderStatus", order.getOrderStatus().toString());
		orderParamMap.put("createdAt", order.getCreatedAt());
		orderParamMap.put("lastModifiedAt", order.getLastModifiedAt());
		return orderParamMap;
	}
}
