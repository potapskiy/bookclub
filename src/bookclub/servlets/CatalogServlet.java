package bookclub.servlets;


import java.net.URISyntaxException;
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

import bookclub.db.dao.BooksDAO;
import bookclub.entities.Book;
import bookclub.utils.Messages;

@Path("/catalog")
public class CatalogServlet {

	
	private BooksDAO booksDAO = new BooksDAO();
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("popular")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPopularBooks(
			@DefaultValue("5")@FormParam("limit") int limit,
			@DefaultValue("0")@FormParam("offset") int offset) {
		JSONObject jsonReruest = new JSONObject();
		int count = 0;
		String msg;
		
		

		try {
			
			
			List<Book> popularBooks = booksDAO.getPopular(limit, offset);
			
			JSONArray jsonBooks = new JSONArray();

			for (Book book : popularBooks) {

				JSONObject jsonBook = new JSONObject();
				jsonBook.put("id", book.getId());
				jsonBook.put("book-name", book.getBookName());
				jsonBook.put("author-name", book.getAuthorName());
				jsonBook.put("info",book.getBookInfo());
				jsonBook.put("likes", book.getLikes());
				jsonBook.put("dislikes", book.getDislikes());
				jsonBook.put("genre", book.getGenre());

				jsonBooks.add(jsonBook);

				count++;
			}
			
			jsonReruest.put("rows", jsonBooks);
			jsonReruest.put("total_books", count);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSearch(
			@DefaultValue("10")@FormParam("limit") int limit,
			@DefaultValue("0")@FormParam("offset") int offset,
			@FormParam("search") String search) {
		JSONObject jsonReruest = new JSONObject();
		int count = 0;
		String msg;
		
		

		try {
			
			
			List<Book> popularBooks = booksDAO.searchBooks(search, limit, offset);
			
			JSONArray jsonBooks = new JSONArray();

			for (Book book : popularBooks) {

				JSONObject jsonBook = new JSONObject();
				jsonBook.put("id", book.getId());
				jsonBook.put("book-name", book.getBookName());
				jsonBook.put("author-name", book.getAuthorName());
				jsonBook.put("info",book.getBookInfo());
				jsonBook.put("likes", book.getLikes());
				jsonBook.put("dislikes", book.getDislikes());
				jsonBook.put("genre", book.getGenre());

				jsonBooks.add(jsonBook);
				count++;

			}
			
			jsonReruest.put("rows", jsonBooks);
			jsonReruest.put("total_books", count);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("search_by_genre")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSearchByGenre(
			@DefaultValue("10")@FormParam("limit") int limit,
			@DefaultValue("0")@FormParam("offset") int offset,
			@FormParam("genre") String genre) {
		JSONObject jsonReruest = new JSONObject();
		int count = 0;
		String msg;
		
		

		try {
			
			
			List<Book> popularBooks = booksDAO.searchBooksByGenre(genre, limit, offset);
			
			JSONArray jsonBooks = new JSONArray();

			for (Book book : popularBooks) {

				JSONObject jsonBook = new JSONObject();
				jsonBook.put("id", book.getId());
				jsonBook.put("book-name", book.getBookName());
				jsonBook.put("author-name", book.getAuthorName());
				jsonBook.put("info",book.getBookInfo());
				jsonBook.put("likes", book.getLikes());
				jsonBook.put("dislikes", book.getDislikes());
				jsonBook.put("genre", book.getGenre());

				jsonBooks.add(jsonBook);
				count++;

			}
			
			jsonReruest.put("rows", jsonBooks);
			jsonReruest.put("total_books", count);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("search_by_genre_and_name")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSearchByGenreAndName(
			@DefaultValue("10")@FormParam("limit") int limit,
			@DefaultValue("0")@FormParam("offset") int offset,
			@FormParam("genre") String genre,
			@FormParam("name") String name) {
		JSONObject jsonReruest = new JSONObject();
		int count = 0;
		String msg;
		
		

		try {
			
			
			List<Book> popularBooks = booksDAO.searchBooksByGenreAndName(genre, name, limit, offset);
			
			JSONArray jsonBooks = new JSONArray();

			for (Book book : popularBooks) {

				JSONObject jsonBook = new JSONObject();
				jsonBook.put("id", book.getId());
				jsonBook.put("book-name", book.getBookName());
				jsonBook.put("author-name", book.getAuthorName());
				jsonBook.put("info",book.getBookInfo());
				jsonBook.put("likes", book.getLikes());
				jsonBook.put("dislikes", book.getDislikes());
				jsonBook.put("genre", book.getGenre());

				jsonBooks.add(jsonBook);
				count++;

			}
			
			jsonReruest.put("rows", jsonBooks);
			jsonReruest.put("total_books", count);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("like")
	@Produces(MediaType.APPLICATION_JSON)
	public String addLike(@FormParam("book-id") int bookId) {
		
		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			booksDAO.addLike(bookId);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("dislike")
	@Produces(MediaType.APPLICATION_JSON)
	public String addDislike(@FormParam("book-id") int bookId) {
		
		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			booksDAO.addDislike(bookId);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	

	
	@SuppressWarnings("unchecked")
	@POST
	@Path("book")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBook(@FormParam("book-id") int bookId) {
		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {
			
			
			Book book = booksDAO.getBook(bookId);
			
			JSONObject jsonBook = new JSONObject();
			jsonBook.put("id", book.getId());
			jsonBook.put("book-name", book.getBookName());
			jsonBook.put("author-name", book.getAuthorName());
			jsonBook.put("info",book.getBookInfo());
			jsonBook.put("likes", book.getLikes());
			jsonBook.put("dislikes", book.getDislikes());
			jsonBook.put("genre", book.getGenre());

			
			jsonReruest.put("data", jsonBook);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}


	@SuppressWarnings("unchecked")
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addBook(@FormParam("bookTitle") String bookTitle,
			@FormParam("bookAuthor") String bookAuthor,
			@FormParam("bookGenre") String bookGenre,
			@FormParam("bookInfo") String info) throws URISyntaxException {

		JSONObject jsonReruest = new JSONObject();
		String msg;

		try {
			
			booksDAO.insertBook(bookTitle, bookAuthor,bookGenre, info);
			msg = Messages.OK;

		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		jsonReruest.put("msg", msg);

		System.out.println(jsonReruest.toJSONString());
		return jsonReruest.toJSONString();

	}
	
	
	
	
	

}
