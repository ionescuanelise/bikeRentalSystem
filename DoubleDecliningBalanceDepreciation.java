package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
class DoubleDecliningBalanceDepreciation implements ValuationPolicy{

    public BigDecimal calculateValue(Bike bike, LocalDate date){

    	BigDecimal depreciationRate = bike.getType().getDepreciationRate();
        BigDecimal replacementValue = bike.getType().getReplacementValue();
        DateRange bikeAge = new DateRange(bike.getPurchaseDate(), date);
        int bikeAgeInYears = (int) bikeAge.toYears(); 
        BigDecimal losingRate;

        BigDecimal a = new BigDecimal(2);
        BigDecimal sub = BigDecimal.ONE.subtract(depreciationRate.multiply(a));
        losingRate = sub.pow(bikeAgeInYears);
        
        replacementValue = replacementValue.multiply(losingRate).setScale(2, RoundingMode.UP);
        
        return replacementValue;
       
    }
      
    
}