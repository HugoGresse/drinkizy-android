package fr.drinkizy;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class SearchResultActivity extends Activity {
	
	private ListView searchResult;
	
	private ArrayList<Bar> mBarsItems;
	private ArrayList<Drinkbar> mDrinkbars;
	
	private Set<String> mBarsUriOfDrinkbars;
	private Multimap<String, Drinkbar> drinksForBars;
	
	private int barsCount;
	
	private String mSearchQuery = "";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search_result);
		overridePendingTransition(R.anim.slide_in_translate, R.anim.slide_out_translate);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.search_result);
	    
	    searchResult = (ListView)findViewById(R.id.search_result);
	    
	    Intent intent = getIntent();
	    if(intent.hasExtra(MainActivity.SEARCH_QUERY)){
	    	mSearchQuery = intent.getStringExtra(MainActivity.SEARCH_QUERY);
	    }
	    
	    loadDrinkizyResults();
		
		searchResult.setOnItemClickListener(new OnItemClickListener(){
			
		    @Override 
		    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3){ 
		    	
		        Intent intentBarSingle = new Intent(SearchResultActivity.this, BarActivity.class);
		        intentBarSingle.putExtra("bar_uri", mBarsItems.get(position).getResource_uri());
		        startActivity(intentBarSingle);
		        //		        getActivity().overridePendingTransition(R.anim.hold, R.anim.hold);
		    }
		});
		
	}
	
	@Override
	public void finish() {
	    super.finish();
	    overridePendingTransition(R.anim.slide_translate_from_left, R.anim.slide_to_right_translate);
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
	    	    
		    	searchResult.setAdapter(new BarListAdapter(SearchResultActivity.this, mBarsItems));
	
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
					    		searchResult.setAdapter(new BarListAdapter(SearchResultActivity.this, mBarsItems));
	    				}
	    			});
		    	}
		    }
		});
    }

    
    
}
