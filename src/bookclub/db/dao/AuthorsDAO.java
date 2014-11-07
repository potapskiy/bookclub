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

public class AuthorsDAO {
	
	Connection conn = null;

	PreparedStatement insertAuthor;
	
	public AuthorsDAO() {

		try {
			InitialContext initialContext = new InitialContext();
			try {

				DataSource ds = (DataSource) initialContext
						.lookup(DBParams.JDBC_ENV);
				conn = ds.getConnection();
				
				insertAuthor = conn.prepareStatement("INSERT INTO "
						+ DBParams.authorsTable
						+ " (name, info) VALUES (?,?)");

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
			st.execute("CREATE TABLE IF NOT EXISTS " + DBParams.authorsTable + " (\r\n" + 
					"  `authorId` INT NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `name` VARCHAR(100) NULL,\r\n" +
					"  `info` TEXT NULL,\r\n" + 
					"  `mark` FLOAT NOT NULL DEFAULT 0,\r\n" + 
					"  PRIMARY KEY (`authorId`))\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8\r\n" + 
					"COLLATE = utf8_general_ci;");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	public boolean insertAuthor(String name, String info) {

		try {

			insertAuthor.setNString(1, name);
			insertAuthor.setNString(2, info);
		
			insertAuthor.executeUpdate();

	        

		} catch (SQLException e) {

			return false;
		}
	
		return true;

	}

}
