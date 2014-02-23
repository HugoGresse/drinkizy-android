package fr.drinkizy.listbar.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.drinkizy.R;
import fr.drinkizy.objects.Bar;

public class BarListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Bar> barItems;
	LayoutInflater inflater; // inflater pour charger le XML pour l'item
	
	public BarListAdapter(Context context, ArrayList<Bar> barItems){
        this.context = context;
        this.barItems = barItems;
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
            convertView = mInflater.inflate(R.layout.condensed_bar_item, null);
        }
          
        ImageView imgage = (ImageView) convertView.findViewById(R.id.image);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.description);
        
        //http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
//        imgIcon.setImageDrawable(barItems.get(position).getSlug());       
        txtTitle.setText(barItems.get(position).getName());
        txtDescription.setText(barItems.get(position).getDescription());
        
        return convertView;
	}

}
