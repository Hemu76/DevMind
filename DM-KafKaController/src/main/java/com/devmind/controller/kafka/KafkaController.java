package com.devmind.controller.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.devmind.controller.service.kafka.KafkaProducerService;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

	@Autowired
    private final KafkaProducerService producer;

    public KafkaController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String send(@RequestParam String message) {
        producer.sendMessage(message);
        return "Message sent to Kafka";
    }
}