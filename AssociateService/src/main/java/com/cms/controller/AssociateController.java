 package com.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.service.IAssociateService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AssociateController {

	RestTemplate restTemplate;
	
	@Autowired
	IAssociateService associateService;
	
	@PostMapping("/associate/addAssociate")
	public Associate addAssociate(@RequestBody Associate associate) throws AssociateInvalidException
	{
		System.out.println(associate);
		return associateService.addAssociate(associate);
	}
	
	@PutMapping("/associate/updateAssociate/{associateId}/{associateAddr}")
	public Associate updateAssociate(@PathVariable String associateId,@PathVariable String associateAddr) throws AssociateInvalidException
	{
		return associateService.updateAssociate(associateId,associateAddr);
	}
	
	@GetMapping("/associate/viewByAssociateId/{associateId}")
	public Associate viewByAssociateId(@PathVariable String associateId) throws AssociateInvalidException
	{
		return associateService.viewByAssociateId(associateId);
	}
	@GetMapping("/associate/viewAll")
	public List<Associate> viewAll()
	{
		return associateService.viewAll();
	}
	
}





