package fr.drinkizy;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

public class AboutFragment extends Fragment {
	
	private TextView librariesList;
	private ScrollView aboutScrollView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.about, container, false);
	    librariesList = (TextView)rootView.findViewById(R.id.about_libraries);
	    aboutScrollView = (ScrollView)rootView.findViewById(R.id.scrollViewAbout);
	    
	    // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
	    aboutScrollView.setClipToPadding(false);
	 	// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
	 	setInsets(this.getActivity(), aboutScrollView);
	 	
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		StringBuilder builder = new StringBuilder();
		String[] libraries = getResources().getStringArray(R.array.about_libraries);
		for (String s : libraries){
			builder.append(s+" \n");
			librariesList.setText(builder.toString());
		}
	}
	
	

	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0,   config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	
}
