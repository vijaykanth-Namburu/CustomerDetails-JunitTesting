package com.customer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.customer.Model.Customer;
import com.customer.repository.CustomerRepository;



@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {CustomerServiceTest.class})
class CustomerServiceTest {
	
	
	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;
	
	
	public List<Customer> customer;

		
	
	@Test
	@Order(1)
	void testGetAllCustomers() {
		
		List<Customer> list=new ArrayList<Customer>();
		list.add(new Customer(1, "vijay", "kanth", "Andhrapradesh", 880023190, "vijaykanth3@gmail.com"));
		
		when(customerRepository.findAll()).thenReturn(null);
		assertEquals(null, customerService.getAllCustomers());

		
		
		when(customerRepository.findAll()).thenReturn(list);                       //mocks the external dependency like call i.e dummy repository for getallcustomers method
		assertEquals(1,customerService.getAllCustomers().size());
		
		
		
		
	}

	@Test
	@Order(2)
	void testFindCustomerbyId() {
		
		Customer customer = new Customer(2, "sree", "kanth", "Andhrapradesh", 976565995, "sreekanth@gmail.com");

		int customerId=2;
		
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customer));
		assertEquals(customer,customerService.findCustomerbyId(customerId));
		
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		assertEquals(null, customerService.findCustomerbyId(customerId));
	}
	
	
	
	
	@Test
	@Order(3)
	void testAddCustomer() {
		
		Customer customer = new Customer(2, "sree", "kanth", "Andhrapradesh", 976565995, "sreekanth@gmail.com");
		
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customer,customerService.addCustomer(customer));
	
	}

	
	
	
	
	
	@Test
	@Order(4)
	void testUpdateCustomer() {
		
		Customer customer = new Customer(2, "sree", "kanth", "Andhrapradesh", 976565995, "sreekanth@gmail.com");
		
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customer,customerService.updateCustomer(2,customer));
		
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		assertEquals(null,customerService.updateCustomer(1, customer));
	}
	
	
	
	
	
	@Test
	@Order(5)
	void testDeleteCustomer() {
		
		Customer customer = new Customer(2, "sree", "kanth", "Andhrapradesh", 976565995, "sreekanth@gmail.com");
		
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customer));
		doNothing().when(customerRepository).deleteById(Mockito.anyInt());
		assertEquals("Deleted account" ,customerService.deleteCustomer(1));
		
		
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		assertEquals(null ,customerService.deleteCustomer(1));

	}

	
	
	
	
	
	
	
	
	
	
	
	/*@BeforeEach
	public Customer getCustomer() {
		Customer customer=new Customer(1, "vijay", "kanth", "Andhrapradesh", 998870030, "vijaykanth@gmail.com");
		return customer;}*/
	

}
