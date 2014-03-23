package fr.drinkizy.objects;

import java.util.List;

public class CommentsObject {
	private List<Comment> objects;

	public List<Comment> getObjects() {
		return objects;
	}
	

	@Override
	public String toString() {
		return "DataObject [comments=" + objects + "]";
	} 
	
	
}
