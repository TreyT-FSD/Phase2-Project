package com.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.app.entity.Airline;
import com.app.util.HybernateUtil;

public class AirlineDAO {
	
	public static List<Airline> getAirlines(){
		
		Session hibernateSession = HybernateUtil.getSessionFactory().openSession();
		
		String queryStr = "FROM Airline";
		TypedQuery query = hibernateSession.createQuery(queryStr);
		
		List<Airline> airlines = query.getResultList();
		
		hibernateSession.close();
		
		return airlines;
	}
	
	public static List<Airline> getUniqueAirlines(){
		List<Airline> airlines = new ArrayList<Airline>();
		
		for (Airline airline : getAirlines()) {
			if(!airlines.contains(airline)) {
				airlines.add(airline);
			}
		}
		return airlines;
	}
	
	public static boolean addAirline(Airline airline) {
		boolean success = false;
		Session session = null;
		
		try {
			session= HybernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.persist(airline);
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
	
	public static boolean deleteAirline(int id) {
		boolean success = false;
		Session session = null;
		
		try {
			session = HybernateUtil.getSessionFactory().openSession();
			
			Airline airline = session.find(Airline.class, id);
			
			if(airline != null) {
				session.beginTransaction();
				session.remove(airline);
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
	
	public static Airline getAirline(String airlineName) {
		Airline airline = null;
		
		Airline tempAirline = new Airline();
		tempAirline.setAirlineName(airlineName);
		
		List<Airline> airlines = getAirlines();
		
		if(airlines.contains(tempAirline)) {
			airline = airlines.get(airlines.indexOf(tempAirline));
		}
		//System.err.println("getAirline returned " + airline);
		return airline;
	}

}
