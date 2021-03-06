package com.kcbgroup.main.kcbcustomercreate.service;

import com.kcbgroup.main.kcbcustomercreate.entity.Customer;
import com.kcbgroup.main.kcbcustomercreate.repository.CustomerRepository;
import com.kcbgroup.main.kcbcustomercreate.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements  CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(String identificationNumber, Customer customerUpdate) throws ResourceNotFoundException {
        Customer customer = customerRepository.findByIdentificationNumber(identificationNumber).orElseThrow(()-> new ResourceNotFoundException("The customer with id: "+ identificationNumber + "has not been found"));
        customer.setFirstName(customerUpdate.getFirstName());
        customer.setLastName(customerUpdate.getLastName());
        customer.setPhoneNumber(customerUpdate.getPhoneNumber());
        return customerRepository.save(customer);
    }

    @Override
    public String deleteCustomer(String identificationNumber) throws ResourceNotFoundException {
        Customer customer = customerRepository.findByIdentificationNumber(identificationNumber).orElseThrow(()-> new ResourceNotFoundException("The customer with id: "+ identificationNumber + "has not been found"));
        customerRepository.delete(customer);
        return("\"The customer with id: \"+ identificationNumber + \"has  been deleted\"");
    }

    @Override
    public Customer findByIdentificationNumber(String identificationNumber)throws ResourceNotFoundException {
        Optional<Customer> result = customerRepository.findByIdentificationNumber(identificationNumber);
        if (result.isPresent()){
            return result.get();
        }else{
            throw new ResourceNotFoundException("The customer with identification Number" + identificationNumber + "has not been found");
        }

    }
}
