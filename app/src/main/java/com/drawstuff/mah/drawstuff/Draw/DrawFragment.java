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

import com.drawstuff.mah.drawstuff.Chat.Chat;
import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.EndScreen;
import com.drawstuff.mah.drawstuff.FirebaseSelectedWord;
import com.drawstuff.mah.drawstuff.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Context;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

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
    private Firebase fbChat;
    private ValueEventListener winCheck;
    ArrayList<String> words = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        fbChat = new Firebase(Constants.FIREBASE_URL).child("chat");


        setRandomWord();
        welcomeNewDrawer();
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
                    //Toast.makeText(getActivity(), "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
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

                        try {
                            Toast.makeText(getActivity(), snapshot.getValue().toString(), Toast.LENGTH_LONG).show(); //TODO: This needs a try catch or null object reference.
                        } catch (Throwable e) {
                            Log.i("Error: ", " -- In DrawFragment, word toast: " + e.toString());
                        }

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

                            try {
                                Toast.makeText(getActivity(), "Someone guessed your word!", Toast.LENGTH_LONG).show();
                                FragmentManager fm = getActivity().getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                StartFragment sf = new StartFragment();
                                ft.replace(R.id.main_activity_container, sf);
                                ft.addToBackStack(null);
                                setWin.removeEventListener(winCheck);
                                ft.commit();
                            } catch (Throwable e) {
                                Log.i("Error: ", " -- In DrawFragment: " + e.toString());
                            }

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



    ////////// Set word //////////
    public void setWords() {
        //animals 18
        words.add("cat");
        words.add("dog");
        words.add("rabbit");
        words.add("spider");
        words.add("fish");
        words.add("house");
        words.add("bee");
        words.add("ant");
        words.add("mouse");
        words.add("bird");
        words.add("giraffe");
        words.add("elephant");
        words.add("bear");
        words.add("monkey");
        words.add("snake");
        words.add("ladybug");
        words.add("alien");
        words.add("unicorn");
        //nature 23
        words.add("flower");
        words.add("tree");
        words.add("grass");
        words.add("sun");
        words.add("sky");
        words.add("leaf");
        words.add("mountain");
        words.add("wave");
        words.add("rain");
        words.add("tornado");
        words.add("thunder");
        words.add("rainbow");
        words.add("sunset");
        words.add("sunrise");
        words.add("forest");
        words.add("waterfall");
        words.add("lake");
        words.add("sea");
        words.add("cloud");
        words.add("bridge");
        words.add("rose");
        words.add("drop");
        words.add("dandelion");
        //vehicles 23
        words.add("car");
        words.add("bus");
        words.add("train");
        words.add("airplane");
        words.add("bicycle");
        words.add("surfboard");
        words.add("skateboard");
        words.add("helicopter");
        words.add("parachute");
        words.add("wheel chair");
        words.add("inlines");
        words.add("segway");
        words.add("kickboard");
        words.add("motorcycle");
        words.add("vespa");
        words.add("skates");
        words.add("caravan");
        words.add("ambulance");
        words.add("truck");
        words.add("police car");
        words.add("tank");
        words.add("jetski");
        words.add("hot-air balloon");

        //food 23
        words.add("candy");
        words.add("cookie");
        words.add("pizza");
        words.add("tomatoe");
        words.add("apple");
        words.add("chocolate");
        words.add("banana");
        words.add("cucumber");
        words.add("sandwich");
        words.add("lollipop");
        words.add("cake");
        words.add("chesse");
        words.add("corn");
        words.add("ketchup");
        words.add("soda");
        words.add("sushi");
        words.add("pancakes");
        words.add("waffle");
        words.add("hot dog");
        words.add("donut");
        words.add("broccoli");
        words.add("coffee");
        words.add("taco");
        //clothing 16
        words.add("pants");
        words.add("jacket");
        words.add("umbrella");
        words.add("socks");
        words.add("skirt");
        words.add("necklace");
        words.add("sunglasses");
        words.add("shorts");
        words.add("shoes");
        words.add("hat");
        words.add("scarf");
        words.add("gloves");
        words.add("cap");
        words.add("bikini");
        words.add("vest");
        words.add("sandals");

        //devices 17
        words.add("smartphone");
        words.add("tv");
        words.add("computer");
        words.add("laptop");
        words.add("harddrive");
        words.add("radio");
        words.add("tablet");
        words.add("cable");
        words.add("gameboy");
        words.add("game console");
        words.add("headphones");
        words.add("cd");
        words.add("watch");
        words.add("calculator");
        words.add("camera");
        words.add("selfie stick");
        words.add("keyboard");
        //buildings 10
        words.add("eiffel tower");
        words.add("turning torso");
        words.add("big ben");
        words.add("pyramid");
        words.add("colosseum");
        words.add("great wall of china");
        words.add("white house");
        words.add("triumphal arch");
        words.add("christ the redeemer");
        words.add("petronas twin towers");

        //tools 10
        words.add("hammer");
        words.add("screwdriver");
        words.add("saw");
        words.add("scissor");
        words.add("pen");
        words.add("knife");
        words.add("spoon");
        words.add("fork");
        words.add("pencil");
        words.add("gun");
        //body parts 11
        words.add("eye");
        words.add("foot");
        words.add("elbow");
        words.add("toe");
        words.add("hair");
        words.add("ear");
        words.add("nose");
        words.add("knee");
        words.add("lips");
        words.add("eyebrows");
        //furniture 12
        words.add("table");
        words.add("lamp");
        words.add("chair");
        words.add("carpet");
        words.add("stove");
        words.add("sofa");
        words.add("bed");
        words.add("pillow");
        words.add("toilet");
        words.add("shower");
        words.add("painting");
        words.add("bathtub");
        //sports 13
        words.add("football");
        words.add("basketball");
        words.add("ice hockey");
        words.add("handball");
        words.add("tennis");
        words.add("golf");
        words.add("floorball");
        words.add("baseball");
        words.add("polo");
        words.add("swimming");
        words.add("horseback riding");
        words.add("running");
        words.add("high jump");

        //logo 5
        words.add("facebook");
        words.add("twitter");
        words.add("nike");
        words.add("puma");
        words.add("apple");

        //Verbs 12
        words.add("paint");
        words.add("run");
        words.add("swim");
        words.add("fly");
        words.add("walk");
        words.add("jump");
        words.add("sing");
        words.add("kiss");
        words.add("talk");
        words.add("listen");
        words.add("write");
        words.add("fight");






            }





    public void setRandomWord(){

        setWords();
        String previouslySelectedWord ="";
        Random r = new Random();

        int x = r.nextInt(words.size() - 0 + 1);

        String selectedWord = words.get(x).toString();


        if(selectedWord == previouslySelectedWord) {

        }



        previouslySelectedWord = selectedWord;

        ////// Push "selectedWord" to firebase
        Firebase setWord = new Firebase(Constants.FIREBASE_URL).child("selectedword");
        setWord.setValue(selectedWord);





    }

    public void welcomeNewDrawer(){

        Chat chat = new Chat("Someone started drawing.", "@DrawStuff");
        fbChat.push().setValue(chat);
    }



}

