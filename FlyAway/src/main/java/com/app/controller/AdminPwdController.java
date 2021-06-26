package com.app.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.app.dao.AdminPwdDAO;
import com.app.entity.AdminPwd;
import com.app.util.HybernateUtil;

/**
 * Servlet implementation class AdminPwdController
 */
public class AdminPwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPwdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//need to see if a password already exists in the DB. If not, 
		String passwordStr = request.getParameter("password");
		
		AdminPwd adminPwd = new AdminPwd();
		adminPwd.setPwd(passwordStr);
		
		
		//get the password and see if it matches the password the user submitted
		if(AdminPwdDAO.insertPassword(adminPwd)) {
			//password is updated
			request.setAttribute("passwordActionMsg", "Password Updated");
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		}
		else {
			request.setAttribute("passwordActionMsg", "Password was not Updated");
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		}
	}
}
