package com.devmind.controller.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.devmind.model.kafka.Order;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Order message) {
        kafkaTemplate.send("test-topic", message);
    }
}