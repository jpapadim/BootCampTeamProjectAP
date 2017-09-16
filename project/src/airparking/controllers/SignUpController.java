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
import airparking.model.Owner;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/signupcontroller")
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		request.setCharacterEncoding("UTF-8");
		String fName = request.getParameter("firstname");
		String lName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirmpassword");
		boolean isOwner = !(request.getParameter("ownerCheck") == null
				|| !((request.getParameter("ownerCheck")).equals("true")));
		String bankAccount = request.getParameter("bankaccount");
		String bankName = request.getParameter("bankname");
		String taxregistrationnumber = request.getParameter("taxregistrationnumber");
		String taxoffice = request.getParameter("taxoffice");
		if (bankAccount != null) {
			bankAccount = (bankAccount.replace('-', ' ')).replaceAll("\\s+", "");
		}
		if (taxregistrationnumber != null) {
			taxregistrationnumber = (taxregistrationnumber.replace('-', ' ')).replaceAll("\\s+", "");
		}
		User currentUser = null;
		if (session.getAttribute("user") != null) {
			currentUser = (User) session.getAttribute("user");
		}
		boolean ok = true;
		AllDAO dao = new AllDAO();
		try {
			if (currentUser == null) {
				if (fName == null || fName.length() < 2) {
					request.setAttribute("fname", "&nbsp;&nbsp;At least 2 characters.");
					ok = false;
				}
				if (lName == null || lName.length() < 2) {
					request.setAttribute("lname", "&nbsp;&nbsp;At least 2 characters.");
					ok = false;
				}
				if (password == null || password.length() < 8) {
					request.setAttribute("password", "&nbsp;&nbsp;At least 8 characters required.");
					ok = false;
				}

				if (confirmpassword == null || !confirmpassword.equals(password)) {
					request.setAttribute("confirmpassword", "&nbsp;&nbsp;Password not confirmed.");
					ok = false;
				}

				if (email == null || email.length() < 4) {
					request.setAttribute("email", "&nbsp;&nbsp;Insert a valid email address.");
					ok = false;
				} else {
					Pattern pattern = Pattern.compile("^.+@.+\\..+$");
					Matcher matcher = pattern.matcher(email);
					if (!matcher.matches()) {
						request.setAttribute("email", "&nbsp;&nbsp;Insert a valid email address.");
						ok = false;
					} else if (dao.isUserRegistered(email)) {
						request.setAttribute("email", "&nbsp;&nbsp;The email '" + email + "' is already registered.");
						ok = false;
					}
				}
			}
			if (currentUser != null || isOwner) {
				if (bankName == null || bankName.isEmpty()) {
					request.setAttribute("bankName", "&nbsp;&nbsp;Select a bank..");
					ok = false;
				}
				if (bankAccount == null || bankAccount.length() != 27) {
					request.setAttribute("bankAccount", "&nbsp;&nbsp;Enter a valid IBAN (27 characters).");
					ok = false;

				} else if (bankAccount.matches("^.*[^a-zA-Z0-9 ].*$")) {
					request.setAttribute("bankAccount", "&nbsp;&nbsp;Enter a valid IBAN.");
					ok = false;
				}
				if (taxregistrationnumber == null || taxregistrationnumber.length() != 9) {
					request.setAttribute("taxregistrationnumber",
							"&nbsp;&nbsp;Enter a valid Tax Registration Number (9 characters).");
					ok = false;
				} else if (taxregistrationnumber.matches("^.*[^0-9 ].*$")) {
					request.setAttribute("taxregistrationnumber",
							"&nbsp;&nbsp;Tax Registration Number must be numeric.");
					ok = false;
				}
				if (taxoffice == null || taxoffice.isEmpty()) {
					request.setAttribute("taxoffice", "&nbsp;&nbsp;Enter a valid tax office.");
					ok = false;
				}
			}
			RequestDispatcher rd = null;
			if (ok == true) {
				User user = new User(0, fName, lName, password, email);
				if (currentUser == null) {
					if (!isOwner)
						dao.registerUser(user);
					else {
						user = new Owner(user, bankAccount, bankName, taxregistrationnumber, taxoffice);
						dao.registerUser((Owner) user);
					}
				}
				else {
					user = new Owner(currentUser, bankAccount, bankName, taxregistrationnumber, taxoffice);
					dao.upgradeUserAccound(currentUser, (Owner) user);
					
				}
				session.setAttribute("user", dao.authenticateUser(user.getEmail(), user.getPassword()));
				request.setAttribute("user", user);
				rd = request.getRequestDispatcher("/jsp/signup_success.jsp");

			} else {
				rd = request.getRequestDispatcher("/jsp/signup.jsp");
			}

			rd.forward(request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}