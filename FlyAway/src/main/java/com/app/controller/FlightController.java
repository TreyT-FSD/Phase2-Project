package com.app.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.dao.AirlineDAO;
import com.app.dao.FlightDAO;
import com.app.dao.LocationDAO;
import com.app.entity.Airline;
import com.app.entity.Flight;
import com.app.entity.Location;

/**
 * Servlet implementation class FlightController
 */
public class FlightController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check to see what action we got if any
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		
		if(action != null) {
			if(action.compareTo("delete")==0 && id != null) {
				//TODO: we could add some input validation for ID here				
				try {					
					boolean success = FlightDAO.deleteFlight(Integer.parseInt(id));
					if(success) {
						request.setAttribute("flightActionMsg", "Flight Deleted");
					}
					else {
						request.setAttribute("flightActionMsg", "Unable to Delete Flight");
					}
				} catch (NumberFormatException e) {
					// TODO: handle exception, maybe log an error 
					//or see if we can get a message back to the admin page for the user to see
				}
			}
		}
		request.getRequestDispatcher("admin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String originStr = request.getParameter("originName");
		String destinationStr = request.getParameter("destinationName");
		String airlineStr = request.getParameter("airlineName");
		String ticketPriceStr = request.getParameter("ticketPrice");

		//TODO: do some input validation to make sure the parameters are valid
		if(originStr != null && originStr.length() != 0 &&
				destinationStr != null && destinationStr.length() != 0 &&
				airlineStr != null && airlineStr.length() != 0 &&
				ticketPriceStr != null && ticketPriceStr.length() != 0) {
						
			Location origin = new Location();
			origin.setLocationName(originStr);
			
			Location destination = new Location();
			destination.setLocationName(destinationStr);
			
			Airline airline = new Airline();
			airline.setAirlineName(airlineStr);
			
			//TODO: use try catch incase parse fails
			double ticketPrice = Double.parseDouble(ticketPriceStr);
			
			Flight flight = FlightDAO.getFlight(origin, destination, airline, ticketPrice);
			
			if(flight == null) {
				flight = new Flight();
				flight.setOrigin(origin);
				flight.setDestination(destination);
				flight.setAirline(airline);
				flight.setTicketPrice(ticketPrice);
			}
			else {
				request.setAttribute("flightActionMsg", "Flight Already Exists");
				request.getRequestDispatcher("admin.jsp").forward(request, response);
				return;
			}
			
			if(FlightDAO.addFlight(flight)) {
				request.setAttribute("flightActionMsg", "Flight Added");
				request.getRequestDispatcher("admin.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("flightActionMsg", "Unable to Add Flight");
		request.getRequestDispatcher("admin.jsp").forward(request, response);
	}
}
