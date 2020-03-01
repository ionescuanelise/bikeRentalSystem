package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Objects;

public class BikeType {
	
	private BigDecimal depreciationRate;
	private BigDecimal replacementValue;
	private String name;
	
	public BikeType(BigDecimal depreciationRate, BigDecimal replacementValue, String name) {
		this.depreciationRate = depreciationRate;
		this.replacementValue = replacementValue;
		this.name = name;
	}
	
	public BigDecimal getDepreciationRate(){
		return this.depreciationRate;
	}
	
	public String getName(String name) {
		return this.name;
	}

    public BigDecimal getReplacementValue() {
        return replacementValue;
    }
    
}