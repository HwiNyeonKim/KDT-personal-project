package com.example.cccoffeebackendfromatoz.controller.api;

import com.example.cccoffeebackendfromatoz.controller.dto.CreateOrderRequest;
import com.example.cccoffeebackendfromatoz.model.order.Order;
import com.example.cccoffeebackendfromatoz.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RestController
public class OrderRestController {
	private final OrderService service;

	public OrderRestController(OrderService orderService) {
		this.service = orderService;
	}

	// Get order list to show
	@GetMapping("/api/v1/orders")
	public List<Order> orderList(@RequestParam Optional<String> email,
	                             @RequestParam Optional<String> address,
	                             @RequestParam Optional<String> postcode,
	                             @RequestParam Optional<String> orderStatus,
	                             @RequestParam(defaultValue = "1970-01-01") String createdDateFrom,
	                             @RequestParam(defaultValue = "9999-12-31") String createdDateTo) {
		// search with created date range condition
		Timestamp createdDateTimeFrom = Timestamp.valueOf(createdDateFrom + " 00:00:00");
		Timestamp createdDateTimeTo = Timestamp.valueOf(createdDateTo + " 23:59:59");
		List<Order> orders = service.getOrdersCreatedBetween(createdDateTimeFrom, createdDateTimeTo);

		// search with email
		if (email.isPresent()) {
			orders = orders.stream().filter(order -> order.getEmail().equals(email.get())).toList();
		}

		// search with address
		if (address.isPresent()) {
			orders = orders.stream().filter(order -> order.getAddress().equals(address.get())).toList();
		}

		// search with postcode
		if (postcode.isPresent()) {
			orders = orders.stream().filter(order -> order.getPostcode().equals(postcode.get())).toList();
		}

		// search with order status
		if (orderStatus.isPresent()) {
			orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus.get())).toList();
		}

		return orders;
	}

	// Get a certain order information by order ID
	@GetMapping("/api/v1/orders/{orderId}")
	public List<Order> getCertainOrder(@PathVariable("orderId") UUID orderID) {
		return service.getOrderById(orderID); // empty if no such order exist.
	}

	// Create a new order!
	@PostMapping("/api/v1/orders")
	public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
		return service.createOrder(createOrderRequest.email(),
				createOrderRequest.address(),
				createOrderRequest.postcode(),
				createOrderRequest.orderItems());
	}

	// Cancel an order
	@DeleteMapping("/api/v1/orders/{orderId}")
	public Order deleteOrder(@PathVariable("orderId") UUID orderId) {
		// 주문 상태만 변경. 내역은 유지
		List<Order> foundOrder = service.getOrderById(orderId);
		try {
			Order order = foundOrder.iterator().next();
			return service.cancelOrder(order);
		} catch (NoSuchElementException e) {
			throw new RuntimeException(
					MessageFormat.format("There is no such an order. Check Order ID : {}",
							orderId)
			);
		}
	}

	// TODO: 주문 정보 업데이트 추가 for address and postcode
}
