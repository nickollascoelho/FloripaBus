package com.arctouch.floripabus.activities.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import static android.app.AlertDialog.Builder;


public class ExitDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Builder dialog = new Builder(getActivity());
        dialog.setTitle("Exiting");
        dialog.setMessage("Are you sure?");

        //actions for the dialog
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });

        dialog.setNegativeButton("CANCEL", null);

        return dialog.create();
    }
}
