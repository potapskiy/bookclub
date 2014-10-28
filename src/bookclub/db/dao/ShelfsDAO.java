package bookclub.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bookclub.db.DBParams;

public class ShelfsDAO {

	Connection conn = null;
	
	PreparedStatement insertShelf;
	PreparedStatement deleteShelf;
	PreparedStatement deleteShelfBooks;
	PreparedStatement insertBookToShelf;
	PreparedStatement deleteBookFromShelf;
	public ShelfsDAO() {

		try {
			InitialContext initialContext = new InitialContext();
			try {

				DataSource ds = (DataSource) initialContext
						.lookup(DBParams.JDBC_ENV);
				conn = ds.getConnection();

				insertShelf = conn
						.prepareStatement("INSERT INTO " + DBParams.shelfsTable
								+ " (userId, name) VALUES (?,?)");
				
				deleteShelf = conn.prepareStatement("DELETE * FROM " + DBParams.shelfsTable
						+ " WHERE shelfId = ?");
				
				deleteShelfBooks = conn.prepareStatement("DELETE * FROM " + DBParams.booksOnShelfs
						+ " WHERE shelfId = ?");
				
				insertBookToShelf = conn
						.prepareStatement("INSERT INTO " + DBParams.booksOnShelfs
								+ " (shelfId, bookId) VALUES (?,?)");
				
				deleteBookFromShelf = conn
						.prepareStatement("DELETE * FROM " + DBParams.booksOnShelfs
								+ " WHERE shelfId =? AND bookId = ?");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (NamingException ex) {
		}

		createShelfsTable();
		createBooksOnShelfsTable();
	}

	public void createShelfsTable() {
		Statement st;
		try {
			st = conn.createStatement();
			st.execute("CREATE TABLE " + DBParams.shelfsTable + " (\r\n"
					+ "  `shelfId` INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `userId` INT NOT NULL,\r\n"
					+ "  `name` VARCHAR(45) NOT NULL,\r\n"
					+ "  PRIMARY KEY (`shelfId`))\r\n" + "ENGINE = InnoDB\r\n"
					+ "DEFAULT CHARACTER SET = utf8\r\n"
					+ "COLLATE = utf8_general_ci;");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void createBooksOnShelfsTable() {
		Statement st;
		try {
			st = conn.createStatement();
			st.execute("CREATE TABLE " + DBParams.booksOnShelfs + " (\r\n"
					+ "  `shelfId` INT NOT NULL,\r\n"
					+ "  `bookId` INT NOT NULL,\r\n" + "  )\r\n"
					+ "ENGINE = InnoDB\r\n"
					+ "DEFAULT CHARACTER SET = utf8\r\n"
					+ "COLLATE = utf8_general_ci;");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	public int insertShelf(int userId, String name) {

		try {

			insertShelf.setInt(1, userId);
			insertShelf.setNString(2, name);


			int affectedRows = insertShelf.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
	        
	        
	        try (ResultSet generatedKeys = insertShelf.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	               return generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }

		} catch (SQLException e) {

			return 0;
		}

	}
	
	public boolean deleteShelf(int shelfId) {

		
		try {

			deleteShelf.setInt(1, shelfId);
			deleteShelf.execute();
			
			deleteShelfBooks.setInt(1, shelfId);
			deleteShelfBooks.execute();
			
		} catch (SQLException e) {

			return false;
		}
		return true;
		
	}
	
	public boolean addBook(int shelfId, int bookId) {

		try {

			insertBookToShelf.setInt(1, shelfId);
			insertBookToShelf.setInt(2, bookId);
			insertBookToShelf.execute();
			
		} catch (SQLException e) {
			return false;
		}
		return true;
		
	}
	
	public boolean deleteBook(int shelfId, int bookId) {

		try {

			deleteBookFromShelf.setInt(1, shelfId);
			deleteBookFromShelf.setInt(2, bookId);
			deleteBookFromShelf.execute();
			
		} catch (SQLException e) {
			return false;
		}
		return true;
		
	}

}
