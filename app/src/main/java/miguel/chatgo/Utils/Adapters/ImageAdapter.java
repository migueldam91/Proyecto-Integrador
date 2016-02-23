package miguel.chatgo.Utils.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import miguel.chatgo.R;
import miguel.chatgo.Utils.Gravatar.Gravatar;
import miguel.chatgo.Utils.Gravatar.MD5Util;

/**
 * Created by Miguel on 2/16/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<ParseUser> users;
    Typeface font;

    public ImageAdapter(Context c,List<ParseUser>users) {
        mContext = c;
        this.users=users;
        font = Typeface.createFromAsset(c.getAssets(), "Lighthouse_PersonalUse.ttf");
    }

    public int getCount() {
        return users.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder holder;
        Gravatar gravatar = new Gravatar();

        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item,null);
            holder=new Viewholder();
            // set image based on selected text
            holder.imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.textView.setText(users.get(position).getUsername());
            holder.textView.setTypeface(font);

            ParseUser user = users.get(position);
            String email =  user.getEmail().toLowerCase();
            if(email.equals(""))
                holder.imageView.setImageResource(R.drawable.avatar_empty);
            else{
//                String hash= md5Util.md5Hex(email);
                String gravatarURL= gravatar.getUrl(email);
                Log.v("From hash ",gravatarURL);
                Picasso.with(mContext).load(gravatarURL)
                        .placeholder(R.drawable.avatar_empty)
                        .fit()
                        .centerInside()
                        .into(holder.imageView);
            }
        } else {
            holder= (Viewholder) convertView.getTag();
        }

        return convertView;
    }

    private static class Viewholder{
        ImageView imageView;
        TextView textView;
    }
}
