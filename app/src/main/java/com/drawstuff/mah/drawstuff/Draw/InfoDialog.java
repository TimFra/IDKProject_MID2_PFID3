package com.drawstuff.mah.drawstuff.Draw;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drawstuff.mah.drawstuff.R;

/**
 * Created by victoria on 2015-05-27.
 */
public class InfoDialog extends DialogFragment {


    public InfoDialog(){
        //Require empty constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }
}
