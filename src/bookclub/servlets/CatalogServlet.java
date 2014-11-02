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

import bookclub.db.dao.BooksDAO;
import bookclub.entities.Book;
import bookclub.utils.Messages;

@Path("/catalog")
public class CatalogServlet {

	
	private BooksDAO booksDAO = new BooksDAO();
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("popular")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserShelfs(
			@DefaultValue("10")@HeaderParam("limit") int limit,
			@DefaultValue("0")@HeaderParam("offset") int offset) {
		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {
			
			
			List<Book> popularBooks = booksDAO.getPopular(limit, offset);
			
			JSONArray jsonBooks = new JSONArray();

			for (Book book : popularBooks) {

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
