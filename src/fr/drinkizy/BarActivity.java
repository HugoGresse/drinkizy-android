package fr.drinkizy;

import java.util.ArrayList;

import org.apache.http.Header;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.objects.Bar;
import fr.drinkizy.objects.Comment;
import fr.drinkizy.objects.CommentsObject;
import fr.drinkizy.objects.Drinkbar;
import fr.drinkizy.objects.DrinkbarsObject;
import fr.drinkizy.pageradapter.BarTabsPagerAdapter;
import fr.drinkizy.rest.DrinkizyRestClient;

public class BarActivity extends Activity implements ActionBar.TabListener  {
	
	private ActionBar actionBar;
	// Tab titles
    private int[] tabs = { R.string.infos_bar, R.string.menu_bar, R.string.avis_bar, R.string.plan_bar };
	private ViewPager viewPager;
	private BarTabsPagerAdapter mAdapter;
	
	protected Bar bar;
	private ArrayList<Drinkbar> mDrinkbarItems;
	private ArrayList<Comment> mCommentItems;
	protected String barUri;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bar_single);
		
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
	    tintManager.setStatusBarTintEnabled(true);
	    tintManager.setTintColor(Color.parseColor(getResources().getString(R.color.orange_drinkizy)));
	    
		overridePendingTransition(R.anim.slide_in_translate, R.anim.slide_out_translate);
		
	    viewPager = (ViewPager) findViewById(R.id.pager);
	    
	    actionBar = getActionBar();
        mAdapter = new BarTabsPagerAdapter(getFragmentManager());
        
        actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
	    // Adding Tabs
        for (int tab_name_id : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name_id)
                    .setTabListener(this));
        }
        
        
        Bundle extras = getIntent().getExtras();;
        extras.setClassLoader(Bar.class.getClassLoader());
        bar =  extras.getParcelable("fr.drinkizy.bar");
        

    	actionBar.setTitle(bar.getName());
    	actionBar.setSubtitle(bar.getAddress()); 
    	
		initViewPager();
        
		//load Boisson and Avis
		loadDrinksOfBar();
		loadCommentsOfBar();
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    overridePendingTransition(R.anim.slide_translate_from_left, R.anim.slide_to_right_translate);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    // Respond to the action bar's Up/Home button
		    case android.R.id.home:
		        NavUtils.navigateUpFromSameTask(this);
		        overridePendingTransition(R.anim.slide_to_right_translate, R.anim.slide_translate_from_left);
		        return true;
		    case R.id.action_search:
		    	Intent intent = new Intent(BarActivity.this, MainActivity.class);
		    	intent.putExtra("from", "bar");
		    	NavUtils.navigateUpTo(this, intent);
		    	overridePendingTransition(R.anim.slide_to_right_translate, R.anim.slide_translate_from_left);
	            return true;

	    }
	    overridePendingTransition(R.anim.slide_translate_from_left, R.anim.slide_to_right_translate);
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		
	}
	
	
	/**
	 * Init ViewPager when BarDatas is loaded
	 */
	private void initViewPager(){
		
        viewPager.setAdapter(mAdapter);
        
        viewPager.setOnPageChangeListener(
	        new ViewPager.SimpleOnPageChangeListener() {
	            @Override
	            public void onPageSelected(int position) {
	                // When swiping between pages, select the corresponding tab.
	            	actionBar.setSelectedNavigationItem(position);
	            }
	        });
        
        
	}
	
    public Bar getBar(){
    	if(bar != null){
    		return bar;
    	} else return new Bar();
    }
    
    public ArrayList<Drinkbar> getDrinkBar(){
    	if(mDrinkbarItems != null){
    		return mDrinkbarItems;
    	} else return null;
    }
    
    public ArrayList<Comment> getAvis(){
    	if(mCommentItems != null){
    		return mCommentItems;
    	} else return null;
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
		    	
		    	sendMessageToFragment(1);
		    }
		    @Override
		    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error){
		    	asyncFailed(1);
		    }
		});

    }
	
	public void loadCommentsOfBar(){
    	RequestParams params = new RequestParams();
    	params.put("format", "json");
    	params.put("object_pk", String.valueOf(bar.getId()));
    	
    	DrinkizyRestClient.get("/api/v1/comment/", params, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.d("BarAvisFragment", response);
		    	Gson gson = new Gson();
		    	CommentsObject commentsObject = gson.fromJson(response, CommentsObject.class);
		    	mCommentItems = (ArrayList<Comment>) commentsObject.getObjects();
		    	
		    	sendMessageToFragment(2);
		    	
		    }
		    
		    @Override
		    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error){
		    	asyncFailed(2);
		    }
		});

    }
	
	public  void sendMessageToFragment(int id){
		Intent intent = new Intent("BarDataLoaded");
		// You can also include some extra data.
		intent.putExtra("id", id);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
	
	public void asyncFailed(int id){
		final int id2 = id;
		new AlertDialog.Builder(this)
		    .setTitle("La recherche a échoué")
		    .setMessage(R.string.load_messagefailed)
		    .setPositiveButton(R.string.load_retry, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	if(id2==1){
		        		loadDrinksOfBar();
		        	} else if(id2 == 2){
		        		loadCommentsOfBar();
		        	} else {
		        		onBackPressed();
		        	}
		        		
		        	
		        }
		     })
		     .setNegativeButton(R.string.load_abort, new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int which) { 
		        	 //onBackPressed();
		         }
		      })
		    .setIcon(R.drawable.drinkizy_logoapp_dark)
		    .show();
	}
    
}
