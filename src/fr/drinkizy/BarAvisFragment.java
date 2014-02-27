package fr.drinkizy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BarAvisFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single_avis, container, false);	
	    
	    return rootView;
	}
}
