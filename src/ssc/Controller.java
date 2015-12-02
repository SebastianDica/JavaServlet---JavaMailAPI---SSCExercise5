package ssc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Model model = new Model();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean result = model.login(username, password);
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		if (result)
		{
			String userIDKey = new String("userID");
		    String userID = new String(username);
		    session.setAttribute(userIDKey, userID);
		    session.setAttribute("model", model);
		    session.setAttribute("status", "");
			dispatcher = request.getRequestDispatcher("createMail.jsp");
		}
		else
		{
			model.close();
			session.setAttribute("statusLog", "Login failed. Could you please try again?");
			dispatcher = request.getRequestDispatcher("index.jsp");
		}
		dispatcher.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
