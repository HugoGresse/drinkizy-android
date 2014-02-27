package fr.drinkizy;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import fr.drinkizy.listdrink.adapter.DrinkListAdapter;
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
	    Log.d("LIST_BOISSONS", "onCreateView");
	    drinksList = (ListView)rootView.findViewById(R.id.drinks_list);
	    
	    return rootView;
	}
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Log.d("LIST_BOISSONS", "onActivityCreated");
		bar = ((MainActivity) getActivity()).getCurrentBar();
		
		loadDrinksOfBar();
		
	}
	
	
	public void loadDrinksOfBar(){
    	Log.d("LIST_BOISSONS", "entered method loadDrinksOfBar");
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
		    	
		    	drinksList.setAdapter(new DrinkListAdapter(getActivity(), mDrinkbarItems));
		        
		    }
		});

    }

}
