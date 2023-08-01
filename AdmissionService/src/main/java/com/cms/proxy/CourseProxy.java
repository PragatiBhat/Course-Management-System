package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.cms.model.Course;

@FeignClient(name="courseapp",url="http://localhost:9091")
public interface CourseProxy {
	@PutMapping("/course/calculateAverageFeedback/{courseId}/{rating}")
	public Course calculateAverageFeedbackAndUpdate(@PathVariable String courseId,@PathVariable float rating);
	
	@GetMapping("/course/viewByCourseId/{courseId}")
	public Course viewByCourseId(@PathVariable String courseId);
}
