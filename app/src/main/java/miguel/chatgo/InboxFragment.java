package miguel.chatgo;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import miguel.chatgo.Utils.utilSingleton;

/**
 * Created by Miguel on 1/8/2016.
 */
public class InboxFragment extends ListFragment{
    private List<ParseObject> mMessages;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inboxfragment, container, false);
        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarListView);
        progressBar.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_MESSAGES);
        query.whereEqualTo(ParseConstants.KEY_RECIPIENTSID, ParseUser.getCurrentUser().getObjectId());
        query.addDescendingOrder(ParseConstants.KEY_CREATEDAT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                //progressBar.setVisibility
                if(e==null){
                    mMessages=objects;
                    String [] usernames= new String[objects.size()];
                    int i = 0;
                    for (ParseObject message : mMessages){
                        usernames[i]= message.getString(ParseConstants.KEY_SENDERNAME);
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getListView().getContext(),
                            android.R.layout.simple_list_item_1,
                            usernames);
                    setListAdapter(adapter);


                }else{
                    utilSingleton.getInstance().generateDialog(e.getMessage(),getView().getContext());
                }
            }
        });

    }
}
