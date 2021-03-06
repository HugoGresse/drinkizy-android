package fr.drinkizy.listbar.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import fr.drinkizy.R;
import fr.drinkizy.objects.Bar;

public class BarListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Bar> barItems;
	LayoutInflater inflater; // inflater pour charger le XML pour l'item
	
	ImageLoader imageLoader;
	Bar mCurrentBar;
	
	public BarListAdapter(Context context, ArrayList<Bar> barItems){
        this.context = context;
        this.barItems = barItems;
        imageLoader = ImageLoader.getInstance();
    }
	
	@Override
	public int getCount() {
		return barItems.size();
	}

	@Override
	public Object getItem(int position) {
		return barItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.bar_list_item, null);
        }
		
		mCurrentBar = barItems.get(position);
        
		ImageView image = (ImageView) convertView.findViewById(R.id.bar_image);
		if(!mCurrentBar.getImagesUrls().isEmpty()){
			String url = context.getResources().getString(R.string.app_url)+mCurrentBar.getImagesUrls().get(0);
			imageLoader.displayImage(url, image);
		}
		
        TextView txtName = (TextView) convertView.findViewById(R.id.name);
        TextView txtAdress = (TextView) convertView.findViewById(R.id.adress);
        TextView txtTheme = (TextView) convertView.findViewById(R.id.bar_theme);
        TextView txtRating = (TextView) convertView.findViewById(R.id.search_bar_rating);
        TextView txtDistance = (TextView) convertView.findViewById(R.id.bar_distance);
        
        if(mCurrentBar.getDistance() != 0)
        	txtDistance.setText("� "+mCurrentBar.getDistance()/1000+" km");
        else txtDistance.setText("");
        
        txtName.setText(mCurrentBar.getName());
        txtAdress.setText(mCurrentBar.getAddress());
		txtTheme.setText(mCurrentBar.getThemesAsAString());
        
		String rating = String.valueOf(mCurrentBar.getRank());
		if( !rating.equals("-1.0") ){
        	DecimalFormat df = new DecimalFormat("#.0");
        	rating = df.format(mCurrentBar.getRank());
        	txtRating.setText(rating);
        } else {
        	txtRating.setText("-/10");
        }
        return convertView;
	}

}
