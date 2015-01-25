package com.arctouch.floripabus.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;


public class DialogUtil {

    private static  ProgressDialog PROGRESS_DIALOG = null;

    public static Dialog createMessageDialog(String message, String title, Activity activity) {
        Dialog dialog = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setTitle((title != null && !title.isEmpty()) ? title : "Alert!");
        dialog = builder.create();

        return dialog;
    }

    public static void showProgressDialog(String message, String title, Activity activity){
        DialogUtil.PROGRESS_DIALOG = ProgressDialog.show(activity,title,message,false,true);
    }

    public static void closeProgressDialog() {
        if (DialogUtil.PROGRESS_DIALOG != null && DialogUtil.PROGRESS_DIALOG.isShowing()) {
           DialogUtil.PROGRESS_DIALOG.dismiss();
           DialogUtil.PROGRESS_DIALOG = null;
        }
    }
}
