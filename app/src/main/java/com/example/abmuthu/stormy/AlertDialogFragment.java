package com.example.abmuthu.stormy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by abmuthu on 12/29/16.
 */

public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String errorMessage = getArguments().getString("passed_msg");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Sorry!")
                .setMessage(errorMessage)
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }


}
