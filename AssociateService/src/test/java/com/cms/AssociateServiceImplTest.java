package com.cms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;
import com.cms.service.AssociateServiceImpl;

//Write Unit Tests for the methods in the AssociateServiceImpl
@SpringBootTest(classes= {AssociateServiceImplTest.class})
public class AssociateServiceImplTest {

	@Mock
	AssociateRepository associateR;
	
	@InjectMocks
	AssociateServiceImpl associateS;
	
	
	
	//check whether the addA ssociate persists the associate object in the database
	@Test
	public void test118AddAssociate() throws AssociateInvalidException {
	 Associate myassociate=new Associate("100","pragati","KK","Abc");
	 
	when(associateR.save(myassociate)).thenReturn(myassociate);
	assertEquals(myassociate, associateS.addAssociate(myassociate));
	}
	
	//check whether the viewByAssociateId returns the associate for the given associate Id
	@Test
	public void test119ViewByAssociateId() throws AssociateInvalidException {
		List<Associate> myassociate=new ArrayList<>();
		myassociate.add(new Associate("100","pragati","KK","Abc"));
		
		
		when(associateR.findById("100")).thenReturn(Optional.of(myassociate.get(0)));
		assertEquals("KK", associateS.viewByAssociateId(String.valueOf(100)).getAssociateAddress());
		
	}
	
	//check whether updateAssociate updates the address of the given assciateId in the database
	@Test
	public void test120updateAssociate() throws AssociateInvalidException {
		List<Associate> myassociate=new ArrayList<>();
		myassociate.add(new Associate("100","pragati","KK","Abc"));
		when(associateR.findById("100")).thenReturn(Optional.of(myassociate.get(0)));
		assertEquals("Pune", associateS.updateAssociate(String.valueOf(100),"Vashi").getAssociateAddress());
		
	}
	
	//check whether viewByAssociateId throws AssociateInvalidException for invalid associateId
	@Test
	public void test121ViewByAssociateIdForInvalidId() throws AssociateInvalidException {
		
		List<Associate> myassociate=new ArrayList<>();
		myassociate.add(new Associate("100","pragati","KK","Abc"));
		when(associateR.findById("100")).thenReturn(Optional.of(myassociate.get(0)));
		assertEquals("KK", associateS.viewByAssociateId(String.valueOf(101)).getAssociateAddress());
		
		
		
	}
	//check whether updateAssociate updates the address of the given assciateId in the database for invalid id
	@Test
	public void test120UpdateassociateForInvalidId() throws AssociateInvalidException {
		
		List<Associate> myassociate=new ArrayList<>();
		myassociate.add(new Associate("100","pragati","KK","Abc"));
		when(associateR.findById("100")).thenReturn(Optional.of(myassociate.get(0)));
		assertEquals("KK", associateS.updateAssociate(String.valueOf(101),"Vashi").getAssociateAddress());
		

	}
}