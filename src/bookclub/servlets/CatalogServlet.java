package bookclub.servlets;


import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import bookclub.db.dao.BooksDAO;
import bookclub.utils.Messages;

@Path("/catalog")
public class CatalogServlet {

	
	private BooksDAO booksDAO = new BooksDAO();
	
	

}
