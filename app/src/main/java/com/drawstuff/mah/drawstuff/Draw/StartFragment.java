package com.drawstuff.mah.drawstuff.Draw;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    public String checked;
    public StartFragment() {
        // Required empty public
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseChecker = new Firebase(Constants.FIREBASE_URL).child("gameInProgress");
        View v = inflater.inflate(R.layout.fragment_start, container, false);

        Button drawButton;
        drawButton = (Button) v.findViewById(R.id.drawBtn);
        drawButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                if (winCheck().equals("false")) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DrawFragment df = new DrawFragment();


                    ft.replace(R.id.main_activity_container, df);
                    ft.commit();

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


                ft.replace(R.id.main_activity_container,cf);
                ft.commit();
            }
        });
        return v;

    }

    public String winCheck(){


        firebaseChecker.getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checked = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return checked;
    }


}
