package miguel.chatgo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import miguel.chatgo.Utils.Adapters.MessageAdapter;
import miguel.chatgo.Utils.utilSingleton;

/**
 * Created by Miguel on 1/8/2016.
 */
public class InboxFragment extends ListFragment {
    private List<ParseObject> mMessages;
    protected SwipeRefreshLayout mswipeRefreshLayout;
    private TextView noMessages;
    private ImageView noMessagesImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inboxfragment, container, false);
        mswipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mswipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        noMessages = (TextView) rootView.findViewById(R.id.emptyLabel);
        noMessagesImage= (ImageView) rootView.findViewById(R.id.imageNoMessages);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveMessages();

    }

    private void retrieveMessages() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_MESSAGES);
        query.whereEqualTo(ParseConstants.KEY_RECIPIENTSID, ParseUser.getCurrentUser().getObjectId());
        query.addDescendingOrder(ParseConstants.KEY_CREATEDAT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(mswipeRefreshLayout.isRefreshing()){
                    mswipeRefreshLayout.setRefreshing(false);
                }
                if (e == null) {
                    mMessages = objects;
                    String[] usernames = new String[objects.size()];
                    int i = 0;
                    for (ParseObject message : mMessages) {
                        usernames[i] = message.getString(ParseConstants.KEY_SENDERNAME);
                        i++;
                    }

                    MessageAdapter adapter = new MessageAdapter(getListView().getContext(), 0, mMessages);
                    setListAdapter(adapter);

                    if (mMessages.size() != 0) {
                        noMessages.setVisibility(View.INVISIBLE);
                        noMessagesImage.setVisibility(View.INVISIBLE);
                    }

                } else {
                    utilSingleton.getInstance().generateDialog(e.getMessage(), getView().getContext());
                }
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ParseObject message = mMessages.get(position);
        String messageType = message.getString(ParseConstants.KEY_FILETYPE);

        ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
        Uri fileUri = Uri.parse(file.getUrl());
        List<String> ids = message.getList(ParseConstants.KEY_RECIPIENTSID);


        //Operación al recibir el mensaje
        if (messageType.equals(ParseConstants.TYPE_IMAGE)) {
            Intent intentTypeImage = new Intent(getActivity(), ViewImageActivity.class);
            intentTypeImage.setData(fileUri);
            startActivity(intentTypeImage);
        } else {
            Intent intentTypeVideo = new Intent(Intent.ACTION_VIEW, fileUri);
            intentTypeVideo.setDataAndType(fileUri, "video/*");
            startActivity(intentTypeVideo);

        }

        if (ids.size() > 1) {
            utilSingleton.getInstance().generateDialog("ids!>1", getActivity().getApplicationContext());
            ids.remove(ParseUser.getCurrentUser().getObjectId());
            ArrayList<String> idsToRemove = new ArrayList<>();
            idsToRemove.add(ParseUser.getCurrentUser().getObjectId());
            message.removeAll(ParseConstants.KEY_RECIPIENTSID, idsToRemove);
            message.saveInBackground();

        } else {
            message.deleteInBackground();
        }
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Toast.makeText(getActivity(), "SwipeRefreshLayout", Toast.LENGTH_SHORT).show();
            retrieveMessages();

        }
    };


}
