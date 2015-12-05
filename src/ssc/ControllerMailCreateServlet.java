package ssc;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControllerMailCreateServlet
 * This class deals with the createmail view of the system.
 * It manages sending and logout.
 * It presents the user with details in case of sending
 * failure. Upon success it specifies.
 * Also it manages logout. In case the user attempts somthing
 * after the session has timedout, the user will be
 * logged out.
 */
@WebServlet("/ControllerMailCreateServlet")
public class ControllerMailCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerMailCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * This method manages a request from the user.
	 * Here, the decision of moving forward or staying still is made.
	 * This depends on the users interactions and whether they check 
	 * out or not.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		RequestDispatcher dispatcher;
		//Checking if the session has timed out.
		if(request.getSession().getMaxInactiveInterval() > 60)
		{
			//Loggin out.
			request.getSession().setAttribute("statusLog", "Session has timed out. "
					+ "Please re-log if you want to use this service.");
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
		//Re-establish the session management timeout.
		request.getSession().setMaxInactiveInterval(60);
		//If they are trying to send something
		if(request.getParameter("Send") != null)
		{
			try
			{
				//Get all the details.
				if(request.getSession().getAttribute("userID")!=null)
				{
					String from = (String) request.getSession().getAttribute("userID");
					String to = request.getParameter("to");
					String subject = request.getParameter("subject");
					String content = request.getParameter("content");
					Model model = (Model) request.getSession().getAttribute("model");
					//Attempt send.
					boolean result = model.sendMessage
							(from, to, new ArrayList<String>(), subject, content);
					//Notify accordingly
					if(result)
					{
						request.getSession().setAttribute("status", "Message succesfully sent!");
						dispatcher = request.getRequestDispatcher("createMail.jsp");
					}
					else
					{
						request.getSession().setAttribute("status", "Message failed to send!");
						dispatcher = request.getRequestDispatcher("createMail.jsp");
					}
				}
				else
				{
					dispatcher = request.getRequestDispatcher("index.jsp");
					dispatcher.forward(request, response);
				}
			}
			catch(Exception e)
			{
				//If something goes wrong.
				request.getSession().setAttribute("status", "Message failed to send!");
				dispatcher = request.getRequestDispatcher("createMail.jsp");
			}
			dispatcher.forward(request, response);
		}
		//The user logs out.
		else
		{
			try
			{
				//Close the connection
				Model model = (Model) request.getSession().getAttribute("model");
				model.close();
			}
			catch(Exception e)
			{
				
			}
			//Reset the session.Move on to the index view.
			request.getSession().setAttribute("userID", "");
			request.getSession().setAttribute("status", "");
			request.getSession().setAttribute("statusLog", "");
			request.getSession().invalidate();
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
