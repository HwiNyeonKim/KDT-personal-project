package com.example.cccoffeebackendfromatoz.model.order;

import com.example.cccoffeebackendfromatoz.model.product.Category;

import java.util.UUID;

public record OrderItem(
	UUID productId,
	Category category,
	long price,
	int quantity
) { }
