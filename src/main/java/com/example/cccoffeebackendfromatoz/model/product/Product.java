package com.example.cccoffeebackendfromatoz.model.product;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Product {
	private final UUID productId;
	private String productName;
	private Category category;
	private long price;
	private String description;
	private final LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;

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
		this.lastModifiedAt = builder.updatedAt;
	}

	private void setLastModifiedAt(LocalDateTime updatedAt) {
		this.lastModifiedAt = updatedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return productId.equals(product.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}
}
