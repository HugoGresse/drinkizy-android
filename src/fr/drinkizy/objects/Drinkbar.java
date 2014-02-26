package fr.drinkizy.objects;


public class Drinkbar {

	private int approval;
	private int disapproval;

	private String drink;
	private String bar;
	
	private double price;
	private double price_happy_hour;
	
	private String resource_uri;
	
	private transient Drink mDrink;
	private transient Bar mBar;
	
	
	public int getApproval() {
		return approval;
	}
	public int getDisapproval() {
		return disapproval;
	}
	public String getDrinkUri() {
		return drink;
	}
	public String getBarUri() {
		return bar;
	}
	public double getPrice() {
		return price;
	}
	public double getPriceHappyHour() {
		return price_happy_hour;
	}
	public String getResource_uri() {
		return resource_uri;
	}
	
	
	public Drink getDrink() {
		return mDrink;
	}
	public Bar getBar() {
		return mBar;
	}
	
	@Override
	public String toString() {
	   return "Drinkbar [drink=" + drink + ", bar=" + bar +"]";
	}

	
	
}
