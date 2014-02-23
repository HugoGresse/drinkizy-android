package fr.drinkizy.objects;

import java.util.List;

public class BarsObject {
	private List<Bar> objects;

	public List<Bar> getObjects() {
		return objects;
	}

	@Override
	public String toString() {
		return "DataObject [bars=" + objects + "]";
	} 
	
	
}
