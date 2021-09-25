package com.example.cccoffeebackendfromatoz.controller.dto;

import com.example.cccoffeebackendfromatoz.model.order.OrderItem;

import java.util.List;

public record CreateOrderRequest(String email, String address, String postcode, List<OrderItem> orderItems) {
}
