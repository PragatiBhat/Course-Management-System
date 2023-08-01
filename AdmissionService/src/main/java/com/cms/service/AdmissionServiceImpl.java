package com.cms.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.repository.AdmissionRepository;

@Service
public class AdmissionServiceImpl implements IAdmissionService {

	@Autowired
	AdmissionRepository admissionR;
	
	@Autowired
	SequenceGeneratorService sequenceGen;
	
	public Admission registerAssociateForCourse(Admission admission)throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		List<Admission> admissi=admissionR.findAll();
		for(Admission admis:admissi)
		{
			if(admis.getRegistrationId()==admission.getRegistrationId())
			{
				throw new AdmissionInvalidException("RegistrationNumber already exists");
			}
		}
		admission.setRegistrationId(sequenceGen.generateSequence(Admission.SEQUENCE_NAME));
		admissionR.save(admission);
		return admission;
	}

	public int calculateFees(String associateId)throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		List<Admission> admiss=admissionR.findByAssociateId(associateId);
		if(admiss==null)
		{
			throw new AdmissionInvalidException("AssociateId does not exists");
		}
		int product=0;
		for(Admission admis:admiss)
		{
			product=product+admis.getFees();
		}
		return product;
	}

	
	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {
		
		Admission admiss=admissionR.findById(regNo).orElseThrow(()-> new AdmissionInvalidException("Invalid Registration Id"));
		/*if(admiss==null)
		{
			throw new AdmissionInvalidException("Invalid Registration Id");
		}*/
		admiss.setFeedback(feedback);
		admissionR.save(admiss);
		return admiss;
	}

	public List<String> highestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		List<Admission> admiss=admissionR.findByAssociateId(associateId);
		if(admiss==null)
		{
			throw new AdmissionInvalidException("AssociateId does not exists");
		}
		int highestFee=0;
		for(Admission admissi:admiss)
		{
			if(highestFee<admissi.getFees())
			{
				highestFee=admissi.getFees();
			}
		}
		List<String> highestFees=new ArrayList<>();
		for(Admission admissi:admiss)
		{
			if(admissi.getFees()==highestFee)
			{
				highestFees.add(admissi.getCourseId());
			}
		}
		return highestFees;
		
	}

	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		List<Admission> admiss=admissionR.findByCourseId(courseId);
		System.out.println(admiss);
		if(admiss==null)
		{
			throw new AdmissionInvalidException("AssociateId does not exists");
		}
		List<String> feedback=new ArrayList<>();
		for(Admission admis:admiss)
		{
			feedback.add(admis.getFeedback());
		}
		return feedback;
		
	}
	
	public Admission getById(long regNo) throws AdmissionInvalidException{
		Admission admiss=admissionR.findById(regNo).orElseThrow(()-> new AdmissionInvalidException("Invalid Registration Id"));
		
		return admiss;
	}

	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		List<Admission> admiss=admissionR.findByCourseId(courseId);
		if(admiss==null)
		{
			throw new AdmissionInvalidException("CourseId does not exists");
		}
		for(Admission admis:admiss)
		{
			admissionR.delete(admis);
		}
		return true;
	}

	public boolean makePayment(int registartionId) throws AdmissionInvalidException{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Admission> viewAll() {
		// TODO Auto-generated method stub
		return admissionR.findAll();
	}

}
