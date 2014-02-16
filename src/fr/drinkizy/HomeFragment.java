package fr.drinkizy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	private View currentView;
	private RelativeLayout searchBar;
	private TextView textView;
	private ImageView logo;
	private View centerView;
	private Button connectButton;
	
	/*
	 * False if logo and button removed
	 */
	private boolean isHome;
	
	private RelativeLayout.LayoutParams searchBarLP;
	private RelativeLayout.LayoutParams centerViewLayoutParam;
	private int heightPosition;
	private ObjectAnimator animator;
	private ValueAnimator animatorMargin;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.home_content, container, false);
	    
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		currentView = getView();
		
		Log.i("DEV", "onActivityCreated");

		logo = (ImageView)currentView.findViewById(R.id.logo);
		searchBar = (RelativeLayout)currentView.findViewById(R.id.searchBar);
		textView = (TextView)currentView.findViewById(R.id.homeSearchField);
		centerView = currentView.findViewById(R.id.centerView);
//		connectButton = (Button)currentView.findViewById(R.id.connectButton);
		
		
		

	}
	
	
}
