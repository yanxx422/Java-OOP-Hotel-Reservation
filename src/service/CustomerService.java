package service;

import model.Customer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class CustomerService {
    private static CustomerService customer_service;
    private Set<Customer> Customer_list = new HashSet<Customer>();

    private CustomerService(){}

    public static CustomerService getInstance(){
        //lazy initialization
        if (customer_service == null){
            customer_service = new CustomerService();
        }
        return customer_service;
    }

    public void addCustomer(String email, String firstName, String lastName){
        Customer_list.add(new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String email){
        for (Customer customer:Customer_list){
            if(email.equals(customer.getEmail())){
                return customer;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomers(){
        return Customer_list;
    }

    public void printAllCustomers(){
        for(Customer customer: Customer_list){
            System.out.println(customer);
        }
    }
}
