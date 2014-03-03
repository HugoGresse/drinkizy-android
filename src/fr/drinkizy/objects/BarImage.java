package fr.drinkizy.objects;


public class BarImage {

	private int id;
	private String image;
	private String resource_uri;
	
	
	public int getId() {
		return id;
	}
	public String getImageUrl() {
		return image;
	}
	public String getResource_uri() {
		return resource_uri;
	}
	
	@Override
	public String toString() {
	   return "BarImage [url=" + image+"]";
	}

	
	
}
