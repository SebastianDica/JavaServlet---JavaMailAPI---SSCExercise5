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
 * This class deals with the index view of the system.
 * It manages login in and the session.
 * It presents the user with details in case of log in 
 * failure. Upon success it moves on to the next view
 * that is controller by ControllerMailCreateServlet.
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
	 * This method manages a request from the user.
	 * Here, the decision of moving forward or staying still is made.
	 * This depends on the users credentials and whether they check 
	 * out or not.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// A few details that are relevant to checking.
		Model model = new Model(); 
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean result = model.login(username, password);
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		//If the user credentials are correct
		if (result)
		{
			String userIDKey = new String("userID");
		    String userID = new String(username);
		    //Session management
		    session.setAttribute(userIDKey, userID);
		    session.setAttribute("model", model);
		    session.setAttribute("status", "");
		    session.setMaxInactiveInterval(60);
		    //Moving on to the next view.
			dispatcher = request.getRequestDispatcher("createMail.jsp");
		}
		//If the user credentials are incorrect
		else
		{
			model.close();
			//Session management and view control.
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
