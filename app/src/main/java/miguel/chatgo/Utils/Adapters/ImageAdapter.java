package miguel.chatgo.Utils.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import java.util.List;
import miguel.chatgo.R;
import miguel.chatgo.Utils.Gravatar.Gravatar;

/**
 * Created by Miguel on 2/16/2016.
 */
@TargetApi(Build.VERSION_CODES.M)
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<ParseUser> users;
    Typeface font;
    Display display;
    private WindowManager windowManager;
    private static int SCREENDISPLAY;
    private static int TAMANOTABLET = DisplayMetrics.DENSITY_360;
    private static int TAMANOSMARTPHONE560DPI= DisplayMetrics.DENSITY_560;
    public ImageAdapter(Context c,List<ParseUser>users) {
        mContext = c;
        this.users=users;
        font = Typeface.createFromAsset(c.getAssets(), "Lighthouse_PersonalUse.ttf");
        windowManager= (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        display= windowManager.getDefaultDisplay();
        SCREENDISPLAY= mContext.getResources().getDisplayMetrics().densityDpi;
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

            String gravatarURL= gravatar.getUrl(email);
            Log.v("From hash ",gravatarURL);
            Picasso.with(mContext).load(gravatarURL)
                    .placeholder(R.drawable.avatar_empty)
                    .fit()
                    .centerInside()
                    .into(holder.imageView);

            //Ajusta el tamaño de cada tile en función del tamaño de la pantalla
            if(SCREENDISPLAY< TAMANOTABLET) {
                resizeTile(holder,200,200,28);
            }else if(SCREENDISPLAY== TAMANOSMARTPHONE560DPI){
                resizeTile(holder,95,95,20);
            }
        } else {
            holder= (Viewholder) convertView.getTag();
        }

        return convertView;
    }
    public int dpToPx(int dps) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);

        return pixels;
    }
    private void resizeTile(Viewholder holder, int wPixel, int hPixel,int textSize) {
        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(wPixel), dpToPx(hPixel)));
        holder.textView.setTextSize(textSize);
    }


    private static class Viewholder{
        ImageView imageView;
        TextView textView;
    }


}
