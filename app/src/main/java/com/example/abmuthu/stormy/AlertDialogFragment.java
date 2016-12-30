package com.example.abmuthu.stormy;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by abmuthu on 12/29/16.
 */

public class AlertDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    }
}
