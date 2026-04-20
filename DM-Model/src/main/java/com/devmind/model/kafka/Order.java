package com.devmind.model.kafka;


public class Order {

	private String orderId;
	private String product;

	public Order() {
		super();
	}

	public Order(String orderId, String product) {
		this.orderId = orderId;
		this.product = product;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
}