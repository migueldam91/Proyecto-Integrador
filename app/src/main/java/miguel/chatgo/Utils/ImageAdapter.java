package miguel.chatgo.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.List;


import miguel.chatgo.R;

/**
 * Created by Miguel on 2/16/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<ParseUser> users;

    public ImageAdapter(Context c,List<ParseUser>users) {
        mContext = c;
        this.users=users;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = new View(mContext);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.item, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            TextView textView = (TextView) gridView.findViewById(R.id.textView);
            textView.setText(users.get(position).getUsername());


            imageView.setImageResource(mThumbIds[position]);
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setMinimumWidth();
////          imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
////            imageView.setPadding(8, 8, 8, 8);

        } else {
            gridView =  convertView;
        }

//        gridView.setImageResource(mThumbIds[position]);
        return gridView;
    }

    // references to our images
    private Integer[] mThumbIds;
    public void refill(){
        mThumbIds= new Integer[getUsersNumber()];
        for(int i=0 ; i<getUsersNumber();i++)
            mThumbIds[i]=R.drawable.noimageavatar;
    }
    private int getUsersNumber(){
        return users.size();
    }
}
