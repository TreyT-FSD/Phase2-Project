package com.app.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.dao.LocationDAO;
import com.app.entity.Location;

/**
 * Servlet implementation class LocationController
 */
public class LocationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationController() {
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
					boolean success = LocationDAO.deleteLocation(Integer.parseInt(id));
					if(success) {
						request.setAttribute("locationActionMsg", "Location Deleted");
					}
					else {
						request.setAttribute("locationActionMsg", "Unable to Delete Location");
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
		String locationStr = request.getParameter("locationName");
			
		if(locationStr != null && locationStr.length() != 0) {
			Location tempLocation = LocationDAO.getLocation(locationStr);
			
			if(tempLocation != null) {
				request.setAttribute("locationActionMsg", "Location Already Exists");
				request.getRequestDispatcher("admin.jsp").forward(request, response);
				return;
			}
			else {
				Location location = new Location();
				location.setLocationName(locationStr);
				if(LocationDAO.addLocation(location)) {
					request.setAttribute("locationActionMsg", "Location Was Added");
					request.getRequestDispatcher("admin.jsp").forward(request, response);
					return;
				}
			}
		}
		request.setAttribute("locationActionMsg", "Unable to Add Location");
		request.getRequestDispatcher("admin.jsp").forward(request, response);
	}

}
