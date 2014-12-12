package bookclub.entities;

public class Book {
	
	private int id;
	private String bookName;
	private String authorName;
	private String bookInfo;
	private int likes;
	private int dislikes;
	private String genre;
	
	
	public Book() {
		this.id = 0;
		this.bookName = "";
		this.authorName = "";
		this.bookInfo = "";
		this.likes = 0;
		this.dislikes = 0;
		this.genre = "";
	}
	
	
	public Book(int id, String bookName, String authorName) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.authorName = authorName;
		this.bookInfo = "";
		this.likes = 0;
		this.dislikes = 0;
		this.genre = "";
	}
	
	public Book(int id, String bookName, String authorName, String bookInfo,
			int likes, int dislikes, String genre) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.authorName = authorName;
		this.bookInfo = bookInfo;
		this.likes = likes;
		this.dislikes = dislikes;
		this.genre = genre;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public String getBookInfo() {
		return bookInfo;
	}


	public void setBookInfo(String bookInfo) {
		this.bookInfo = bookInfo;
	}


	public int getLikes() {
		return likes;
	}


	public void setLikes(int likes) {
		this.likes = likes;
	}


	public int getDislikes() {
		return dislikes;
	}


	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
	

}
