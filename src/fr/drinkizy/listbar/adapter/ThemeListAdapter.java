package fr.drinkizy.listbar.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.drinkizy.R;
import fr.drinkizy.objects.Theme;

public class ThemeListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Theme> themeItems;
	LayoutInflater inflater; // inflater pour charger le XML pour l'item
	
	
	
	Theme mCurrentTheme;
	
	public ThemeListAdapter(Context context, ArrayList<Theme> themeItems){
        this.context = context;
        this.themeItems = themeItems;     
    }
	
	@Override
	public int getCount() {
		return themeItems.size();
	}

	@Override
	public Object getItem(int position) {
		return themeItems.get(position);
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
            convertView = mInflater.inflate(R.layout.theme_list_item, null);
        }
		
		mCurrentTheme = themeItems.get(position);
		
        TextView txtName = (TextView) convertView.findViewById(R.id.name);
        txtName.setText(mCurrentTheme.getName());
		
      
        return convertView;
	}
	
	

}
