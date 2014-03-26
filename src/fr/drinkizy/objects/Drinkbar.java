package fr.drinkizy.objects;

import android.os.Parcel;
import android.os.Parcelable;


public class Drinkbar implements Parcelable {

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
	
	public static final Parcelable.Creator<Drinkbar> CREATOR = new Creator<Drinkbar>() { 
		public Drinkbar createFromParcel(Parcel source) { 
			Drinkbar mDrinkBar = new Drinkbar(); 
			mDrinkBar.approval = source.readInt(); 
			mDrinkBar.disapproval = source.readInt(); 
			mDrinkBar.drink = source.readParcelable(Drink.class.getClassLoader()); 
			mDrinkBar.bar = source.readString(); 
			mDrinkBar.price = source.readDouble(); 
			mDrinkBar.price_happy_hour = source.readDouble(); 
			mDrinkBar.resource_uri = source.readString(); 
			mDrinkBar.mBar = source.readParcelable(Bar.class.getClassLoader()); 
		    return mDrinkBar; 
		}

		@Override
		public Drinkbar[] newArray(int size) {
			return new Drinkbar[size];
		}
		
	};
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(approval);
		dest.writeInt(disapproval);
		dest.writeParcelable(drink, 0);
		dest.writeString(bar);
		dest.writeDouble(price);
		dest.writeDouble(price_happy_hour);
		dest.writeString(resource_uri);
		dest.writeParcelable(mBar, 0);
	}
	
	
	
	
}
