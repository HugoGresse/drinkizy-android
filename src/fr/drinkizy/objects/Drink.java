package fr.drinkizy.objects;

import android.os.Parcel;
import android.os.Parcelable;


public class Drink implements Parcelable {		
			
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
		
		public static final Parcelable.Creator<Drink> CREATOR = new Creator<Drink>() { 
			public Drink createFromParcel(Parcel source) { 
				Drink mDrink = new Drink(); 
				mDrink.name = source.readString(); 
				mDrink.description = source.readString();
				mDrink.slug = source.readString();
				mDrink.resource_uri = source.readString(); 
				mDrink.subcategory = source.readParcelable(DrinkSubcategory.class.getClassLoader()); 
			    return mDrink; 
			}

			@Override
			public Drink[] newArray(int size) {
				return new Drink[size];
			}
			
		};
		
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(name);
			dest.writeString(description);
			dest.writeString(slug);
			dest.writeString(resource_uri);
			dest.writeParcelable(subcategory, 0);
		}
		
		
}
