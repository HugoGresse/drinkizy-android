package fr.drinkizy.objects;

import android.os.Parcel;
import android.os.Parcelable;


public class BarImage implements Parcelable{

	private int id;
	private String image;
	private String resource_uri;
	
	
	public int getId() {
		return id;
	}
	public String getImageUrl() {
		return image;
	}
	public String getResource_uri() {
		return resource_uri;
	}
	
	@Override
	public String toString() {
	   return "BarImage [url=" + image+"]";
	}
	
	public static final Parcelable.Creator<BarImage> CREATOR = new Creator<BarImage>() { 
		public BarImage createFromParcel(Parcel source) { 
			BarImage mBarImage = new BarImage(); 
			mBarImage.id = source.readInt(); 
			mBarImage.image = source.readString(); 
			mBarImage.resource_uri = source.readString(); 
		    return mBarImage; 
		}

		@Override
		public BarImage[] newArray(int size) {
			return new BarImage[size];
		}
		
	};
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(image);
		dest.writeString(resource_uri);
		
	}

	
	
}
