package com.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.customer.Model.Customer;

@Service
public interface CustomerService {

	

	
	public List<Customer> getAllCustomers();
	
	public Customer addCustomer(Customer customer);

	public Customer findCustomerbyId(int customerId);
	
	public Customer updateCustomer(int customerId, Customer customer);
	
	public String deleteCustomer(int customerId);

}

