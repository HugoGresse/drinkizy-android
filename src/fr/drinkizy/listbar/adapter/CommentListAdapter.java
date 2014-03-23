package fr.drinkizy.listbar.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import fr.drinkizy.R;
import fr.drinkizy.objects.Comment;
import fr.drinkizy.objects.User;

public class CommentListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Comment> commentItems;
	private User mUser;
	LayoutInflater inflater; // inflater pour charger le XML pour l'item
	
	ImageLoader imageLoader;
	Comment mCurrentComment;
	
	public CommentListAdapter(Context context, ArrayList<Comment> commentItems){
        this.context = context;
        this.commentItems = commentItems;
        imageLoader = ImageLoader.getInstance();
    }
	
	@Override
	public int getCount() {
		return commentItems.size();
	}

	@Override
	public Object getItem(int position) {
		return commentItems.get(position);
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
            convertView = mInflater.inflate(R.layout.comment_list_item, null);
        }
		
		mCurrentComment = commentItems.get(position);
        
		ImageView image = (ImageView) convertView.findViewById(R.id.user_avatar);
		
		mUser = mCurrentComment.getUser();
		
		if(mUser.getAvatarUrl() != ""){
			String url = context.getResources().getString(R.string.app_url)+mUser.getAvatarUrl();
			imageLoader.displayImage(url, image);
		}

        TextView txtUserName = (TextView) convertView.findViewById(R.id.user_name);
        TextView txtDate = (TextView) convertView.findViewById(R.id.date);
        TextView txtComment = (TextView) convertView.findViewById(R.id.comment);
           
        //absListViews
        
       
        txtUserName.setText(mUser.getUserName());
        txtDate.setText(mCurrentComment.getDate());
        txtComment.setText(mCurrentComment.getComment());
        
        return convertView;
	}

}
