package fr.drinkizy.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
