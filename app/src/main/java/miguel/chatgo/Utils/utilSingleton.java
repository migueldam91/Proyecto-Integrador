package miguel.chatgo.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.parse.ParseUser;

import java.util.List;

import miguel.chatgo.MainActivity;
import miguel.chatgo.R;

/**
 * Created by Miguel on 1/20/2016.
 */
public class utilSingleton {
    private static utilSingleton UtilSingleton;

    private utilSingleton(){}

    public static utilSingleton getInstance(){
        if(UtilSingleton ==null)
            UtilSingleton =new utilSingleton();

        return UtilSingleton;
    }

    public void getDescription(){
        Log.v("Singleton creado", "singleton creado");
    }

    public void copyUsernamesIntoStringArray(String [] usernames, List<ParseUser> mUsers){
        int i =0 ;
        for (ParseUser user : mUsers){
            usernames[i]=user.getUsername();
            i++;
        }
    }
    public AlertDialog generateDialog(String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.editFriendsErrorTitle)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public  android.support.v7.app.AlertDialog dialogCameraChoices(Context context,DialogInterface.OnClickListener mDialogListener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(
                new ContextThemeWrapper(context,R.style.DialogAppTheme));
        String[] options = context.getResources().getStringArray(R.array.camera_choices);
        builder.setTitle(R.string.menu_choose_option_label)
                .setItems(options, mDialogListener)
                .setPositiveButton(android.R.string.ok, null)
        ;
        android.support.v7.app.AlertDialog dialog = builder.create();

        return dialog;
    }

}
