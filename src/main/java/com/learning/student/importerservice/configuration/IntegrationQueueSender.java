package com.learning.student.importerservice.configuration;

import com.learning.student.importerservice.util.XmlToJsonTransformer;
import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.AcceptAllFileListFilter;

import java.io.File;

/**
 * Sends the Student from an .xml file to student-service. Reads the file on every CREATE/ MODIFY event.
 */
@Configuration
@Setter
public class IntegrationQueueSender {

    @Value("${student.file.path}")
    private String path;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.studentrouting}")
    private String routingKey;

    @Bean
    public IntegrationFlow integrationFlow(AmqpTemplate jsonRabbitTemplate, XmlToJsonTransformer xmlToJsonTransformer) {
        return IntegrationFlows.from(sourceDirectory(),
                spec -> spec.poller(Pollers.fixedRate(5000)))
                .filter(source -> ((File) source).getName().endsWith(".xml"))
                .log()
                .transform(xmlToJsonTransformer, "transform")
                .log()
                .handle(Amqp.outboundAdapter(jsonRabbitTemplate).routingKey(routingKey).exchangeName(exchange))
                .log()
                .get();
    }

    @Bean
    public MessageSource<File> sourceDirectory() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(path));
        messageSource.setFilter(new AcceptAllFileListFilter<>());
        messageSource.setUseWatchService(true);
        messageSource.setWatchEvents(FileReadingMessageSource.WatchEventType.CREATE, FileReadingMessageSource.WatchEventType.MODIFY);
        return messageSource;
    }
}
