package com.customer.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.Model.Customer;
import com.customer.service.CustomerService;



@RestController
public class CustomerController {

	
	
	@Autowired
	CustomerService service;
	
	
	
	@PostMapping("/addCustomer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer cus) throws Exception {
		Customer msg = service.addCustomer(cus);

		return new ResponseEntity<Customer>(new HttpHeaders(), HttpStatus.OK);

	}

	
	@PutMapping("/update/{customerId}")
	public ResponseEntity<?> updateCustomer(@PathVariable("customerId") int customerId, @RequestBody Customer customer)
			throws Exception {
		Customer c = service.updateCustomer(customerId, customer);
		if (c != null) {
			return new ResponseEntity<>(c, HttpStatus.OK);
		} else {
			throw new Exception();
		}
		//return new ResponseEntity<Customer>(new HttpHeaders(), HttpStatus.OK);

	}

	
	
	
	@GetMapping("/getAllCustomers")
	public ResponseEntity<?> getAllCustomers() throws Exception {
		
		List<Customer> list = service.getAllCustomers();
		return new ResponseEntity<>(list, HttpStatus.FOUND);
		
	
	}

	@GetMapping("/findCustomer/{customerId}")
	public ResponseEntity<?> findCustomerbyId(@PathVariable("customerId") int customerId) throws NotFoundException {
		Customer customer = service.findCustomerbyId(customerId);
		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} 
		else {
			throw new NotFoundException(); 
		}
	}
	
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") int customerId) throws NotFoundException {
		String msg = service.deleteCustomer(customerId);
		if (msg != null) {
			return new ResponseEntity<>("Deleted", HttpStatus.OK);
		} else {
			throw new NotFoundException();
		}
	}
}
