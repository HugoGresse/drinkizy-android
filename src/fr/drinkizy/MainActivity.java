package fr.drinkizy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.navdrawer.adapter.NavDrawerItem;
import fr.drinkizy.navdrawer.adapter.NavigationDrawerAdapter;
import fr.drinkizy.objects.Bar;
import fr.drinkizy.objects.BarsObject;


public class MainActivity extends Activity {
	
	public static final String SEARCH_QUERY = "search_query";
	public static final String DISTANCE_QUERY = "distance_query";
	public static final String THEME_QUERY = "theme_query";
	public static boolean drinkLogo = true;
	
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
		
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
	    tintManager.setStatusBarTintEnabled(true);
//	    tintManager.setNavigationBarTintEnabled(false);
	    tintManager.setTintColor(Color.parseColor(getResources().getString(R.color.orange_drinkizy)));
	    
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
        
        mDrawerList.setClipToPadding(false);
        setInsets(this, mDrawerList);
        
        mTitle = mDrawerTitle = getTitle();
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
        
        if (savedInstanceState == null && drinkLogo) {
        	selectItem(1); // c'est crade, on changera quand on aura la FAQ
        	Handler handler = new Handler(); 
        	handler.postDelayed(new Runnable() { 
                public void run() { 
                	selectItem(0);
                	drinkLogo = false;
                } 
           }, 600); 
            
        }else if(!drinkLogo){
        	selectItem(0);
        }
       
	};
	
	@Override
	public void finish() {
	    super.finish();
	    overridePendingTransition(R.anim.slide_translate_from_left, R.anim.slide_to_right_translate);
	}
	

	@Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();
		
		if (fm.getBackStackEntryCount() > 2) {
			
            // only show dialog while there's back stack entry
            fm.popBackStack();
            setTitle(mTitle);
            
        } else {
            // or just go back to main activity
        	// super.onBackPressed();
            finish();
        }
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
			    changeFragment(new SearchFragment(), position, R.anim.fade_in, R.anim.fade_out);
				break;
			case 1:
				 // Insert the fragment by replacing any existing fragment
			    // Create new fragment from our own Fragment class
			    changeFragment(new HomeFragment(), position, R.anim.fade_in, R.anim.fade_out);
				break;
			case 2:
				 // Insert the fragment by replacing any existing fragment
			    // Create new fragment from our own Fragment class
			    changeFragment(new AboutFragment(), position, R.anim.fade_in, R.anim.fade_out);
				break;
				
			default:
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

	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0, config.getPixelInsetTop(true) , config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
    
    
}


