package com.drawstuff.mah.drawstuff;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.drawstuff.mah.drawstuff.Chat.Chat;
import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.Draw.DrawingView;
import com.drawstuff.mah.drawstuff.Draw.InfoDialog;
import com.firebase.client.Firebase;


public class Main extends Activity {

    private DrawingView mDrawingView;
    private Firebase mFirebaseRef;

    //final View drawView = (View) mDrawingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        getActionBar().hide();
        setContentView(R.layout.activity_main);  //This is empty to makr place for fragments

        //So put in the startfragment
        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL).child("chat");
        if (savedInstanceState == null) {  //If savedInstanceState not is null then we already have the activity fx if we were interupted by a phonecall and  b

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction(); //Start adding the fragment by getting the manager for handling this
            SplashFragment sf = new SplashFragment(); //Creates splashscreen
            ft.add(R.id.main_activity_container, sf); //And add it to the manager
            ft.commit(); //OK go ahead do your transaction nothing really happens until here
        }
    }



public boolean onCreateOptionsMenu ( Menu menu){
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentTransaction fragm = getFragmentManager().beginTransaction();
            InfoDialog im = new InfoDialog();
            im.show(fragm, "info");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        //getFragmentManager().popBackStack();

        if(getFragmentManager().findFragmentByTag("drawTag") !=null) {
            Firebase chicken = new Firebase(Constants.FIREBASE_URL).child("quit");
            chicken.setValue("true");
            Firebase inDraw = new Firebase(Constants.FIREBASE_URL).child("gameInProgress");
            inDraw.setValue("false");
            Chat chat = new Chat(Constants.userName+" has quit.", "@DrawStuff");
            Toast.makeText(this, "You quit drawing.", Toast.LENGTH_SHORT).show();
            mFirebaseRef.push().setValue(chat);
            this.getActionBar().setTitle("DrawStuff");

            Firebase drawing = new Firebase(Constants.FIREBASE_URL).child("draw");
            drawing.removeValue();


        }
        if(getFragmentManager().findFragmentByTag("catTag") != null){
            Firebase inDraw = new Firebase(Constants.FIREBASE_URL).child("gameInProgress");
            inDraw.setValue("false");
            Toast.makeText(this, "You quit drawing.", Toast.LENGTH_SHORT).show();
            this.getActionBar().setTitle("DrawStuff");
        }
            super.onBackPressed();
        
    }



}
