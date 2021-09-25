package com.example.cccoffeebackendfromatoz.controller.dto;

import com.example.cccoffeebackendfromatoz.model.product.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {
}
