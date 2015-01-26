package com.arctouch.floripabus.common;

import android.app.Activity;
import android.app.ProgressDialog;


public class DialogUtil {

    private static  ProgressDialog dialog;

    public static void showProgressDialog(String message, String title, Activity activity){
        DialogUtil.dialog = ProgressDialog.show(activity,title,message,false,true);
    }

    public static void closeProgressDialog() {
        if (DialogUtil.dialog != null && DialogUtil.dialog.isShowing()) {
           DialogUtil.dialog.dismiss();
           DialogUtil.dialog = null;
        }
    }
}
