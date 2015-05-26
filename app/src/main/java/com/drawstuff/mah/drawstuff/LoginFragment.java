package com.drawstuff.mah.drawstuff;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drawstuff.mah.drawstuff.Draw.StartFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragmentlogin, container, false);

        Button doneBtn = (Button)  v.findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    Log.i("FragmentTransaction,", "Moving to startfragment");
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction(); //Start adding the fragment by getting the manager for handling this
                    StartFragment sf = new StartFragment(); //Creates splashscreen
                    ft.replace(R.id.fragment_login, sf); //And add it to the manager

                    ft.commit();
                } catch (Throwable e) {
                    Log.i("Error: ", "NullPointer... :" + e.toString());
                }

            }
        });

        return v;
    }


}
