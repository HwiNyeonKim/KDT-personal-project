package com.example.cccoffeebackendfromatoz.order.repository;

import com.example.cccoffeebackendfromatoz.order.model.Order;
import com.example.cccoffeebackendfromatoz.order.model.OrderStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
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

	// update order status
	Optional<Order> updateStatus(Order changedOrder, OrderStatus orderStatus);
	// TODO: 주문 정보 변경 기능 추가

	// delete an order
	void deleteOrder(UUID orderId);
	default void deleteOrder(Order order) {
		deleteOrder(order.getOrderId());
	}
	void deleteAll();
}
