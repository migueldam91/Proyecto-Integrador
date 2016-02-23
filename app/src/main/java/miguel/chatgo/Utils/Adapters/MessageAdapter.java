package miguel.chatgo.Utils.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

import miguel.chatgo.ParseConstants;
import miguel.chatgo.R;

/**
 * Created by Miguel on 1/26/2016.
 */
public class MessageAdapter extends ArrayAdapter<ParseObject> {
    private Context mContext;
    private List<ParseObject> mMessages;

    public MessageAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
        mContext = context;
        mMessages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.imagePic);
            holder.textView = (TextView) convertView.findViewById(R.id.senderLabel);
            holder.timeLabel = (TextView) convertView.findViewById(R.id.timeLabel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject message = mMessages.get(position);
        Date createAt = message.getCreatedAt();
        long now = new Date().getTime();
        String convertedDate = DateUtils.getRelativeTimeSpanString(
                createAt.getTime(),
                now,
                DateUtils.DAY_IN_MILLIS).toString();
        holder.timeLabel.setText(convertedDate);
        if (message.getString(ParseConstants.KEY_FILETYPE).equals(ParseConstants.TYPE_IMAGE)) {
            holder.iconImageView.setImageResource(R.drawable.picbutton);
        } else {
            holder.iconImageView.setImageResource(R.drawable.play);
        }
        holder.textView.setText(message.getString(ParseConstants.KEY_SENDERNAME));
        return convertView;
    }

    public static class ViewHolder {
        ImageView iconImageView;
        TextView textView;
        TextView timeLabel;

    }
}


