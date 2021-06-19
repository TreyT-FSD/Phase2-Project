package com.app.entity;

import javax.persistence.*;

@Entity
@Table(name="flight")
public class Flight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@PrimaryKeyJoinColumn
	private int flightId;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "originId")
	private Location origin;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "destinationId")
	private Location destination;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "airlineId")
	private Airline airline;
	
	private double ticketPrice;
	

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public Location getOrigin() {
		return origin;
	}

	public void setOrigin(Location origin) {
		this.origin = origin;
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		
		if(!(obj instanceof Flight)) {
			return false;
		}
		
		Flight objAsFlight = (Flight)obj;		
		return (origin.equals(objAsFlight.getOrigin()) &&
				destination.equals(objAsFlight.getDestination()) &&
				airline.equals(objAsFlight.getAirline()) &&
				Double.compare(ticketPrice, objAsFlight.getTicketPrice()) == 0);
	}

	@Override
	public String toString() {
		return "Flight [flightId=" + flightId + ", origin=" + origin + ", destination=" + destination + ", airline="
				+ airline + ", ticketPrice=" + ticketPrice + "]";
	}
	
	
}
