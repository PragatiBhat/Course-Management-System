package com.cms;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cms.controller.AssociateController;
import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.service.AssociateServiceImpl;

//Write mockito tests for the endpoints in the AssociateController
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.cms")
@SpringBootTest(classes = { AssociateControllerTest.class })
public class AssociateControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	AssociateServiceImpl associateS;

	@InjectMocks
	AssociateController associateC;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(associateC).build();
	}

	// Test whether the endpoint /viewByAssociateId/{associateId} is successful
	@Test
	public void test115RestApiCallForViewByAssociateId() throws Exception {

		List<Associate> myAssociate = new ArrayList<>();
		myAssociate.add(new Associate("100", "pragati", "KK", "Abc"));

		when(associateS.viewByAssociateId("100")).thenReturn(myAssociate.get(0));

		mockMvc.perform(get("/associate/viewByAssociateId/{associateId}", "101")).andExpect(status().isOk())
				.andDo(print());

	}

	// Test whether the end point /updateAssociate/{associateId}/{associateAddress}
	// is successful
	@Test
	public void test116RestApiCallForUpdateAssociate() throws Exception {

		List<Associate> myAssociate = new ArrayList<>();
		myAssociate.add(new Associate("100", "pragati", "KK", "Abc"));

		when(associateS.updateAssociate("100", "Vashi")).thenReturn(myAssociate.get(0));

		mockMvc.perform(put("/associate/updateAssociate/{associateId}/{associateAddr}", "100", "Vashi"))
				.andExpect(status().isOk()).andDo(print());

	}

	// Test whether the endpoint /addAssociate is successful
	@Test
	public void test117RestApiCallForAddAssociate() throws Exception {

		List<Associate> myAssociate = new ArrayList<>();
		myAssociate.add(new Associate("100", "pragati", "KK", "Abc"));

		when(associateS.addAssociate(myAssociate.get(0))).thenReturn(myAssociate.get(0));

		mockMvc.perform(post("/associate/addAssociate")).andExpect(status().isOk()).andDo(print());
	}

	// Test whether the endpoint /viewByAssociateId/{associateId} is successful for
	// invalid token
	@Test
	public void test115RestApiCallForViewByAssociateIdForInvalidToken() throws AssociateInvalidException {
		String associateId = "123";

		when(associateS.viewByAssociateId(associateId)).thenThrow(AssociateInvalidException.class);

		// Act and Assert
		assertThrows(AssociateInvalidException.class, () -> associateC.viewByAssociateId(associateId));
		verify(associateS, times(1)).viewByAssociateId(associateId);
	}

	// Test whether the endpoint /viewByAssociateId/{associateId} is successful for
	// invalid id
	@Test
	public void test115RestApiCallForViewByAssociateIdForInvalidId() throws AssociateInvalidException {
		
		String invalidAssociateId = "invalid";
		AssociateInvalidException exception = new AssociateInvalidException("Invalid Associate ID");
		when(associateS.viewByAssociateId(invalidAssociateId)).thenThrow(exception);

		// Act
		try {
			Associate result = associateC.viewByAssociateId(invalidAssociateId);
		} catch (AssociateInvalidException e) {
			// Assert
			assertEquals("Invalid Associate ID", e.getMessage());
			verify(associateS, times(1)).viewByAssociateId(invalidAssociateId);
		}

	}

	// Test whether the end point /updateAssociate/{associateId}/{associateAddress}
	// is successful for invalid token
	@Test
	public void test116RestApiCallForUpdateAssociateForInvalidToken() throws AssociateInvalidException {
		
		String associateId = "123";
        String updatedAddress = "Nagpur";

        when(associateS.updateAssociate(associateId, updatedAddress)).thenThrow(AssociateInvalidException.class);

        // Act & Assert
        assertThrows(AssociateInvalidException.class, () -> associateC.updateAssociate(associateId, updatedAddress));

        verify(associateS, times(1)).updateAssociate(associateId, updatedAddress);

	}

	// Test whether the end point /updateAssociate/{associateId}/{associateAddress}
	// is successful for invalid id
	@Test
	public void test116RestApiCallForUpdateAssociateForInvalidId() throws AssociateInvalidException {
		
		String associateId = "invalid";
        String updatedAddress = "Nagpur";

        when(associateS.updateAssociate(associateId, updatedAddress)).thenThrow(AssociateInvalidException.class);

        // Act & Assert
        assertThrows(AssociateInvalidException.class, () -> associateC.updateAssociate(associateId, updatedAddress));

        verify(associateS, times(1)).updateAssociate(associateId, updatedAddress);

	}
	@Test
	public void test117RestApiCallForAddAssociateForInvalidToken() throws AssociateInvalidException {
		
		Associate associateToAdd = new Associate("123", "Ram", "Nagpur", "ram.@example.com");

        when(associateS.addAssociate(associateToAdd)).thenThrow(AssociateInvalidException.class);

        // Act & Assert
        assertThrows(AssociateInvalidException.class, () -> associateC.addAssociate(associateToAdd));

        verify(associateS, times(1)).addAssociate(associateToAdd);

	}

}