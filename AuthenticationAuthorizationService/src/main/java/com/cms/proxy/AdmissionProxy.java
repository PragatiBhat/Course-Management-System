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

import com.cms.model.Admission;

@FeignClient(name="admissionapp",url="http://localhost:9093")
public interface AdmissionProxy {

	@PostMapping("/admission/register/{associateId}/{courseId}")
	public ResponseEntity<Admission> registerAssociateForCourse(@RequestBody Admission admission,@PathVariable String associateId,@PathVariable String courseId);
	
	@PutMapping("/admission/calculateFees/{associateId}")
	public ResponseEntity<Integer> calculateFees(@PathVariable String associateId);
	
	@PostMapping("/admission/feedback/{regNo}/{feedback}/{feedbackRating}")
	public ResponseEntity<Admission> addFeedback(@PathVariable Long regNo,@PathVariable String feedback,@PathVariable float feedbackRating);
	
	@GetMapping("/admission/highestFee/{associateId}")
	public ResponseEntity<List<String>> highestFeeForTheRegisteredCourse(@PathVariable String associateId);
	
	@GetMapping("/admission/viewFeedbackByCourseId/{couserId}")
	public ResponseEntity<List<String>> viewFeedbackByCourseId(@PathVariable String courseId);
	
	@DeleteMapping("/admission/deactivate/{courseId}")
	public ResponseEntity<Boolean> deactivateAdmission(@PathVariable String courseId);
	
	@PostMapping("/admission/makePayment/{registartionId}/{fees}")
	public boolean makePayment(@PathVariable int registartionId);
	
	@GetMapping("/admission/viewAll")
	public ResponseEntity<List<Admission>> viewAll();
	
}
