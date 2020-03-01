package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class LinearDepreciation implements ValuationPolicy{
	
    public BigDecimal calculateValue(Bike bike, LocalDate date){
    	
    	DateRange bikeAge = new DateRange(bike.getPurchaseDate(), date);
    	int no_years_bike = (int) bikeAge.toYears();
		BigDecimal no_years = new BigDecimal(no_years_bike);

		BigDecimal depreciationRate_bike = bike.getType().getDepreciationRate();
		
		BigDecimal replacementValue_bike = bike.getType().getReplacementValue();
		replacementValue_bike = replacementValue_bike.multiply(BigDecimal.ONE.subtract(depreciationRate_bike.multiply(no_years)));
		replacementValue_bike = replacementValue_bike.setScale(0, RoundingMode.UP);
		
        return replacementValue_bike;
    }
    
}
