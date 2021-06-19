package com.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "airline")
public class Airline {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int airlineId;	
	
	private String airlineName;
	

	public int getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		
		if(!(obj instanceof Airline)) {
			return false;
		}
		
		Airline objAsAirline = (Airline)obj;
		//System.err.println("comparing airline " + toString() + " against " + objAsAirline.toString());
		return (airlineName.compareTo(objAsAirline.getAirlineName()) == 0);
			
	}

	@Override
	public String toString() {
		return "Airline [airlineId=" + airlineId + ", airlineName=" + airlineName + "]";
	}
	
	
	
	
}
