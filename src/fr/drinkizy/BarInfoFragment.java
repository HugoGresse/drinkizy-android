package fr.drinkizy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.drinkizy.objects.Bar;

public class BarInfoFragment extends Fragment {
	
	private Bar bar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single_info, container, false);	

	    
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
//		Bar myBar = ((BarFragment) getParentFragment()).getBar();
		bar = ((MainActivity) getActivity()).getCurrentBar();
		
	}
	
	
	
}
