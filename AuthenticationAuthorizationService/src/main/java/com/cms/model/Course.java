
package com.cms.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Course {
	
	private String courseId;
	
	private String courseName;
	private Integer fees;
	private Integer duration;
	
	private String courseType;
	private float rating;

	
}
