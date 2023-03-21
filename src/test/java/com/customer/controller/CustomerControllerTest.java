package com.customer.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.customer.Controller.CustomerController;
import com.customer.Model.Customer;
import com.customer.service.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ComponentScan(basePackages = "com.customer") 
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes= {CustomerControllerTest.class})
class CustomerControllerTest {
	
	
	@Autowired
	MockMvc mockmvc;
	
	
	@Mock
	private CustomerServiceImpl service;
	
	@InjectMocks
	private CustomerController controller;
	
	@Mock
	private CustomerController control;
	
	@Autowired
	private ObjectMapper objectmapper;
	
	@BeforeEach
	public void setup()
	{
		mockmvc =MockMvcBuilders.standaloneSetup(controller).build();
		
	}
	
	
	@Test
	void testGetAllCustomers() throws Exception {
		
		List<Customer> customer = new ArrayList<Customer>();
		customer.add(new Customer(1, "sree", "kanth", "Telangana", 998870030, "sreekanth@gmail.com"));
		
		when(service.getAllCustomers()).thenReturn(customer);
		this.mockmvc.perform(get("/getAllCustomers"))
					.andExpect(status().isFound())
					.andDo(print());
		
		}
	 

	@Test
	void testFindCustomerbyId() throws Exception, Exception {
		Customer customer = new Customer(1, "sree", "kanth", "Telangana", 998870030, "sreekanth@gmail.com");
		
		int customerId=1;
		
		when(service.findCustomerbyId(customerId)).thenReturn(customer);
		this.mockmvc.perform(get("/findCustomer/{customerId}",customerId))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".customerId").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath(".firstName").value("sree"))
		.andExpect(MockMvcResultMatchers.jsonPath(".lastName").value("kanth"))
		.andExpect(MockMvcResultMatchers.jsonPath(".address").value("Telangana"))
		.andExpect(MockMvcResultMatchers.jsonPath(".mobileNumber").value(998870030))
		.andExpect(MockMvcResultMatchers.jsonPath(".mailId").value("sreekanth@gmail.com"))
		.andDo(print());
		
		
		/*when(service.findCustomerbyId(Mockito.anyInt())).thenReturn(null);
		this.mockmvc.perform(("/findCustomer/{customerId}",Mockito.anyInt()))
		.andExpect(status().isNotFound());*/
		
		//when(service.findCustomerbyId(Mockito.anyInt())).thenReturn(null);
		//this.mockmvc.perform("/findCustomer/{customerId}",0)))
		
		//Assert.assertSame(new Exception(),service.findCustomerbyId(customerId));

}
	
	@Test
	void testFindCustomerbyIdfail() throws Exception{
		Customer customer = new Customer(1, "sree", "kanth", "Telangana", 998870030, "sreekanth@gmail.com");
		
		int customerId=1;
		
		when(service.findCustomerbyId(Mockito.anyInt())).thenReturn(null);
		controller.findCustomerbyId(1);
		this.mockmvc.perform(get("/findCustomer/{customerId}",customerId))
		.andExpect((ResultMatcher) new NotFoundException());
		//Mockito.doThrow(new NotFoundException()).when(control).findCustomerbyId(Mockito.anyInt());
		//Assert.assertSame(new Exception(),service.findCustomerbyId(customerId));


		
	}
	
	@Test
	void testDeleteCustomer() throws Exception{
   Customer customer = new Customer(1, "sree", "kanth", "Telangana", 998870030, "sreekanth@gmail.com");
		
   		when(service.deleteCustomer(Mockito.anyInt())).thenReturn("Deleted");
		this.mockmvc.perform(delete("/delete/{customerId}",1))
		.andExpect(status().isOk());
		
	}
	
	@Test
	void testAddCustomer() throws JsonProcessingException, Exception {
		   Customer customer = new Customer(1, "sree", "kanth", "Telangana", 998870030, "sreekanth@gmail.com");
		   
		   	when(service.addCustomer(Mockito.any(Customer.class))).thenReturn(customer);
		   	this.mockmvc.perform(post("/addCustomer")
		   			.contentType(MediaType.APPLICATION_JSON_UTF8)
		   			.content(objectmapper.writeValueAsString(customer)))
		   			.andExpect(status().isOk())
		   			.andDo(print());
		   				
	}
	
	@Test
	void testUpdateCustomer() throws Exception{
		   Customer customer = new Customer(1, "sree", "kanth", "Telangana", 998870030L, "sreekanth@gmail.com");
		   customer.setMobileNumber(998870030L);
		   when(service.updateCustomer(Mockito.anyInt(),Mockito.any(Customer.class))).thenReturn(customer);
		   this.mockmvc.perform(((MockHttpServletRequestBuilder) put("/update/{customerId}",1))
				   		.contentType(MediaType.APPLICATION_JSON_UTF8)
				   		.content(objectmapper.writeValueAsString(customer)))
		   				.andExpect(status().isOk())
		   				
		   				
		   				/*.andExpect(MockMvcResultMatchers.jsonPath(".customerId").value(1))
		   				.andExpect(MockMvcResultMatchers.jsonPath(".firstName").value("sree"))
		   				.andExpect(MockMvcResultMatchers.jsonPath(".lastName").value("kanth"))
		   				.andExpect(MockMvcResultMatchers.jsonPath(".address").value("Telangana"))
		   				.andExpect(MockMvcResultMatchers.jsonPath(".mobileNumber").value(998870030))
		   				.andExpect(MockMvcResultMatchers.jsonPath(".mailId").value("sreekanth@gmail.com"))
		   				.andDo(print());*/
		   				
		   				.andExpect(jsonPath("$.firstName", is(customer.getFirstName())))
		   				.andExpect(jsonPath("$.lastName", is(customer.getLastName())))
		   				.andExpect(jsonPath("$.address", is(customer.getAddress())))
		   				.andExpect(jsonPath("$.mobileNumber").value(998870030))
		   				.andExpect(jsonPath("$.mailId", is(customer.getMailId())))		
		   				.andDo(print());
		   
		   /*	when(service.updateCustomer(Mockito.any(Customer.class),anyInt())).thenReturn(customer);
		   	this.mockmvc.perform(put("/update/{customerId}",customerId)	   				
		   				.contentType(MediaType.APPLICATION_JSON_UTF8)
		   				.content(objectmapper.writeValueAsString(customer)))
		   				.andExpect(status().isOk())
		   				.andDo(print());*/
			
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
