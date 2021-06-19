package com.app.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.app.entity.*;
import com.app.util.HybernateUtil;


/**
 * Servlet implementation class FlyAwayController
 */
public class FlyAwayController extends HttpServlet {
	
		
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlyAwayController() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		SessionFactory sessionFactory = HybernateUtil.getSessionFactory();
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			out.print("Setting up DB: ");
			out.print(session);
			
			Location origin = new Location();
			origin.setLocationName("KAUS");
			
			Location destination = new Location();
			destination.setLocationName("KDFW");
			
			Airline airline = new Airline();
			airline.setAirlineName("Delta");
			
			Flight flight = new Flight();
			flight.setOrigin(origin);
			flight.setDestination(destination);
			flight.setAirline(airline);
			flight.setTicketPrice(123.45);
			
			session.beginTransaction();
			
			session.persist(flight);
			
			session.getTransaction().commit();
			
			session.close();
			
			
		} catch (HibernateException e) {
			System.out.println("Hibernate exception thrown: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(session != null) {
					session.close();
				}
			} catch (HibernateException e) {
				System.out.println("Hibernate exception thrown when closing the session: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
