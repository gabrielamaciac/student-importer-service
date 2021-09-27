package com.learning.student.importerservice.util;

import com.learning.student.importerservice.integration.model.Address;
import com.learning.student.importerservice.integration.model.Grade;
import com.learning.student.importerservice.integration.model.Mark;
import com.learning.student.importerservice.integration.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Slf4j
public class CustomXmlParser {
    private final File file;

    public Student getStudentFromXml() {
        try (FileInputStream fileIS = new FileInputStream(this.getFile())) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the document from file path
            Document xmlDocument = builder.parse(fileIS);

            // Create XPath object from XPathFactory
            XPath xPath = XPathFactory.newInstance().newXPath();

            // Convert the xml to Student
            return parseStudent(xmlDocument, xPath);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Error while parsing the xml document for student: ", e);
        }
        return null;
    }

    private Student parseStudent(Document doc, XPath xPath) {
        try {
            Student student = new Student();
            // Get all nodes that are immediate children of the student and set them
            String firstName = (String) xPath.evaluate("student//firstName//text()", doc, XPathConstants.STRING);
            student.setFirstName(firstName);
            String lastName = (String) xPath.evaluate("student//lastName//text()", doc, XPathConstants.STRING);
            student.setLastName(lastName);
            String cnp = (String) xPath.evaluate("student//cnp//text()", doc, XPathConstants.STRING);
            student.setCnp(cnp);
            String dateOfBirth = (String) xPath.evaluate("student//dateOfBirth//text()", doc, XPathConstants.STRING);
            student.setDateOfBirth(dateOfBirth);

            // Get address nodes
            Address address = new Address();
            String street = (String) xPath.evaluate("student//address//street//text()", doc, XPathConstants.STRING);
            address.setStreet(street);
            String number = (String) xPath.evaluate("student//address//number//text()", doc, XPathConstants.STRING);
            address.setNumber(number);
            String city = (String) xPath.evaluate("student//address//city//text()", doc, XPathConstants.STRING);
            address.setCity(city);
            String country = (String) xPath.evaluate("student//address//country//text()", doc, XPathConstants.STRING);
            address.setCountry(country);
            student.setAddress(address);

            // Get grades
            student.setGrades(getGradeList(doc, xPath));

            return student;
        } catch (XPathExpressionException e) {
            log.error("Exception while parsing the xml nodes for student: ", e);
        }
        return null;
    }

    private List<Grade> getGradeList(Document doc, XPath xPath) {
        List<Grade> gradeList = new ArrayList<>();
        try {
            //TODO .//grades -> List<Element> -> stream .//marks

            // Get all grades and iterate over them to construct the Grade object
            NodeList grades = (NodeList) xPath.evaluate("student//grades", doc, XPathConstants.NODESET);
            for (int i = 0; i < grades.getLength(); i++) {
                Grade grade = new Grade();
                // Get subject of each grade
                String subject = (String) xPath.evaluate(".//subject//text()", grades.item(i), XPathConstants.STRING);
                grade.setSubject(subject);
                // Get all marks and iterate over them to construct the Mark object
                List<Mark> markList = new ArrayList<>();
                NodeList marks = (NodeList) xPath.evaluate(".//marks", grades.item(i), XPathConstants.NODESET);
                for (int j = 0; j < marks.getLength(); j++) {
                    // Get dateReceived and mark of each mark item and set them on the Mark object
                    Mark markObject = new Mark();
                    Node dateReceived = (Node) xPath.evaluate(".//dateReceived//text()", marks.item(j), XPathConstants.NODE);
                    Node mark = (Node) xPath.evaluate(".//mark//text()", marks.item(j), XPathConstants.NODE);
                    markObject.setDateReceived(dateReceived.getTextContent());
                    markObject.setMark(parseMark(mark.getTextContent()));
                    markList.add(markObject);
                }
                grade.setMarks(markList);
                gradeList.add(grade);
            }
        } catch (XPathExpressionException e) {
            log.error("Exception while parsing the xml nodes for grade: ", e);
        }
        return gradeList;
    }

    private Double parseMark(String mark) {
        try {
            return Double.parseDouble(mark);
        } catch (NumberFormatException ex) {
            //default mark if input is not a digit
            return 0.0;
        }
    }
}