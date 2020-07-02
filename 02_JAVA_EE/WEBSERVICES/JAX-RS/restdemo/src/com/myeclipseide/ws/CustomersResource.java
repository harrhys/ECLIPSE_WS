package com.myeclipseide.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.sun.jersey.spi.resource.Singleton;

@Produces("application/xml")
@Path("customers")
@Singleton
public class CustomersResource {

	private TreeMap<Integer, Customer> customerMap = new TreeMap<Integer, Customer>();

	public CustomersResource() {
		// hardcode a single customer into the database for demonstration
		// purposes
		/*Customer customer = new Customer();
		customer.setName("Harold Abernathy");
		customer.setAddress("Sheffield, UK");
		addCustomer(customer);*/
		
		Customer customer1 = new Customer();
		customer1.setName("1Harold Abernathy");
		customer1.setAddress("1Sheffield, UK");
		addCustomer(customer1);
	}

	@GET
	public List<Customer> getCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		customers.addAll(customerMap.values());
		return customers;
	}

	@GET
	@Path("{id}")
	public Customer getCustomer(@PathParam("id") int cId) {
		return customerMap.get(cId);
	}

	@POST
	@Path("add")
	@Produces("text/plain")
	@Consumes("application/xml")
	public String addCustomer(Customer customer) {
		int id = customerMap.size();
		customer.setId(id);
		customerMap.put(id, customer);
		return "Customer " + customer.getName() + " added with Id " + id;
	}
}