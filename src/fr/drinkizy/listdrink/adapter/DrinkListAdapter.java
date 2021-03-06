package fr.drinkizy.listdrink.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import fr.drinkizy.R;
import fr.drinkizy.objects.Drinkbar;

public class DrinkListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Drinkbar> drinkItems;
	LayoutInflater inflater; // inflater pour charger le XML pour l'item
	
	ImageLoader imageLoader;
	
	public DrinkListAdapter(Context context, ArrayList<Drinkbar> drinkItems){
        this.context = context;
        this.drinkItems = drinkItems;
        imageLoader = ImageLoader.getInstance();
    }
	
	@Override
	public int getCount() {
		return drinkItems.size();
	}

	@Override
	public Object getItem(int position) {
		return drinkItems.get(position);
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
            convertView = mInflater.inflate(R.layout.drink_list_item, null);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.name);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.drinkbar_price);
           
        //absListViews
        txtName.setText(drinkItems.get(position).getDrink().getName());
        txtPrice.setText( String.valueOf(drinkItems.get(position).getPrice())+ " �"   );
        
        return convertView;
	}

}
