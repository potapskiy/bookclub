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
import bookclub.entities.Comment;

public class CommentsDAO {

	Connection conn = null;

	PreparedStatement getBookComments;
	PreparedStatement insertComment;
	PreparedStatement deleteComment;

	public CommentsDAO() {

		try {
			InitialContext initialContext = new InitialContext();
			try {

				DataSource ds = (DataSource) initialContext
						.lookup(DBParams.JDBC_ENV);
				conn = ds.getConnection();

				getBookComments = conn.prepareStatement("SELECT * FROM "
						+ DBParams.commentsTable + " c\r\n" + "LEFT JOIN "
						+ DBParams.usersTable + " u ON c.userId=u.userId \r\n"
						+ "WHERE bookId = ?");
				
				insertComment = conn.prepareStatement("INSERT INTO " + DBParams.commentsTable
						+ " (userId, bookId, commentText) VALUES (?,?,?)");
				
				deleteComment = conn.prepareStatement("DELETE * FROM " + DBParams.commentsTable
						+ " WHERE commentId = ?");

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
			st.execute("CREATE TABLE " + DBParams.commentsTable + " (\r\n"
					+ "  `commentId` INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `userId` INT NOT NULL,\r\n"
					+ "  `bookId` INT NOT NULL,\r\n"
					+ "  `commentText` TEXT NOT NULL,\r\n"
					+ "  PRIMARY KEY (`commentId`))\r\n"
					+ "ENGINE = InnoDB\r\n"
					+ "DEFAULT CHARACTER SET = utf8\r\n"
					+ "COLLATE = utf8_general_ci;");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Comment> getBookComments(int bookId) throws SQLException {

		List<Comment> comments = new ArrayList<Comment>();

		getBookComments.setInt(1, bookId);
		ResultSet rs = getBookComments.executeQuery();

		while (rs.next()) {

			comments.add(new Comment(rs.getInt("commentId"),rs.getString("name"), rs
					.getString("surname"), rs.getString("commentText")));

		}
		
		return comments;

	}
	
	public boolean insertComment(int userId, int bookId, String comment) {

		
		try {

			insertComment.setInt(1, userId);
			insertComment.setInt(2, bookId);
			insertComment.setNString(3, comment);
			
			insertComment.execute();
			
		} catch (SQLException e) {

			return false;
		}
		return true;
		
	}
	
	public boolean deleteComment(int commentId) {

		
		try {

			deleteComment.setInt(1, commentId);

			
			deleteComment.execute();
			
		} catch (SQLException e) {

			return false;
		}
		return true;
		
	}
}
