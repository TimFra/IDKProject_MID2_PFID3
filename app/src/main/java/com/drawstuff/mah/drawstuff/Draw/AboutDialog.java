package com.drawstuff.mah.drawstuff.Draw;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;

import com.drawstuff.mah.drawstuff.R;


public class AboutDialog extends DialogFragment {

   @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
       alertDialogBuilder.setTitle(R.string.About);
       alertDialogBuilder.setMessage(Html.fromHtml(getString(R.string.AboutFragmentText)));
       alertDialogBuilder.setPositiveButton("OK", null);

       return alertDialogBuilder.create();
   }
   }