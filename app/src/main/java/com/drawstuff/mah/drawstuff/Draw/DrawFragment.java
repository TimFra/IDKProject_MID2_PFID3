package com.drawstuff.mah.drawstuff.Draw;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.EndScreen;
import com.drawstuff.mah.drawstuff.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawFragment extends Fragment implements ColorPickerDialog.OnColorChangedListener{


    public DrawFragment() {
        // Required empty public constructor
    }
    private static final int COLOR_MENU_ID = Menu.FIRST;
    private DrawingView mDrawingView;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private Firebase word;
    private Firebase setWin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);




        mFirebaseRef = new Firebase(Constants.FIREBASE_URL).child("draw");
        mDrawingView = new DrawingView(getActivity(), mFirebaseRef);
        word = new Firebase(Constants.FIREBASE_URL);
        return mDrawingView;
    }



    @Override
    public void onStart() {
        super.onStart();
        // Set up a notification to let us know when we're connected or disconnected from the Firebase servers

        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(getActivity(), "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();

            }
        });
        displayWord();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Clean up our listener so we don't have it attached twice.
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mDrawingView.cleanup();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == COLOR_MENU_ID) {
            new ColorPickerDialog(getActivity(), this, 0xFFFF0000).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void colorChanged(int newColor) {
        mDrawingView.setColor(newColor);
    }


    public void displayWord(){


        /// win check
        setWin = new Firebase(Constants.FIREBASE_URL).child("gameInProgress");
        setWin.getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dsList = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : dsList) {

                    if(snapshot.getValue().equals("false")){

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        EndScreen es = new EndScreen();
                        ft.replace(R.id.main_activity_container,es);
                        ft.commit();
                    }

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        word.getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> dsList = dataSnapshot.getChildren();
                for (DataSnapshot snapshot : dsList) {
                    if (snapshot.getKey().equals("selectedword")) {
                       // for (int i = 0; i < 10; i++) {
                            Toast.makeText(getActivity(), snapshot.getValue().toString(), Toast.LENGTH_LONG).show();
                      //  }

                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }
}

