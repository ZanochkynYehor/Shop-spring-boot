package com.example.shopspringboot.domain;

public enum OrderStatus {
	ACCEPTED,
	CONFIRMED,
	FORMED,
	SENT,
	COMPLETED,
	CANCELED;

	@Override
	public String toString() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}