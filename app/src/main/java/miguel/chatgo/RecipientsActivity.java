package miguel.chatgo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class RecipientsActivity extends Activity {
    UtilSingleton utilidades = UtilSingleton.getInstance();
    ParseUser mCurrentUser;
    ParseRelation<ParseUser> mFriendsRelation;
    List<ParseUser> mUsers;
    String [] usernames;
    ProgressBar progressBar;
    TextView addFriends_Textview;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar= (ProgressBar) findViewById(R.id.recipientsProgress);
        mListView = (ListView) findViewById(R.id.recipientsList);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentUser= ParseUser.getCurrentUser();
        mFriendsRelation=mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
        ParseQuery<ParseUser> query = mFriendsRelation.getQuery();

        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(ParseConstants.MAX_USERS);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    mUsers = objects;
                    usernames = new String[objects.size()];
                    //copyUsernamesIntoStringArray(usernames);
                    utilidades.copyUsernamesIntoStringArray(usernames,mUsers);
                    progressBar.setVisibility(View.GONE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            RecipientsActivity.this,
                            android.R.layout.simple_list_item_checked,
                            usernames);
                    mListView.setAdapter(adapter);
                    mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    if (mUsers.size() != 0)
                        addFriends_Textview.setVisibility(View.INVISIBLE);
                } else {
                    utilidades.generateDialog(e.getMessage(), getApplicationContext()).show();

                }
            }
        });
    }
}
