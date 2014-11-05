package bookclub.db;

public interface DBParams {

	String JDBC_ENV = "java:comp/env/jdbc/DBBookClub";
	
	String SALT = "text_for_salt";
	
	
	
	String usersTable = "users";
	String booksTable = "books";
	String authorsTable = "authors";
	String commentsTable = "comments";
	String adminTable = "admin";
	
	String shelfsTable = "shelfs";
	String booksOnShelfs = "book_on_shelfs";
	
}
