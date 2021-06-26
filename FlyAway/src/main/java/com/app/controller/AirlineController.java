package com.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.dao.AirlineDAO;
import com.app.entity.Airline;

/**
 * Servlet implementation class AirlineController
 */
public class AirlineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AirlineController() {
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
					boolean success = AirlineDAO.deleteAirline(Integer.parseInt(id));
					if(success) {
						request.setAttribute("airlineActionMsg", "Airline Deleted");
					}
					else {
						//TODO: this may need to move otherwise it may not get set during all error cases
						//EX, if id is null, we dont set either of these
						request.setAttribute("airlineActionMsg", "Unable to delete airline");
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
		//TODO: make this more robust, needs input validation
		String airlineName = request.getParameter("airlineName");
		
		if(airlineName != null && airlineName.length() != 0) {
			//try to retrieve the airline
			Airline tempAirline = AirlineDAO.getAirline(airlineName);
			if(tempAirline != null) {
				//System.err.println("Airline already exists: " + tempAirline);
				request.setAttribute("airlineActionMsg", "Airline Already Exists");
				request.getRequestDispatcher("admin.jsp").forward(request, response);
				return;
			} else {
				Airline airline = new Airline();
				airline.setAirlineName(airlineName);				
				if(AirlineDAO.addAirline(airline)) {
					request.setAttribute("airlineActionMsg", "Airline Was Added");
					request.getRequestDispatcher("admin.jsp").forward(request, response);
					return;
				}
			}
		}
		request.setAttribute("airlineActionMsg", "Unable to Add Airline");
		request.getRequestDispatcher("admin.jsp").forward(request, response);
	}
}
