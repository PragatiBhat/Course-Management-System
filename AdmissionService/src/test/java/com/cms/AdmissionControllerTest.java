package com.cms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cms.controller.AdmissionController;
import com.cms.exception.AdmissionInvalidException;
import com.cms.service.AdmissionServiceImpl;

//Write mockito tests for the endpoints in the AdmissionController
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.cms")
@SpringBootTest(classes = { AdmissionControllerTest.class })
public class AdmissionControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	AdmissionServiceImpl admissionS;

	@InjectMocks
	AdmissionController admissionC;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(admissionC).build();
	}

	// Test whether the endpoint /highestFee/{associateId} is successful
	@Test
	public void test101RestApiCallForHighestFeeForTheRegisteredCourse() throws AdmissionInvalidException {

		String associateId = "123";
		List<String> courses = Arrays.asList("Java Language", "Python Language");
		when(admissionS.highestFeeForTheRegisteredCourse(associateId)).thenReturn(courses);

		// Act
		List<String> result = admissionC.highestFeeForTheRegisteredCourse(associateId);

		// Assert
		assertEquals(courses, result);
		verify(admissionS, times(1)).highestFeeForTheRegisteredCourse(associateId);

	}

	// Test whether the end point /viewFeedbackByCourseId/{courseId} is successful
	@Test
	public void test102RestApiCallForViewFeedbackByCourseId() throws AdmissionInvalidException {
		String courseId = "C001";
		List<String> feedbackList = Arrays.asList("Great course!", "Excellent instructor");
		when(admissionS.viewFeedbackByCourseId(courseId)).thenReturn(feedbackList);

		// Act
		List<String> result = admissionC.viewFeedbackByCourseId(courseId);

		// Assert
		assertEquals(feedbackList, result);
		verify(admissionS, times(1)).viewFeedbackByCourseId(courseId);

	}

	// Test whether the end point /calculateFees/{associateId} is successful
	@Test
	public void test107RestApiCallForCalculateFees() throws AdmissionInvalidException {

		String associateId = "A001";
		int expectedFees = 2500;
		when(admissionS.calculateFees(associateId)).thenReturn(expectedFees);

		// Act
		int result = admissionC.calculateFees(associateId);

		// Assert
		assertEquals(expectedFees, result);
		verify(admissionS, times(1)).calculateFees(associateId);

	}

	// Test whether the end point /highestFee/{associateId} is successful for
	// invalid id
	@Test
	public void test101RestApiCallForHighestFeeForTheRegisteredCourseForInvalidId() throws AdmissionInvalidException {

		String invalidAssociateId = "INVALID";
		when(admissionS.highestFeeForTheRegisteredCourse(invalidAssociateId))
				.thenThrow(AdmissionInvalidException.class);

		// Act and Assert
		assertThrows(AdmissionInvalidException.class,
				() -> admissionC.highestFeeForTheRegisteredCourse(invalidAssociateId));
		verify(admissionS, times(1)).highestFeeForTheRegisteredCourse(invalidAssociateId);

	}

	// Test whether the end point /highestFee/{associateId} is successful for
	// invalid token
	@Test
	public void test101RestApiCallForHighestFeeForTheRegisteredCourseForInvalidToken() throws AdmissionInvalidException {

		String invalidAssociateId = "INVALID";
		when(admissionS.highestFeeForTheRegisteredCourse(invalidAssociateId))
				.thenThrow(AdmissionInvalidException.class);

		// Act and Assert
		assertThrows(AdmissionInvalidException.class,
				() -> admissionC.highestFeeForTheRegisteredCourse(invalidAssociateId));
		verify(admissionS, times(1)).highestFeeForTheRegisteredCourse(invalidAssociateId);

	}

	// Test whether the end point /viewFeedbackByCourseId/{courseId} is successful
	// for invalid id
	@Test
	public void test102RestApiCallForViewFeedbackByCourseIdForInvalidId() throws AdmissionInvalidException {

		String invalidCourseId = "INVALID";
		when(admissionS.viewFeedbackByCourseId(invalidCourseId)).thenThrow(AdmissionInvalidException.class);

		// Act and Assert
		assertThrows(AdmissionInvalidException.class,
				() -> admissionC.viewFeedbackByCourseId(invalidCourseId));
		verify(admissionS, times(1)).viewFeedbackByCourseId(invalidCourseId);

	}

	// Test whether the end point /viewFeedbackByCourseId/{courseId} is successful
	// for invalid token
	@Test
	public void test102RestApiCallForViewFeedbackByCourseIdForInvalidToken() throws AdmissionInvalidException {

		String courseId = "C001";
		String invalidToken = "INVALID_TOKEN";
		when(admissionS.viewFeedbackByCourseId(courseId)).thenThrow(AdmissionInvalidException.class);

		// Act and Assert
		assertThrows(AdmissionInvalidException.class, () -> admissionC.viewFeedbackByCourseId(courseId));
		verify(admissionS, times(1)).viewFeedbackByCourseId(courseId);

	}

	// Test whether the end point /deactivate/{courseId} is successful for invalid
	// id
	@Test
	public void test103RestApiCallForDeactivateAdmissionForInvalidId() throws AdmissionInvalidException {

		String invalidCourseId = "INVALID";
		when(admissionS.deactivateAdmission(invalidCourseId)).thenThrow(AdmissionInvalidException.class);

		// Act and Assert
		assertThrows(AdmissionInvalidException.class, () -> admissionC.deactivateAdmission(invalidCourseId));
		verify(admissionS, times(1)).deactivateAdmission(invalidCourseId);

	}

	// Test whether the end point /deactivate/{courseId} is successful for invalid
	// token
	@Test
	public void test103RestApiCallForDeactivateAdmissionForInvalidToken() throws AdmissionInvalidException {

		String courseId = "C001";
		String invalidToken = "INVALID_TOKEN";
		when(admissionS.deactivateAdmission(courseId)).thenThrow(AdmissionInvalidException.class);

		// Act and Assert
		assertThrows(AdmissionInvalidException.class, () -> admissionC.deactivateAdmission(courseId));
		verify(admissionS, times(1)).deactivateAdmission(courseId);

	}

	// Test whether the end point /calculateFees/{associateId} is successful for
	// invalid id
	@Test
	public void test107RestApiCallForCalculateFeesForInvalidId() throws AdmissionInvalidException {

		String invalidAssociateId = "INVALID";
		when(admissionS.calculateFees(invalidAssociateId)).thenThrow(AdmissionInvalidException.class);

		// Act and Assert
		assertThrows(AdmissionInvalidException.class, () -> admissionC.calculateFees(invalidAssociateId));
		verify(admissionS, times(1)).calculateFees(invalidAssociateId);

	}

	// Test whether the end point /calculateFees/{associateId} is successful for
	// invalid token
	@Test
	public void test107RestApiCallForCalculateFeesForInvalidToken() throws AdmissionInvalidException {

		String associateId = "A001";
		String invalidToken = "INVALID_TOKEN";
		when(admissionS.calculateFees(associateId)).thenThrow(AdmissionInvalidException.class);

		// Act and Assert
		assertThrows(AdmissionInvalidException.class, () -> admissionC.calculateFees(associateId));
		verify(admissionS, times(1)).calculateFees(associateId);

	}

}
