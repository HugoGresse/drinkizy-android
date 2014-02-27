package fr.drinkizy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BarPlanFragment extends Fragment {
	
	private static View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
//	    View rootView = inflater.inflate(R.layout.bar_single_plan, container, false);	
//	    
//	    return rootView;
	    // see http://stackoverflow.com/questions/14083950/duplicate-id-tag-null-or-parent-id-with-another-fragment-for-com-google-androi
	    if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	    }
	    try {
	        view = inflater.inflate(R.layout.bar_single_plan, container, false);
	    } catch (InflateException e) {
	        /* map is already there, just return view as it is */
	    }
	    return view;
	}

}
