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
import android.widget.TextView;
import android.widget.Toast;

import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.Draw.StartFragment;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    private Firebase versionCheck;
    private ValueEventListener versionListener;
    private int fbVersion;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_splash, container, false);
        versionCheck = new Firebase(Constants.FIREBASE_URL).child("version");
        versionListener = versionCheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fbVersion = Integer.parseInt(dataSnapshot.getValue().toString());


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        splashTimer();
        return v;
    }


    public void splashTimer(){

        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {



                if(fbVersion == Constants.version) {
                    try {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction(); //Start adding the fragment by getting the manager for handling this
                        LoginFragment lf = new LoginFragment(); //Creates splashscreen
                        ft.replace(R.id.splash, lf); //And add it to the manager

                        ft.commit();
                    } catch (Throwable e) {
                        Log.i("Error: ", "NullPointer... :" + e.toString());
                    }
                } else {

                    if(fbVersion == 0){

                        TextView version = (TextView) getView().findViewById(R.id.versionText);
                        TextView version2 = (TextView) getView().findViewById(R.id.versionText2);
                        version.setText("Could not establish connection.");
                        version2.setText("Please try restarting application.");


                    }else {
                        TextView version = (TextView) getView().findViewById(R.id.versionText);
                        TextView version2 = (TextView) getView().findViewById(R.id.versionText2);
                        version.setText("You need to download the latest version.");
                        version2.setText("Your version: " + Constants.version + ", latest: " + fbVersion + ".");
                    }

                }

            }
        }.start();

    }
}
