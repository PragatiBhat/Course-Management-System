
package com.cms.model;

import lombok.Data;

@Data
public class Course {
	
	private String courseId;
	
	private String courseName;
	
	private Integer fees;
	
	private Integer duration;
	
	
	private String courseType;
	private float rating;

}
