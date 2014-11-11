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
import bookclub.entities.User;

public class AuthorsDAO {
	
	Connection conn = null;

	PreparedStatement insertAuthor;
	PreparedStatement getIdByName;
	
	public AuthorsDAO() {

		try {
			InitialContext initialContext = new InitialContext();
			try {

				DataSource ds = (DataSource) initialContext
						.lookup(DBParams.JDBC_ENV);
				conn = ds.getConnection();
				
				insertAuthor = conn.prepareStatement("INSERT INTO "
						+ DBParams.authorsTable
						+ " (name, info) VALUES (?,?)",
						Statement.RETURN_GENERATED_KEYS);
				
				getIdByName = conn.prepareStatement("SELECT * FROM "
						+ DBParams.authorsTable + " WHERE name = ? LIMIT 1");

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
	
	
	public int insertAuthor(String name, String info) {

		try {

			insertAuthor.setNString(1, name);
			insertAuthor.setNString(2, info);
		
			int affectedRows = insertAuthor.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
	        
	        
	        try (ResultSet generatedKeys = insertAuthor.getGeneratedKeys()) {
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
	
	public int getAuthorIDByName(String name) {

		try {

			getIdByName.setNString(1, name);
			ResultSet rs = getIdByName.executeQuery();

			int id = 0;
			
			if (rs.next()) {
				id = rs.getInt("authorId");
			}

			if (id == 0){
				id = insertAuthor(name, ""); 
			}

	        
			return id;
		} catch (SQLException e) {
			return 0;
		}
	
		
	}
	
	
	
	public List<String> getAuthorsList(String textForSearch){
		List<String> tl = new ArrayList<String>();
		ResultSet rs;
		String name;
		textForSearch = textForSearch.toLowerCase();
		
		System.out.println("aaaaaaaaaaaaaaaaaaaa");
		try {
			String query = "SELECT name FROM authors WHERE name LIKE '%"+textForSearch+"%'";
			
			PreparedStatement pst = conn.prepareStatement(query);
			
			
			rs = pst.executeQuery();
			while (rs.next()) {
				name = rs.getString("name");
				System.out.println(name);
				tl.add(name);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return tl;
		
		
	}

}
