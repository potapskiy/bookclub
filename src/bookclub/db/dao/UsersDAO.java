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

public class UsersDAO {

	Connection conn = null;

	PreparedStatement isUserRegistred;
	PreparedStatement insertUser;

	public UsersDAO() {

		try {
			InitialContext initialContext = new InitialContext();
			try {

				DataSource ds = (DataSource) initialContext
						.lookup(DBParams.JDBC_ENV);
				conn = ds.getConnection();

				isUserRegistred = conn.prepareStatement("SELECT * FROM "
						+ DBParams.usersTable + " WHERE login = ? LIMIT 1");
				
				insertUser = conn.prepareStatement("INSERT INTO " + DBParams.usersTable
						+ " (login, password, name, surname) VALUES (?,?,?,?)");

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
			st.execute("CREATE TABLE " + DBParams.usersTable + " (\r\n"
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



			isUserRegistred.setNString(1, userLogin);
			ResultSet rs = isUserRegistred.executeQuery();

			if (rs.next()) {
				return true;
			}

		return false;

	}

	public boolean insertUser(String login, String password, String name,
			String surname) {

		
		try {

			insertUser.setNString(1, login);
			insertUser.setNString(2, password);
			insertUser.setNString(3, name);
			insertUser.setNString(4, surname);
			
			insertUser.execute();
			
		} catch (SQLException e) {

			return false;
		}
		return true;
		
	}

}
