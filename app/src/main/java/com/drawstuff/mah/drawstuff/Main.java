package com.drawstuff.mah.drawstuff;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.Draw.DrawingView;
import com.drawstuff.mah.drawstuff.Draw.StartFragment;
import com.firebase.client.Firebase;


public class Main extends Activity {

    private DrawingView mDrawingView;

    //final View drawView = (View) mDrawingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);  //This is empty to makr place for fragments
        //So put in the startfragment
        Firebase.setAndroidContext(this);
        if (savedInstanceState == null) {  //If savedInstanceState not is null then we already have the activity fx if we were interupted by a phonecall and  b
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction(); //Start adding the fragment by getting the manager for handling this
            StartFragment sf = new StartFragment(); //Create the fragment
            ft.add(R.id.main_activity_container, sf); //And add it to the manager
            ft.commit(); //OK go ahead do your transaction nothing really happens until here
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        menu.add(0, Constants.COLOR_MENU_ID, 0, "Color").setShortcut('3', 'c');
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }



}
