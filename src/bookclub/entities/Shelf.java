package bookclub.entities;

public class Shelf {

	
	private int id;
	private int userId;
	private String name;
	private int count;
	
	public Shelf(int id, int userId, String name, int count) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
