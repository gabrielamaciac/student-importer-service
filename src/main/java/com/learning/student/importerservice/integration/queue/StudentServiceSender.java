package com.learning.student.importerservice.integration.queue;

import com.learning.student.importerservice.integration.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Sends the Student from JSON (http request) to student-service.
 */
@Component
@Slf4j
public class StudentServiceSender {

    @Value("${spring.rabbitmq.studentrouting}")
    private String routingKey;

    @Autowired
    private AmqpTemplate jsonRabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    public void convertAndSend(Student student) {
        jsonRabbitTemplate.convertAndSend(exchange, routingKey, student);
        log.info("Sending student to queue: " + student.getFirstName());
    }
}
