package bookclub.servlets;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
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
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addShelf(@FormParam("user-id") int userId,
			@FormParam("name") String name) {


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
	@POST
	@Path("delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteShelf(@FormParam("shelf-id") int shelfId) {
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
	@POST
	@Path("addbook")
	@Produces(MediaType.APPLICATION_JSON)
	public String addBookToShelf(@FormParam("shelf-id") int shelfId,
			@FormParam("book-id") int bookId) {
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
	@POST
	@Path("deletebook")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteBookFromShelf(@FormParam("shelf-id") int shelfId,
			@FormParam("book-id") int bookId) {
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
	@POST
	@Path("user_shelfs")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserShelfs(@FormParam("user-id") int userId,
			@DefaultValue("10")@FormParam("limit") int limit,
			@DefaultValue("0")@FormParam("offset") int offset) {
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
				jsonShelf.put("count", shelf.getCount());

				jsonShelfs.add(jsonShelf);

			}
			
			jsonReruest.put("rows", jsonShelfs);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("get_books")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBooksOnShelf(@FormParam("shelf-id") int shelfId,
			@DefaultValue("10")@FormParam("limit") int limit,
			@DefaultValue("0")@FormParam("offset") int offset) {
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
			
			jsonReruest.put("rows", jsonBooks);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	

}
