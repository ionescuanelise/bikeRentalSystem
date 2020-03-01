package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

enum DepositMethod{
	LINEAR,
	DOUBLE_DECLINING_BALANCE
}

public class Quote {

	//private Request customerRequest;
	private BikeProvider provider;
	private BigDecimal price;
	private BigDecimal deposit;
	private HashMap<BikeType, HashSet<Bike>> bikes = new HashMap<>();
	private DateRange period;
	private Customer customer;
	private DepositMethod depositMethod; 

	public Customer getCustomer() {
		return customer;
	}

	public DateRange getPeriod() {
		return period;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setPeriod(DateRange period) {
		this.period = period;
	}

	public Quote(Request customerRequest, BikeProvider provider, HashMap<BikeType, HashSet<Bike>> bikes) {
		this.bikes = bikes;
		this.provider = provider;
		this.period = customerRequest.getPeriod();
		this.customer = customerRequest.getClient();
		this.calculatePrice(customerRequest);
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void calculatePrice(Request customerRequest) {
		HashMap<BikeType, Integer> bikesRequested = customerRequest.getBikesRequested();
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal days = new BigDecimal(customerRequest.getPeriod().toDays());
		for (BikeType type : bikesRequested.keySet()) {
			BigDecimal number = new BigDecimal(bikesRequested.get(type));
			BigDecimal priceType = this.provider.getDailyRentalPricePerType().get(type);
			BigDecimal multiplication = priceType.multiply(number);
			multiplication = multiplication.multiply(days);
			result = result.add(multiplication);   // daily rental price from the provider * the number of bikes of that type requested * number of days
		}
		this.setPrice(result);
	}

	public BigDecimal getDeposit() {
		return deposit;
	}
	
	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}
	
	public BikeProvider getProvider() {
		return provider;
	}
	
	public void setProvider(BikeProvider provider) {
		this.provider = provider;
	}
	
	public HashMap<BikeType, HashSet<Bike>> getBikes() {
		return bikes;
	}
	
	public BigDecimal computeDeposit() {
		BigDecimal result = BigDecimal.ZERO;
		if (this.depositMethod == DepositMethod.LINEAR) {
			LinearDepreciation testerLinear = new LinearDepreciation();
			for (BikeType type : this.getBikes().keySet())
				for (Bike bike : this.getBikes().get(type))
			        result.add(testerLinear.calculateValue(bike, LocalDate.now()));
		}
		else if (this.depositMethod == DepositMethod.DOUBLE_DECLINING_BALANCE) {
			DoubleDecliningBalanceDepreciation testerDouble = new DoubleDecliningBalanceDepreciation();
			for (BikeType type : this.getBikes().keySet())
				for (Bike bike : this.getBikes().get(type))
			        result.add(testerDouble.calculateValue(bike, LocalDate.now()));
		}
		return result;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bikes == null) ? 0 : bikes.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((deposit == null) ? 0 : deposit.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Quote other = (Quote) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		return true;
	}
	
	
}
