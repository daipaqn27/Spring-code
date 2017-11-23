package org.java.spring.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.java.spring.model.JavaClazz;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XSLTUtils {
	public static DOMSource clazzToDomSource(JavaClazz clazz) throws JAXBException, ParserConfigurationException, SAXException, IOException{
		
			JAXBContext jaxbContext = JAXBContext.newInstance(JavaClazz.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			jaxbMarshaller.marshal(clazz, outputStream);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new ByteArrayInputStream(outputStream.toByteArray()));
			
			return new DOMSource(document);
			
	}
}
