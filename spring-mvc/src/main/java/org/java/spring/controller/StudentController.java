package org.java.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.java.spring.dao.StudentDAO;
import org.java.spring.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
	public ModelAndView listStudents(@RequestParam(value = "q", required = false) String query){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("student.list");
		
		if(query != null && !"".equals(query)){
			mv.addObject("students", studentDAO.getListStudentByName(query));
		}else {
			mv.addObject("students", studentDAO.list());
		}
		
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
	
	@RequestMapping(value="/student/avatar/save", method = RequestMethod.POST)
	public ModelAndView handleFormUpload(@RequestParam("file") MultipartFile file, int id,
			HttpServletRequest request){
		if (file.isEmpty()) return new ModelAndView("student.error");
		
		try {
//			byte[] bytes = file.getBytes();
//			System.out.println("found---> " + bytes.length);
			Path avatarFile = getImageFile(request, id);
			Files.write(avatarFile, file.getBytes(), StandardOpenOption.CREATE);
			
			System.out.println(avatarFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/student/list");
	}
	
	private Path getImageFile(HttpServletRequest request, int id){
		ServletContext servletContext = request.getSession().getServletContext();
		String diskPath = servletContext.getRealPath("/");
		File folder = new File(diskPath + File.separator + "avatar" );
		folder.mkdirs();
		return new  File(folder, String.valueOf(id) + ".jpg").toPath();
	}
	
	@RequestMapping(value="/student/avatar/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(Integer id, HttpServletRequest request){
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		if(id != null){
			Path avatarPath = getImageFile(request, id);
			if (Files.exists(avatarPath))
				try {
					byteOutput.write(Files.readAllBytes(avatarPath));
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(byteOutput.toByteArray(), headers , HttpStatus.CREATED);
	}
}