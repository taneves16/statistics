package com.n26.entity;

/**
 * Entity object to hold Transaction details
 * 
 * @author Tanya
 *
 */
public class Transaction {

	private final Double amount;
	private final Long timeStamp;
	
	public Transaction(Double amount, Long timeStamp) {
		this.amount = amount;
		this.timeStamp = timeStamp;
	}

	public Double getAmount() {
		return amount;
	}

	public Long getTimestamp() {
		return timeStamp;
	}
	
	@Override
	public String toString() {
	   return "Transaction [amount=" + getAmount() + ",timeStamp=" + getTimestamp() + "]";
	}
	
}
