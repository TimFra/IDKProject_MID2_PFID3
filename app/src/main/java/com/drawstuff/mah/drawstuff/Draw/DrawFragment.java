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
import com.firebase.client.core.Context;

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
    private ValueEventListener winCheck;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);




        mFirebaseRef = new Firebase(Constants.FIREBASE_URL).child("draw");
        mDrawingView = new DrawingView(getActivity(), mFirebaseRef);
        word = new Firebase(Constants.FIREBASE_URL);
        setWin = new Firebase(Constants.FIREBASE_URL).child("gameInProgress");
        winChecker();
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
                //In case of internet connectivity issues, users should get a feedback that they are not connected and might experience issues with connectivity.
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


    /**
     * TODO: Below in a method to get the colorpicker inside the drawing application. This is currently active. Please read the documentation in ColorPickerDialog.java
     * Either implement this or remove it before release.
     *
     */
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



        /**
         * This is a function that checks for the correct word. This item will contain more words and will be presented to the drawer before he or she starts to draw.
         * TODO: Expand the wordlist with more words. Implement so that when a drawing session begins, the drawer gets a message on what the winning word will be.
         */
        word.getRoot().addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void winChecker(){
        //Below is a checker that halts users from picking "Drawer" as the game element if someone is already drawing.
        //TODO: Fix the gameInProgress function so  that it works constantly and doesn't crash.

        winCheck = setWin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getKey().equals("gameInProgress")) {
                    if (dataSnapshot.getValue() != null) {
                        if (dataSnapshot.getValue().toString().equals("false")) {

                            Log.i("asdf", "Value changed");
                            Toast.makeText(getActivity(), "Someone guessed your word!", Toast.LENGTH_LONG).show();
                            FragmentManager fm = getActivity().getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            StartFragment sf = new StartFragment();
                            ft.replace(R.id.main_activity_container, sf);
                            Log.i("asdf", "-- 2nd");
                            setWin.removeEventListener(winCheck);
                            ft.commit();
                        }

                    }
                }

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //TODO: Add a toast or message if the user experience an error in regard to trying to become a drawer.
            }
        });
    }

}

