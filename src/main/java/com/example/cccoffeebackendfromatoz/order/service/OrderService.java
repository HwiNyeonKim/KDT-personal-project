package com.example.cccoffeebackendfromatoz.order.service;

import com.example.cccoffeebackendfromatoz.order.model.Order;
import com.example.cccoffeebackendfromatoz.order.model.OrderItem;
import com.example.cccoffeebackendfromatoz.order.model.OrderStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface OrderService {
	// create an order
	Order createOrder(String email, String address, String postcode, List<OrderItem> orderItems);

	// find order(s)
	List<Order> getAllOrder();
	List<Order> getOrderById(UUID orderId);
	List<Order> getOrdersByEmail(String email);
	List<Order> getOrdersByAddress(String address);
	List<Order> getOrdersByOrderStatus(OrderStatus status);
	List<Order> getOrdersCreatedBetween(Timestamp from, Timestamp to);
	default List<Order> getOrdersCreatedBefore(Timestamp to) {
		return getOrdersCreatedBetween(Timestamp.valueOf("1970-01-01 00:00:00"), to);
	}
	default List<Order> getOrdersCreatedAfter(Timestamp from) {
		return getOrdersCreatedBetween(from, Timestamp.valueOf("9999-12-31 23:59:59"));
	}

	// delete order (Cancellation)
	default void deleteOrder(Order order) {
		deleteOrder(order.getOrderId());
	}
	void deleteOrder(UUID orderId);
	void deleteAll();

	// cancel order
	Order cancelOrder(Order order);
}
