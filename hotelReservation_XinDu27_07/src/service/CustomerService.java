package service;

import model.Customer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;


public class CustomerService {
    private static CustomerService customerService;
    public Set<Customer> customerSet;

    private CustomerService() {
        customerSet = new LinkedHashSet<>();
    }

    public static CustomerService getInstance() {    //static reference
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }


    public void addCustomer(String firstName, String lastName, String email) {
        if (customerSet.isEmpty()) {
            customerSet.add(new Customer(firstName, lastName, email));
        } else {
            for (Customer customer : customerSet) {
                if (customer.getEmail().equals(email)) {
                    System.out.println("Account: " + email + " already exists");
                    return;
                }
            }
            customerSet.add(new Customer(firstName, lastName, email));

        }
    }

    public Customer getCustomer(String customerEmail) {
        for (Customer customer : customerSet) {
            if (customer.getEmail().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        return customerSet;
    }


    public void deleteCustomer(String customerEmail) {
        customerSet.removeIf((c) -> c.getEmail().equals(customerEmail));
    }
}





