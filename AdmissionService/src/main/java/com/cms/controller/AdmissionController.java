package com.cms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cms.Publisher.RabbitMqJsonProducer;
//import com.cms.Publisher.RabbitMqJsonProducer;
import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Associate;
import com.cms.model.EmailRequest;
import com.cms.model.RegistrationDetails;
//import com.cms.model.RegistrationDetails;
import com.cms.payment.Order;
import com.cms.payment.PaypalService;
import com.cms.proxy.CourseProxy;
import com.cms.proxy.ServiceProxy;
import com.cms.service.EmailService;
import com.cms.service.IAdmissionService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdmissionController {

	private static final String CANCEL = "http://localhost:4200/cancel";
	private static final String SUCCESS_URL = "http://localhost:4200/success";

	@Autowired
    EmailService emailService;
	
	@Autowired
	PaypalService paypalService;

	@Autowired
	IAdmissionService admissionS;

	@Autowired
	ServiceProxy serviceP;

	@Autowired
	CourseProxy courseP;

//	@Autowired
//	PaypalService pservice;

	private RabbitMqJsonProducer producer;

	public AdmissionController(RabbitMqJsonProducer producer) {
		this.producer = producer;
	}
//    
//    @GetMapping("/publish")
//	public ResponseEntity<String> sendMessage(){
//		producer.sendMessage("Hi");
//		return ResponseEntity.ok("Message sent to RabbitMQ....");
//	}

	@PostMapping("/admission/register/{associateId}/{courseId}")
	@ResponseStatus(HttpStatus.OK)
	public Admission registerAssociateForCourse(@RequestBody Admission admission, @PathVariable String associateId,
			@PathVariable String courseId) throws AdmissionInvalidException {
		Associate associate=serviceP.viewByAssociateId(associateId);
		courseP.viewByCourseId(courseId);
		Admission admission1 = admissionS.registerAssociateForCourse(admission);
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = currentDate.format(formatter);
		RegistrationDetails registrationD = new RegistrationDetails(admission1.getRegistrationId(),
				admission1.getCourseId(), admission1.getAssociateId(), formattedDate);
		producer.sendMessage(registrationD);
		EmailRequest emailR=new EmailRequest();
		emailService.sendEmail(associate.getAssociateEmailId(), "Registration Successful", registrationD.toString());
		return admission1;
	}

	@PutMapping("/admission/calculateFees/{associateId}")
	public int calculateFees(@PathVariable String associateId) throws AdmissionInvalidException {
		return admissionS.calculateFees(associateId);
	}

	@PostMapping("/admission/feedback/{regNo}/{feedback}/{feedbackRating}")
	public Admission addFeedback(@PathVariable Long regNo, @PathVariable String feedback,
			@PathVariable float feedbackRating) throws AdmissionInvalidException {

		Admission admiss = admissionS.addFeedback(regNo, feedback, feedbackRating);
		courseP.calculateAverageFeedbackAndUpdate(admiss.getCourseId(), feedbackRating);
		return admiss;
	}

	@GetMapping("/admission/highestFee/{associateId}")
	public List<String> highestFeeForTheRegisteredCourse(@PathVariable String associateId)
			throws AdmissionInvalidException {
		return admissionS.highestFeeForTheRegisteredCourse(associateId);
	}

	@GetMapping("/admission/viewFeedbackByCourseId/{courseId}")
	public List<String> viewFeedbackByCourseId(@PathVariable String courseId) throws AdmissionInvalidException {

		return admissionS.viewFeedbackByCourseId(courseId);
	}

	@DeleteMapping("/admission/deactivate/{courseId}")
	public boolean deactivateAdmission(@PathVariable String courseId) throws AdmissionInvalidException {
		return admissionS.deactivateAdmission(courseId);
	}

	@GetMapping("/admission/getDetails/{regNo}")
	public Admission getbyId(@PathVariable long regNo) throws AdmissionInvalidException{
		
		return admissionS.getById(regNo);
	}
	@PostMapping("/admission/makePayment/{registartionId}/{fees}")
	public List<String> makePayment(@PathVariable int registartionId,@PathVariable double fees) throws IOException {
//		try {
////			Order order=new Order();
////			order.setPrice(fees);
////			order.setCurrency("USD");
////			order.setMethod("paypal");
////			order.setIntent("sale");
////			order.setDescription("Payment of the order");
//			Payment payment = pservice.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
//					order.getIntent(), order.getDescription(), "CANCEL",
//					"SUCCESS_URL");
//			for(Links link:payment.getLinks()) {
//				if(link.getRel().equals("approval_url")) {
//					
//					return "redirect:"+link.getHref();
//				}
//			}
//			
//		} catch (PayPalRESTException e) {
//		
//			e.printStackTrace();
//		}	 	  	  	    	      	        	 	
//		return "Home"
		List<String> re=new ArrayList<>();
		try {
			
			Order order=new Order();
			order.setPrice(fees);
			order.setCurrency("USD");
			order.setMethod("paypal");
			order.setIntent("sale");
			order.setDescription("Payment of the order");
			Payment payment = paypalService.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
					order.getIntent(), order.getDescription(), CANCEL, SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {

					String redirectUrl = UriComponentsBuilder.fromUriString(link.getHref()).build().toUriString();
					//return "redirect:" + //redirectUrl
					re.add(redirectUrl);				
					return re;
				}
			}

		} catch (PayPalRESTException e) {

			e.printStackTrace();
		}
		return re;

	}

	@GetMapping("/admission/viewAll")
	public List<Admission> viewAll() {
		return admissionS.viewAll();
	}
	
	@PostMapping("admission/sendEmail")
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
    }
}
