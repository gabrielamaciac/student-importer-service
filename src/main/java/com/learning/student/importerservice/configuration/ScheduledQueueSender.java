package com.learning.student.importerservice.configuration;

import com.learning.student.importerservice.integration.model.Student;
import com.learning.student.importerservice.integration.queue.StudentServiceSender;
import com.learning.student.importerservice.util.CustomXmlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
public class ScheduledQueueSender {
    private final StudentServiceSender studentServiceSender;
    private final CustomXmlParser customXmlParser;

    @Value("${student.file.path}")
    private String path;

    @Value("${student.file.successful}")
    private String successfulPath;

    @Value("${student.file.archived}")
    private String archivedPath;

    public ScheduledQueueSender(StudentServiceSender studentServiceSender, CustomXmlParser customXmlParser) {
        this.studentServiceSender = studentServiceSender;
        this.customXmlParser = customXmlParser;
    }

    @Scheduled(fixedDelay = 5000)
    public void sendStudentJob() {
        FilenameFilter textFileFilter = (dir, name) -> name.endsWith(".xml");

        File dir = new File(path);
        File[] files = dir.listFiles(textFileFilter);

        for (File file : files) {
            sendAndMoveFile(file.toPath());
        }
    }

    private void sendAndMoveFile(Path path) {
        try {
            convertAndSend(path);
        } catch (IOException e) {
            log.error("File couldn't be moved.", e);
        }
    }

    private void convertAndSend(Path path) throws IOException {
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
    }

    private String constructDestinationFile(String path) {
        return String.format(path + "\\student_%s.xml", LocalDateTime.now().toString().replace(":", ""));
    }
}
