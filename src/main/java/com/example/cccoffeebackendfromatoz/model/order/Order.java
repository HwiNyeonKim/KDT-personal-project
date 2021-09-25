package com.example.cccoffeebackendfromatoz.model.order;

import com.example.cccoffeebackendfromatoz.utils.ValidationUtils;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Order {
	private final UUID orderId;
	private final String email;
	private final List<OrderItem> orderItems;
	private final LocalDateTime createdAt;
	private String address;
	private String postcode;
	private OrderStatus orderStatus;
	private LocalDateTime lastModifiedAt;

	public static class Builder {
		private final UUID orderId;
		private final String email;
		private final List<OrderItem> orderItems;
		private final LocalDateTime createdAt;

		private String address;
		private String postcode;
		private OrderStatus orderStatus;
		private LocalDateTime lastModifiedAt;

		public Builder(UUID orderId, String email, List<OrderItem> orderItems, LocalDateTime createdAt) {
			if (!ValidationUtils.validateEmail(email)) {
				throw new RuntimeException("Invalid email");
			}
			this.orderId = orderId;
			this.email = email;
			this.orderItems = orderItems;
			this.createdAt = createdAt;
		}

		public Builder address(String address) {
			this.address = address;
			return this;
		}

		public Builder postcode(String postcode) {
			this.postcode = postcode;
			return this;
		}

		public Builder orderStatus(OrderStatus orderStatus) {
			this.orderStatus = orderStatus;
			return this;
		}

		public Builder lastModifiedAt(LocalDateTime lastModifiedAt) {
			this.lastModifiedAt = lastModifiedAt;
			return this;
		}

		public Order build() {
			return new Order(this);
		}
	}

	public Order(Builder builder) {
		this.orderId = builder.orderId;
		this.email = builder.email;
		this.address = builder.address;
		this.postcode = builder.postcode;
		this.orderItems = builder.orderItems;
		this.orderStatus = builder.orderStatus;
		this.createdAt = builder.createdAt;
		this.lastModifiedAt = builder.lastModifiedAt;
	}

	// TODO: 어떻게 set method를 없애면서도 역할을 유지할 수 있을까?
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return orderId.equals(order.orderId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}
}
