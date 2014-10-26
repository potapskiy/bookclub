package bookclub.db.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bookclub.db.DBParams;

public class BooksDAO {
	
	Connection conn = null;

		public BooksDAO() {

		try {
			InitialContext initialContext = new InitialContext();
			try {

				DataSource ds = (DataSource) initialContext
						.lookup(DBParams.JDBC_ENV);
				conn = ds.getConnection();

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
					"  `info` TEXT NULL,\r\n" + 
					"  `genre` VARCHAR(45) NOT NULL,\r\n" + 
					"  `mark` FLOAT NOT NULL DEFAULT 0,\r\n" + 
					"  PRIMARY KEY (`bookId`))\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8\r\n" + 
					"COLLATE = utf8_general_ci;");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
