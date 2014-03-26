package fr.drinkizy.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class DrinkSubcategory implements Parcelable {
	
	private String description;
	private String name;
	private String slug;
	private String resource_uri;
	
	private DrinkCategory category;
	
	
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

	public DrinkCategory getCategory() {
		return category;
	}
	
	
	@Override
	public String toString() {
		return "DrinkSubategory [name=" + name + ", description=" + description
				+ "]";
	}
	
	public static final Parcelable.Creator<DrinkSubcategory> CREATOR = new Creator<DrinkSubcategory>() { 
		public DrinkSubcategory createFromParcel(Parcel source) { 
			DrinkSubcategory mDrinkSubcategory = new DrinkSubcategory(); 
			mDrinkSubcategory.description = source.readString();
			mDrinkSubcategory.name = source.readString(); 
			mDrinkSubcategory.slug = source.readString();
			mDrinkSubcategory.resource_uri = source.readString(); 
			mDrinkSubcategory.category = source.readParcelable(DrinkCategory.class.getClassLoader()); 
		    return mDrinkSubcategory; 
		}

		@Override
		public DrinkSubcategory[] newArray(int size) {
			return new DrinkSubcategory[size];
		}
		
	};
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(description);
		dest.writeString(name);
		dest.writeString(slug);
		dest.writeString(resource_uri);
		dest.writeParcelable(category, 0);
	}
	
}
