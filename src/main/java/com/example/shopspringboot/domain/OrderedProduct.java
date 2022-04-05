package com.example.shopspringboot.domain;

public record OrderedProduct(Product product, int price, int count) {

	public OrderedProduct {
		product = new Product(product.getId(), product.getName(), product.getPrice(), product.getDescription(),
				product.getCategory(), product.getManufacturer());
	}

	public Product product() {
		return new Product(product.getId(), product.getName(), product.getPrice(), product.getDescription(),
				product.getCategory(), product.getManufacturer());
	}
}