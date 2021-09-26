package com.example.cccoffeebackendfromatoz.controller.dto;

import com.example.cccoffeebackendfromatoz.product.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {
}
