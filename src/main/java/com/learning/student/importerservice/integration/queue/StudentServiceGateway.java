package com.learning.student.importerservice.integration.queue;

import com.learning.student.importerservice.integration.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StudentServiceGateway {

    @Autowired
    private AmqpTemplate jsonRabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public void convertAndSend(Student student) {
        jsonRabbitTemplate.convertAndSend(exchange, routingkey, student);
        log.info("Sending message: " + student);
    }
}
