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
import fr.drinkizy.rest.DownloadImageTask;

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
            convertView = mInflater.inflate(R.layout.bar_list_item, null);
        }
        
		//ImageView image = (ImageView) convertView.findViewById(R.id.image);
		new DownloadImageTask((ImageView) convertView.findViewById(R.id.image))
		.execute("http://drinkizy.alwaysdata.net/static/medias/bars/"+barItems.get(position).getSlug()+".jpg");
		

        TextView txtName = (TextView) convertView.findViewById(R.id.name);
        TextView txtAdress = (TextView) convertView.findViewById(R.id.adress);
        TextView txtTheme = (TextView) convertView.findViewById(R.id.theme);
        
        //http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
//        imgIcon.setImageDrawable(barItems.get(position).getSlug());       
        //absListViews
        txtName.setText(barItems.get(position).getName());
        txtAdress.setText(barItems.get(position).getAddress());
        txtTheme.setText(barItems.get(position).getTheme());
        
        return convertView;
	}

}
