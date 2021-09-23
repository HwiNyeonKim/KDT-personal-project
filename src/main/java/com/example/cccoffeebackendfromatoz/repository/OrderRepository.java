package com.example.cccoffeebackendfromatoz.repository;

import com.example.cccoffeebackendfromatoz.model.Order;
import com.example.cccoffeebackendfromatoz.model.OrderStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface OrderRepository {
	// find order(s) from DB
	List<Order> findAll();
	List<Order> findById(UUID orderId);
	List<Order> findByEmail(String email);
	List<Order> findByAddress(String address);
	List<Order> findByCreatedTime(Timestamp from, Timestamp to);
	List<Order> findByOrderStatus(OrderStatus orderStatus);

	// create an order
	Order insert(Order order);

	// delete an order
	void deleteOrder(UUID orderId);
	default void deleteOrder(Order order) {
		deleteOrder(order.getOrderId());
	}
	void deleteAll();
}
