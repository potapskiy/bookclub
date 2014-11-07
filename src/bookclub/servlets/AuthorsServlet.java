package bookclub.servlets;


import java.net.URISyntaxException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import bookclub.db.dao.AuthorsDAO;
import bookclub.utils.Messages;

@Path("/authors")
public class AuthorsServlet {

	private AuthorsDAO authorsDAO = new AuthorsDAO();

	@SuppressWarnings("unchecked")
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	public String registerUser(@FormParam("authorName") String name,
			@FormParam("authorInfo") String info) throws URISyntaxException {

		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			authorsDAO.insertAuthor(name, info);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		jsonReruest.put("msg", msg);
		
	    return jsonReruest.toJSONString();

	}

	

}

