package airparking.controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import airparking.dao.AllDAO;
import airparking.model.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);

		request.setCharacterEncoding("UTF-8");
		RequestDispatcher login = request.getRequestDispatcher("/jsp/intro.jsp");
		
		String username = request.getParameter("email");
		String password = request.getParameter("password");
		
		boolean ok=false;
		if (username == null || username.isEmpty()){
			request.setAttribute("errormessage", "Email address is required!"); 
			if(password == null || password.isEmpty()) request.setAttribute("errormessage", "Email address and password are required!"); 
			else if(password.length() < 8) request.setAttribute("errormessage", "Email address is required. Password must be at least 8 characters long!");	
		}
		else{
			Pattern pattern = Pattern.compile("^.+@.+\\..+$");
			Matcher matcher = pattern.matcher(username);
			if (!matcher.matches()) {
				if(password == null || password.isEmpty()) request.setAttribute("errormessage", "Email address is not valid. Password is required!");
				else if(password.length() < 8) request.setAttribute("errormessage", "Email address is not valid. Password must be at least 8 characters long!");
			}
			else{
				request.setAttribute("mail",username);
				if(password == null || password.isEmpty()) request.setAttribute("errormessage", "Password is required!");
				else if(password.length() < 8) request.setAttribute("errormessage", "Password must be at least 8 characters long!");
				else ok=true;
			}
		}

		AllDAO udao = new AllDAO();
		
		try {
			if(ok){
			User user = udao.authenticateUser(username, password);
			session.setAttribute("user", user);
			}
		} catch (Exception e) {
			request.removeAttribute("mail");
			request.setAttribute("errormessage", e.getMessage());
			
		}
		
		
		login.forward(request, response);
		
	}

}
