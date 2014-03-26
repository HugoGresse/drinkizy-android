package fr.drinkizy;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FaqWebClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    view.loadUrl(url);
	    return true;
	}

	@Override
	public void onPageFinished(WebView view, String url) {
	    view.loadUrl("javascript:$('.navbar').css('display', 'none'); $('.theme-band').css('display', 'none');" +
	    		" $('#footer').css('display', 'none'); $('.cover-container').css('padding', '0 10px'); " +
	    		"");
	}

}
