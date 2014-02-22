package fr.drinkizy.objects;

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
	
	private String theme;
	private String website;
	
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
	public String getTheme() {
		return theme;
	}
	public String getWebsite() {
		return website;
	}
	
	
	@Override
	public String toString() {
	   return "Bar [name=" + name + ", address=" + address +"]";
	}

	
	
}
