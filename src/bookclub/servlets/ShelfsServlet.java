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
import bookclub.entities.Book;
import bookclub.entities.Comment;
import bookclub.entities.Shelf;

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
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("user_shelfs")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserShelfs(@HeaderParam("user-id") int userId,
			@DefaultValue("10")@HeaderParam("limit") int limit,
			@DefaultValue("0")@HeaderParam("offset") int offset) {
		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {
			int shelfsCount = shelfsDAO.getUserShelfsCount(userId);

			jsonReruest.put("userId", userId);
			jsonReruest.put("total_shelfs", shelfsCount);
			
			List<Shelf> userShelfs = shelfsDAO.getUserShelfs(userId, limit, offset);
			
			JSONArray jsonShelfs = new JSONArray();

			for (Shelf shelf : userShelfs) {

				JSONObject jsonShelf = new JSONObject();
				jsonShelf.put("id", shelf.getId());
				jsonShelf.put("name", shelf.getName());
				

				jsonShelfs.add(jsonShelf);

			}
			
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("get_books")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBooksOnShelf(@HeaderParam("shelf-id") int shelfId,
			@DefaultValue("10")@HeaderParam("limit") int limit,
			@DefaultValue("0")@HeaderParam("offset") int offset) {
		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {
			int booksCount = shelfsDAO.getBooksOnShelfCount(shelfId);

			jsonReruest.put("shelfId", shelfId);
			jsonReruest.put("total_books", booksCount);
			
			List<Book> booksOnShelf = shelfsDAO.getBooksOnShelf(shelfId, limit, offset);
			
			JSONArray jsonBooks = new JSONArray();

			for (Book book : booksOnShelf) {

				JSONObject jsonBook = new JSONObject();
				jsonBook.put("id", book.getId());
				jsonBook.put("book-name", book.getBookName());
				jsonBook.put("author-name", book.getAuthorName());

				jsonBooks.add(jsonBook);

			}
			
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	

}
