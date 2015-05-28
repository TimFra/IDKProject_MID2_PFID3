package com.drawstuff.mah.drawstuff.Draw;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drawstuff.mah.drawstuff.R;

/**
 * Created by victoria on 2015-05-27.
 */
public class InfoDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("How to play");
        alertDialogBuilder.setMessage(Html.fromHtml(getString(R.string.InfoD)));
        alertDialogBuilder.setPositiveButton("OK", null);

        return alertDialogBuilder.create();
    }
}





