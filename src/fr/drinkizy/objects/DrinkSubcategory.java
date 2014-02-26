package fr.drinkizy.objects;

public class DrinkSubcategory {
	
	private String description;
	private String name;
	private String slug;
	private String resource_uri;
	private String category;
	
	private transient DrinkCategory mCategory;
	
	
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
	public String getCategoryUri() {
		return category;
	}
	
	public DrinkCategory getCategory() {
		return mCategory;
	}
	
	
	@Override
	public String toString() {
		return "DrinkSubategory [name=" + name + ", description=" + description
				+ "]";
	}
	
}
