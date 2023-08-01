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

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;
import com.cms.service.CourseServiceImpl;

//Write Unit Tests for the methods in the CourseServiceImpl
@SpringBootTest(classes={CourseServiceImplTest.class})
public class CourseServiceImplTest {
	
	@Mock
	CourseRepository courseR;
	
	@InjectMocks
	CourseServiceImpl courseS;
	
//check whether the addCourse persists the course object in the database	
	@Test
	public void test127AddCourse() {
		
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findAll()).thenReturn(myCourse);
		assertEquals("Python", courseS.addCourse(myCourse.get(0)));

	}
	
//check whether viewByCourseId returns the course for the given courseId	
	@Test
	public void test128ViewByCourseId() throws CourseInvalidException {
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals("Python", courseS.viewByCourseId(String.valueOf(200)).getCourseName());
	}
	
//check whether the findFeedbackRatingForCourseId	returns the feedback rating for the given courseId
	@Test
	public void test129FindFeedbackRatingForCourseId() throws CourseInvalidException {
		final double DELTA = 1e-15;
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals(4, courseS.findFeedbackRatingForCourseId(String.valueOf(200)),DELTA);
	}
	
	//check whether updateCourse updates the fees for the given courseId in the database
	@Test
	public void test130UpdateCourse() throws CourseInvalidException {
		
		final double DELTA = 1e-15;
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals(40000, courseS.updateCourse(String.valueOf(200),40000).getFees(),DELTA);
	}
	
//check whether the calculateAverageFeedbackAndUpdate calculates the average feedback rating with the given rating and existing rating in the database for the given courseId and updates in the database	
	@Test
	public void test131CalculateAverageFeedbackAndUpdate() throws CourseInvalidException {
		
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals(4, courseS.calculateAverageFeedbackAndUpdate(String.valueOf(200),4));
	}
	
//check whether the deactivateCourse removes the course of the given courseId in the database	
	@Test
	public void test132DeactivateCourse() throws CourseInvalidException {
		
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals("Python", courseS.deactivateCourse(String.valueOf(200)).getCourseName());
	}
	
//check whether viewByCourseId throws CourseInvalidException when an invalid courseid is passed	
	@Test
	public void test133ViewByCourseIdForInvalidId() throws CourseInvalidException {
		
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals("Python", courseS.viewByCourseId(String.valueOf(201)).getCourseName());
	}
	
	
	//check whether updateCourse throws CourseInvalidException for invalid course id
	@Test
	public void test135updateCourseInvalidId() throws CourseInvalidException {
		
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals(50000, courseS.updateCourse(String.valueOf(201),50000).getCourseName());
	}
	
	//check whether calculateAverageFeedbackAndUpdate throws CourseInvalidExcception for invalid course id
	@Test
	public void test136calculateAverageFeedbackAndUpdateForInvalidId() throws CourseInvalidException {
		
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals(5, courseS.calculateAverageFeedbackAndUpdate(String.valueOf(201),4));
	}
	//check whether findFeedbackRating throws CourseInvalidExcception for invalid course id
	@SuppressWarnings("deprecation")
	@Test
	public void test137findFeedbackRatingForCourseIdForInvalidId() throws CourseInvalidException {
		
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals(5, courseS.findFeedbackRatingForCourseId(String.valueOf(201)));
	}
	
	//check whether deactivateCourse throws CourseInvalidExcception for invalid course id
	@Test
	public void test138deactivateCourseForInvalidId() throws CourseInvalidException {
		
		List<Course> myCourse=new ArrayList<>();
		myCourse.add(new Course("200","Python",10000,5,"Online",4));
		
		
		when(courseR.findById("200")).thenReturn(Optional.of(myCourse.get(0)));
		assertEquals("Python", courseS.deactivateCourse(String.valueOf(201)).getCourseName());
	}
	
	

}
