package fr.drinkizy.objects;


public class Drink {		
			
		private String name;
		private String description;
		private String slug;
		private String resource_uri;
		
		private String subcategory;
		private String creator;
		
		private transient DrinkSubcategory mSubcategory;
		//private transient User mCreator;
		
		
		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
		public String getSlug() {
			return slug;
		}
		public String getCreatorUri() {
			return creator;
		}
		public String getSubcategoryUri() {
			return subcategory;
		}
		public String getResource_uri() {
			return resource_uri;
		}
		
//		public User getCreator() {
//			return mCreator;
//		}
		public DrinkSubcategory getSubcategory() {
			return mSubcategory;
		}
		
		@Override
		public String toString() {
			return "Drink [name=" + name + ", description=" + description
					+ ", mSubcategory=" + mSubcategory + "]";
		}
		
		
}
