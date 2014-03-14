package fr.drinkizy;

import android.app.Fragment;
import android.os.Bundle;
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
		bar =  ((BarActivity) getActivity()).getBar();
		
		String url = getActivity().getResources().getString(R.string.app_static_url)+bar.getSlug()+".jpg";
		ImageLoader.getInstance().displayImage(url, image);
		
		
		deleteOrSetInfo();
	}
	
	private void deleteOrSetInfo(){
		name.setText(bar.getName());
		themes.setText(bar.getThemesAsAString());
		
		if(bar.getAddress().isEmpty()){
			image_adress.setVisibility(View.GONE);
			text_adress.setVisibility(View.GONE);
		}
		else
			text_adress.setText(bar.getAddress());
		
		if(bar.getPhone().isEmpty()){
			image_tel.setVisibility(View.GONE);
			text_tel.setVisibility(View.GONE);
		}else
			text_tel.setText(bar.getPhone());
		
		if(bar.getWebsite().isEmpty()){
			image_site.setVisibility(View.GONE);
			text_site.setVisibility(View.GONE);
		}else
			text_site.setText(bar.getWebsite());
		
		
		if(bar.getMail().isEmpty()){
			image_email.setVisibility(View.GONE);
			text_email.setVisibility(View.GONE);
		}else
			text_email.setText(bar.getPhone());
		
		description.setText(bar.getDescription());
	}
	
}
