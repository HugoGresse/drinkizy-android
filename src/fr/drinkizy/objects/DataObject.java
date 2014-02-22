package fr.drinkizy.objects;

import java.util.List;

public class DataObject<T> {
	private List<T> objects;

	public List<T> getBars() {
		return objects;
	}

	@Override
	public String toString() {
		return "DataObject [bars=" + objects + "]";
	} 
	
	
}
