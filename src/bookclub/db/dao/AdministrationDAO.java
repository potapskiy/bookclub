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
import bookclub.entities.User;

public class AdministrationDAO {

	Connection conn = null;

	PreparedStatement selectUserByLogin;
	PreparedStatement insertUser;
	PreparedStatement isUserRegistred;

	public AdministrationDAO() {

		try {
			InitialContext initialContext = new InitialContext();
			try {

				DataSource ds = (DataSource) initialContext
						.lookup(DBParams.JDBC_ENV);
				conn = ds.getConnection();

				selectUserByLogin = conn.prepareStatement("SELECT * FROM "
						+ DBParams.adminTable + " WHERE login = ? LIMIT 1");
				
				isUserRegistred = conn.prepareStatement("SELECT * FROM "
						+ DBParams.adminTable + " WHERE login = ? AND password = ? LIMIT 1");

				insertUser = conn.prepareStatement("INSERT INTO "
						+ DBParams.adminTable
						+ " (login, password, name, surname) VALUES (?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (NamingException ex) {
		}

		createTable();
	}

	public void createTable() {
		Statement st;
		try {
			st = conn.createStatement();
			st.execute("CREATE TABLE IF NOT EXISTS " + DBParams.adminTable + " (\r\n"
					+ "  `userId` INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `login` VARCHAR(45) NOT NULL,\r\n"
					+ "  `password` VARCHAR(256) NOT NULL,\r\n"
					+ "  `name` VARCHAR(45) NOT NULL,\r\n"
					+ "  `surname` VARCHAR(45) NOT NULL,\r\n"
					+ "  PRIMARY KEY (`userId`))\r\n" + "ENGINE = InnoDB\r\n"
					+ "DEFAULT CHARACTER SET = utf8\r\n"
					+ "COLLATE = utf8_general_ci;\r\n" + "");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean isUserRegistred(String userLogin) throws SQLException {

		selectUserByLogin.setNString(1, userLogin);
		ResultSet rs = selectUserByLogin.executeQuery();

		if (rs.next()) {
			return true;
		}

		return false;

	}
	
	public User isUserDataCorrect(String userLogin, String userPassword) throws SQLException {

		isUserRegistred.setNString(1, userLogin);
		isUserRegistred.setNString(2, userPassword);
		ResultSet rs = isUserRegistred.executeQuery();

		if (rs.next()) {
			return new User(rs.getInt("userId"), rs.getString("login"), 
					rs.getString("password"), rs.getString("name"), rs.getString("surname"));
		}

		return null;

	}

	public int insertUser(String login, String password, String name,
			String surname) {

		try {

			insertUser.setNString(1, login);
			insertUser.setNString(2, password);
			insertUser.setNString(3, name);
			insertUser.setNString(4, surname);

			int affectedRows = insertUser.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
	        
	        
	        try (ResultSet generatedKeys = insertUser.getGeneratedKeys()) {
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
	
	public User getUser(String userLogin) throws SQLException {

		selectUserByLogin.setNString(1, userLogin);
		ResultSet rs = selectUserByLogin.executeQuery();

		if (rs.next()) {
			return new User(rs.getInt("userId"), rs.getString("login"), 
					rs.getString("password"), rs.getString("name"), rs.getString("surname"));
		}

		return null;

	}

}
