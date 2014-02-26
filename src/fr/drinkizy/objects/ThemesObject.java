package fr.drinkizy.objects;

import java.util.List;

public class ThemesObject {
	private List<Theme> objects;

	public List<Theme> getObjects() {
		return objects;
	}
	

	@Override
	public String toString() {
		return "DataObject [themes=" + objects + "]";
	} 
	
	
}
