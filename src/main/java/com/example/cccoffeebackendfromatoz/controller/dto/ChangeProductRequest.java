package com.example.cccoffeebackendfromatoz.controller.dto;

import java.util.Optional;

public record ChangeProductRequest(
		Optional<String> name,
		Optional<Long> price,
		Optional<String> description
) {
}
