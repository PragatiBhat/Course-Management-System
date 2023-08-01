package com.cms.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.proxy.AdmissionProxy;
import com.cms.service.ICourseService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {
		
	WebClient webClient;
	
	@Autowired
	ICourseService courseS;
	
	@Autowired 
	AdmissionProxy admissproxy;
	
	@PostMapping("/course/addCourse")
	public Course addCourse(@RequestBody Course courses) throws CourseInvalidException
	{
		return courseS.addCourse(courses);
	}
	
	@PutMapping("/course/update/{courseId}/{courseFees}")
	public Course updateCourse(@PathVariable String courseId,@PathVariable Integer courseFees) throws CourseInvalidException
	{
		return courseS.updateCourse(courseId, courseFees);
	}
	
	@GetMapping("/course/viewByCourseId/{courseId}")
	public Course viewByCourseId(@PathVariable String courseId) throws CourseInvalidException
	{
		return courseS.viewByCourseId(courseId);
	}
	@GetMapping("/course/viewFeedback/{courseId}")
	public float viewFeedback(@PathVariable String courseId) throws CourseInvalidException
	{
		return courseS.findFeedbackRatingForCourseId(courseId);
	}
	
	@PutMapping("/course/calculateAverageFeedback/{courseId}/{rating}")
	public Course calculateAverageFeedbackAndUpdate(@PathVariable String courseId,@PathVariable float rating) throws CourseInvalidException
	{
		return courseS.calculateAverageFeedbackAndUpdate(courseId, rating);
	}
	@DeleteMapping("/course/deactivate/{courseId}") 
	public Course deactivateCourse(@PathVariable String courseId) throws CourseInvalidException
	{
		
		admissproxy.deactivateAdmission(courseId);
		return courseS.deactivateCourse(courseId);
		
	}
	@GetMapping("/course/viewAll")
	public ResponseEntity<List<Course>> viewAll()
	{
		return new ResponseEntity<>(courseS.viewAll(),HttpStatus.OK); 
	}

}
