package fr.drinkizy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.listbar.adapter.CommentListAdapter;
import fr.drinkizy.objects.Comment;


public class BarAvisFragment extends Fragment {
	
	private ArrayList<Comment> mCommentItems;
	
	private ProgressBar progressBar;
	private ListView commentsList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single_avis, container, false);	
	    
	    commentsList = (ListView)rootView.findViewById(R.id.bar_comments);
	    progressBar = (ProgressBar)rootView.findViewById(R.id.progessBarAvis);
	    
	    // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
	    commentsList.setClipToPadding(false);
 		// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
 		setInsets(getActivity(), commentsList);
 		
 		
 		getAndDisplayComment();
 		
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mDataReceiver, new IntentFilter("BarDataLoaded"));
	}
	
	private BroadcastReceiver mDataReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Get extra data included in the Intent
			int id = intent.getIntExtra("id", 0);
			if(id != 2) return;
			
			
			getAndDisplayComment();
			Log.i("DEV", "data loaded");
		}
	};
	
	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0, config.getPixelInsetTop(true) + config.getNavigationBarHeight(), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	
	public void getAndDisplayComment(){
		if(getActivity() == null) return;
		
		mCommentItems = ((BarActivity) getActivity()).getAvis();
		setProgressBar(View.GONE);
		commentsList.setAdapter(new CommentListAdapter(getActivity(), mCommentItems));
	}
	
	
    /**
     * Toggle visibility of loader and visibility of list
     * @param visibility
     */
	public void setProgressBar(int visibility){
		progressBar.setVisibility(visibility);
		if(visibility == View.GONE)
			commentsList.setVisibility(View.VISIBLE);
	}
	
}
