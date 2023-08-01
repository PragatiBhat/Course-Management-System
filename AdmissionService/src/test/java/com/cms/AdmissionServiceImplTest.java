package com.cms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.repository.AdmissionRepository;
import com.cms.service.AdmissionServiceImpl;

//Write Unit Tests for the methods in the AdmissionServiceImpl
@SpringBootTest(classes={AdmissionServiceImplTest.class})
public class AdmissionServiceImplTest {
	
	@Mock
	AdmissionRepository admissionR;
	
	@InjectMocks
	AdmissionServiceImpl admissionS;
	
	//check whether the calculateFees calculates fees for the given associate Id
	//@SuppressWarnings("unchecked")
	@Test
	public void test108CalculateFees() throws AdmissionInvalidException {
		
		List<Admission> myadmission=new ArrayList<>();
		myadmission.add(new Admission(300,"200","100",5000,"good"));
		
		
		//when(admissionR.findByAssociateId("100")).thenReturn(Optional.of(myadmission.get(0)));
		assertEquals(5000, admissionS.calculateFees("300"));
	}
	
	//check whether addFeedback adds the feedback entered by the assocaite for the given registration Id
	@Test
	public void test109AddFeedback() throws AdmissionInvalidException {
		
		List<Admission> myadmission=new ArrayList<>();
		myadmission.add(new Admission(300,"200","100",5000,""));
		
		
		when(admissionR.findById((long) 300)).thenReturn(Optional.of(myadmission.get(0)));
		assertEquals("excellent", admissionS.addFeedback((long)300,"excellent",4).getFeedback());
	}
	
	
	//check whether highestFeeForTheRegisteredCourse returns the highest fee among all the courses registered by the associate
	@Test
	public void test110highestFeeForTheRegisteredCourse() {
		
	}
	
	//check whether the viewFeedbackByCourseId returns the list of feedbacks for the given courseId
	@Test
	public void test111ViewFeedbackByCourseId() {
		
	}
	
	//check whether deactivateAdmission removes the admission for the given courseId in the database
	@Test
	public void test112DeactivateAdmission() {
		
	}
	
	//check whether addFeedback throws AdmissionInvalidException when the registration id is invalid
	@Test
	public void test113AddFeedbackForInvalidId() throws AdmissionInvalidException {
		
		List<Admission> myadmission=new ArrayList<>();
		myadmission.add(new Admission(300,"200","100",5000,""));
		
		
		when(admissionR.findById((long) 300)).thenReturn(Optional.of(myadmission.get(0)));
		assertEquals("excellent", admissionS.addFeedback((long)301,"excellent",4).getFeedback());
	}
	
	//check whether viewFeedbackByCourseId throws AdmissionInvalidException when the course id is invalid 
	@Test
	public void test114ViewFeedbackByCourseIdForInvalidCourseId() {
		
	}
	
	

	//check whether the calculateFees throws AdmissionInvalidException for invalid associate Id
	@Test
	public void test115calculateFeesForInvalidAssociateId() {
		
	}
	
	//check whether the highestFeeForTheRegisteredCourse throws AdmissionInvalidException for invalid associate Id
	@Test
	public void test116highestFeeForTheRegisteredCourseForInvalidAssociateId() {
		
	}
	
	//check whether the deactivateAdmission throws AdmissionInvalidException for invalid course Id
	@Test
	public void test117deactivateAdmissionForInvalidCourseId() {
		
	}
	

}
