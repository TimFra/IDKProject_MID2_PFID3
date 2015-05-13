package com.drawstuff.mah.drawstuff;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.Draw.DrawFragment;
import com.drawstuff.mah.drawstuff.Draw.StartFragment;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class EndScreen extends Fragment {

    public Firebase winner;
    public Firebase setWin;
    public EndScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_end_screen, container, false);
        // Inflate the layout for this fragment
        winner = new Firebase(Constants.FIREBASE_URL);
        displayWord();

        setWin = new Firebase(Constants.FIREBASE_URL).child("gameInProgress");
        setWin.setValue("false");



        Button backButton;
        backButton = (Button) v.findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                StartFragment sf = new StartFragment();


                ft.replace(R.id.main_activity_container, sf);
                ft.commit();

            }
        });
        return v;
    }

    public void displayWord(){
        winner.getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView win = (TextView) getView().findViewById(R.id.textWinner);

                Iterable<DataSnapshot> dsList = dataSnapshot.getChildren();
                for (DataSnapshot snapshot : dsList) {
                    if (snapshot.getKey().equals("gameState")) {
                        for (int i = 0; i < 10; i++) {
                            win.setText(snapshot.getValue().toString());
                        }

                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }


}
