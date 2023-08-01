package com.cms.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;
@Service
public class CourseServiceImpl implements ICourseService{
	
	@Autowired
	CourseRepository courseR;
	
	
	@Autowired
	SequenceGeneratorService sequenceGen;
	
	@Override
	public Course addCourse(Course cObj) throws CourseInvalidException  {
		
			List<Course> cour=courseR.findAll();
			for(Course cou:cour)
			{
				if(cou.getCourseId().equals(cObj.getCourseId()))
				{
					throw new CourseInvalidException("CourseId already exists");
				}
			}
			cObj.setCourseId(String.valueOf(sequenceGen.generateSequence(Course.SEQUENCE_NAME)));
			courseR.save(cObj);
			return cObj;
	}

	@Override
	public Course updateCourse(String courseId, Integer fees) throws CourseInvalidException {
		Course course=courseR.findById(courseId).orElseThrow(()-> new CourseInvalidException("CourseId does not exists"));
		/*if(course==null)
		{
			throw new CourseInvalidException("CourseId does not exists");
		}*/
		course.setFees(fees);
		courseR.save(course);
		return course;
	}

	@Override
	public Course viewByCourseId(String courseId) throws CourseInvalidException {
		Course course=courseR.findById(courseId).orElseThrow(()-> new CourseInvalidException("Invalid Course Id"));
		/*if(course==null)
		{
			throw new CourseInvalidException("Invalid Course Id");
		}*/
		return course;
	}

	@Override
	public Course calculateAverageFeedbackAndUpdate(String courseId, float rating) throws CourseInvalidException {
		Course course=courseR.findById(courseId).orElseThrow(()-> new CourseInvalidException("CourseId does not exist"));
		/*if(course==null)
		{
			throw new CourseInvalidException("CourseId does not exist");
		}*/
		float rate=(course.getRating()+rating)/2;
		course.setRating(rate);
		courseR.save(course);
		return course;
	}

		
	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException {
		Course course=courseR.findById(courseId).orElseThrow(()-> new CourseInvalidException("CourseId does not exists"));
		/*if(course==null)
		{
			throw new CourseInvalidException("CourseId does not exists");
		}*/
		return course.getRating();
	}

	@Override
	public Course deactivateCourse(String courseId) throws CourseInvalidException {
		
		Course course=courseR.findById(courseId).orElseThrow(()-> new CourseInvalidException("CourseId does not exists"));
		/*if(course==null)
		{
			throw new CourseInvalidException("CourseId does not exists");
		}*/
		courseR.delete(course);
		return course;
		
	}

	@Override
	public List<Course> viewAll() {
		
		return courseR.findAll();
	}
	

}
