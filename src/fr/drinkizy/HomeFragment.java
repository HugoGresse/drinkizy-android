package fr.drinkizy;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

public class HomeFragment extends Fragment {

	private RelativeLayout layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.drinkizy_logo, container, false);
	    
	    layout = (RelativeLayout) rootView.findViewById(R.id.layout_logo);
	    
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		// This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
		layout.setClipToPadding(false);
		// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
		setInsets(this.getActivity(), layout);
	}
	

	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0,   config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	
}
