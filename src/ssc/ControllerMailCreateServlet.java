package ssc;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ControllerMailCreateServlet
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("Send") != null)
		{
			RequestDispatcher dispatcher;
			try
			{
				String from = (String) request.getSession().getAttribute("userID");
				String to = request.getParameter("to");
				String subject = request.getParameter("subject");
				String content = request.getParameter("content");
				Model model = (Model) request.getSession().getAttribute("model");
				boolean result = model.sendMessage
						(from, to, new ArrayList<String>(), subject, content);
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
			catch(Exception e)
			{
				request.getSession().setAttribute("status", "Message failed to send!");
				dispatcher = request.getRequestDispatcher("createMail.jsp");
			}
			dispatcher.forward(request, response);
		}
		else
		{
			RequestDispatcher dispatcher;
			try
			{
				Model model = (Model) request.getSession().getAttribute("model");
				model.close();
			}
			catch(Exception e)
			{
				
			}
			request.getSession().setAttribute("userID", "");
			request.getSession().setAttribute("status", "");
			request.getSession().setAttribute("statusLog", "");
			request.getSession().invalidate();
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
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
