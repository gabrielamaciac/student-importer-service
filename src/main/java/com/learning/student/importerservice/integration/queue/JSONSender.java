package com.learning.student.importerservice.integration.queue;

import com.learning.student.importerservice.integration.model.StudentMsg;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JSONSender {

    @Autowired
    private AmqpTemplate jsonRabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public void send(StudentMsg student) {
        jsonRabbitTemplate.convertAndSend(exchange, routingkey, student);
        System.out.println("Send msg = " + student);
    }
}
