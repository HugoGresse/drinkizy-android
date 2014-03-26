package fr.drinkizy;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class FaqFragment extends Fragment {

	private WebView webview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		
	    // Inflate the layout for this fragment
	    View rootView = inflater.inflate(R.layout.webview, container, false);
	    webview = (WebView)rootView.findViewById(R.id.webview);
	    
	    // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
	    webview.setClipToPadding(false);
	 	// Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
	 	setInsets(this.getActivity(), webview);
	 		
	    return rootView;
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		final FaqWebClient faqClient = new FaqWebClient();
		webview.setWebViewClient(faqClient);
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		webview.loadUrl("http://drinkizy.alwaysdata.net/faq/");
	}
	
	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
			SystemBarTintManager tintManager = new SystemBarTintManager(context);
			SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
			view.setPadding(0,   config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	
}
