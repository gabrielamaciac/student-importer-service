package com.learning.student.importerservice.configuration;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.integration.queue.StudentServiceSender;
import com.learning.student.importerservice.util.XmlToJsonTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
@Component
public class ScheduledQueueSender {
    private final StudentServiceSender studentServiceSender;
    private final XmlToJsonTransformer xmlToJsonTransformer;

    @Value("${student.file.path}")
    private String path;

    public ScheduledQueueSender(StudentServiceSender studentServiceSender, XmlToJsonTransformer xmlToJsonTransformer) {
        this.studentServiceSender = studentServiceSender;
        this.xmlToJsonTransformer = xmlToJsonTransformer;
    }

    @Scheduled(fixedDelay = 10000)
    public void sendStudentJob() {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile)
                    .forEach(this::convertAndSend);
        } catch (IOException e) {
            log.error("IOException occurred while reading folder: " + path, e);
        }
    }

    private void convertAndSend(Path path) {
        Student student = xmlToJsonTransformer.transform(path.toString());
        studentServiceSender.convertAndSend(student);
        log.info("Scheduled job sending student to student-queue: " + student.getFirstName());
    }
}
