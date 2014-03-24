package fr.drinkizy;

import java.util.ArrayList;
import java.util.Set;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.listbar.adapter.BarListAdapter;
import fr.drinkizy.objects.Bar;
import fr.drinkizy.objects.BarsObject;
import fr.drinkizy.objects.Drinkbar;
import fr.drinkizy.objects.DrinkbarsObject;
import fr.drinkizy.rest.DrinkizyRestClient;

public class SearchResultActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener{
	
	private ListView searchResult;
	private ProgressBar progressBar;
	
	private ArrayList<Bar> mBarsItems;
	private ArrayList<Drinkbar> mDrinkbars;
	
	private Set<String> mBarsUriOfDrinkbars;
	private Multimap<String, Drinkbar> drinksForBars;
	
	private int barsCount;
	
	private String mSearchQuery = "";
	private int mDistanceQuery = 0;
	private String mThemeQuery = "";
	
	// Acquire a reference to the system Location Manager
	LocationManager locationManager;
	Location mLastKnownLocation;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search_result);
		
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
	    tintManager.setStatusBarTintEnabled(true);
	    tintManager.setTintColor(Color.parseColor(getResources().getString(R.color.orange_drinkizy)));
	    
		overridePendingTransition(R.anim.slide_in_translate, R.anim.slide_out_translate);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.search_result);
	    
	    searchResult = (ListView)findViewById(R.id.search_result);
	    progressBar = (ProgressBar)findViewById(R.id.progessBarSearchResult);
	    
	    Intent intent = getIntent();
	    if(intent.hasExtra(MainActivity.SEARCH_QUERY)){
	    	mSearchQuery = intent.getStringExtra(MainActivity.SEARCH_QUERY);
	    }
	    if(intent.hasExtra(MainActivity.DISTANCE_QUERY)){
	    	mDistanceQuery = intent.getIntExtra(MainActivity.DISTANCE_QUERY, 200000);
	    }
	    if(intent.hasExtra(MainActivity.THEME_QUERY)){
	    	mThemeQuery = intent.getStringExtra(MainActivity.THEME_QUERY);
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
	public void onPause(){
		super.onPause();
	    overridePendingTransition(R.anim.slide_translate_from_left, R.anim.slide_to_right_translate);
	}
	
    public void loadDrinkizyResults(){
    	
    	String locationProvider = LocationManager.NETWORK_PROVIDER;
    	// Or, use GPS location data:
    	// String locationProvider = LocationManager.GPS_PROVIDER;
    	if(mDistanceQuery != 0){
	    	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	    	mLastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
    	}
    	
    	RequestParams params = new RequestParams();
    	params.put("format", "json");
    	String url = "/api/v1/bar/";
    	
    	
    	if(!mThemeQuery.isEmpty()){
    		Log.d("SearchResultActivity", "mThemeQuery : "+mThemeQuery);
    		params.put("themes__slug", mThemeQuery);
    		loadDrinkizyBars(params, url);
    	}else{
	    	if(!mSearchQuery.isEmpty()){
	    		params.put("q", mSearchQuery);
	    		url = "/api/v1/drinkbar/search/";
	    		loadDrinkizyDrinkbars(params, url);
	    	}else{
	    		if(mDistanceQuery != 0){
	    			params.put("distance",String.valueOf(mDistanceQuery));
	    			params.put("lat", String.valueOf(mLastKnownLocation.getLatitude()));
	    			params.put("long", String.valueOf(mLastKnownLocation.getLongitude()));
		    	}
	    		
	    		loadDrinkizyBars(params, url);
	
	    	}
    	}
    	
    }
    
    
    public void loadDrinkizyBars(RequestParams paramsBars, String urlBars){
    	
    	DrinkizyRestClient.get(urlBars, paramsBars, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.d("SearchResultActivity", "loadDrinkizyBars : "+response);
		    	Gson gson = new Gson();
		    	BarsObject barsObject = gson.fromJson(response, BarsObject.class);
		    	mBarsItems = (ArrayList<Bar>) barsObject.getObjects();
	    	    
		    	searchResult.setAdapter(new BarListAdapter(SearchResultActivity.this, mBarsItems));
		    	
		    	setProgressBar(View.GONE);
		    }
		});

    }
    
    
    public void loadDrinkizyDrinkbars(RequestParams paramsDrinkbars, String urlDrinkbars){
    	
    	DrinkizyRestClient.get(urlDrinkbars, paramsDrinkbars, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	//Log.d("SearchResultActivity", "loadDrinkizyBars : "+response);
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
		    	
		    	for(String barUri : mBarsUriOfDrinkbars){
		    		Log.d("SearchResultActivity", "barUri : "+barUri);
		    	}

		    	RequestParams paramsBar = new RequestParams();
		    	paramsBar.put("format", "json");
		    	if(mDistanceQuery != 0){
		    		Log.d("LOCATION_OF_USER", "search query and distance query != 0");
		    		paramsBar.put("distance", mDistanceQuery);
		    		paramsBar.put("lat", mLastKnownLocation.getLatitude());
		    		paramsBar.put("long", mLastKnownLocation.getLongitude());
		    	}
		    	
		    	for(final String barUri : mBarsUriOfDrinkbars){
		    		Log.d("SearchResultActivity", "DrinkizyRestClient.get : "+barUri);
		    		DrinkizyRestClient.get(barUri, paramsBar, new AsyncHttpResponseHandler() {
		    		    		
					    @Override
					    public void onSuccess(String response) { 
					    	barsCount--;
					    	Log.d("SearchResultActivity", "loadDrinkizyBars "+barUri+" : "+response);
					    	Gson gson = new Gson();
					    	Bar bar = gson.fromJson(response, Bar.class);
					    	
					    	mBarsItems.add(bar);

					    	if(barsCount == 0)
					    		searchResult.setAdapter(new BarListAdapter(SearchResultActivity.this, mBarsItems));
	    				}
					    
					    @Override
					     public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error){
					    	Log.d("SearchResultActivity", "loadDrinkizyBars fail "+barUri+" : "+statusCode);
					     }
					    
					    
	    			});
		    	}
		    	
		    	setProgressBar(View.GONE);
		    }
		});
    }

	public void setProgressBar(int visibility){
		progressBar.setVisibility(visibility);
		if(visibility == View.GONE)
			searchResult.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}