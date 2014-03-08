package fr.drinkizy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;

import fr.drinkizy.navdrawer.adapter.NavDrawerItem;
import fr.drinkizy.navdrawer.adapter.NavigationDrawerAdapter;
import fr.drinkizy.objects.Bar;
import fr.drinkizy.objects.BarsObject;


public class MainActivity extends Activity {
	
	public static final String SEARCH_QUERY = "search_query";
	
	private ArrayList<NavDrawerItem> mDrawerItems;
	private String[] mNavMenuTitles;
	private TypedArray mNavMenuIcons;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    String jsonResponse;
    
    protected BarsObject mBarsObjects;
    protected Bar currentBar;
    
	protected ImageLoader imageLoader = ImageLoader.getInstance();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		mNavMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        mNavMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        
        mDrawerItems = new ArrayList<NavDrawerItem>();
        for (int i = 0; i < mNavMenuTitles.length; ++i) {
        	mDrawerItems.add(new NavDrawerItem(mNavMenuTitles[i], mNavMenuIcons.getResourceId(i, -1)));
        }
		
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navigation_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new NavigationDrawerAdapter(getApplicationContext(), mDrawerItems));
        
        mNavMenuIcons.recycle();
        
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle( 
        		this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {
        	
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        if (savedInstanceState == null) {
        	selectItem(0);
        	Handler handler = new Handler(); 
        	handler.postDelayed(new Runnable() { 
                public void run() { 
                	selectItem(1);
                } 
           }, 200); 
            
        }
       
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public void finish() {
	    super.finish();
	    overridePendingTransition(R.anim.slide_translate_from_left, R.anim.slide_to_right_translate);
	}
	

	@Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();
		
		Log.i("StackCount :", Integer.toString(fm.getBackStackEntryCount())  );
		
		if (fm.getBackStackEntryCount() > 2) {
			
			Log.i("Back pressed", "popBackStack" );
			
            // only show dialog while there's back stack entry
            fm.popBackStack();
            setTitle(mTitle);
            
        } else {
        	Log.i("Back pressed", "super" );
            // or just go back to main activity
        	// super.onBackPressed();
            finish();
            
        }
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}
	
	/**
	 * Change content fragment
	 * @param frag
	 * @param position
	 */
	private void changeFragment(Fragment frag, int position, int animIn, int animOut){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(animIn, animOut, animIn, animOut);
		ft.replace(R.id.drawer_content, frag, Integer.toString(position));
		ft.addToBackStack(frag.getClass().toString());
	    ft.commit();
	}
	
	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		
		switch (position) {
			case 0:
				 // Insert the fragment by replacing any existing fragment
			    // Create new fragment from our own Fragment class
			    changeFragment(new HomeFragment(), position, R.anim.fade_in, R.anim.fade_out);
				break;
			case 1:
			    changeFragment(new SearchFragment(), position, R.anim.fade_in, R.anim.fade_out);
				break;
			
			default:
				Log.w("Fragment", "No Fragment to change on selectItem");
				break;
		}
	   
	    // Highlight the selected item, update the title, and close the drawer
	    mDrawerList.setItemChecked(position, true);
	    setTitle(mNavMenuTitles[position]);
	    mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getActionBar().setTitle(mTitle);
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

	public Bar getCurrentBar() {
		return currentBar;
	}

	public void setCurrentBar(Bar currentBar) {
		this.currentBar = currentBar;
	}
    
    
}


