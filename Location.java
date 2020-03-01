package uk.ac.ed.bikerental;

public class Location {
    private String postcode;
    private String address;
    
    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode;
        this.address = address;
    }
    
    public boolean isNearTo(Location other) {

    	String first = this.postcode.substring(0, 2);
    	String second = other.postcode.substring(0, 2);
    	
    	if(first.equals(second))
    		return true;
    	
    	return false;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress() {
        return address;
    }
    
    // You can add your own methods here
}
