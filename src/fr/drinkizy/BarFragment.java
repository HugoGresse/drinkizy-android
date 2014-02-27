package fr.drinkizy;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import fr.drinkizy.objects.Bar;
import fr.drinkizy.pageradapter.BarTabsPagerAdapter;
import fr.drinkizy.rest.DrinkizyRestClient;

public class BarFragment extends Fragment implements ActionBar.TabListener  {
	
	private ActionBar actionBar;
	// Tab titles
    private int[] tabs = { R.string.infos_bar, R.string.menu_bar, R.string.avis_bar, R.string.plan_bar };
	private ViewPager viewPager;
	private BarTabsPagerAdapter mAdapter;
	protected Bar bar;
	protected String barUri;
    
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single, container, false);
	    viewPager = (ViewPager) rootView.findViewById(R.id.pager);
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		actionBar = getActivity().getActionBar();
		
        mAdapter = new BarTabsPagerAdapter(getActivity().getFragmentManager());
        
        Bundle bundle = this.getArguments();
        barUri = bundle.getString("res_uri", "");
        loadBar();
	}
	
	public void onDestroyView(){
		super.onDestroyView();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
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
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * Init ViewPager when BarDatas is loaded
	 */
	private void initViewPager(){
        viewPager.setAdapter(mAdapter);
        
	    // Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    
	    // Adding Tabs
        for (int tab_name_id : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name_id)
                    .setTabListener(this));
        }
	    
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the corresponding tab.
                    	actionBar.setSelectedNavigationItem(position);
                    }
                });
        
        
	}
	
    public void loadBar(){
    	
    	RequestParams params = new RequestParams();
    	params.put("format", "json");
    	    	
    	DrinkizyRestClient.get(barUri, params, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Gson gson = new Gson();
		    	bar = gson.fromJson(response, Bar.class);
				
		    	((MainActivity) getActivity()).setCurrentBar(bar);
		    	
				initViewPager();
		        
		    }
		});
    	
        //new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}")

    }
    
    public Bar getBar(){
    	if(bar != null){
    		return bar;
    	} else return new Bar();
    }
}
