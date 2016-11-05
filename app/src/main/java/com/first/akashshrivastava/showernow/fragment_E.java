package com.first.akashshrivastava.showernow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by akashshrivastava on 05/11/16.
 */

public class fragment_E  extends android.support.v4.app.Fragment implements View.OnClickListener {

    RadioButton mslim;
    RadioButton mmedium;
    RadioButton mheavy;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_e, container, false);

        //Firebase Auth initialization and database
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        mslim = (RadioButton) view.findViewById(R.id.slim);
        mslim.setOnClickListener(this);

        mmedium = (RadioButton) view.findViewById(R.id.medium);
        mmedium.setOnClickListener(this);

        mheavy = (RadioButton) view.findViewById(R.id.heavy);
        mheavy.setOnClickListener(this);

        RadioGroup mradioFluffiness = (RadioGroup) view.findViewById(R.id.radio_group_fluffiness);
        mradioFluffiness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if(checkedId == R.id.slim){
                    ((MainActivity) getActivity()).fluffiness = "Slim";

                    //Implement logic to change picture on SHowerActivity, for male

                }else if(checkedId == R.id.medium){
                    ((MainActivity) getActivity()).fluffiness = "Medium";

                    //Implement logic to change picture on SHowerActivity, for female

                }else if(checkedId == R.id.heavy){
                    ((MainActivity) getActivity()).fluffiness = "Heavy";

                }
            }
        });

        Button upButton = (Button) view.findViewById(R.id.nextPage_e);
        upButton.setOnClickListener(this);


        Button previousButton = (Button) view.findViewById(R.id.previousPage_e);
        previousButton.setOnClickListener(this);
        return view;

    }




    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.previousPage_e) {

            ((MainActivity)getActivity()).setPreviousPage();
        }
        else if (view.getId() == R.id.nextPage_e){
            if(((MainActivity) getActivity()).fluffiness != null) {

                mDatabaseReference.child("User").child(mFirebaseAuth.getCurrentUser().getUid()).child("Fluffiness").setValue(((MainActivity) getActivity()).fluffiness);

                Intent i = new Intent(getActivity(), ShowerActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(getActivity(), "Please select an option",
                        Toast.LENGTH_LONG).show();
            }




        }
    }
}
