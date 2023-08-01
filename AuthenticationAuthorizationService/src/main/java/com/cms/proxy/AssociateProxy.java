package com.cms.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cms.model.Associate;

@FeignClient(name="associateapp",url="http://localhost:9092")
public interface AssociateProxy {
	
	@PostMapping("/associate/addAssociate")
	public ResponseEntity<Associate> addAssociate(@RequestBody Associate associate);
	
	@PutMapping("/associate/updateAssociate/{associateId}/{associateAddr}")
	public ResponseEntity<Associate> updateAssociate(@PathVariable String associateId,@PathVariable String associateAddr);
	
	@GetMapping("/associate/viewByAssociateId/{associateId}")
	public ResponseEntity<Associate> viewByAssociateId(@PathVariable String associateId);
	
	@GetMapping("/associate/viewAll")
	public ResponseEntity<List<Associate>> viewAll();
}
