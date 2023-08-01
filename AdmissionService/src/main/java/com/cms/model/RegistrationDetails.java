package com.cms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationDetails {

	private long registrationid;
	private String courseId;
	private String associateId;
	private String regDate;
}
