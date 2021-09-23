package com.example.cccoffeebackendfromatoz.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Product {
	private final UUID productId;
	private String productName;
	private Category category;
	private long price;
	private String description;
	private final LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static class Builder {
		private final UUID productId;
		private final LocalDateTime createdAt;

		private String productName = "";
		private Category category = Category.UNCATEGORIZED;
		private long price = -1;
		private String description = "";
		private LocalDateTime updatedAt = LocalDateTime.now();

		public Builder(UUID productId, LocalDateTime createdAt) {
			this.productId = productId;
			this.createdAt = createdAt;
		}

		public Builder productName(String productName) {
			this.productName = productName;
			return this;
		}

		public Builder category(Category category) {
			this.category = category;
			return this;
		}

		public Builder price(long price) {
			this.price = price;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder updatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public Product build() {
			return new Product(this);
		}
	}

	public Product(Builder builder) {
		this.productId = builder.productId;
		this.productName = builder.productName;
		this.category = builder.category;
		this.price = builder.price;
		this.description = builder.description;
		this.createdAt = builder.createdAt;
		this.updatedAt = builder.updatedAt;
	}

	public void setProductName(String productName) {
		this.productName = productName;
		setUpdatedAt(LocalDateTime.now());
	}

	public void setCategory(Category category) {
		this.category = category;
		setUpdatedAt(LocalDateTime.now());
	}

	public void setPrice(long price) {
		this.price = price;
		setUpdatedAt(LocalDateTime.now());
	}

	public void setDescription(String description) {
		this.description = description;
		setUpdatedAt(LocalDateTime.now());
	}

	private void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
