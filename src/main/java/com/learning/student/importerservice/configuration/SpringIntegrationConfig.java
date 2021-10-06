package com.learning.student.importerservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.MessageHandler;

import java.io.File;

@Configuration
@EnableIntegration
public class SpringIntegrationConfig {
    @Value("${student.file.path}")
    private String path;

    @Bean
    public IntegrationFlow fileMover() {
        return IntegrationFlows.from("fileInputChannel")
                .filter(source -> ((File) source).getName().endsWith(".xml"))
                .handle(moveFileMessageHandler())
                .get();
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
    public FileReadingMessageSource fileReadingMessageSource() {
        CompositeFileListFilter<File> filter = new CompositeFileListFilter<>();
        filter.addFilter(new SimplePatternFileListFilter("*.xml"));

        FileReadingMessageSource reader = new FileReadingMessageSource();
        reader.setDirectory(new File(path));
        reader.setFilter(filter);
        reader.setUseWatchService(true);
        reader.setWatchEvents(FileReadingMessageSource.WatchEventType.CREATE, FileReadingMessageSource.WatchEventType.MODIFY);

        return reader;
    }

    @Bean
    @ServiceActivator(inputChannel = "fileInputChannel", requiresReply = "false")
    public MessageHandler moveFileMessageHandler() {
        return new FileHandler();
    }
}