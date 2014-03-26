package fr.drinkizy.objects;

import android.os.Parcel;
import android.os.Parcelable;


public class Theme implements Parcelable{
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
	
	public static final Parcelable.Creator<Theme> CREATOR = new Creator<Theme>() { 
		public Theme createFromParcel(Parcel source) { 
			Theme mTheme = new Theme(); 
			mTheme.name = source.readString(); 
			mTheme.description = source.readString(); 
			mTheme.slug = source.readString(); 
			mTheme.resource_uri = source.readString(); 
		    return mTheme; 
		}

		@Override
		public Theme[] newArray(int size) {
			return new Theme[size];
		}
		
	};
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(name); 
		parcel.writeString(description); 
		parcel.writeString(slug); 
		parcel.writeString(resource_uri); 
		
	}

	
	
}
