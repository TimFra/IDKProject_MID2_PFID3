package com.drawstuff.mah.drawstuff;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drawstuff.mah.drawstuff.Constants.Constants;
import com.drawstuff.mah.drawstuff.Draw.DrawFragment;
import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    public Firebase currentDrawer;
    public Firebase currentWord;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        currentDrawer = new Firebase(Constants.FIREBASE_URL).child("currentDrawer");
        currentWord = new Firebase(Constants.FIREBASE_URL).child("selectedword");

        currentDrawer.setValue(Constants.userName);
        currentWord.setValue("9849782562482464");



        getActivity().getActionBar().setTitle("DrawStuff: Choose Category");

        Button cat1 = (Button) v.findViewById(R.id.animalBtn);
        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Constants.category = 1;

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DrawFragment df = new DrawFragment();
                    ft.replace(R.id.fragment_start, df, "drawTag");
                    ft.addToBackStack(null);
                    fm.popBackStack();
                    ft.commit();

                } catch (Throwable e){
                    Log.i("Category error", "FragmentTransaction");
                }

            }
        });

        Button cat2 = (Button) v.findViewById(R.id.natureBtn);
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Constants.category = 2;

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DrawFragment df = new DrawFragment();
                    ft.replace(R.id.fragment_start, df, "drawTag");
                    ft.addToBackStack(null);
                    fm.popBackStack();
                    ft.commit();

                } catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        Button cat3 = (Button) v.findViewById(R.id.foodBtn);
        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                Constants.category = 3;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df,"drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        Button cat4 = (Button) v.findViewById(R.id.toolsBtn);
        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Constants.category = 4;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df,"drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        Button cat5 = (Button) v.findViewById(R.id.devicesBtn);
        cat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Constants.category = 5;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df,"drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        Button cat6 = (Button) v.findViewById(R.id.furnitureBtn);
        cat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Constants.category = 6;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df,"drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });


        Button cat7 = (Button) v.findViewById(R.id.clothingBtn);
        cat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Constants.category = 7;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df,"drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        Button cat8 = (Button) v.findViewById(R.id.bodyBtn);
        cat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Constants.category = 8;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df,"drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        Button cat9 = (Button) v.findViewById(R.id.vehiclesBtn);
        cat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Constants.category = 9;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df,"drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        Button cat10 = (Button) v.findViewById(R.id.sportsBtn);
        cat10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Constants.category = 10;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df,"drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        Button cat11 = (Button) v.findViewById(R.id.logosBtn);
        cat11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Constants.category = 11;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df,"drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        Button cat12 = (Button) v.findViewById(R.id.verbsBtn);
        cat12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Constants.category = 12;

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DrawFragment df = new DrawFragment();
                ft.replace(R.id.fragment_start, df, "drawTag");
                ft.addToBackStack(null);
                fm.popBackStack();
                ft.commit();

            }catch (Throwable e) {
                    Log.i("Category error", "FragmentTransaction");
                }
            }
        });

        return v;
    }


}
