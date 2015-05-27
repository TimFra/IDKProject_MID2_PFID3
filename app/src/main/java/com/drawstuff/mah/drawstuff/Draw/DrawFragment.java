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

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.drawstuff.mah.drawstuff.Chat.Chat;

import com.drawstuff.mah.drawstuff.Constants.Constants;

import com.drawstuff.mah.drawstuff.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawFragment extends Fragment{


    public DrawFragment() {
        // Required empty public constructor
    }
    private static final int About = Menu.FIRST;
    private DrawingView mDrawingView;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private Firebase word;
    private Firebase setWin;
    private Firebase fbChat;
    private ValueEventListener winCheck;
    private ValueEventListener containerListener;
    private ValueEventListener checkTimeOut;
    private Firebase clearDraw;
    private Firebase chicken;
    private Firebase currentPlayer;
    private Firebase timeOut;



    ArrayList<String> words = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        fbChat = new Firebase(Constants.FIREBASE_URL).child("chat");




        mFirebaseRef = new Firebase(Constants.FIREBASE_URL).child("draw");
        mDrawingView = new DrawingView(getActivity(), mFirebaseRef);
        word = new Firebase(Constants.FIREBASE_URL);
        setWin = new Firebase(Constants.FIREBASE_URL).child("gameInProgress");
        chicken = new Firebase(Constants.FIREBASE_URL).child("chicken");
        currentPlayer = new Firebase(Constants.FIREBASE_URL).child("currentDrawer");

        chicken.setValue("false");
        currentPlayer.setValue(Constants.userName);

        timeOut = new Firebase(Constants.FIREBASE_URL).child("timedOut");
        checkTimeOut = timeOut.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("true")){
                    try {
                        Toast.makeText(getActivity(), "You timed out.", Toast.LENGTH_LONG).show();
                        setWin.setValue("false");
                        FragmentManager fm = getActivity().getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        StartFragment sf = new StartFragment();
                        ft.replace(R.id.main_activity_container, sf, "startFragment");
                        ft.addToBackStack(null);
                        fm.popBackStack();
                        timeOut.removeEventListener(checkTimeOut);
                        ft.commit();
                    } catch (Throwable e){
                        Log.i("Error","DrawFragment.java: TimeOut listener: "+e);
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


                    setRandomWord();
                    welcomeNewDrawer();
                    winChecker();

        container.addView(mDrawingView);
        View v = inflater.inflate(R.layout.fragment_draw, container, false);

        containerListener = setWin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("false")){
                    container.removeView(mDrawingView);
                    setWin.removeEventListener(containerListener);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Setup color
        Firebase currentColor = new Firebase(Constants.FIREBASE_URL).child("currentColor");
        currentColor.setValue("black");

        // Setup buttons

        ImageButton trashcan;


        trashcan = (ImageButton) v.findViewById(R.id.binButton);
        clearDraw = new Firebase(Constants.FIREBASE_URL).child("draw");
        trashcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDraw.removeValue();

                // Maste tomma mdrawing view


            }
        });

        ImageButton rubber;


        rubber = (ImageButton) v.findViewById(R.id.rubberButton);
        rubber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setColor(0xFFffffff);
                Firebase currentColor = new Firebase(Constants.FIREBASE_URL).child("currentColor");
                currentColor.setValue("white");

            }
        });



        final Button blackButton;
        blackButton = (Button) v.findViewById(R.id.blackButton);

        final Button redButton;
        redButton = (Button) v.findViewById(R.id.redButton);

        final Button yellowButton;
        yellowButton = (Button) v.findViewById(R.id.yellowButton);

        final Button greenButton;
        greenButton = (Button) v.findViewById(R.id.greenButton);

        final Button blueButton;
        blueButton = (Button) v.findViewById(R.id.blueButton);

        final Button purpleButton;
        purpleButton = (Button) v.findViewById(R.id.purpleButton);

        final Button pinkButton;
        pinkButton = (Button) v.findViewById(R.id.pinkButton);


        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setColor(0xFF000000);
                Firebase currentColor = new Firebase(Constants.FIREBASE_URL).child("currentColor");
                currentColor.setValue("black");

                blackButton.setBackground(getResources().getDrawable(R.drawable.button_style_blackbutton_pressed));


                    redButton.setBackground(getResources().getDrawable(R.drawable.button_style_redbutton));



                    yellowButton.setBackground(getResources().getDrawable(R.drawable.button_style_yellowbutton));



                    greenButton.setBackground(getResources().getDrawable(R.drawable.button_style_greenbutton));


                    blueButton.setBackground(getResources().getDrawable(R.drawable.button_style_bluebutton));



                    purpleButton.setBackground(getResources().getDrawable(R.drawable.button_style_purplebutton));



                    pinkButton.setBackground(getResources().getDrawable(R.drawable.button_style_pinkbutton));



            }
        });


        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setColor(0xFFC04545);
                Firebase currentColor = new Firebase(Constants.FIREBASE_URL).child("currentColor");
                currentColor.setValue("red");
                redButton.setBackground(getResources().getDrawable(R.drawable.button_style_redbutton_pressed));
                blackButton.setBackground(getResources().getDrawable(R.drawable.button_style_blackbutton));
                yellowButton.setBackground(getResources().getDrawable(R.drawable.button_style_yellowbutton));
                greenButton.setBackground(getResources().getDrawable(R.drawable.button_style_greenbutton));
                blueButton.setBackground(getResources().getDrawable(R.drawable.button_style_bluebutton));
                purpleButton.setBackground(getResources().getDrawable(R.drawable.button_style_purplebutton));
                pinkButton.setBackground(getResources().getDrawable(R.drawable.button_style_pinkbutton));


            }
        });


        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setColor(0xFFC0B945);
                Firebase currentColor = new Firebase(Constants.FIREBASE_URL).child("currentColor");
                currentColor.setValue("yellow");

                yellowButton.setBackground(getResources().getDrawable(R.drawable.button_style_yellowbutton_pressed));
                    redButton.setBackground(getResources().getDrawable(R.drawable.button_style_redbutton));
                    blackButton.setBackground(getResources().getDrawable(R.drawable.button_style_blackbutton));
                    greenButton.setBackground(getResources().getDrawable(R.drawable.button_style_greenbutton));
                    blueButton.setBackground(getResources().getDrawable(R.drawable.button_style_bluebutton));
                    purpleButton.setBackground(getResources().getDrawable(R.drawable.button_style_purplebutton));
                    pinkButton.setBackground(getResources().getDrawable(R.drawable.button_style_pinkbutton));


            }
        });


        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setColor(0xFF7FC045);
                Firebase currentColor = new Firebase(Constants.FIREBASE_URL).child("currentColor");
                currentColor.setValue("green");


                yellowButton.setBackground(getResources().getDrawable(R.drawable.button_style_yellowbutton));
                redButton.setBackground(getResources().getDrawable(R.drawable.button_style_redbutton));
                blackButton.setBackground(getResources().getDrawable(R.drawable.button_style_blackbutton));
                greenButton.setBackground(getResources().getDrawable(R.drawable.button_style_greenbutton_pressed));
                blueButton.setBackground(getResources().getDrawable(R.drawable.button_style_bluebutton));
                purpleButton.setBackground(getResources().getDrawable(R.drawable.button_style_purplebutton));
                pinkButton.setBackground(getResources().getDrawable(R.drawable.button_style_pinkbutton));

            }
        });


        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setColor(0xFF459FC0);
                Firebase currentColor = new Firebase(Constants.FIREBASE_URL).child("currentColor");
                currentColor.setValue("blue");

                yellowButton.setBackground(getResources().getDrawable(R.drawable.button_style_yellowbutton));
                redButton.setBackground(getResources().getDrawable(R.drawable.button_style_redbutton));
                blackButton.setBackground(getResources().getDrawable(R.drawable.button_style_blackbutton));
                greenButton.setBackground(getResources().getDrawable(R.drawable.button_style_greenbutton));
                blueButton.setBackground(getResources().getDrawable(R.drawable.button_style_bluebutton_pressed));
                purpleButton.setBackground(getResources().getDrawable(R.drawable.button_style_purplebutton));
                pinkButton.setBackground(getResources().getDrawable(R.drawable.button_style_pinkbutton));

            }
        });


        purpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setColor(0xFF9645C0);
                Firebase currentColor = new Firebase(Constants.FIREBASE_URL).child("currentColor");
                currentColor.setValue("purple");


                yellowButton.setBackground(getResources().getDrawable(R.drawable.button_style_yellowbutton));
                redButton.setBackground(getResources().getDrawable(R.drawable.button_style_redbutton));
                blackButton.setBackground(getResources().getDrawable(R.drawable.button_style_blackbutton));
                greenButton.setBackground(getResources().getDrawable(R.drawable.button_style_greenbutton));
                blueButton.setBackground(getResources().getDrawable(R.drawable.button_style_bluebutton));
                purpleButton.setBackground(getResources().getDrawable(R.drawable.button_style_purplebutton_pressed));
                pinkButton.setBackground(getResources().getDrawable(R.drawable.button_style_pinkbutton));

            }
        });


        pinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setColor(0xFFC04596);
                Firebase currentColor = new Firebase(Constants.FIREBASE_URL).child("currentColor");
                currentColor.setValue("pink");


                yellowButton.setBackground(getResources().getDrawable(R.drawable.button_style_yellowbutton));
                redButton.setBackground(getResources().getDrawable(R.drawable.button_style_redbutton));
                blackButton.setBackground(getResources().getDrawable(R.drawable.button_style_blackbutton));
                greenButton.setBackground(getResources().getDrawable(R.drawable.button_style_greenbutton));
                blueButton.setBackground(getResources().getDrawable(R.drawable.button_style_bluebutton));
                purpleButton.setBackground(getResources().getDrawable(R.drawable.button_style_purplebutton));
                pinkButton.setBackground(getResources().getDrawable(R.drawable.button_style_pinkbutton_pressed));

            }
        });

        return v;
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
                    //Toast.makeText(getActivity(), "Connected to Firebase", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(),"Your word to draw is: " + snapshot.getValue().toString(), Toast.LENGTH_LONG).show(); //TODO: This needs a try catch or null object reference.
                            getActivity().getActionBar().setTitle("DrawStuff: " + snapshot.getValue().toString());
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
                                ft.replace(R.id.main_activity_container, sf, "startFragment");
                                ft.addToBackStack(null);
                                fm.popBackStack();
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
        //animals
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
        words.add("dolphin");
        words.add("shark");

        //nature
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

        //vehicles
        words.add("car");
        words.add("bus");
        words.add("train");
        words.add("airplane");
        words.add("bicycle");
        words.add("surfboard");
        words.add("skateboard");
        words.add("helicopter");
        words.add("parachute");
        words.add("inlines");
        words.add("segway");
        words.add("kickboard");
        words.add("motorcycle");
        words.add("vespa");
        words.add("skates");
        words.add("caravan");
        words.add("ambulance");
        words.add("truck");
        words.add("tank");
        words.add("jetski");

        //food
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
        words.add("donut");
        words.add("broccoli");
        words.add("coffee");
        words.add("taco");
        //clothing
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
        words.add("heels");
        words.add("dress");
        words.add("suit");
        words.add("hoodie");
        words.add("shirt");
        words.add("pyjamas");

        //devices
        words.add("smartphone");
        words.add("tv");
        words.add("computer");
        words.add("laptop");
        words.add("harddrive");
        words.add("radio");
        words.add("tablet");
        words.add("cable");
        words.add("gameboy");
        words.add("headphones");
        words.add("cd");
        words.add("watch");
        words.add("calculator");
        words.add("camera");
        words.add("keyboard");
        words.add("fan");
        words.add("battery");


        //tools
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
        words.add("spade");
        words.add("bucket");
        words.add("brush");
        words.add("whistle");
        words.add("keys");
        words.add("flashlight");
        words.add("saucepan");
        words.add("teapot");
        words.add("plate");
        words.add("glass");
        //body parts
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
        words.add("finger");
        words.add("arm");
        words.add("hand");
        words.add("tongue");
        words.add("chest");
        words.add("tooth");
        words.add("face");
        words.add("leg");
        words.add("stomach");
        words.add("heart");
        //furniture
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
        words.add("armchair");
        words.add("shelf");
        words.add("wardrobe");
        words.add("chandelier");
        words.add("window");
        words.add("mirror");
        words.add("door");
        words.add("curtain");
        //sports
        words.add("football");
        words.add("basketball");
        words.add("handball");
        words.add("tennis");
        words.add("golf");
        words.add("floorball");
        words.add("baseball");
        words.add("polo");
        words.add("swimming");
        words.add("running");
        words.add("badminton");
        words.add("boxing");
        words.add("volleyball");
        words.add("ballet");
        words.add("sailing");
        words.add("cycling");

        //logo 5
        words.add("facebook");
        words.add("twitter");
        words.add("nike");
        words.add("puma");
        words.add("apple");

        //Verbs
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
        words.add("cry");
        words.add("laugh");
        words.add("drive");
        words.add("climb");
        words.add("dance");
        words.add("look");
        words.add("clap");
        words.add("sleep");






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

        Chat chat = new Chat(Constants.userName +" started drawing.", "@DrawStuff");
        fbChat.push().setValue(chat);
    }






}

