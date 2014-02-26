package fr.drinkizy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import fr.drinkizy.listbar.adapter.BarListAdapter;
import fr.drinkizy.objects.Bar;
import fr.drinkizy.objects.BarsObject;
import fr.drinkizy.objects.Theme;
import fr.drinkizy.objects.ThemesObject;
import fr.drinkizy.rest.DrinkizyRestClient;

public class SearchResultFragment extends Fragment {
	
	private ListView searchResult;
	
	private ArrayList<Bar> mBarsItems;
	private ArrayList<Theme> mThemesItems;
	
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
		
		
		getDrinkizyBars();
		
		searchResult.setOnItemClickListener(new OnItemClickListener(){
			
		    @Override 
		    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3){ 
		    	BarFragment myBarFrag = new BarFragment();
		    	Bundle bundle = new Bundle();
		    	bundle.putString("res_uri", mBarItems.get(position).getResource_uri());
		    	myBarFrag.setArguments(bundle);
		    	changeFragment(myBarFrag, 1, R.string.search_result);
		    }
		});
	}
	
    public void getDrinkizyBars(){

    	RequestParams paramsBars = new RequestParams();
    	paramsBars.put("format", "json");
    	String urlBars = "/api/v1/bar/";
    	if(mSearchQuery != ""){
    		paramsBars.put("q", mSearchQuery);
    		urlBars = "/api/v1/bar/search";
    	}
    	
    	DrinkizyRestClient.get(urlBars, paramsBars, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	
		    	Gson gson = new Gson();
		    	BarsObject barsObject = gson.fromJson(response, BarsObject.class);
		    	mBarsItems = (ArrayList<Bar>) barsObject.getObjects();
	    	    
		    	
		    	RequestParams paramsThemes = new RequestParams();
		    	paramsThemes.put("format", "json");
	    		DrinkizyRestClient.get("/api/v1/theme/", paramsThemes, new AsyncHttpResponseHandler() {
	    		    		
				    @Override
				    public void onSuccess(String response) {  	
				    	Gson gson = new Gson();
				    	ThemesObject themesObject = gson.fromJson(response, ThemesObject.class);
				    	mThemesItems = (ArrayList<Theme>) themesObject.getObjects();
				    	
				    	setBarsAdapter();
    				}
    			});
	
		    }
		});

    }

	public void setBarsAdapter(){
	
		for(Bar bar : mBarsItems){
			for(Theme theme : mThemesItems){
				for(String theme_uri : bar.getThemesResUris()){
					if(theme_uri.equals(theme.getResource_uri())){
						bar.addTheme(theme);
					}
				}
			}
		}
		
		searchResult.setAdapter(new BarListAdapter(getActivity(), mBarsItems));
	}
    
	private void changeFragment(Fragment frag, int position, int actionBarTitle){
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
		ft.replace(R.id.drawer_content, frag, Integer.toString(position));
	    ft.commit();
	    
	    getActivity().getActionBar().setTitle(actionBarTitle);
	}
}
