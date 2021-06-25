package com.app.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.app.dao.FlightDAO;
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
		
		//PrintWriter out = response.getWriter();

		//TODO: some validation on the input
		String originStr = request.getParameter("originName");
		String destinationStr = request.getParameter("destinationName");
		int passengerCount = Integer.parseInt(request.getParameter("passengerCount"));
		
		Location origin = new Location();
		origin.setLocationName(originStr);
		
		Location destination = new Location();
		destination.setLocationName(destinationStr);
		
		//find all flights for the given origin and destination
		List<Flight> flights = FlightDAO.getFlights();
		
		List<Flight> matchingFlights = new ArrayList<Flight>();
		
		for (Flight flight : flights) {
			if(flight.getOrigin().getLocationName().compareTo(originStr) == 0 &&
				flight.getDestination().getLocationName().compareTo(destinationStr) == 0) {
				//its a match
				matchingFlights.add(flight);
			}
		}
		
		if(matchingFlights.size() != 0) {
			//go to search results
			request.setAttribute("tripDate", request.getAttribute("tripDate"));
			request.setAttribute("matchingFlights", matchingFlights);
			request.getRequestDispatcher("search-results.jsp").forward(request, response);
			return;
		}
		else {
			//no matching flights found, prompt user for new input
			request.setAttribute("searchActionMsg", "No Flights Found");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Flight flight = FlightDAO.getFlight(Integer.parseInt(request.getParameter("id")));
			if(flight != null) {
				int numPassengers = Integer.parseInt(request.getParameter("passengerCount"));
				
				if(numPassengers >= 1 && numPassengers <= 9) {
					//for simplicity we are treating the tripDate as a string throughout the app, assume its valid
					String tripDate = request.getParameter("tripDate");
					
					Booking booking = new Booking(flight, tripDate);

					for(int i = 1; i <= numPassengers; i++) {
						booking.addPassenger(
								new Passenger(
										request.getParameter("passengerFirstName" + i), 
										request.getParameter("passengerLastName" + i)));
					}
					request.setAttribute("booking", booking);
					request.getSession().setAttribute("completedBooking", booking);
					request.getRequestDispatcher("booking-summary.jsp").forward(request, response);
				}
			}
			else {
				request.setAttribute("errorMsg", "The flight is no longer available. Please select another flight.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		} catch (NumberFormatException e) {
			//for some reason the id parameter was invalid, send user back to homepage
			//response.sendError(404);
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		
	}

}
