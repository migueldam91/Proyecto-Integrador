package miguel.chatgo;

/**
 * Created by Miguel on 1/8/2016.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private Resources resources;
    private String[] tabTitles;
    private int[] imageResId = {
            R.drawable.ic_tab_inbox,
            R.drawable.ic_tab_friends,
            android.R.drawable.ic_menu_help
    };
    public SectionsPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext=context;
        resources=context.getResources();
        tabTitles= new String[]{
                resources.getString(R.string.title_messages_tab),
                resources.getString(R.string.title_friends_tab),
                resources.getString(R.string.title_settings_tab)
        };
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new InboxFragment();
            case 1:
                return new FriendsFragment();
            case 2:
                return new SettingsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return null;
    }
    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.tabTextView);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.tabImageView);
        img.setImageResource(imageResId[position]);
        return v;
    }


}