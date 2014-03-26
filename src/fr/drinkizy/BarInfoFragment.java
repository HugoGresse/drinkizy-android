package fr.drinkizy;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.objects.Bar;

public class BarInfoFragment extends Fragment {
	
	private Bar bar;
	
	//View element
	private ScrollView scrollview;
	private ImageView image;
	private TextView rating;
	private TextView name;
	private TextView themes;
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
	    
	    scrollview = (ScrollView)rootView.findViewById(R.id.info_scrollview);
	    		
	    image = (ImageView)rootView.findViewById(R.id.bar_image);
	    image_adress = (ImageView)rootView.findViewById(R.id.image_adress);
	    image_tel = (ImageView)rootView.findViewById(R.id.image_tel);
	    image_site = (ImageView)rootView.findViewById(R.id.image_site);
	    image_email = (ImageView)rootView.findViewById(R.id.image_email);
	    
	    rating = (TextView)rootView.findViewById(R.id.bar_rating);
	    name = (TextView)rootView.findViewById(R.id.bar_name);
	    themes = (TextView)rootView.findViewById(R.id.bar_theme);
	    text_adress = (TextView)rootView.findViewById(R.id.text_adress);
	    text_tel = (TextView)rootView.findViewById(R.id.text_tel);
	    text_site = (TextView)rootView.findViewById(R.id.text_site);
	    text_email = (TextView)rootView.findViewById(R.id.text_email);
	    description = (TextView)rootView.findViewById(R.id.text_description);
	    

		// This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
		scrollview.setClipToPadding(false);
		// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
		setInsets(this.getActivity(), scrollview);
		
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		//		Bar myBar = ((BarFragment) getParentFragment()).getBar();
		bar =  ((BarActivity) getActivity()).getBar();
		
		String url = getActivity().getResources().getString(R.string.app_url)+bar.getImagesUrls().get(0);
		
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
		
		if(bar.getRank() != -1){
			String ratingNote = String.valueOf(bar.getRank());
        	DecimalFormat df = new DecimalFormat("#.0");
        	ratingNote = df.format(bar.getRank());
        	rating.setText(ratingNote+"/10");
		}
		
		description.setText(bar.getDescription());
	}
	
	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0,   config.getPixelInsetTop(true) + config.getNavigationBarHeight(), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	
	
}
