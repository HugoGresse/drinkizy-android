package fr.drinkizy.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	   return "User [user_name=" + username + "]";
	}

	
	
}
