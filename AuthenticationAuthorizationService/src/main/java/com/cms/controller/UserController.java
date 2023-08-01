package com.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.model.Admission;
import com.cms.model.Associate;
import com.cms.model.Course;
import com.cms.proxy.AdmissionProxy;
import com.cms.proxy.AssociateProxy;
import com.cms.proxy.CourseProxy;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class UserController{
	
	@Autowired
	AuthController authController;
	
	@Autowired
	CourseProxy courseproxy;
	
	@Autowired
	private AssociateProxy associateproxy;
	
	@Autowired
	AdmissionProxy admissionProxy;
    
	@GetMapping(value="/course/viewByCourseId/{courseId}",produces="Application/json")
	public ResponseEntity<Course> viewByCourseId(@RequestHeader("Authorization") String authorization,@PathVariable String courseId)
	{
		if(!authController.isValidToken(authorization))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return courseproxy.viewByCourseId(courseId);
	}
	
	@PutMapping("/associate/updateAssociate/{associateId}/{associateAddr}")
	public ResponseEntity<Associate> updateAssociate(@RequestHeader("Authorization") String authorization,@PathVariable String associateId,@PathVariable String associateAddr)
	{
		if(!authController.isValidToken(authorization))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return associateproxy.updateAssociate(associateId,associateAddr);
	}
	
	@GetMapping("/associate/viewByAssociateId/{associateId}")
	public ResponseEntity<Associate> viewByAssociateId(@RequestHeader("Authorization") String authorization,@PathVariable String associateId)
	{
		if(!authController.isValidToken(authorization))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return associateproxy.viewByAssociateId(associateId);
	}
	
	@PostMapping("/admission/register/{associateId}/{courseId}")
	public ResponseEntity<Admission> registerAssociateForCourse(@RequestHeader("Authorization") String authorization,@RequestBody Admission admission,@PathVariable String associateId,@PathVariable String courseId)
	{
		if(!authController.isValidToken(authorization))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return admissionProxy.registerAssociateForCourse(admission,associateId, courseId);
	}
	
	@PostMapping("/admission/feedback/{regNo}/{feedback}/{feedbackRating}")
	public ResponseEntity<Admission> addFeedback(@RequestHeader("Authorization") String authorization,@PathVariable Long regNo,@PathVariable String feedback,@PathVariable Float feedbackRating)
	{
		if(!authController.isValidToken(authorization))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return admissionProxy.addFeedback(regNo,feedback,feedbackRating);
	}
			
	@GetMapping("/admission/viewFeedbackByCourseId/{couserId}")
	public ResponseEntity<List<String>> viewFeedbackByCourseId(@RequestHeader("Authorization") String authorization,@PathVariable String courseId)
	{
		if(!authController.isValidToken(authorization))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return admissionProxy.viewFeedbackByCourseId(courseId);
	}
	
	
}