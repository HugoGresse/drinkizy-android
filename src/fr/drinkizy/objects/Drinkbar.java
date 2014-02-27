package fr.drinkizy.objects;


public class Drinkbar {

	private int approval;
	private int disapproval;

	private Drink drink;
	private String bar;
	
	private double price;
	private double price_happy_hour;
	
	private String resource_uri;
	
	private transient Bar mBar;
	
	
	public int getApproval() {
		return approval;
	}
	public int getDisapproval() {
		return disapproval;
	}
	public Drink getDrink() {
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
	

	public Bar getBar() {
		return mBar;
	}
	
	@Override
	public String toString() {
	   return "Drinkbar [drink=" + drink + ", bar=" + bar +"]";
	}

	
	
}
