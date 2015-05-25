package com.drawstuff.mah.drawstuff;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drawstuff.mah.drawstuff.Draw.DrawFragment;
import com.drawstuff.mah.drawstuff.Draw.StartFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BufferFragment extends Fragment {


    public BufferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        StartFragment sf = new StartFragment();
        ft.replace(R.id.fragment_buffer, sf,"startFrag");
        ft.addToBackStack(null);
        ft.commit();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buffer, container, false);
    }


}
