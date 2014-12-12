package bookclub.entities;

public class Comment {
	
	private int commentId;
	private int userId;
	private String userName;
	private String userSurname;
	private String userLogin;
	private String commentText;
	
	public Comment(int commentId, String userName, String userSurname, String commentText) {
		super();
		this.commentId = commentId;
		this.userName = userName;
		this.userSurname = userSurname;
		this.commentText = commentText;
	}
	
	public Comment(int commentId, String userName, String userSurname, String login, String commentText) {
		super();
		this.commentId = commentId;
		this.userName = userName;
		this.userSurname = userSurname;
		this.userLogin = login;
		this.commentText = commentText;
	}
	
	public Comment(int commentId, int userId, String userName,
			String userSurname, String commentText) {
		super();
		this.commentId = commentId;
		this.userId = userId;
		this.userName = userName;
		this.userSurname = userSurname;
		this.commentText = commentText;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSurname() {
		return userSurname;
	}
	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	

}
