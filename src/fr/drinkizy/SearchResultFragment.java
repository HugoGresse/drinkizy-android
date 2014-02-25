package fr.drinkizy;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import fr.drinkizy.rest.DrinkizyRestClient;

public class SearchResultFragment extends Fragment {
	
	private ListView searchResult;
	private ArrayList<Bar> mBarItems;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.search_result, container, false);
	    
	    searchResult = (ListView)rootView.findViewById(R.id.search_result);
	    
	    
	    return rootView;
	}
	
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		
		getDrinkizyBars();
		
		searchResult.setOnItemClickListener(new OnItemClickListener(){
			
		    @Override 
		    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3){ 
		    	Bundle bundle = new Bundle();
		        // Our object is just an integer :-P
//		        bundle.putInt("BAR", mBarItems.get(position));

		    	changeFragment(new BarFragment(), 1, R.string.search_result);
		    }
		});
	}
	
    public void getDrinkizyBars(){
    	
    	RequestParams params = new RequestParams();
    	params.put("format", "json");
    	
    	DrinkizyRestClient.get("bar/", params, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Gson gson = new Gson();
		    	BarsObject barsObject = gson.fromJson(response, BarsObject.class);
		    	
		    	List<Bar> bars = barsObject.getObjects();
		    	
				mBarItems = new ArrayList<Bar>();
				mBarItems.addAll(bars);
				
				
		        // Set the adapter for the list view
				searchResult.setAdapter(new BarListAdapter(getActivity(), mBarItems));
		        
		    }
		});
    	
        //new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}")

    }
    
	private void changeFragment(Fragment frag, int position, int actionBarTitle){
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
		ft.replace(R.id.drawer_content, frag, Integer.toString(position));
	    ft.commit();
	    
	    getActivity().getActionBar().setTitle(actionBarTitle);
	}
}
