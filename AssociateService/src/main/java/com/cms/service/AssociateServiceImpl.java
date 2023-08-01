package com.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;

@Service
public class AssociateServiceImpl implements IAssociateService{
	
	@Autowired
	AssociateRepository associateR;
	
	@Autowired
	SequenceGeneratorService sequenceG;
	
	public Associate addAssociate(Associate cObj) throws AssociateInvalidException {
		// TODO Auto-generated method stub
		List<Associate> associ=associateR.findAll();
		for(Associate asso:associ)
		{
			if(asso.getAssociateId().equals(cObj.getAssociateId()))
			{
				throw new AssociateInvalidException("AssociateId already exists");
			}
		}
		cObj.setAssociateId(String.valueOf(sequenceG.generateSequence(Associate.SEQUENCE_NAME)));
		associateR.save(cObj);
		return cObj;
	}

	public Associate viewByAssociateId(String associateId) throws AssociateInvalidException {
		// TODO Auto-generated method stub
		Associate associ=associateR.findById(associateId).orElseThrow(()-> new AssociateInvalidException("Invalid Associate Id"));
		/*if(associ==null)
		{
			throw new AssociateInvalidException("Invalid Associate Id");
		}*/
		return associ;
	}

	public Associate updateAssociate(String associateId, String associateAddress)throws AssociateInvalidException {
		// TODO Auto-generated method stub
		Associate associ=associateR.findById(associateId).orElseThrow(()-> new AssociateInvalidException("AssociateId does not exists"));
		/*f(associ==null)
		{
			throw new AssociateInvalidException("AssociateId does not exists");
		}*/
		associ.setAssociateAddress(associateAddress);
		associateR.save(associ);
		return associ;
	}

	
	public List<Associate> viewAll() {
		// TODO Auto-generated method stub
		return associateR.findAll();
	}

}

