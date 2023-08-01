package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cms.model.Associate;

@FeignClient(name="associateapp",url="http://localhost:9092")
public interface ServiceProxy {
	@GetMapping("/associate/viewByAssociateId/{associateId}")
	public Associate viewByAssociateId(@PathVariable String associateId);
}