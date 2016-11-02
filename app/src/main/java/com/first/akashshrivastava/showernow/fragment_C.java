package com.first.akashshrivastava.showernow;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by akashshrivastava on 31/07/16.
 */
public class fragment_C extends Fragment implements View.OnClickListener {

    Button nextButton;
    Button previousButton;
    EditText mweight;
    EditText mheight;
    EditText mage;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);

        //Firebase Auth initialization and database
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        Button nextButton = (Button) view.findViewById(R.id.nextPage_c);
        nextButton.setOnClickListener(this);

        Button previousButton = (Button) view.findViewById(R.id.previousPage_c);
        previousButton.setOnClickListener(this);



        return view;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            YoYo.with(Techniques.FadeIn)
                    .duration(3000)
                    .playOn(getView().findViewById(R.id.imageView1));
            YoYo.with(Techniques.FadeIn)
                    .duration(3000)
                    .playOn(getView().findViewById(R.id.imageView2));
            YoYo.with(Techniques.FadeIn)
                    .duration(3000)
                    .playOn(getView().findViewById(R.id.imageView3));
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextPage_c) {

            //int weightInt = Integer.parseInt(weight.getText().toString());
            mweight = (EditText)getView().findViewById(R.id.input_weight);
            mheight = (EditText)getView().findViewById(R.id.input_height);
            mage = (EditText)getView().findViewById(R.id.input_age);

            if (mweight.getText().toString().matches("")) {

                Toast.makeText(getActivity(), "Please enter your weight in kg", Toast.LENGTH_SHORT).show();

            }else if(mheight.getText().toString().matches("")){

                Toast.makeText(getActivity(), "Please enter your height in cm", Toast.LENGTH_SHORT).show();

            }else if(mage.getText().toString().matches("")) {

                Toast.makeText(getActivity(), "Please enter your age", Toast.LENGTH_SHORT).show();

            }else{

                //Firebase entry...when non of the text items are empty..
                String newweight = mweight.getText().toString().trim();
                String newheight = mheight.getText().toString().trim();
                String newage = mage.getText().toString().trim();

                //Saves to firebase, in user and makes a child age, height, weight..
                mDatabase.child("User").child(mFirebaseAuth.getCurrentUser().getUid()).child("Age").setValue(newage);
                mDatabase.child("User").child(mFirebaseAuth.getCurrentUser().getUid()).child("Height").setValue(newheight);
                mDatabase.child("User").child(mFirebaseAuth.getCurrentUser().getUid()).child("Weight").setValue(newweight);

                Intent i = new Intent(getActivity(), ShowerActivity.class);
                startActivity(i);
            }

        }else if(view.getId() == R.id.previousPage_c){
            ((MainActivity) getActivity()).setPreviousPage();
        }
    }
}