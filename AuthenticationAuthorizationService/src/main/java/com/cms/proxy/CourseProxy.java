package com.cms.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cms.model.Course;

@FeignClient(name="courseapp",url="http://localhost:9091")
public interface CourseProxy {
	
	@PostMapping("/course/addCourse")
	public ResponseEntity<Course> addCourse(@RequestBody Course courses);
	
	@PutMapping("/course/update/{courseId}/{courseFees}")
	public ResponseEntity<Course> updateCourse(@PathVariable String courseId,@PathVariable Integer courseFees);
	
	@GetMapping("/course/viewByCourseId/{courseId}")
	public ResponseEntity<Course> viewByCourseId(@PathVariable String courseId);
	
	@GetMapping("/course/viewFeedback/{courseId}")
	public ResponseEntity<Float> viewFeedback(@PathVariable String courseId);
	
	@PutMapping("/course/calculateAverageFeedback/{courseId}/{rating}")
	public ResponseEntity<Course> calculateAverageFeedbackAndUpdate(@PathVariable String courseId,@PathVariable float rating);
	
	@DeleteMapping("/course/deactivate/{courseId}") 
	public ResponseEntity<Course> deactivateCourse(@PathVariable String courseId);
	
	@GetMapping("/course/viewAll")
	public ResponseEntity<List<Course>> viewAll();
}
