package uk.ac.ed.bikerental;

public class Customer {
	
    private Location customerLocation;
    private String name;
    
	public String getName() {
		return name;
	}

	public Customer(Location customerLocation, String name) {
		assert customerLocation!=null;
		assert name.length() >= 3;
		this.customerLocation = customerLocation;
		this.name = name;
	}

	public Location getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(Location customerLocation) {
		this.customerLocation = customerLocation;
	}
	
}
