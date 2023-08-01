package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="admissionapp",url="http://localhost:9093")
public interface AdmissionProxy {
	
	@DeleteMapping("/admission/deactivate/{courseId}")
	public boolean deactivateAdmission(@PathVariable String courseId);

}
