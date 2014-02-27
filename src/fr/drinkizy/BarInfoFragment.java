package fr.drinkizy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import fr.drinkizy.objects.Bar;

public class BarInfoFragment extends Fragment {
	
	private Bar bar;
	
	//View element
	private ImageView image;
	private TextView rating;
	private TextView name;
	private TextView themes;
	private TextView price;
	private ImageView image_adress;
	private ImageView image_tel;
	private ImageView image_site;
	private ImageView image_email;
	private TextView text_adress;
	private TextView text_tel;
	private TextView text_site;
	private TextView text_email;
	private TextView description;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single_info, container, false);	
	    
	    image = (ImageView)rootView.findViewById(R.id.bar_image);
	    image_adress = (ImageView)rootView.findViewById(R.id.image_adress);
	    image_tel = (ImageView)rootView.findViewById(R.id.image_tel);
	    image_site = (ImageView)rootView.findViewById(R.id.image_site);
	    image_email = (ImageView)rootView.findViewById(R.id.image_email);
	    
	    rating = (TextView)rootView.findViewById(R.id.bar_rating);
	    name = (TextView)rootView.findViewById(R.id.bar_name);
	    themes = (TextView)rootView.findViewById(R.id.bar_theme);
	    price = (TextView)rootView.findViewById(R.id.bar_price);
	    text_adress = (TextView)rootView.findViewById(R.id.text_adress);
	    text_tel = (TextView)rootView.findViewById(R.id.text_tel);
	    text_site = (TextView)rootView.findViewById(R.id.text_site);
	    text_email = (TextView)rootView.findViewById(R.id.text_email);
	    description = (TextView)rootView.findViewById(R.id.text_description);
	    
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
//		Bar myBar = ((BarFragment) getParentFragment()).getBar();
		bar = ((MainActivity) getActivity()).getCurrentBar();
		
		String url = getActivity().getResources().getString(R.string.app_static_url)+bar.getSlug()+".jpg";
		ImageLoader.getInstance().displayImage(url, image);
		
		name.setText(bar.getName());
		themes.setText(bar.getThemesAsAString());
		
		text_adress.setText(bar.getAddress());
		text_tel.setText(bar.getPhone());
		text_site.setText(bar.getWebsite());
		text_email.setText(bar.getMail());
		
		description.setText(bar.getDescription());
	}
	
	
}
