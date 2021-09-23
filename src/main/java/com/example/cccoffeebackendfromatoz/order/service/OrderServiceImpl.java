package com.example.cccoffeebackendfromatoz.order.service;

import com.example.cccoffeebackendfromatoz.model.order.Order;
import com.example.cccoffeebackendfromatoz.model.order.OrderItem;
import com.example.cccoffeebackendfromatoz.model.order.OrderStatus;
import com.example.cccoffeebackendfromatoz.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
	private final OrderRepository repository;

	public OrderServiceImpl(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public Order createOrder(String email, String address, String postcode, List<OrderItem> orderItems) {
		Order order = new Order.Builder(UUID.randomUUID(), email, orderItems, LocalDateTime.now())
				.orderStatus(OrderStatus.ACCEPTED)
				.address(address)
				.postcode(postcode)
				.lastModifiedAt(LocalDateTime.now())
				.build();
		return repository.insert(order);
	}

	@Override
	public List<Order> getAllOrder() {
		return repository.findAll();
	}

	@Override
	public List<Order> getOrderById(UUID orderId) {
		return repository.findById(orderId);
	}

	@Override
	public List<Order> getOrdersByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public List<Order> getOrdersByAddress(String address) {
		return repository.findByAddress(address);
	}

	@Override
	public List<Order> getOrdersByOrderStatus(OrderStatus orderStatus) {
		return repository.findByOrderStatus(orderStatus);
	}

	@Override
	public List<Order> getOrdersCreatedBetween(Timestamp from, Timestamp to) {
		return repository.findByCreatedTime(from, to);
	}

	@Override
	public void deleteOrder(UUID orderId) {
		repository.deleteOrder(orderId);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
}
