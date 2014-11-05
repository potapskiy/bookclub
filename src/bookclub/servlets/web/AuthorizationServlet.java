package bookclub.servlets.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookclub.crypto.SHAHashing;
import bookclub.db.dao.AdministrationDAO;
import bookclub.entities.User;

/**
 * WORK NOW! Servlet for authorization
 * 
 */
@WebServlet("/auth")
public class AuthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected RequestDispatcher dispatcher = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthorizationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config); // added this line then it worked
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		System.out.println("************ LOGIN SERVLET  get *************");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		System.out.println("************ LOGIN SERVLET  post *************");

		HttpSession session = request.getSession(false);
		System.out.println(session);
//		if (session != null) {
//			getServletContext().getRequestDispatcher("/").forward(request,
//					response);
//			// dispatcher = request.getRequestDispatcher("pages/login.html");
//			// response = RespNoCache.setNoCacheParams(response);
//			// dispatcher.forward(request, response);
//			return;
//		}

		String login = request.getParameter("login");
		String password = request.getParameter("pass");
		
		System.out.println(login+"  "+ password);
		
		String passHash = SHAHashing.getHash(password);
		
		AdministrationDAO administrationDAO  = new AdministrationDAO();
		
		User user = null;
		try {
			user = administrationDAO.isUserDataCorrect(login, passHash);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(user);
		
		if (user != null){
			request.removeAttribute("Error");
			session = request.getSession(true);
            session.setAttribute("user", user);
            getServletContext().getRequestDispatcher("/pages/main.jsp").forward(request,
					response);
		}else {
            request.setAttribute("Error","error.autofailed");
            getServletContext().getRequestDispatcher("/").forward(request,
					response);
		}
		
		

	}

	// System.out.println("Servlet: " + login + "   " + pass);

}