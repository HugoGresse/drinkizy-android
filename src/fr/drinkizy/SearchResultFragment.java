package fr.drinkizy;

import java.util.ArrayList;
import java.util.Set;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import fr.drinkizy.listbar.adapter.BarListAdapter;
import fr.drinkizy.objects.Bar;
import fr.drinkizy.objects.BarsObject;
import fr.drinkizy.objects.Drinkbar;
import fr.drinkizy.objects.DrinkbarsObject;
import fr.drinkizy.rest.DrinkizyRestClient;

public class SearchResultFragment extends Fragment {
	
	private ListView searchResult;
	
	private ArrayList<Bar> mBarsItems;
	private ArrayList<Drinkbar> mDrinkbars;
	
	private Set<String> mBarsUriOfDrinkbars;
	private Multimap<String, Drinkbar> drinksForBars;
	
	private int barsCount;
	
	private String mSearchQuery = "";
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.search_result, container, false);
	    
	    searchResult = (ListView)rootView.findViewById(R.id.search_result);
	    
	    Bundle bundle = this.getArguments();
	    if(bundle.containsKey(MainActivity.SEARCH_QUERY)){
	    	mSearchQuery = bundle.getString(MainActivity.SEARCH_QUERY);
	    }
	    
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		
		loadDrinkizyResults();
		
		searchResult.setOnItemClickListener(new OnItemClickListener(){
			
		    @Override 
		    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3){ 

		        Intent intentBarSingle = new Intent(getActivity(), BarActivity.class);
		        intentBarSingle.putExtra("bar_uri", mBarsItems.get(position).getResource_uri());
		        startActivity(intentBarSingle);
		        getActivity().overridePendingTransition(R.anim.hold, R.anim.hold);
		    }
		});
	}
	
    public void loadDrinkizyResults(){
    	
    	RequestParams params = new RequestParams();
    	params.put("format", "json");
    	String url = "/api/v1/bar/";
    	
    	if(!mSearchQuery.isEmpty()){
    		params.put("q", mSearchQuery);
    		url = "/api/v1/drinkbar/search/";
    		loadDrinkizyDrinkbars(params, url);
    	}else{
    		loadDrinkizyBars(params, url);

    	}
    	
    }
    
    
    public void loadDrinkizyBars(RequestParams paramsBars, String urlBars){
    	
    	DrinkizyRestClient.get(urlBars, paramsBars, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	
		    	Gson gson = new Gson();
		    	BarsObject barsObject = gson.fromJson(response, BarsObject.class);
		    	mBarsItems = (ArrayList<Bar>) barsObject.getObjects();
	    	    
		    	searchResult.setAdapter(new BarListAdapter(getActivity(), mBarsItems));
	
		    }
		});

    }
    
    
    public void loadDrinkizyDrinkbars(RequestParams paramsDrinkbars, String urlDrinkbars){
    	
    	DrinkizyRestClient.get(urlDrinkbars, paramsDrinkbars, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {

		    	Gson gson = new Gson();
		    	DrinkbarsObject drinkbarsObject = gson.fromJson(response, DrinkbarsObject.class);
		    	mDrinkbars = (ArrayList<Drinkbar>) drinkbarsObject.getObjects();
	    	    
		    	mBarsItems = new ArrayList<Bar>();
		    	
		    	// create multimap to store bar_uris as keys and drinkbars associated as values
		    	drinksForBars = ArrayListMultimap.create();
		    	
		    	for(Drinkbar drinkbar : mDrinkbars){
		    		drinksForBars.put(drinkbar.getBarUri(), drinkbar);
		    	}

		    	mBarsUriOfDrinkbars = drinksForBars.keySet();
		    	barsCount = mBarsUriOfDrinkbars.size();

		    	RequestParams paramsBar = new RequestParams();
		    	paramsBar.put("format", "json");
		    	
		    	for(final String barUri : mBarsUriOfDrinkbars){
		    		
		    		DrinkizyRestClient.get(barUri, paramsBar, new AsyncHttpResponseHandler() {
		    		    		
					    @Override
					    public void onSuccess(String response) { 
					    	barsCount--;
					    	
					    	Gson gson = new Gson();
					    	Bar bar = gson.fromJson(response, Bar.class);
					    	
					    	mBarsItems.add(bar);

					    	if(barsCount == 0)
					    		searchResult.setAdapter(new BarListAdapter(getActivity(), mBarsItems));
	    				}
	    			});
		    	}
		    }
		});
    }

    
    
}
