package org.java.spring.controller;

import javax.validation.Valid;

import org.java.spring.dao.StudentDAO;
import org.java.spring.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class StudentController {
	@Autowired
	private StudentDAO studentDAO;
	
	@RequestMapping(value="student/add", method = RequestMethod.GET)
	public ModelAndView add(){
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("student.form");
		return new ModelAndView("student.form", "command", new Student());
	}
	
	@RequestMapping(value = "student/save1", method = RequestMethod.POST)
	public ModelAndView save1(Student student){
//		Student student = new Student(name, age);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("student.view");
		mv.addObject("student", student);
//		mv.addObject("age", age);
		return mv;
	}
	
	@RequestMapping(value = "student/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("command") Student student, BindingResult result){
		if(result.hasErrors()){
			ModelAndView model = new ModelAndView("student.form","command", student);
			model.addObject("errors", result);
			return model;
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("student.view");
		mv.addObject("student", student);
		studentDAO.insert(student);
		return mv;
	}
	
}