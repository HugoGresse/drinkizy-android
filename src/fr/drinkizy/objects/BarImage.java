package fr.drinkizy.objects;

import android.os.Parcel;
import android.os.Parcelable;


public class BarImage implements Parcelable{

	private int id;
	private String image;
	private String resource_uri;
	
	public BarImage(){
		
	}
	public BarImage(Parcel in){
		id = in.readInt(); 
		image = in.readString(); 
		resource_uri = in.readString(); 
	}
	
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

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(image);
		dest.writeString(resource_uri);
		
	}
	
	public static final Parcelable.Creator<BarImage> CREATOR = new Creator<BarImage>() { 
		public BarImage createFromParcel(Parcel source) { 
			return new BarImage(source); 
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

	
	
}
