package com.demo.guestbook.ui.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtil {

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("App Initialization");
        dialog.setMessage(message);
        dialog.setCancelable(false);
        return dialog;
    }

    public static AlertDialog getErrorAlertDialog(Activity activity, String requestMessage) {

        StringBuffer sb = new StringBuffer();
        sb.append("Couldn't complete attempt for: ");
        sb.append(requestMessage);
        sb.append("\n  Please make sure you are connected to the internet and try again later");
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Service Error");
        alertDialog.setMessage(sb.toString());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return alertDialog;
    }


}
