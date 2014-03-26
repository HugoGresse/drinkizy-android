package fr.drinkizy;

import java.util.ArrayList;
import java.util.Set;

import org.apache.http.Header;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
	private ActionBar actionBar;
	
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
		
		// Change color of status bar and enable Translucent on 4.4
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
	    tintManager.setStatusBarTintEnabled(true);
	    tintManager.setTintColor(Color.parseColor(getResources().getString(R.color.orange_drinkizy)));
	    
	    // Override default triansition between this activity and the others
		overridePendingTransition(R.anim.slide_in_translate, R.anim.slide_out_translate);
		
		actionBar = getActionBar();
		
		// Eneble home button and change ActionBar Title
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.search_result);
	    
		
	    searchResult = (ListView)findViewById(R.id.search_result);
	    progressBar = (ProgressBar)findViewById(R.id.progessBarSearchResult);
	    
	    // Get receiving intent
	    Intent intent = getIntent();
	    if(intent.hasExtra(MainActivity.SEARCH_QUERY)){
	    	mSearchQuery = intent.getStringExtra(MainActivity.SEARCH_QUERY);
	    }
	    if(intent.hasExtra(MainActivity.DISTANCE_QUERY)){
	    	mDistanceQuery = intent.getIntExtra(MainActivity.DISTANCE_QUERY, 200000);
	    	actionBar.setSubtitle("A proximité");
	    }
	    if(intent.hasExtra(MainActivity.THEME_QUERY)){
	    	mThemeQuery = intent.getStringExtra(MainActivity.THEME_QUERY);
	    }
	    
	    if (savedInstanceState == null)
	    	loadDrinkizyResults();
	    	
		
	    //Listener on List item to load BarActivity
		searchResult.setOnItemClickListener(new OnItemClickListener(){
			
		    @Override 
		    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3){ 
		    	
		        Intent intentBarSingle = new Intent(SearchResultActivity.this, BarActivity.class);
		        intentBarSingle.putExtra("fr.drinkizy.bar", mBarsItems.get(position));
		        //intentBarSingle.setExtrasClassLoader(getClass().getClassLoader());
		        startActivity(intentBarSingle);
		    }
		});
		
		
		
	}
		
	@Override
	public void onPause(){
		super.onPause();
	    overridePendingTransition(R.anim.slide_translate_from_left, R.anim.slide_to_right_translate);
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	}
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("mbars_items", mBarsItems);
        super.onSaveInstanceState(outState);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	@SuppressWarnings("unchecked")
    	ArrayList<Bar> serializable =  (ArrayList<Bar>) savedInstanceState.getSerializable("mbars_items");
    	mBarsItems = serializable;
    	setBarListAdapter();
    	setProgressBar(View.GONE);
        super.onRestoreInstanceState(savedInstanceState);
    }
    
	/**
	 * Load data from API 
	 */
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
    		params.put("themes__slug", mThemeQuery);
    		loadDrinkizyBars(params, url);

	    	actionBar.setSubtitle(mThemeQuery);
    	}else{
	    	if(!mSearchQuery.isEmpty()){
	    		params.put("q", mSearchQuery);
	    		url = "/api/v1/drinkbar/search/";
	    		loadDrinkizyDrinkbars(params, url);
		    	actionBar.setSubtitle(mSearchQuery);
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
    
    /**
     * Load bar data depending the research request
     * Used for empty search, proximity search and theme search
     * @param paramsBars
     * @param urlBars
     */
    public void loadDrinkizyBars(RequestParams paramsBars, String urlBars){
    	
    	DrinkizyRestClient.get(urlBars, paramsBars, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Gson gson = new Gson();
		    	BarsObject barsObject = gson.fromJson(response, BarsObject.class);
		    	mBarsItems = (ArrayList<Bar>) barsObject.getObjects();
	    	    
		    	setBarListAdapter();
		    }
		    
		    @Override
		    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error){
		    	asyncFailed(statusCode, headers, responseBody, error);
		    }
		   
		});

    }
    
    /**
     * Load drink Barand parse Object
     * used for searching bar 
     * @param paramsDrinkbars
     * @param urlDrinkbars
     */
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
		    	if(mDistanceQuery != 0){
		    		paramsBar.put("distance", mDistanceQuery);
		    		paramsBar.put("lat", mLastKnownLocation.getLatitude());
		    		paramsBar.put("long", mLastKnownLocation.getLongitude());
		    	}
		    	
		    	for(final String barUri : mBarsUriOfDrinkbars){
		    		DrinkizyRestClient.get(barUri, paramsBar, new AsyncHttpResponseHandler() {
		    		    		
					    @Override
					    public void onSuccess(String response) { 
					    	barsCount--;
					    	Gson gson = new Gson();
					    	Bar bar = gson.fromJson(response, Bar.class);
					    	
					    	mBarsItems.add(bar);

					    	if(barsCount == 0)
					    		setBarListAdapter();
	    				}
					    
					    @Override
					    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error){
					    	asyncFailed(statusCode, headers, responseBody, error);
					    }
					    
	    			});
		    	}
		    	
		    }
		});
    }
    
    /**
     * Toggle visibility of loader and visibility of list
     * @param visibility
     */
	public void setProgressBar(int visibility){
		progressBar.setVisibility(visibility);
		if(visibility == View.GONE)
			searchResult.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Set adapter for the result list
	 */
	private void setBarListAdapter(){
    	setProgressBar(View.GONE);
		searchResult.setAdapter(new BarListAdapter(SearchResultActivity.this, mBarsItems));
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		
	}

	@Override
	public void onDisconnected() {
		
	}
	
	public void asyncFailed(int statusCode, Header[] headers, byte[] responseBody, Throwable error){
		new AlertDialog.Builder(this)
		    .setTitle("La recherche a échoué")
		    .setMessage(R.string.load_messagefailed)
		    .setPositiveButton(R.string.load_retry, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	loadDrinkizyResults();
		        }
		     })
		     .setNegativeButton(R.string.load_abort, new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int which) { 
		        	 onBackPressed();
		         }
		      })
		    .setIcon(R.drawable.drinkizy_logoapp_dark)
		    .show();
	}
}