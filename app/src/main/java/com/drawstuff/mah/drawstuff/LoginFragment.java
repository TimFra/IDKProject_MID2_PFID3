package com.drawstuff.mah.drawstuff;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.Draw.StartFragment;

import java.util.Random;


/**
 * Sets up the username used for the application (Constats.userName)
 */
public class LoginFragment extends Fragment {

    private String mUsername;

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

                    Log.i("FragmentTransaction,", "Moving to startfragment");
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction(); //Start adding the fragment by getting the manager for handling this
                    StartFragment sf = new StartFragment(); //Creates splashscreen
                    ft.replace(R.id.main_activity_container, sf); //And add it to the manager
                    //setupUsername();
                      EditText name;
                        name = (EditText) getActivity().findViewById(R.id.setUserName); //This allows the user to pick a username instead of the random generated one.
                        mUsername = name.getText().toString();

                        if(name.getText().toString().equals("")){
                            Random r = new Random();
                            mUsername = "User" + r.nextInt(1000000);
                        }

                        Constants.userName = mUsername;
                    ft.commit();

            }
        });

        return v;
    }



}
