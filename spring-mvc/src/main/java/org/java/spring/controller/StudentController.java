package org.java.spring.controller;

import javax.validation.Valid;

import org.java.spring.dao.StudentDAO;
import org.java.spring.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
		
		if(student.getId() > 0){
			studentDAO.update(student);
		}else{
			studentDAO.insert(student);
		}
		return new ModelAndView("redirect:/student/list");
	}
	
	@RequestMapping(value = "/student/list", method = RequestMethod.GET)
	public ModelAndView listStudent(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("student.list");
		mv.addObject("students", studentDAO.list());
		return mv;
	}
	
	@RequestMapping(value = "/student/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id){
		studentDAO.delete(id);
		return new ModelAndView("redirect:/student/list");
	}
	
	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable int id){
		Student student = studentDAO.get(id);
		return new ModelAndView("student.form", "command", student);
	}
	
	@RequestMapping(value = "student/edit/save", method = RequestMethod.GET)
	public ModelAndView saveEdit(){
		return new ModelAndView("forward:/student/save");
	}
	
	@RequestMapping(value = "/student/json/{id}", method = RequestMethod.GET)
	public @ResponseBody Student viewJson(@PathVariable int id){
		return studentDAO.get(id);
	}
}