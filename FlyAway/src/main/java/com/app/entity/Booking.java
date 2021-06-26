package com.app.entity;

import java.util.ArrayList;
import java.util.List;

public class Booking {
	
	private String tripDate; 
	
	private List<Passenger> passengers;
	
	private Flight flight;

	public Booking(Flight flight, String tripDate) {
		passengers = new ArrayList<Passenger>();
		this.flight=flight;
		this.tripDate=tripDate;
	}
	
	public Booking(List<Passenger> passengers, Flight flight, String tripDate) {
		this.passengers = passengers;
		this.flight = flight;
		this.tripDate=tripDate;
	}
	
	public void addPassenger(Passenger passenger) {
		passengers.add(passenger);
	}
	
	public Flight getFlight() {
		return flight;
	}
	
	public List<Passenger> getPassengers() {
		return passengers;
	}
	
	public String getTripDate() {
		return tripDate;
	}
}
