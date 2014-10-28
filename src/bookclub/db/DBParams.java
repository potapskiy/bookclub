package bookclub.db;

public interface DBParams {

	String JDBC_ENV = "java:comp/env/jdbc/DBBookClub";
	
	String usersTable = "users";
	String booksTable = "books";
	String authorsTable = "authors";
	String commentsTable = "comments";
	
	String shelfsTable = "shelfs";
	String booksOnShelfs = "book_on_shelfs";
	
}
