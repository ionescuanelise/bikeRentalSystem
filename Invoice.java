package uk.ac.ed.bikerental;

public class Invoice {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderNumber;
		result = prime * result + ((quote == null) ? 0 : quote.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if (orderNumber != other.orderNumber)
			return false;
		if (quote == null) {
			if (other.quote != null)
				return false;
		} else if (!quote.equals(other.quote))
			return false;
		return true;
	}

	private final int orderNumber;
	private final Quote quote;
	
	public Invoice(int orderNumber, Quote quote) {
		this.orderNumber = orderNumber;
		this.quote = quote;
	}

}