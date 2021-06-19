package com.app.dao;

import java.util.List;

import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.app.entity.Airline;
import com.app.entity.Flight;
import com.app.entity.Location;
import com.app.util.HybernateUtil;

public class FlightDAO {
	public static List<Flight> getFlights(){
		
		Session hibernateSession = HybernateUtil.getSessionFactory().openSession();
		
		String queryStr = "FROM Flight";
		TypedQuery query = hibernateSession.createQuery(queryStr);
		
		List<Flight> flights = query.getResultList();
		
		hibernateSession.close();
		
		return flights;
	}
	
	/**
	 * Returns the flight if one already exists otherwise null.	
	 * @param origin
	 * @param destinaion
	 * @param airline
	 * @param ticketPrice
	 * @return
	 */
	public static Flight getFlight(Location origin, Location destinaion, Airline airline, double ticketPrice) {
		Flight flight = null;
		
		Flight tempFlight = new Flight();
		tempFlight.setOrigin(origin);
		tempFlight.setDestination(destinaion);
		tempFlight.setAirline(airline);
		tempFlight.setTicketPrice(ticketPrice);
		
		List<Flight> flights = getFlights();
		
		if(flights.contains(tempFlight)) {
			flight = flights.get(flights.indexOf(tempFlight));
		}
		
		return flight;
	}
	
	public static boolean addFlight(Flight flight) {
		//TODO: maybe do some input validation on flight
		boolean success = false;
		Session session = null;
		
		try {
			session= HybernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			//TODO: here we need to see if we can retrieve any of the existing objects
			//airline
			//location
			
			//if any of these exist in the DB already, use that instance when persisting flight instead
			List<Airline> airlines = AirlineDAO.getAirlines();
			if(airlines.contains(flight.getAirline())){
				flight.setAirline(airlines.get(airlines.indexOf(flight.getAirline())));
			}
			
			List<Location> locations = LocationDAO.getLocations();
			if(locations.contains(flight.getOrigin())) {
				flight.setOrigin(locations.get(locations.indexOf(flight.getOrigin())));
			}
			
			if(locations.contains(flight.getDestination())) {
				flight.setDestination(locations.get(locations.indexOf(flight.getDestination())));
			}
			
			
			//session.persist(flight);
			session.merge(flight);
			//session.save(flight);
			session.getTransaction().commit();
			session.close();
			
			success=true;
		} catch (HibernateException e) {
			// TODO: handle exception
			//there was some error opening the session
		} catch (RollbackException ex) {
			//TODO: handle exception
			//there was an error while committing to the DB
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return success;
	}
	
	public static boolean deleteFlight(int id) {
		boolean success = false;
		Session session = null;
		
		try {
			session = HybernateUtil.getSessionFactory().openSession();
			
			Flight flight = session.find(Flight.class, id);
			
			if(flight != null) {
				session.beginTransaction();
				session.remove(flight);
				session.getTransaction().commit();
				success=true;
				session.close();
			}
		} catch (IllegalArgumentException e) {
			//there was some issue during find operation, id was probably invalid
		} catch (Exception e) {
			// TODO: handle exception, assume the delete failed
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return success;
	}

}
