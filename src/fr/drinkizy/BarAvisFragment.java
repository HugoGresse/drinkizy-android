package fr.drinkizy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import fr.drinkizy.listbar.adapter.CommentListAdapter;
import fr.drinkizy.objects.Bar;
import fr.drinkizy.objects.Comment;
import fr.drinkizy.objects.CommentsObject;
import fr.drinkizy.rest.DrinkizyRestClient;


public class BarAvisFragment extends Fragment {
	
	private Bar bar;
	private ArrayList<Comment> mCommentItems;
	
	private ListView commentsList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.bar_single_avis, container, false);	
	    
	    commentsList = (ListView)rootView.findViewById(R.id.bar_comments);
	    
	    // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
	    commentsList.setClipToPadding(false);
 		// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
 		setInsets(this.getActivity(), commentsList);
 		
	    return rootView;
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		bar =  ((BarActivity) getActivity()).getBar();
		
		loadCommentsOfBar();
		
	}
	
	

	public void loadCommentsOfBar(){
    	Log.d("BarAvisFragment", "id bar: "+bar.getId());
    	RequestParams params = new RequestParams();
    	params.put("format", "json");
    	params.put("object_pk", String.valueOf(bar.getId()));
    	
    	DrinkizyRestClient.get("/api/v1/comment/", params, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.d("BarAvisFragment", response);
		    	Gson gson = new Gson();
		    	CommentsObject commentsObject = gson.fromJson(response, CommentsObject.class);
		    	mCommentItems = (ArrayList<Comment>) commentsObject.getObjects();
		    	
		    	commentsList.setAdapter(new CommentListAdapter(getActivity(), mCommentItems));
		    	
		    }
		});

    }
	
	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0, config.getPixelInsetTop(true) + config.getNavigationBarHeight(), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	
}
