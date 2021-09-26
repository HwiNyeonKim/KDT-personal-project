package com.example.cccoffeebackendfromatoz.order.model;

import com.example.cccoffeebackendfromatoz.product.model.Category;

import java.util.UUID;

public record OrderItem(
	UUID productId,
	Category category,
	long price,
	int quantity
) { }
