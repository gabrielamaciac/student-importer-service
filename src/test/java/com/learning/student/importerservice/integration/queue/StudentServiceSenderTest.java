package com.learning.student.importerservice.integration.queue;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.test.util.ImporterTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test for {@link StudentServiceSender}
 */
class StudentServiceSenderTest {
    private final Student student = ImporterTestData.getStudent();

    private AmqpTemplate jsonRabbitTemplate;
    private StudentServiceSender studentServiceSender;

    @BeforeEach
    void setUp() {
        jsonRabbitTemplate = mock(AmqpTemplate.class);
        studentServiceSender = new StudentServiceSender(jsonRabbitTemplate);
    }

    @Test
    void searchPayloadIsConvertedAndSent() {
        studentServiceSender.convertAndSend(student);
        verify(jsonRabbitTemplate).convertAndSend(null, null, student);
    }
}
