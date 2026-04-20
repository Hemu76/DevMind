package com.devmind.controller.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.devmind.model.kafka.Order;

@Service
public class KafkaConsumerService {

	@KafkaListener(topics = "test-topic", groupId = "test-group")
	public void consume(Order order) {
		System.out.println("📩 Received Order: " + order.getOrderId() + " - " + order.getProduct());
	}
}