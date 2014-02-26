package fr.drinkizy.objects;

public class DrinkCategory {
	
	private String description;
	private String name;
	private String slug;
	private String resource_uri;
	
	
	public String getDescription() {
		return description;
	}
	public String getName() {
		return name;
	}
	public String getSlug() {
		return slug;
	}
	public String getResource_uri() {
		return resource_uri;
	}
	
	
	@Override
	public String toString() {
		return "DrinkCategory [name=" + name + ", description=" + description
				+ "]";
	}
	
}
