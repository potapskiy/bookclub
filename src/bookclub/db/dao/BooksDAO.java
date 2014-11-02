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

	public BooksDAO() {

		try {
			InitialContext initialContext = new InitialContext();
			try {

				DataSource ds = (DataSource) initialContext
						.lookup(DBParams.JDBC_ENV);
				conn = ds.getConnection();
				
				
				getPopular = conn
						.prepareStatement("SELECT b.bookId, b.name as bookName, a.name as authorName FROM "
								+ DBParams.booksTable + " b\r\n" + 
								"LEFT JOIN " + DBParams.authorsTable + " a ON b.authorId=a.authorId \r\n"
								+ " ORDER BY likes DESC limit ?,?");
				

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
			st.execute("CREATE TABLE "+ DBParams.booksTable+" (\r\n" + 
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

			books.add(new Book(rs.getInt("bookId"),rs.getString("bookName"),rs.getString("authorName")));

		}
		
		return books;

	}

}
