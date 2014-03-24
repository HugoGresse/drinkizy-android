package fr.drinkizy;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.objects.Bar;


public class BarPlanFragment extends Fragment {
	
	private static View view;
	private GoogleMap mMap;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
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
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		

        // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
        // ((ViewGroup) view).setClipToPadding(false);
		// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
		// 
		
		setBarAndMarker( ((BarActivity)getActivity()).getBar());
	}
	
	//called it when bar is loaded on BarActivity
	public void setBarAndMarker(Bar bar){
		
		LatLng barPos = new LatLng(bar.getLatitude(), bar.getLongitude());
		
		// add marker
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.addMarker(new MarkerOptions()
		        .position(barPos)
		        .title(bar.getName())
		        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
		

		setInsets(this.getActivity(), mMap);
		
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barPos,15));
	    // Zoom in, animating the camera.
		mMap.animateCamera(CameraUpdateFactory.zoomIn());
	    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
		mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		
	}

	public static void setInsets(Activity context, GoogleMap mMap2) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			mMap2.setPadding(0,   config.getPixelInsetTop(true) + config.getNavigationBarHeight(), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	
}
