package com.app.entity;

public class Passenger {
	
	private String firstName;
	
	private String lastName;
	
	
	public Passenger(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

}
