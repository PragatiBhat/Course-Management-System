
package com.cms.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="course")
public class Course implements Serializable{
	
	@Transient
    public static final String SEQUENCE_NAME = "course_sequences";
	
	@Id
	private String courseId;
	
	private String courseName;
	private Integer fees;
	private Integer duration;
	
	private String courseType;
	private float rating;

	
}
