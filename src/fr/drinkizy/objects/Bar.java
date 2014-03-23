package fr.drinkizy.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bar {
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
	
	private int distance;
	
	private List<Theme> themes;
	private List<BarImage> images;
	private transient List<Drinkbar> drinks_list = new ArrayList<Drinkbar>();
	
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

	
	
}
