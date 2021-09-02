package com.learning.student.importerservice.util;

import com.learning.student.importerservice.integration.model.Address;
import com.learning.student.importerservice.integration.model.Grade;
import com.learning.student.importerservice.integration.model.Mark;
import com.learning.student.importerservice.integration.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
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
    private File file;

    public Student getStudentFromXml() {
        try {
            FileInputStream fileIS = new FileInputStream(this.getFile());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the document from file path
            Document xmlDocument = builder.parse(fileIS);

            // Create XPath object from XPathFactory
            XPath xPath = XPathFactory.newInstance().newXPath();

            // Convert the xml to Student
            return parseStudent(xmlDocument, xPath);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Error while parsing the xml document for student: " + e);
        }
        return null;
    }

    public Student parseStudent(Document doc, XPath xPath) {
        try {
            Student student = new Student();
            String expression = "student";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            // Get all nodes that are immediate children of the student and iterate over them
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    student.setFirstName(eElement.getElementsByTagName("firstName").item(0).getTextContent());
                    student.setLastName(eElement.getElementsByTagName("lastName").item(0).getTextContent());
                    student.setCnp(eElement.getElementsByTagName("cnp").item(0).getTextContent());
                    student.setDateOfBirth(eElement.getElementsByTagName("dateOfBirth").item(0).getTextContent());
                }
            }
            String addressExp = "student//address";
            NodeList addressNodes = (NodeList) xPath.compile(addressExp).evaluate(doc, XPathConstants.NODESET);
            Address address = new Address();
            // Get all nodes that are immediate children of the "address" tag and iterate over them
            for (int i = 0; i < addressNodes.getLength(); i++) {
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    address.setStreet(eElement.getElementsByTagName("street").item(0).getTextContent());
                    address.setNumber(eElement.getElementsByTagName("number").item(0).getTextContent());
                    address.setCity(eElement.getElementsByTagName("city").item(0).getTextContent());
                    address.setCountry(eElement.getElementsByTagName("country").item(0).getTextContent());
                }
            }
            student.setAddress(address);
            // Get all grades
            student.setGrades(getGradeList(doc, xPath));
            return student;
        } catch (XPathExpressionException e) {
            log.error("Exception while parsing the xml nodes for student: " + e);
        }
        return null;
    }

    private List<Grade> getGradeList(Document doc, XPath xPath) {
        List<Grade> gradeList = new ArrayList<>();
        try {
            XPathExpression gradesExpression = xPath.compile("student//grades");
            Object gradesResult = gradesExpression.evaluate(doc, XPathConstants.NODESET);
            NodeList grades = (NodeList) gradesResult;

            // Get all grades and iterate over them
            for (int i = 0; i < grades.getLength(); i++) {
                Element eElement = (Element) grades.item(i);
                String subject = eElement.getElementsByTagName("subject").item(0).getTextContent();
                Grade grade = new Grade();
                grade.setSubject(subject);

                //Get all marks and iterate over them
                List<Mark> markList = new ArrayList<>();
                NodeList marks = eElement.getElementsByTagName("marks");
                for (int j = 0; j < marks.getLength(); j++) {
                    Element marksElement = (Element) marks.item(j);
                    String dateReceived = marksElement.getElementsByTagName("dateReceived").item(0).getTextContent();
                    String mark = marksElement.getElementsByTagName("mark").item(0).getTextContent();
                    //set each mark in the marks array
                    Mark markObject = new Mark();
                    markObject.setDateReceived(dateReceived);
                    try {
                        markObject.setMark(Double.parseDouble(mark));
                    } catch (NumberFormatException ex) {
                        //default mark if input is not a digit
                        markObject.setMark(0.0);
                    }
                    markList.add(markObject);
                }
                grade.setMarks(markList);
                gradeList.add(grade);
            }
        } catch (XPathExpressionException e) {
            log.error("Exception while parsing the xml nodes for grade: " + e);
        }
        return gradeList;
    }
}
