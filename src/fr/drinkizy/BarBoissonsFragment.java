package fr.drinkizy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.listdrink.adapter.DrinkListAdapter;
import fr.drinkizy.listdrink.adapter.SectionListAdapter;
import fr.drinkizy.objects.Drinkbar;

public class BarBoissonsFragment extends Fragment {
	
	private ArrayList<Drinkbar> mDrinkbarItems;
	
	private ListView drinksList;
	private ProgressBar progressBar;
	
	public BarBoissonsFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single_boissons, container, false);	
	    
	    drinksList = (ListView)rootView.findViewById(R.id.drinks_list);
	    progressBar = (ProgressBar)rootView.findViewById(R.id.progessBarBoisson);
	    
	    // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
	    //drinksList.setClipToPadding(false);
 		// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
 		//setInsets(this.getActivity(), drinksList);
	    return rootView;
	}
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		// This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
		drinksList.setClipToPadding(false);
 		// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
 		setInsets(this.getActivity(), drinksList);
 		
 		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mDataReceiver, new IntentFilter("BarDataLoaded"));
 		
 		if(mDrinkbarItems != null)
 			displayDrink();
	}
	
	private BroadcastReceiver mDataReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Get extra data included in the Intent
			int id = intent.getIntExtra("id", 0);
			if(id != 1) return;
			
			mDrinkbarItems = ((BarActivity) getActivity()).getDrinkBar();
			displayDrink();
		}
	};

		
	@Override
	public void onDestroy() {
		// Unregister since the activity is about to be closed.
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mDataReceiver);
		super.onDestroy();
	}
	
	
	private void displayDrink(){
		
		setProgressBar(View.GONE);
		
		//drinksList.setAdapter();
        
        // create our list and custom adapter  
		SectionListAdapter adapter = new SectionListAdapter(getActivity()); 
		
		Multimap<String, Drinkbar> categoryDrinkMap = ArrayListMultimap.create();
	
		for (Drinkbar drinkbar : mDrinkbarItems) {
			categoryDrinkMap.put(drinkbar.getDrink().getSubcategory().getCategory().getName(), drinkbar);
		}
		
		for (String cat : categoryDrinkMap.keySet()) {
			ArrayList<Drinkbar> drinkItems = new ArrayList<Drinkbar>(categoryDrinkMap.get(cat));
			adapter.addSection(cat, new DrinkListAdapter(getActivity(), drinkItems));  
		}
		
		drinksList.setAdapter(adapter);  
	}
	
	
	private static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0,  config.getPixelInsetTop(true) + config.getNavigationBarHeight(), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	

    /**
     * Toggle visibility of loader and visibility of list
     * @param visibility
     */
	private void setProgressBar(int visibility){
		progressBar.setVisibility(visibility);
		if(visibility == View.GONE)
			drinksList.setVisibility(View.VISIBLE);
	}
	


}
