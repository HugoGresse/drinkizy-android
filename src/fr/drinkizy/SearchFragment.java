/**
 * 
 */
package fr.drinkizy;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author Hugo
 *
 */
public class SearchFragment extends Fragment {
	
	private Button happyHour;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.search, container, false);
	    
	    happyHour = (Button)rootView.findViewById(R.id.happyHour);
	    
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);

		happyHour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("DEV", "happy hour clicked");
				
			}
		});

	}
}
