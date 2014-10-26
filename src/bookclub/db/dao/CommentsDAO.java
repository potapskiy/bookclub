package bookclub.db.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bookclub.db.DBParams;

public class CommentsDAO {
	
	Connection conn = null;

		public CommentsDAO() {

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
			st.execute("CREATE TABLE "+ DBParams.commentsTable + " (\r\n" + 
					"  `commentId` INT NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `userId` INT NOT NULL,\r\n" + 
					"  `bookId` INT NOT NULL,\r\n" + 
					"  `commentText` TEXT NOT NULL,\r\n" + 
					"  PRIMARY KEY (`commentId`))\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8\r\n" + 
					"COLLATE = utf8_general_ci;");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
