package fr.drinkizy.objects;

import java.util.List;

public class DrinkbarsObject {
	private List<Drinkbar> objects;

	public List<Drinkbar> getObjects() {
		return objects;
	}
	

	@Override
	public String toString() {
		return "DataObject [bars=" + objects + "]";
	} 
	
	
}
