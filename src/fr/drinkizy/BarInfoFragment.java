package fr.drinkizy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BarInfoFragment extends Fragment {

	private Bundle args;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single_info, container, false);	
	    args = getArguments();
	    	
	    Log.i("DEV", args.toString());
	    
	    return rootView;
	}
	
}
