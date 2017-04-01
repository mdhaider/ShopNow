package com.oneapplab.shopnow.MessageBoxService;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

import com.oneapplab.shopnow.MessageBoxService.connectionInterface.DialogInterfaceListener;
import com.oneapplab.shopnow.R;


/**
 * Created by i822763 on 8/3/16.
 */
public class MessageBoxService {
    private static MessageBoxService mInstance = new MessageBoxService();
    private android.content.Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public static MessageBoxService sharedInstance() {
        return mInstance;
    }

    public void showOKMessageBox(String title, String message, final DialogInterfaceListener dialogInterfaceListener) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogInterfaceListener.okyClicked();
            }
        });
        dlgAlert.create().show();
    }

    public void showOKMessageBox(String message, final DialogInterfaceListener dialogInterfaceListener) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Ariba");
        dlgAlert.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogInterfaceListener.okyClicked();
            }
        });
        dlgAlert.create().show();
    }

}