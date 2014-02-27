/**
 * 
 */
package fr.drinkizy;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * @author Hugo
 *
 */
public class SearchFragment extends Fragment {
	
	private Button happyHour;
	private ImageButton searchButton;
	private AutoCompleteTextView autoCompleteTextView;
	private ListView starredListBar;
	
	private static final String[] COUNTRIES = new String[] {
		//TODO récupérer la liste des autocomplétions sur Django
        "Belgium", "France", "Italy", "Germany", "Spain"
    };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.search, container, false);
	    
	    
	    happyHour = (Button)rootView.findViewById(R.id.happyHour);
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
			
				if(autoCompleteTextView.getText().toString() != ""){
					Bundle bundle = new Bundle();
					bundle.putString(MainActivity.SEARCH_QUERY, autoCompleteTextView.getText().toString());
					SearchResultFragment fragment = new SearchResultFragment();
					fragment.setArguments(bundle);
					changeFragment(fragment, 1, R.string.search_result);
				}else{
					changeFragment(new SearchResultFragment(), 1, R.string.search_result);
				}
				
			}
		});
		
		
//		happyHour.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Log.i("DEV", "happy hour clicked");
//				
//			}
//		});

	}
	
	private void changeFragment(Fragment frag, int position, int actionBarTitle){
		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
		ft.replace(R.id.drawer_content, frag, Integer.toString(position));
	    ft.commit();
	    
	    getActivity().getActionBar().setTitle(actionBarTitle);
	}
	
}
