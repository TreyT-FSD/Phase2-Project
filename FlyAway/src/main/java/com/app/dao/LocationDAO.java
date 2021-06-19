package com.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.app.entity.Airline;
import com.app.entity.Location;
import com.app.util.HybernateUtil;

public class LocationDAO {
	
	public static List<Location> getLocations(){
		
		Session hibernateSession = HybernateUtil.getSessionFactory().openSession();
		
		String queryStr = "FROM Location";
		TypedQuery query = hibernateSession.createQuery(queryStr);
		
		List<Location> locations = query.getResultList();
		
		hibernateSession.close();
		
		return locations;
	}
	
	public static List<Location> getUniqueLocations(){
		List<Location> locations = new ArrayList<Location>();
		
		for (Location location : getLocations()) {
			if(!locations.contains(location)) {
				locations.add(location);
			}
		}
		return locations;
	}
	
	public static boolean addLocation(Location location) {
		boolean success = false;
		Session session = null;
		
		try {
			session= HybernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.persist(location);
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
	
	public static boolean deleteLocation(int id) {
		boolean success = false;
		Session session = null;
		
		try {
			session = HybernateUtil.getSessionFactory().openSession();
			
			Location location = session.find(Location.class, id);
			
			
			if(location != null) {
				session.beginTransaction();
				session.remove(location);
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
	
	public static Location getLocation(String locationName) {
		Location location = null;
		
		Location tempLocation = new Location();
		tempLocation.setLocationName(locationName);
		
		List<Location> locations = getLocations();
		
		if(locations.contains(tempLocation)) {
			location = locations.get(locations.indexOf(tempLocation));
		}
		System.err.println("getLocation with value=" + locationName + " returned " + location);
		return location;
	}

}
