package fr.drinkizy.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Bar implements Parcelable {
	private int id;
	
	private String address;
	private int approval;
	private int disapproval;
	private String description;
	
	private double latitude;
	private double longitude;
	private String mail;
	
	private String name;
	private String phone;
	
	private String resource_uri;
	private String slug;
	
	private String website;
	
	private float rank;
	private int distance;
	
	private List<Theme> themes = new ArrayList<Theme>();
	private List<BarImage> images = new ArrayList<BarImage>();
	private transient List<Drinkbar> drinks_list = new ArrayList<Drinkbar>();
	
	
	public Bar(){
		
	}
	
	public Bar(Parcel in){
	    address = in.readString(); 
	    description = in.readString(); 
	    mail = in.readString(); 
	    name = in.readString(); 
		phone = in.readString(); 
		resource_uri = in.readString(); 
		slug = in.readString(); 
		website = in.readString();
		
		id = in.readInt();
		approval = in.readInt();
		disapproval = in.readInt();
		distance = in.readInt();
		
		latitude = in.readDouble();
		longitude = in.readDouble();
		rank = in.readFloat();
		
		Log.i("DEV", "here good");
		in.readTypedList(themes, Theme.CREATOR);
		Log.i("DEV", "here better");
		
		in.readTypedList(images, BarImage.CREATOR);
		in.readTypedList(drinks_list, Drinkbar.CREATOR);
	}
	
	
	
	public int getId() {
		return id;
	}
	public String getAddress() {
		return address;
	}
	public int getApproval() {
		return approval;
	}
	public int getDisapproval() {
		return disapproval;
	}
	public String getDescription() {
		return description;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String getMail() {
		return mail;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getResource_uri() {
		return resource_uri;
	}
	public String getSlug() {
		return slug;
	}
	public String getWebsite() {
		return website;
	}
	public float getRank() {
		return rank;
	}
	public int getDistance() {
		return distance;
	}
	
	public List<Theme> getThemes() {
    	return themes;
	}
	
	public List<BarImage> getImages() {
    	return images;
	}
	
	public ArrayList<String> getImagesUrls() {
		Iterator<BarImage> it = images.iterator();
		ArrayList<String> imagesUrls = new ArrayList<String>();
		while(it.hasNext()){
			imagesUrls.add(it.next().getImageUrl());
		}
    	return imagesUrls;
	}
	
	public String getThemesAsAString() {
		Iterator<Theme> it_themes = themes.iterator();
        StringBuilder sb = new StringBuilder();
		while (it_themes.hasNext()) {
			sb.append(it_themes.next().getName());
			if(it_themes.hasNext())
				sb.append(" / ");
		}
		return sb.toString();
	}
	
	public List<Drinkbar> getDrinks() {
    	return drinks_list;
	}
	
	
	public void addDrink(Drinkbar drinkbar){
		drinks_list.add(drinkbar);
	}
	
	@Override
	public String toString() {
	   return "Bar [name=" + name + ", address=" + address +"]";
	}
	


	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(address); 
		parcel.writeString(description); 
		parcel.writeString(mail); 
		parcel.writeString(name); 
		parcel.writeString(phone); 
		parcel.writeString(resource_uri); 
		parcel.writeString(slug); 
		parcel.writeString(website);
		
		parcel.writeInt(id);
		parcel.writeInt(approval);
		parcel.writeInt(disapproval);
		parcel.writeInt(distance);
		
		parcel.writeDouble(latitude);
		parcel.writeDouble(longitude);
		parcel.writeFloat(rank);
		
		parcel.writeTypedList(themes);
		parcel.writeTypedList(images);
		parcel.writeTypedList(drinks_list);
	}
	
	public static final Parcelable.Creator<Bar> CREATOR = new Creator<Bar>() { 
		public Bar createFromParcel(Parcel source) { 
			return new Bar(source);
		}

		@Override
		public Bar[] newArray(int size) {
			return new Bar[size];
		}
		
	};
		

	
	
}
