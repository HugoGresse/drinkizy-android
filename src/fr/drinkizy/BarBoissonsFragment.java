package fr.drinkizy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.listdrink.adapter.DrinkListAdapter;
import fr.drinkizy.listdrink.adapter.SectionListAdapter;
import fr.drinkizy.objects.Bar;
import fr.drinkizy.objects.Drinkbar;
import fr.drinkizy.objects.DrinkbarsObject;
import fr.drinkizy.rest.DrinkizyRestClient;

public class BarBoissonsFragment extends Fragment {
	
	private Bar bar;
	private ArrayList<Drinkbar> mDrinkbarItems;
	
	private ListView drinksList;
	
	public BarBoissonsFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single_boissons, container, false);	
	    
	    drinksList = (ListView)rootView.findViewById(R.id.drinks_list);
	    
	    // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
	    //drinksList.setClipToPadding(false);
 		// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
 		//setInsets(this.getActivity(), drinksList);
	 	
	    return rootView;
	}
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		bar =  ((BarActivity) getActivity()).getBar();
		
		loadDrinksOfBar();
		
	}
	
	
	public void loadDrinksOfBar(){
    	
    	RequestParams params = new RequestParams();
    	params.put("format", "json");
    	params.put("bar__slug", bar.getSlug());
    	
    	DrinkizyRestClient.get("/api/v1/drinkbar/", params, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Gson gson = new Gson();
		    	DrinkbarsObject drinkbarsObject = gson.fromJson(response, DrinkbarsObject.class);
		    	mDrinkbarItems = (ArrayList<Drinkbar>) drinkbarsObject.getObjects();
		    	
		    	displayDrink();
		    }
		});

    }
	
	public void displayDrink(){
		
//		drinksList.setAdapter();
        
        // create our list and custom adapter  
		SectionListAdapter adapter = new SectionListAdapter(getActivity()); 
		
		Multimap<String, Drinkbar> categoryDrinkMap = ArrayListMultimap.create();
	
		for (Drinkbar drinkbar : mDrinkbarItems) {
			categoryDrinkMap.put(drinkbar.getDrink().getSubcategory().getCategory().getName(), drinkbar);
		}
		
		for (String cat : categoryDrinkMap.keySet()) {
			ArrayList<Drinkbar> drinkItems = new ArrayList<Drinkbar>(categoryDrinkMap.get(cat));
			adapter.addSection(cat, new DrinkListAdapter(getActivity(), drinkItems));  
		}
		
		drinksList.setAdapter(adapter);  
	}
	
	
	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0,  config.getPixelInsetTop(true) + config.getNavigationBarHeight(), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	
	
	
	

}
