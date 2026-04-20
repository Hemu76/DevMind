package com.devmind.model.kafka;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private String orderId;
	@JsonProperty
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