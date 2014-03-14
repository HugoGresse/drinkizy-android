package fr.drinkizy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
	
	private TextView librariesList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.about, container, false);
	    librariesList = (TextView)rootView.findViewById(R.id.about_libraries);
	    
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
	
	
}
