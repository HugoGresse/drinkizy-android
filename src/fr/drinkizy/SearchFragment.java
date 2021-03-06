/**
 * 
 */
package fr.drinkizy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.listbar.adapter.ThemeListAdapter;
import fr.drinkizy.objects.Theme;
import fr.drinkizy.objects.ThemesObject;
import fr.drinkizy.rest.DrinkizyRestClient;

/**
 * @author Hugo
 *
 */
public class SearchFragment extends Fragment {
	
	private static final int DISTANCE = 200000;
	
	private RelativeLayout searchContainer;
	
	private ImageButton searchButton;
	private Button searchGpsButton;
	
	private Button themeSportifButton;
	private Button themeConcertButton;
	private Button themePubButton;
	private Button themeGeekButton;
	private Button themeLoungeButton;
	private Button themeRockButton;
	
	private AutoCompleteTextView autoCompleteTextView;
	private ListView themesList;
	private ArrayList<String> bannedThemes;
	
	private ArrayList<Theme> mThemesItems;
	
	private static final String[] COUNTRIES = new String[] {
		//TODO r�cup�rer la liste des autocompl�tions sur Django
        "Belgium", "France", "Italy", "Germany", "Spain"
    };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.search, container, false);
	    
	    searchContainer = (RelativeLayout)rootView.findViewById(R.id.search_container);
	    
	    searchButton = (ImageButton)rootView.findViewById(R.id.searchIcon);
	    searchGpsButton = (Button) rootView.findViewById(R.id.b_search_gps);
	    
	    themeSportifButton = (Button)rootView.findViewById(R.id.theme_sportif);
		themeConcertButton = (Button)rootView.findViewById(R.id.theme_concert);
		themePubButton = (Button)rootView.findViewById(R.id.theme_pub);
		themeGeekButton = (Button)rootView.findViewById(R.id.theme_geek);
		themeLoungeButton = (Button)rootView.findViewById(R.id.theme_lounge);
		themeRockButton = (Button)rootView.findViewById(R.id.theme_rock);
		
	    themesList = (ListView)rootView.findViewById(R.id.themes);
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);
	    autoCompleteTextView = (AutoCompleteTextView)rootView.findViewById(R.id.homeSearchField);
	    autoCompleteTextView.setAdapter(adapter);
        
	    // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
	    searchContainer.setClipToPadding(false);
	 	// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
	 	setInsets(this.getActivity(), searchContainer);
	 		
	    	    
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResearch();
			}
		});
		autoCompleteTextView.setOnKeyListener(new OnKeyListener(){
		    public boolean onKey(View v, int keyCode, KeyEvent event){
		        if (event.getAction() == KeyEvent.ACTION_DOWN){
		            switch (keyCode){
		                case KeyEvent.KEYCODE_DPAD_CENTER:
		                case KeyEvent.KEYCODE_ENTER:
		                	sendResearch();
		                    return true;
		                default:
		                    break;
		            }
		        }
		        return false;
		    }
		});
		
		// search near current Location
		searchGpsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SearchResultActivity.class);
				intent.putExtra(MainActivity.DISTANCE_QUERY, DISTANCE);
				
				startActivity(intent);
			}
		});
		
		initializeButtonsThemesListeners();
		
		initializeBannedThemes();
		
		loadDrinkizyThemes();
		
		themesList.setOnItemClickListener(new OnItemClickListener(){
			
		    @Override 
		    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3){ 
		    	
		        Intent intentBarsForTheme = new Intent(getActivity(), SearchResultActivity.class);
		        
		        intentBarsForTheme.putExtra(MainActivity.THEME_QUERY, mThemesItems.get(position).getSlug());
		        startActivity(intentBarsForTheme);
		        //		        getActivity().overridePendingTransition(R.anim.hold, R.anim.hold);
		    }
		});
		
		

	}
	
	public void sendResearch(){
		Intent intent = new Intent(getActivity(), SearchResultActivity.class);
		
		if(autoCompleteTextView.getText().toString() != ""){
			intent.putExtra(MainActivity.SEARCH_QUERY, autoCompleteTextView.getText().toString());
		}
		
		startActivity(intent);
	}
	
	public void loadDrinkizyThemes(){
    	
		RequestParams params = new RequestParams();
		params.put("format", "json");
		String url = "/api/v1/theme/";
		
    	DrinkizyRestClient.get(url, params, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		  
		    	Gson gson = new Gson();
		    	ThemesObject themesObject = gson.fromJson(response, ThemesObject.class);
		    	mThemesItems = (ArrayList<Theme>) themesObject.getObjects();
	    	    
		    	
		    	// on supprime les th�mes principaux qui ont une ic�ne propre
		    	for(int i = mThemesItems.size()-1; i > -1; i--){
		    		for(int j = 0; j < bannedThemes.size(); j++){
			    		if(bannedThemes.get(j).equals(mThemesItems.get(i).getSlug())){
			    			mThemesItems.remove(i);
			    		}
		    		}
		    	}
		    	
		    	themesList.setAdapter(new ThemeListAdapter(getActivity(), mThemesItems));
		    	
		    }
		});

    }
		
	private void initializeBannedThemes(){
		bannedThemes = new ArrayList<String>();
        bannedThemes.add("bars-sportifs");
        bannedThemes.add("bars-concerts");
        bannedThemes.add("pubs");
        bannedThemes.add("bars-geek");
        bannedThemes.add("bars-lounge");
        bannedThemes.add("bars-rock");
	}
	
	private void initializeButtonsThemesListeners(){
		themeSportifButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentBarsForTheme = new Intent(getActivity(), SearchResultActivity.class);

				intentBarsForTheme.putExtra(MainActivity.THEME_QUERY, "bars-sportifs");
				startActivity(intentBarsForTheme);
				
			}
		});
		
		themeConcertButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentBarsForTheme = new Intent(getActivity(), SearchResultActivity.class);
				
				intentBarsForTheme.putExtra(MainActivity.THEME_QUERY, "bars-concerts");
				startActivity(intentBarsForTheme);
				
			}
		});
		
		themePubButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentBarsForTheme = new Intent(getActivity(), SearchResultActivity.class);
				
				intentBarsForTheme.putExtra(MainActivity.THEME_QUERY, "pubs");
				startActivity(intentBarsForTheme);
				
			}
		});
		
		themeGeekButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentBarsForTheme = new Intent(getActivity(), SearchResultActivity.class);
				
				intentBarsForTheme.putExtra(MainActivity.THEME_QUERY, "bars-geek");
				startActivity(intentBarsForTheme);
				
			}
		});
		
		themeLoungeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentBarsForTheme = new Intent(getActivity(), SearchResultActivity.class);
				
				intentBarsForTheme.putExtra(MainActivity.THEME_QUERY, "bars-lounge");
				startActivity(intentBarsForTheme);
				
			}
		});
		
		themeRockButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentBarsForTheme = new Intent(getActivity(), SearchResultActivity.class);
				
				intentBarsForTheme.putExtra(MainActivity.THEME_QUERY, "bars-rock");
				startActivity(intentBarsForTheme);
				
			}
		});
	}
	
	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0,   config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
}
