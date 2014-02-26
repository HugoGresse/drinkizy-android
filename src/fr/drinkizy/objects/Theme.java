package fr.drinkizy.objects;

import com.google.gson.annotations.Expose;

public class Theme {
	@Expose private String name;
	@Expose private String description;
	@Expose private String slug;
	@Expose private String resource_uri;
	
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
