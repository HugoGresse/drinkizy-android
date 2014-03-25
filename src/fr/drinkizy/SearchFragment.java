/**
 * 
 */
package fr.drinkizy;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

/**
 * @author Hugo
 *
 */
public class SearchFragment extends Fragment {
	
	private ImageButton searchButton;
	private AutoCompleteTextView autoCompleteTextView;
	
	private static final String[] COUNTRIES = new String[] {
		//TODO récupérer la liste des autocomplétions sur Django
        "Belgium", "France", "Italy", "Germany", "Spain"
    };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.search, container, false);
	    
	    
	    searchButton = (ImageButton)rootView.findViewById(R.id.searchIcon);
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);
	    autoCompleteTextView = (AutoCompleteTextView)rootView.findViewById(R.id.homeSearchField);
	    autoCompleteTextView.setAdapter(adapter);
        
	    	    
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getActivity(), SearchResultActivity.class);
				
				if(autoCompleteTextView.getText().toString() != ""){
					intent.putExtra(MainActivity.SEARCH_QUERY, autoCompleteTextView.getText().toString());
				}
				
				intent.putExtra(MainActivity.DISTANCE_QUERY, 200000);
				
				startActivity(intent);
				
			}
		});
		
		

	}
	
	
}
