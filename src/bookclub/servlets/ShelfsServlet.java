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

import bookclub.db.dao.ShelfsDAO;
import bookclub.utils.Messages;

@Path("/shelfs")
public class ShelfsServlet {

	private ShelfsDAO shelfsDAO = new ShelfsDAO();

	@SuppressWarnings("unchecked")
	@GET
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addShelf(@HeaderParam("user-id") int userId,
			@HeaderParam("name") String name) {


		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			int id = shelfsDAO.insertShelf(userId, name);

			
			JSONObject jsonData = new JSONObject();

			jsonData.put("id", id);
			jsonData.put("userId", userId);
			jsonData.put("name", name);


			jsonReruest.put("data", jsonData);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteShelf(@HeaderParam("shelf-id") int shelfId) {
		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			shelfsDAO.deleteShelf(shelfId);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("addbook")
	@Produces(MediaType.APPLICATION_JSON)
	public String addBookToShelf(@HeaderParam("shelf-id") int shelfId,
			@HeaderParam("book-id") int bookId) {
		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			shelfsDAO.addBook(shelfId,bookId);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("deletebook")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteBookFromShelf(@HeaderParam("shelf-id") int shelfId,
			@HeaderParam("book-id") int bookId) {
		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			shelfsDAO.deleteBook(shelfId,bookId);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	

}
