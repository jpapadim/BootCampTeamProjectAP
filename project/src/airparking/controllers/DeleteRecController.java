package airparking.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import airparking.dao.AllDAO;

/**
 * Servlet implementation class DeleteRecController
 */
@WebServlet("/deleteRecord")
public class DeleteRecController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRecController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/viewDBcontent.jsp");
		String table=request.getParameter("table");
		String ids=request.getParameter("id");
		System.out.println(table);
		System.out.println(ids);
		AllDAO dao=new AllDAO();
		int id=Integer.parseInt(request.getParameter("id"));
		try {
		if(table.equals("user")){
				dao.deleteUser(id);
		}
		else if(table.equals("owner")){
			dao.deleteOwner(id);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
