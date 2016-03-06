package miguel.chatgo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

import java.awt.font.TextAttribute;

/**
 * Created by Miguel on 3/6/2016.
 */
public class SettingsFragment extends android.support.v4.app.Fragment {
    private Context mContext;
    private TextView mExitField;
    private TextView mWhoField;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settingsfragment, container, false);
        mContext = container.getContext();
        mExitField = (TextView) rootView.findViewById(R.id.exitField);
        mWhoField = (TextView) rootView.findViewById(R.id.whoField);
        mExitField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        setFuente();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void logOut() {
        ParseUser.logOut();
        Intent logOutIntent = new Intent(mContext, LoginActivity.class);
        logOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logOutIntent);
    }

    private void setFuente() {
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "Lighthouse_PersonalUse.ttf");
        mExitField.setTypeface(font);
        mWhoField.setTypeface(font);
    }
}
