package fr.drinkizy.objects;


public class Comment {
	private int id;
	
	private String comment;
	private String submit_date;
	private User user;
	
	
	public int getId() {
		return id;
	}
	public String getComment() {
		return comment;
	}
	public String getDate() {
		return submit_date;
	}
	public User getUser() {
		return user;
	}
	
	@Override
	public String toString() {
	   return "Comment [user=" + user + ", comment=" + comment +", date=" + submit_date +"]";
	}

	
	
}
