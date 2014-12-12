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
import bookclub.entities.Comment;
import bookclub.entities.Shelf;

public class ShelfsDAO {

	Connection conn = null;

	PreparedStatement insertShelf;
	PreparedStatement updateShelf;
	PreparedStatement getShelf;
	PreparedStatement deleteShelf;
	PreparedStatement deleteShelfBooks;
	PreparedStatement insertBookToShelf;
	PreparedStatement deleteBookFromShelf;
	PreparedStatement getUserShelfs;
	PreparedStatement getUserShelfsCount;
	PreparedStatement getBooksOnShelfCount;
	PreparedStatement getBooksOnShelf;

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

				getShelf = conn.prepareStatement("SELECT * FROM "
						+ DBParams.shelfsTable + " WHERE shelfId = ?)");

				updateShelf = conn.prepareStatement("UPDATE "
						+ DBParams.shelfsTable
						+ " SET name = ? WHERE shelfId = ?");

				deleteShelf = conn.prepareStatement("DELETE FROM "
						+ DBParams.shelfsTable + " WHERE shelfId = ?");

				deleteShelfBooks = conn.prepareStatement("DELETE FROM "
						+ DBParams.booksOnShelfs + " WHERE shelfId = ?");

				insertBookToShelf = conn.prepareStatement("INSERT INTO "
						+ DBParams.booksOnShelfs
						+ " (shelfId, bookId) VALUES (?,?)");

				deleteBookFromShelf = conn.prepareStatement("DELETE FROM "
						+ DBParams.booksOnShelfs
						+ " WHERE shelfId =? AND bookId = ?");

				getUserShelfs = conn.prepareStatement("SELECT * FROM "
						+ DBParams.shelfsTable + " WHERE userId = ? limit ?,?");

				getUserShelfsCount = conn
						.prepareStatement("SELECT COUNT(*) as com_count FROM "
								+ DBParams.shelfsTable + " WHERE userId = ?");

				getBooksOnShelfCount = conn
						.prepareStatement("SELECT COUNT(*) as com_count FROM "
								+ DBParams.booksOnShelfs + " WHERE shelfId = ?");

				getBooksOnShelf = conn
						.prepareStatement("SELECT bs.bookId, b.name as bookName, "
								+ "b.genre, b.likes, b.dislikes, "
								+ "a.name as authorName FROM "
								+ DBParams.booksOnShelfs
								+ " bs\r\n"
								+ "LEFT JOIN "
								+ DBParams.booksTable
								+ " u ON bs.bookId=b.bookId \r\n"
								+ "LEFT JOIN "
								+ DBParams.authorsTable
								+ " a ON b.authorId=a.authorId \r\n"
								+ " WHERE shelfId = ? limit ?,?");

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
			st.execute("CREATE TABLE IF NOT EXISTS " + DBParams.shelfsTable
					+ " (\r\n" + "  `shelfId` INT NOT NULL AUTO_INCREMENT,\r\n"
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
			st.execute("CREATE TABLE IF NOT EXISTS " + DBParams.booksOnShelfs
					+ " (\r\n" + "  `shelfId` INT NOT NULL,\r\n"
					+ "  `bookId` INT NOT NULL\r\n" + "  )\r\n"
					+ "ENGINE = InnoDB\r\n"
					+ "DEFAULT CHARACTER SET = utf8\r\n"
					+ "COLLATE = utf8_general_ci;");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Shelf getShelf(int shelfId) {

		try {
			getShelf.setInt(1, shelfId);

			ResultSet rs = getShelf.executeQuery();

			if (rs.next()) {

				return new Shelf(rs.getInt("shelfId"), rs.getInt("userId"),
						rs.getNString("name"), 0);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new Shelf();

	}

	public int insertShelf(int userId, String name) {

		try {

			insertShelf.setInt(1, userId);
			insertShelf.setNString(2, name);

			int affectedRows = insertShelf.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException(
						"Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = insertShelf.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException(
							"Creating user failed, no ID obtained.");
				}
			}

		} catch (SQLException e) {

			return 0;
		}

	}

	public boolean updateShelf(int shelfId, String name) {

		try {

			updateShelf.setNString(1, name);
			updateShelf.setInt(2, shelfId);

			updateShelf.executeUpdate();

		} catch (SQLException e) {

			return false;
		}

		return true;

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

	public int getUserShelfsCount(int userId) throws SQLException {

		getUserShelfsCount.setInt(1, userId);
		ResultSet rs = getUserShelfsCount.executeQuery();

		int count = (rs.next()) ? rs.getInt("com_count") : 0;

		return count;

	}

	public List<Shelf> getUserShelfs(int userId, int limit, int offset)
			throws SQLException {

		List<Shelf> userShelfs = new ArrayList<Shelf>();

		getUserShelfs.setInt(1, userId);
		getUserShelfs.setInt(2, offset);
		getUserShelfs.setInt(3, limit);
		ResultSet rs = getUserShelfs.executeQuery();

		while (rs.next()) {

			int shelfId = rs.getInt("shelfId");
			int bookCount = getBooksOnShelfCount(shelfId);
			userShelfs.add(new Shelf(shelfId, userId, rs.getString("name"),
					bookCount));

		}

		return userShelfs;

	}

	public int getBooksOnShelfCount(int shelfId) throws SQLException {

		getBooksOnShelfCount.setInt(1, shelfId);
		ResultSet rs = getBooksOnShelfCount.executeQuery();

		int count = (rs.next()) ? rs.getInt("com_count") : 0;

		return count;

	}

	public List<Book> getBooksOnShelf(int shelfId, int limit, int offset)
			throws SQLException {

		List<Book> books = new ArrayList<Book>();

		getBooksOnShelf.setInt(1, shelfId);
		getBooksOnShelf.setInt(2, offset);
		getBooksOnShelf.setInt(3, limit);
		ResultSet rs = getBooksOnShelf.executeQuery();

		while (rs.next()) {

			books.add(new Book(rs.getInt("bookId"), rs.getString("bookName"),
					rs.getString("authorName"),"",rs.getInt("likes"),rs.getInt("dislikes"),rs.getString("genre")));

		}

		return books;

	}

}
