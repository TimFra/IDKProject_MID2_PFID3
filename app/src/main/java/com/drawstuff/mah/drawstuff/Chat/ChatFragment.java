package com.drawstuff.mah.drawstuff.Chat;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.EndScreen;
import com.drawstuff.mah.drawstuff.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends ListFragment {

    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private String storedWord;
    private ChatListAdapter mChatListAdapter;
    private Firebase guessWord;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL).child("chat");
        guessWord = new Firebase(Constants.FIREBASE_URL).child("selectedword");

        setupUsername();


        final EditText inputText = (EditText) v.findViewById(R.id.messageInput);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                    sendMessage();
                }
                return true;
            }
        });


        v.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sendMessage();


            }
        });
        return v;
    }


    @Override
    public void onStart() {

        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time


        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_message, mUsername);

        listView.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
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
                // No-op
            }
        });

        guessWord.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storedWord = (String) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity(), "Error getting value", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

    private void setupUsername() {
        SharedPreferences prefs = getActivity().getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        if (mUsername == null) {
            Random r = new Random();
            // Assign a random user name if we don't have one saved

            mUsername = "Guesser" + r.nextInt(100000);
            prefs.edit().putString("username", mUsername).commit();
        }
    }

    private void sendMessage() {
        EditText inputText = (EditText) getView().findViewById(R.id.messageInput);
        String input = inputText.getText().toString();
        String win = mUsername + " guessed the right word! +pts!";
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            Chat winMsg = new Chat(win, "*** Game ***");

            // If guess is correct

            mFirebaseRef.push().setValue(chat);
            inputText.setText("");
            if (storedWord.equals(input.toLowerCase())) {

                // What happens when you win
                Firebase gameStateFirebase = new Firebase(Constants.FIREBASE_URL).child("roundWinner");
                gameStateFirebase.setValue(mUsername);

                // Toast.makeText(getActivity(), "Winner: " + mUsername, Toast.LENGTH_SHORT).show();
                mFirebaseRef.push().setValue(winMsg);

                final Firebase firebaseChecker = new Firebase(Constants.FIREBASE_URL).child("gameInProgress");
                firebaseChecker.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        firebaseChecker.setValue("false");
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }


        }


    }
}



