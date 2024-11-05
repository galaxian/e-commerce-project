package com.example.order.domain;

public enum OrderStatus {
	PENDING,
	AWAITING_SHIPMENT,
	SHIPPED,
	DELIVERED,
	CANCELLED;

	public boolean isCancelable() {
		return this == PENDING || this == AWAITING_SHIPMENT;
	}
}
