package com.customer.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.Model.Customer;
import com.customer.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	
	

	
	@Override
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
		
	}


	

	@Override
	public String deleteCustomer(int customerId) {

		Optional<?> del = customerRepository.findById(customerId);
		if (del.isPresent()) {
			customerRepository.deleteById(customerId);;
			return "Deleted account" ;
		} else {
			return null;
		}
	}

	
	
	@Override
	public List<Customer> getAllCustomers() {
		
		List<Customer> list = customerRepository.findAll();
		if (list == null) {
			return null;
		} else {
			return list;
		}
		
		
	}

	
	
	@Override
	public Customer findCustomerbyId(int customerId) {

		Optional<?> optional = customerRepository.findById(customerId);
		if (optional.isPresent()) {
			return (Customer) optional.get();
		} else {
			return null;
		}
			
	}



	@Override
	public Customer updateCustomer(int customerId, Customer customer) {
		Customer c = null;
		Optional<?> cust = customerRepository.findById(customerId);
		if (cust.isPresent()) {
			c = (Customer) cust.get();
			c.setMobileNumber(customer.getMobileNumber());
			c.setAddress(customer.getAddress());
			c.setLastName(customer.getLastName());
			c.setMailId(customer.getMailId());

			return customerRepository.save(c);
		}
		return null;
	}
	
	
	


}
