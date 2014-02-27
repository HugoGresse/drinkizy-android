package fr.drinkizy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single_boissons, container, false);	
	    
	    
	    drinksList = (ListView)rootView.findViewById(R.id.drinks_list);
	    
	    return rootView;
	}
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		bar = ((MainActivity) getActivity()).getCurrentBar();
		
		loadDrinksOfBar();
		
	}
	
	
	public void loadDrinksOfBar(){
    	
    	RequestParams params = new RequestParams();
    	params.put("format", "json");
    	params.put("bar__slug", bar.getSlug());
    	
    	DrinkizyRestClient.get("/api/v1/drinkbar/", params, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.d("LIST_BOISSONS", "loadDrinksOfBar onSuccess");
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
		
		Map<String, ArrayList<Drinkbar>> categoryDrinkMap = new HashMap<String, ArrayList<Drinkbar>>();
		
		for (Drinkbar drinkbar : mDrinkbarItems) {
			String cat = drinkbar.getDrink().getSubcategory().getCategory().getName();
			
			if(!categoryDrinkMap.containsKey(cat) ){
				ArrayList<Drinkbar> drinkBarList = new ArrayList<Drinkbar>();
				drinkBarList.add(drinkbar);
				categoryDrinkMap.put(cat, drinkBarList);
			} else {
				ArrayList<Drinkbar> drinkBarList = categoryDrinkMap.get(cat);
				drinkBarList.add(drinkbar);
				categoryDrinkMap.put(cat, drinkBarList);
			}
			
		}
		
		for (Map.Entry<String, ArrayList<Drinkbar>> entry : categoryDrinkMap.entrySet()) {
			String cat = entry.getKey();
			ArrayList<Drinkbar> values = entry.getValue();
			adapter.addSection(cat, new DrinkListAdapter(getActivity(), values));  
		}
		
		Log.i("DEV", categoryDrinkMap.toString());
		
		drinksList.setAdapter(adapter);  
	}
	
	
	
	
	
	
	

}
