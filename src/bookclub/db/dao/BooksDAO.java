package bookclub.db.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bookclub.db.DBParams;
import bookclub.entities.Book;

public class BooksDAO {
	
	Connection conn = null;
	
	PreparedStatement getPopular;
	PreparedStatement searchBooks;
	PreparedStatement searchBooksByGenre;
	PreparedStatement searchBooksByGenreAndName;
	PreparedStatement addLike;
	PreparedStatement addDislike;
	PreparedStatement getBook;
	PreparedStatement insertBook;
	public BooksDAO() {

		try {
			InitialContext initialContext = new InitialContext();
			try {

				DataSource ds = (DataSource) initialContext
						.lookup(DBParams.JDBC_ENV);
				conn = ds.getConnection();
				
				
				getPopular = conn
						.prepareStatement("SELECT b.bookId, b.info, "
								+ "b.genre, b.likes, b.dislikes, b.name as bookName, a.name as authorName FROM "
								+ DBParams.booksTable + " b\r\n" + 
								"LEFT JOIN " + DBParams.authorsTable + " a ON b.authorId=a.authorId \r\n"
								+ " ORDER BY likes DESC limit ?,?");
				
				searchBooks = conn
						.prepareStatement("SELECT b.bookId, b.info, "
								+ "b.genre, b.likes, b.dislikes, b.name as bookName, a.name as authorName FROM "
								+ DBParams.booksTable + " b\r\n" + 
								"LEFT JOIN " + DBParams.authorsTable + " a ON b.authorId=a.authorId \r\n"
								+ " WHERE (b.name like ?) or (a.name like ?)"
								+ "ORDER BY likes DESC limit ?,?");
				
				searchBooksByGenre = conn
						.prepareStatement("SELECT b.bookId, b.info, "
								+ "b.genre, b.likes, b.dislikes, b.name as bookName, a.name as authorName FROM "
								+ DBParams.booksTable + " b\r\n" + 
								"LEFT JOIN " + DBParams.authorsTable + " a ON b.authorId=a.authorId \r\n"
								+ " WHERE b.genre = ? "
								+ "ORDER BY likes DESC limit ?,?");
				
				searchBooksByGenreAndName = conn
						.prepareStatement("SELECT b.bookId, b.info, "
								+ "b.genre, b.likes, b.dislikes, b.name as bookName, a.name as authorName FROM "
								+ DBParams.booksTable + " b\r\n" + 
								"LEFT JOIN " + DBParams.authorsTable + " a ON b.authorId=a.authorId \r\n"
								+ " WHERE (b.genre = ?) and " 
								+ "((b.name like ?) or (a.name like ?))"
								+ "ORDER BY likes DESC limit ?,?");
				
				
				addLike = conn.prepareStatement("UPDATE " + DBParams.booksTable 
						+ " SET likes=likes+1 WHERE bookId = ?");
				
				addDislike = conn.prepareStatement("UPDATE " + DBParams.booksTable 
						+ " SET dislikes=dislikes+1 WHERE bookId = ?");
				
				getBook = conn.prepareStatement("SELECT "
						+ "b.bookId, b.name as bookName, b.likes, b.dislikes, "
						+ "b.info, b.genre, a.name as authorName FROM "
						+ DBParams.booksTable + " b\r\n" + "LEFT JOIN "
						+ DBParams.authorsTable
						+ " a ON b.authorId=a.authorId \r\n"
						+ " WHERE bookId = ?");
				
				insertBook = conn.prepareStatement("INSERT INTO "
						+ DBParams.booksTable
						+ " (name, authorId, genre, info) VALUES (?,?,?,?)");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (NamingException ex) {}

		createTable();
	}

	public void createTable() {
		Statement st;
		try {
			st = conn.createStatement();
			st.execute("CREATE TABLE IF NOT EXISTS "+ DBParams.booksTable+" (\r\n" + 
					"  `bookId` INT NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `authorId` INT NOT NULL,\r\n" +
					"  `name` VARCHAR(100) NULL,\r\n" + 
					"  `info` TEXT NULL,\r\n" + 
					"  `genre` VARCHAR(45) NOT NULL,\r\n" + 
					"  `likes` int NOT NULL DEFAULT 0,\r\n" +
					"  `dislikes` int NOT NULL DEFAULT 0,\r\n" +
					"  PRIMARY KEY (`bookId`))\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8\r\n" + 
					"COLLATE = utf8_general_ci;");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	public List<Book> getPopular(int limit, int offset) throws SQLException {

		List<Book> books = new ArrayList<Book>();

		getPopular.setInt(1, offset);
		getPopular.setInt(2, limit);
		ResultSet rs = getPopular.executeQuery();

		while (rs.next()) {

			books.add(new Book(rs.getInt("bookId"),rs.getString("bookName"),rs.getString("authorName"),
					rs.getString("info"),rs.getInt("likes"),rs.getInt("dislikes"),rs.getString("genre")));
			
		}
		
		return books;

	}
	
	public List<Book> searchBooks(String search, int limit, int offset) throws SQLException {

		List<Book> books = new ArrayList<Book>();

		search = "%".concat(search).concat("%");
		searchBooks.setString(1, search);
		searchBooks.setString(2, search);
		searchBooks.setInt(3, offset);
		searchBooks.setInt(4, limit);
		ResultSet rs = searchBooks.executeQuery();

		while (rs.next()) {

			books.add(new Book(rs.getInt("bookId"),rs.getString("bookName"),rs.getString("authorName"),
					rs.getString("info"),rs.getInt("likes"),rs.getInt("dislikes"),rs.getString("genre")));
			
		}
		
		return books;

	}
	
	public List<Book> searchBooksByGenre(String genre, int limit, int offset) throws SQLException {

		List<Book> books = new ArrayList<Book>();
		
		searchBooksByGenre.setString(1, genre);
		searchBooksByGenre.setInt(2, offset);
		searchBooksByGenre.setInt(3, limit);
		ResultSet rs = searchBooksByGenre.executeQuery();

		while (rs.next()) {

			books.add(new Book(rs.getInt("bookId"),rs.getString("bookName"),rs.getString("authorName"),
					rs.getString("info"),rs.getInt("likes"),rs.getInt("dislikes"),rs.getString("genre")));
			
		}
		
		return books;

	}
	
	public List<Book> searchBooksByGenreAndName(String genre, String name, int limit, int offset) throws SQLException {

		List<Book> books = new ArrayList<Book>();
		
		name = "%".concat(name).concat("%");
		searchBooksByGenreAndName.setString(1, genre);
		searchBooksByGenreAndName.setString(2, name);
		searchBooksByGenreAndName.setString(3, name);
		searchBooksByGenreAndName.setInt(4, offset);
		searchBooksByGenreAndName.setInt(5, limit);
		ResultSet rs = searchBooksByGenreAndName.executeQuery();

		while (rs.next()) {

			books.add(new Book(rs.getInt("bookId"),rs.getString("bookName"),rs.getString("authorName"),
					rs.getString("info"),rs.getInt("likes"),rs.getInt("dislikes"),rs.getString("genre")));
			
		}
		
		return books;

	}
	
	
	public boolean addLike(int bookID) {
		try {

			addLike.setInt(1, bookID);
			
			addLike.execute();
			
		} catch (SQLException e) {

			return false;
		}
		return true;
	}
	
	public boolean addDislike(int bookID) {
		try {

			addDislike.setInt(1, bookID);
			
			addDislike.execute();
			
		} catch (SQLException e) {

			return false;
		}
		return true;
	}
	
	
	public Book getBook(int bookId) throws SQLException {

		Book book = null;
		
		getBook.setInt(1, bookId);
		ResultSet rs = getBook.executeQuery();

		if (rs.next()) {

			book = new Book(rs.getInt("bookId"),rs.getString("bookName"),rs.getString("authorName"));
			book.setBookInfo(rs.getNString("info"));
			book.setLikes(rs.getInt("likes"));
			book.setDislikes(rs.getInt("dislikes"));
			book.setGenre(rs.getString("genre"));

		}
		
		return book;

	}
	
	public boolean insertBook(String bookTitle, String bookAuthor,
			String bookGenre, String info) {

		try {
			
			AuthorsDAO authorsDAO = new AuthorsDAO();
			
			int authorID = authorsDAO.getAuthorIDByName(bookAuthor);
			
			
			System.out.println("Book: " + bookTitle+"\n Author: "
			+ authorID+"\n Genre: "+ bookGenre+"\n Info: "+  info);
			
			insertBook.setNString(1, bookTitle);
			insertBook.setInt(2, authorID);
			insertBook.setNString(3, bookGenre);
			insertBook.setNString(4, info);
		
			insertBook.executeUpdate();

	        

		} catch (SQLException e) {

			return false;
		}
	
		return true;

	}
	

}
