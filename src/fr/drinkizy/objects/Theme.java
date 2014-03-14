package fr.drinkizy.objects;


public class Theme {
	private String name;
	private String description;
	private String slug;
	private String resource_uri;
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getSlug() {
		return slug;
	}
	public String getResource_uri() {
		return resource_uri;
	}
	
	
	
	
	@Override
	public String toString() {
	   return "Theme [name=" + name + ", description=" + description +"]";
	}

	
	
}
