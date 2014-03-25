package fr.drinkizy.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class DrinkCategory implements Parcelable {
	
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
	
	public static final Parcelable.Creator<DrinkCategory> CREATOR = new Creator<DrinkCategory>() { 
		public DrinkCategory createFromParcel(Parcel source) { 
			DrinkCategory mDrinkCategory = new DrinkCategory(); 
			mDrinkCategory.description = source.readString();
			mDrinkCategory.name = source.readString(); 
			mDrinkCategory.slug = source.readString();
			mDrinkCategory.resource_uri = source.readString(); 
		    return mDrinkCategory; 
		}

		@Override
		public DrinkCategory[] newArray(int size) {
			return new DrinkCategory[size];
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
	}
	
}
