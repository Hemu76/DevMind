package com.devmind.controller.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.devmind.controller.service.kafka.KafkaProducerService;
import com.devmind.model.kafka.Order;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

	@Autowired
    private final KafkaProducerService producer;

    public KafkaController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String send(@RequestBody Order message) {
        producer.sendMessage(message);
        return "Message sent to Kafka";
    }
}