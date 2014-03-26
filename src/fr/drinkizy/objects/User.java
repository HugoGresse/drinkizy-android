package fr.drinkizy.objects;


public class User {
	private int id;
	
	private String avatar;
	private String username;
	
	
	public int getId() {
		return id;
	}
	public String getAvatarUrl() {
		return avatar;
	}
	public String getUserName() {
		return username;
	}
	
	@Override
	public String toString() {
	   return username;
	}

	
	
}
