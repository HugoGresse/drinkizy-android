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
		connectButton = (Button)currentView.findViewById(R.id.connectButton);
		
		centerViewLayoutParam = (RelativeLayout.LayoutParams) centerView.getLayoutParams();
		searchBarLP = (RelativeLayout.LayoutParams) searchBar.getLayoutParams();
		
		animator = ObjectAnimator.ofFloat(logo, "alpha", 0);
		animator.addListener(new AnimatorListenerAdapter() {
		    @Override
		    public void onAnimationEnd(Animator animation) {
		    	((RelativeLayout)logo.getParent()).removeView(logo);
		    	((RelativeLayout)centerView.getParent()).removeView(centerView);
		    	((RelativeLayout)connectButton.getParent()).removeView(connectButton);
		    	
		    	heightPosition = (int)(searchBar.getY());
		    	
		    	searchBarLP.addRule(RelativeLayout.ABOVE, 0);
		    	searchBar.setY(heightPosition);
		    	
		    	Log.i("DEV", Float.toString(heightPosition));
		    	
		    	TranslateAnimation anim = new TranslateAnimation( 0, 0, heightPosition - searchBar.getMeasuredHeight(), 0);
		        anim.setDuration(400);
		        anim.setFillAfter( true );
		        
		        anim.setAnimationListener(new AnimationListener() {
		            @Override
		            public void onAnimationEnd(Animation arg0) {
		            	searchBarLP.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
		            }
					@Override
					public void onAnimationRepeat(Animation animation) {}
					@Override
					public void onAnimationStart(Animation animation) {}
		        });
		        searchBar.startAnimation(anim);
		    	
		        animator.removeAllListeners();
		    }
		});
		
		
		textView.setOnTouchListener(new View.OnTouchListener() {
			@Override
	        public boolean onTouch(View v, MotionEvent event) {
	            if(MotionEvent.ACTION_DOWN == event.getAction()) {
	            	animator.start();
	            }
	            return false; // return is important...
	        }
		});
		
		

	}
	
	private int dipsToPixels(int dips){
	    final float scale = getResources().getDisplayMetrics().density;
	    return (int)(dips * scale + 0.5f);
	}
	
}
