package org.java.spring.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.java.spring.dao.StudentDAO;
import org.java.spring.model.JavaClazz;
import org.java.spring.utils.XSLTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ClazzController {
	@Autowired
	private StudentDAO studentDAO;
	
	@RequestMapping(value="/clazz/xml", produces=MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody JavaClazz viewInXml(){
		return new JavaClazz(studentDAO.list());
	}
	
	@RequestMapping(value="/clazz/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JavaClazz viewJson(){
		return new JavaClazz(studentDAO.list());
	}
	
	@RequestMapping(value="/clazz/xslt")
	public ModelAndView viewXSLT() throws JAXBException, ParserConfigurationException, SAXException, IOException{
		JavaClazz clazz = new JavaClazz(studentDAO.list());
		ModelAndView mv = new ModelAndView("ClazzView");
		mv.getModelMap().put("data", XSLTUtils.clazzToDomSource(clazz));
		return mv;
	}
	
	@RequestMapping(value="/clazz/excel")
	public ModelAndView viewExcel(){
		JavaClazz clazz = new JavaClazz(studentDAO.list());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("excelView");
		mv.getModelMap().put("clazzObj", clazz);
		return mv;
	}
	
	@RequestMapping(value="/clazz/pdf", produces=MediaType.APPLICATION_PDF_VALUE)
	public ModelAndView viewPdf(){
		JavaClazz clazz = new JavaClazz(studentDAO.list());
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("pdfView");
//		mv.getModelMap().put("clazzObj", clazz);
		return new ModelAndView("pdfView", "clazzObj", clazz);
	}
	
	@RequestMapping(value="/clazz/report", produces=MediaType.APPLICATION_PDF_VALUE)
	public ModelAndView viewReport(){
		JRDataSource dataSource = new JRBeanCollectionDataSource(studentDAO.list());
		ModelAndView mv = new ModelAndView("pdfReport");
		mv.addObject("dataSource",dataSource);
		return mv;
	}
}
