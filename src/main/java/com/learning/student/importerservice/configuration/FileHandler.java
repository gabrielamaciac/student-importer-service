package com.learning.student.importerservice.configuration;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.integration.queue.StudentServiceSender;
import com.learning.student.importerservice.util.CustomXmlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
public class FileHandler implements MessageHandler {
    @Autowired
    private CustomXmlParser customXmlParser;
    @Autowired
    private StudentServiceSender studentServiceSender;

    @Value("${student.file.path}")
    private String path;

    @Value("${student.file.successful}")
    private String successfulPath;

    @Value("${student.file.archived}")
    private String archivedPath;

    public void handleMessage(Message<?> message) throws MessagingException {
        File file = (File) message.getPayload();
        log.info("File received " + file.getAbsolutePath());
        moveFile(file.toPath());
    }

    private void moveFile(Path path) {
        try {
            Student student = customXmlParser.getStudentFromXml(path.toString());
            if (Objects.nonNull(student)) {
                studentServiceSender.convertAndSend(student);
                log.info("Scheduled job sending student to student-queue: " + student.getFirstName());
                Files.createDirectories(Paths.get(successfulPath));
                Files.move(path, Path.of(constructDestinationFile(successfulPath)), StandardCopyOption.ATOMIC_MOVE);
            } else {
                Files.createDirectories(Paths.get(archivedPath));
                Files.move(path, Path.of(constructDestinationFile(archivedPath)), StandardCopyOption.ATOMIC_MOVE);
                log.info("Failed to parse student xml. Moved to archived.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String constructDestinationFile(String path) {
        return String.format(path + "\\student_%s.xml", LocalDateTime.now().toString().replace(":", ""));
    }
}