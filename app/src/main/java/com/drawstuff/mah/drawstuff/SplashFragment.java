package com.drawstuff.mah.drawstuff;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drawstuff.mah.drawstuff.Draw.StartFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        splashTimer();
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }


    public void splashTimer(){

        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {

                try {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction(); //Start adding the fragment by getting the manager for handling this
                    LoginFragment lf = new LoginFragment(); //Creates splashscreen
                    ft.replace(R.id.splash, lf); //And add it to the manager

                    ft.commit();
                } catch (Throwable e) {
                    Log.i("Error: ", "NullPointer... :" + e.toString());
                }

            }
        }.start();

    }
}
