package miguel.chatgo;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class RecipientsActivity extends AppCompatActivity {
    private static String TAG = RecipientsActivity.class.getSimpleName();
    utilSingleton utilidades = utilSingleton.getInstance();
    ParseUser mCurrentUser;
    ParseRelation<ParseUser> mFriendsRelation;
    List<ParseUser> mUsers;
    String[] usernames;
    ProgressBar progressBar;
    ListView mListView;
    MenuItem mSendMenuItem;
    ArrayList<String> recipientsIdsAux = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients);
        progressBar = (ProgressBar) findViewById(R.id.recipientsProgress);
        mListView = (ListView) findViewById(R.id.recipientsList);
        progressBar.setVisibility(View.GONE);

        //mFriendsRelation=mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Falta por meter el nombre del objeto del list en el arrayList
                //recipientsIdsAux.add()
                if (mListView.getCheckedItemCount() != 0)
                    mSendMenuItem.setVisible(true);
                else
                    mSendMenuItem.setVisible(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipients, menu);
        mSendMenuItem = menu.getItem(0);
        mSendMenuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.actionSend:
                ParseObject message = createMessage();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    private ParseObject createMessage() {
        ParseObject message = new ParseObject(ParseConstants.CLASS_MESSAGES);
        message.put(ParseConstants.KEY_SENDERID, ParseUser.getCurrentUser().getObjectId());
        message.put(ParseConstants.KEY_SENDERNAME, ParseUser.getCurrentUser().getUsername());
        message.put(ParseConstants.KEY_RECIPIENTSID, getRecipientsIds());
        return message;
    }

    private ArrayList<String> getRecipientsIds() {
        ArrayList<String> recipientsIds = new ArrayList<>();
        for (int i = 0; i < mListView.getCount(); i++) {
            if (mListView.isItemChecked(i)) {
                ParseObject recipient= mUsers.get(i);
                String recipientId = recipient.getObjectId();
                recipientsIds.add(recipientId);
            }
        }

        //recipientsIds.add()

        return recipientsIds;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
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
                    utilidades.copyUsernamesIntoStringArray(usernames, mUsers);
                    progressBar.setVisibility(View.GONE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            RecipientsActivity.this,
                            android.R.layout.simple_list_item_checked,
                            usernames);
                    mListView.setAdapter(adapter);
                    mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                }
            }
        });

    }

//    protected void checkMarked(Menu menu, final ListView listView){
//        MenuItem menuItem = menu.findItem(R.id.actionSend);
//        if(listView.isItemChecked(position)){
//            mFriendsRelation.add(mUsers.get(position));
//        }else{
//            mFriendsRelation.remove(mUsers.get(position));
//        }
//
//        mCurrentUser.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e != null) {
//                    generateDialog(e.getMessage()).show();
//                    Log.e("ParseException", e.getMessage());
//                }
//            }
//        });
//        if(listView.si>0)
//            menuItem.setVisible(true);
//
//    }


}