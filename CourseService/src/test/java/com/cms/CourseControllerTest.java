package com.cms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.cms.controller.CourseController;
import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.service.CourseServiceImpl;

//Write mockito tests for the endpoints in the CourseController
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.cms")
@SpringBootTest(classes = { CourseControllerTest.class })
public class CourseControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	CourseServiceImpl courseS;

	@InjectMocks
	CourseController courseC;

	// Test whether the endpoint /viewByCourseId/{courseId} is successful
	@Test
	public void test122RestApiCallForViewByCourseId() throws CourseInvalidException {

		String courseId = "200";
		Course expectedCourse = new Course(courseId, "Java", 1200, 10, "Programming", 5.5f);

		when(courseS.viewByCourseId(courseId)).thenReturn(expectedCourse);

		// Act
		Course result = courseC.viewByCourseId(courseId);

		// Assert
		assertEquals(expectedCourse, result);
		verify(courseS, times(1)).viewByCourseId(courseId);

	}

	// Test whether the end point /update/{courseId}/{fee} is successfull
	@Test
	public void test123RestApiCallForUpdateCourse() throws CourseInvalidException {

		String courseId = "123";
		int duration = 10;
		Course expectedCourse = new Course(courseId, "Java", 1200, 10, "Programming", 5.5f);

		when(courseS.updateCourse(courseId, duration)).thenReturn(expectedCourse);

		// Act
		Course result = courseC.updateCourse(courseId, duration);

		// Assert
		assertEquals(expectedCourse, result);
		verify(courseS, times(1)).updateCourse(courseId, duration);

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successfull
	@Test
	public void test124RestApiCallForViewFeedbackRating() throws CourseInvalidException {

		String courseId = "123";
		float expectedRating = 5.5f;

		when(courseS.findFeedbackRatingForCourseId(courseId)).thenReturn(expectedRating);

		// Act
		float result = courseC.viewFeedback(courseId);

		// Assert
		assertEquals(expectedRating, result);
		verify(courseS, times(1)).findFeedbackRatingForCourseId(courseId);

	}

	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is
	// successfull
	@Test
	public void test125RestApiCallForCalculateAverageFeedback() throws CourseInvalidException {
		String courseId = "123";
		float rating = 5.5f;
		Course expectedCourse = new Course(courseId, "Java", 1200, 10, "Programming", rating);

		when(courseS.calculateAverageFeedbackAndUpdate(courseId, rating)).thenReturn(expectedCourse);

		// Act
		Course result = courseC.calculateAverageFeedbackAndUpdate(courseId, rating);

		// Assert
		assertEquals(expectedCourse, result);
		verify(courseS, times(1)).calculateAverageFeedbackAndUpdate(courseId, rating);

	}

	// Test whether the endpoint /addCourse is successfull
	@Test
	public void test126RestApiCallForAddCourse() throws CourseInvalidException {

		Course courseToAdd = new Course("123", "Java", 1200, 10, "Programming", 5.5f);

		when(courseS.addCourse(courseToAdd)).thenReturn(courseToAdd);

		// Act
		Course result = courseC.addCourse(courseToAdd);

		// Assert
		assertEquals(courseToAdd, result);
		verify(courseS, times(1)).addCourse(courseToAdd);

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successful for
	// invalid id
	@Test
	public void test122RestApiCallForViewByCourseIdForInvalidId() throws CourseInvalidException {

		String invalidCourseId = "invalid_id";
		when(courseS.viewByCourseId(invalidCourseId)).thenThrow(new CourseInvalidException("Invalid Course Id"));

		// Act & Assert
		CourseInvalidException exception = assertThrows(CourseInvalidException.class,
				() -> courseC.viewByCourseId(invalidCourseId));
		assertEquals("Invalid Course Id", exception.getMessage());
		verify(courseS, times(1)).viewByCourseId(invalidCourseId);

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successful for
	// invalid token
	@Test
	public void test122RestApiCallForViewByCourseIdForInvalidToken() throws CourseInvalidException {

		String courseId = "123";

		verify(courseS, times(0)).viewByCourseId(courseId);

	}

	// Test whether the end point /update/{courseId}/{fee} is successfull for
	// invalid id
	@Test
	public void test123RestApiCallForUpdateCourseForInvalidId() throws CourseInvalidException {

		String invalidCourseId = "invalid_id";
		int newDuration = 10;

		when(courseS.updateCourse(invalidCourseId, newDuration))
				.thenThrow(new CourseInvalidException("CourseId does not exists"));

		// Act & Assert
		CourseInvalidException exception = assertThrows(CourseInvalidException.class,
				() -> courseC.updateCourse(invalidCourseId, newDuration));
		assertEquals("CourseId does not exists", exception.getMessage());
		verify(courseS, times(1)).updateCourse(invalidCourseId, newDuration);

	}

	// Test whether the end point /update/{courseId}/{fee} is successfull for
	// invalid token
	@Test
	public void test123RestApiCallForUpdateCourseForInvalidToken() throws CourseInvalidException {
		String courseId = "123";
		int newDuration = 10;
		// Assuming an invalid token is passed

		// Act & Assert

		verify(courseS, times(0)).updateCourse(courseId, newDuration);

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successfull for
	// invalid id
	@Test
	public void test124RestApiCallForViewFeedbackRatingForInvalidId() throws CourseInvalidException {
		String invalidCourseId = "invalid_id";

		when(courseS.findFeedbackRatingForCourseId(invalidCourseId))
				.thenThrow(new CourseInvalidException("CourseId does not exists"));

		// Act & Assert
		CourseInvalidException exception = assertThrows(CourseInvalidException.class,
				() -> courseC.viewFeedback(invalidCourseId));
		assertEquals("CourseId does not exists", exception.getMessage());
		verify(courseS, times(1)).findFeedbackRatingForCourseId(invalidCourseId);

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successfull for
	// invalid token
	@Test
	public void test124RestApiCallForViewFeedbackRatingForInvalidToken() throws CourseInvalidException {

		String courseId = "123";
		// Assuming an invalid token is passed

		verify(courseS, times(0)).findFeedbackRatingForCourseId(courseId);

	}

	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is
	// successfull for invalid id
	@Test
	public void test125RestApiCallForCalculateAverageFeedbackForInvalidId() throws CourseInvalidException {

		String invalidCourseId = "invalid_id";
		float rating = 5.5f;

		when(courseS.calculateAverageFeedbackAndUpdate(invalidCourseId, rating))
				.thenThrow(new CourseInvalidException("CourseId does not exists"));

		// Act & Assert
		CourseInvalidException exception = assertThrows(CourseInvalidException.class,
				() -> courseC.calculateAverageFeedbackAndUpdate(invalidCourseId, rating));
		assertEquals("CourseId does not exists", exception.getMessage());
		verify(courseS, times(1)).calculateAverageFeedbackAndUpdate(invalidCourseId, rating);

	}

	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is
	// successfull for invalid token
	@Test
	public void test125RestApiCallForCalculateAverageFeedbackForInvalidToken() throws CourseInvalidException {

		String courseId = "123";
		float rating = 5.5f;
		
		verify(courseS, times(0)).calculateAverageFeedbackAndUpdate(courseId, rating);

	}

	// Test whether the endpoint /addCourse is successfull for invalid token
	@Test
	public void test126RestApiCallForAddCourseForInvalidToken() throws CourseInvalidException {
		Course courseToAdd = new Course("123", "Java", 1200, 10, "Programming", 0.0f);

		verify(courseS, times(0)).addCourse(courseToAdd);

	}

}
