package miguel.chatgo.Utils;

import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;
import android.widget.Button;

/**
 * Created by Miguel on 3/5/2016.
 */
public class GoChatDialog extends AlertDialog {
    public GoChatDialog(final Context context) {
        super(context);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = getButton(DialogInterface.BUTTON_POSITIVE);

              
            }
        });
    }
}