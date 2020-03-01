package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

enum StatusBike {
	IN_TRANSIT_TO_CLIENT, AVAILABLE, IN_CLIENT_S_POSSESION, IN_TRANSIT_FROM_PARTNER
}

enum depreciationType {
	LINEAR_DEPRECIATION, DOUBLE_DECLINING_BALANCE_DEPRECIATION
}

public class Bike {

	private LocalDate purchaseDate;
	private BikeType type;
	private StatusBike status;
	private Set<DateRange> unavailability = new HashSet<>();
	private depreciationType typeOfDepreciation;

	public Bike(BikeType type, LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
		this.type = type;
		this.setStatus(StatusBike.AVAILABLE);
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public BikeType getType() {
		return type;
	}

	public boolean isAvailable(DateRange period) {
		for (DateRange interval : this.unavailability) {
			if (interval.overlaps(period))
				return false;
		}
		return true;
	}

	public Set<DateRange> getUnavailability() {
		return unavailability;
	}

	public void setStatus(StatusBike status) {
		this.status = status;
	}

	public StatusBike getStatus() {
		return status;
	}

	public void addUnavailability(DateRange period) {
		unavailability.add(period);
	}

	public depreciationType getTypeOfDepreciation() {
		return typeOfDepreciation;
	}

	public void setTypeOfDepreciation(depreciationType typeOfDepreciation) {
		this.typeOfDepreciation = typeOfDepreciation;
	}

}