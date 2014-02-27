package fr.drinkizy.objects;


public class Drink {		
			
		private String name;
		private String description;
		private String slug;
		private String resource_uri;
		
		private DrinkSubcategory subcategory;
		

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

		public DrinkSubcategory getSubcategory() {
			return subcategory;
		}
		public String getResource_uri() {
			return resource_uri;
		}
		
		public String getCategoryChain() {
			return subcategory.getCategory().getName()+" > "+subcategory.getName();
		}

		
		@Override
		public String toString() {
			return "Drink [name=" + name + ", description=" + description
					+ ", mSubcategory=" + subcategory + "]";
		}
		
		
}
