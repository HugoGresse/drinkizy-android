package fr.drinkizy.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import fr.drinkizy.listbar.adapter.BarListAdapter;
import fr.drinkizy.rest.DrinkizyRestClient;

public class Bar {
	private String address;
	private int approval;
	private int disapproval;
	private String description;
	
	private double latitude;
	private double longitude;
	private String mail;
	
	private String name;
	private int nb_pictures;
	private String phone;
	
	private String resource_uri;
	private String slug;
	
	private List<String> themes;
	private String website;
	
	private transient List<Theme> themes_list = new ArrayList<Theme>();
	private transient List<Drinkbar> drinks_list = new ArrayList<Drinkbar>();
	
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
	public int getNb_pictures() {
		return nb_pictures;
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
	public List<String> getThemesResUris() {
		return themes;
	}
	public String getWebsite() {
		return website;
	}
	
	public void setThemes(List<Theme> themes){
		themes_list = themes;
	}
	
	public List<Theme> getThemes() {
    	return themes_list;
	}
	
	public List<Drinkbar> getDrinks() {
    	return drinks_list;
	}
	
	public void addTheme(Theme theme){
		themes_list.add(theme);
	}
	
	public void addDrink(Drinkbar drinkbar){
		drinks_list.add(drinkbar);
	}
	
	@Override
	public String toString() {
	   return "Bar [name=" + name + ", address=" + address +"]";
	}

	
	
}
