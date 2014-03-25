package fr.drinkizy;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.objects.Bar;
import fr.drinkizy.pageradapter.BarTabsPagerAdapter;

public class BarActivity extends Activity implements ActionBar.TabListener  {
	
	private ActionBar actionBar;
	// Tab titles
    private int[] tabs = { R.string.infos_bar, R.string.menu_bar, R.string.avis_bar, R.string.plan_bar };
	private ViewPager viewPager;
	private BarTabsPagerAdapter mAdapter;
	protected Bar bar;
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
    
}
