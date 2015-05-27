package com.drawstuff.mah.drawstuff.Draw;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.drawstuff.mah.drawstuff.Chat.ChatFragment;
import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.Draw.DrawFragment;
import com.drawstuff.mah.drawstuff.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    public Firebase firebaseChecker;
    public Firebase clearDraw;
    public Firebase timeOut;
    public StartFragment() {
        // Required empty public
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseChecker = new Firebase(Constants.FIREBASE_URL).child("gameInProgress");
        clearDraw = new Firebase(Constants.FIREBASE_URL).child("draw");
        timeOut = new Firebase(Constants.FIREBASE_URL).child("timedOut");
        View v = inflater.inflate(R.layout.fragment_start, container, false);
        getActivity().getActionBar().show();
        getActivity().getActionBar().setTitle("DrawStuff");

        Button drawButton;
        drawButton = (Button) v.findViewById(R.id.drawBtn);
        drawButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                firebaseChecker.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue().toString().equals("false")) {
                            timeOut.setValue("false");
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            DrawFragment df = new DrawFragment();
                            firebaseChecker.setValue("true");
                            clearDraw.removeValue();
                            ft.replace(R.id.fragment_start, df,"drawTag");
                            ft.addToBackStack(null);
                            
                            ft.commit();

                             } else if (dataSnapshot.getValue().toString().equals("true")) {
                               Toast.makeText(getActivity(), "There is already someone drawing.", Toast.LENGTH_SHORT).show();
                            //  }

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });



               // }
            }
        });


        Button guessButton;
        guessButton = (Button) v.findViewById(R.id.guessBtn);

        guessButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ChatFragment cf = new ChatFragment();
                ft.replace(R.id.fragment_start,cf, "chatTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return v;

    }




}
