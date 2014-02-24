package fr.drinkizy;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		    	
				ArrayList<Bar> mBarItems = new ArrayList<Bar>();
				mBarItems.addAll(bars);
				
				
		        // Set the adapter for the list view
				searchResult.setAdapter(new BarListAdapter(getActivity(), mBarItems));
		        
		    }
		});
    	
        //new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}")

    }
}
