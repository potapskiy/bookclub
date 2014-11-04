package bookclub.servlets;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bookclub.db.dao.AuthorsDAO;
import bookclub.db.dao.BooksDAO;
import bookclub.db.dao.CommentsDAO;
import bookclub.db.dao.ShelfsDAO;
import bookclub.db.dao.UsersDAO;
import bookclub.entities.Book;
import bookclub.utils.Messages;


@Path("/test")
public class TestServlet {
	
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserShelfs() {
		JSONObject jsonReruest = new JSONObject();

		String msg;

		
		try{
			
			AuthorsDAO authorsDAO = new AuthorsDAO();
			BooksDAO booksDAO = new BooksDAO();
			CommentsDAO commentsDAO = new CommentsDAO();
			ShelfsDAO shelfsDAO = new ShelfsDAO();
			UsersDAO usersDAO = new UsersDAO();
			
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = e.toString();
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}

	
	
	
}
