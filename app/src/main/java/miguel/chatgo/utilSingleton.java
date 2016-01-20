package miguel.chatgo;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 1/20/2016.
 */
public class UtilSingleton {
    private static UtilSingleton UtilSingleton;

    private UtilSingleton(){}

    public static UtilSingleton getInstance(){
        if(UtilSingleton ==null)
            UtilSingleton =new UtilSingleton();

        return UtilSingleton;
    }

    public void getDescription(){
        Log.v("Singleton creado", "singleton creado");
    }

    protected void copyUsernamesIntoStringArray(String [] usernames, List<ParseUser> mUsers){
        int i =0 ;
        for (ParseUser user : mUsers){
            usernames[i]=user.getUsername();
            i++;
        }
    }
    protected AlertDialog generateDialog(String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.editFriendsErrorTitle)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
