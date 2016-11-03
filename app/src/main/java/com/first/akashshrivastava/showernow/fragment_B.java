package com.first.akashshrivastava.showernow;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by akashshrivastava on 31/07/16.
 */
public class fragment_B extends Fragment implements View.OnClickListener {

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        Button nextButton = (Button) view.findViewById(R.id.nextPage_b);
        nextButton.setOnClickListener(this);

        Button previousButton = (Button) view.findViewById(R.id.previousPage_b);
        previousButton.setOnClickListener(this);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_gender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if(checkedId == R.id.radio_male){
                    ((MainActivity) getActivity()).gender = "male";

                    //Implement logic to change picture on SHowerActivity, for male

                }else if(checkedId == R.id.radio_female){
                    ((MainActivity) getActivity()).gender = "female";

                    //Implement logic to change picture on SHowerActivity, for female

                }else if(checkedId == R.id.radio_other){
                    ((MainActivity) getActivity()).gender = "other";
                    Toast.makeText(getActivity(), "This option makes it harder for us to monitor",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            YoYo.with(Techniques.FadeIn)
                    .duration(3000)
                    .playOn(getView().findViewById(R.id.imageView7));
            YoYo.with(Techniques.FadeIn)
                    .duration(3000)
                    .playOn(getView().findViewById(R.id.imageView8));
            YoYo.with(Techniques.FadeIn)
                    .duration(3000)
                    .playOn(getView().findViewById(R.id.imageView9));
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextPage_b) {
            if(((MainActivity) getActivity()).gender != null) {

                //Code for changing the image on shower activity


                mDatabaseReference.child("User").child(mFirebaseAuth.getCurrentUser().getUid()).child("gender").setValue(((MainActivity) getActivity()).gender);
                ((MainActivity) getActivity()).setNextPage();
            }else{
                Toast.makeText(getActivity(), "Please select an option",
                        Toast.LENGTH_LONG).show();
            }
        }else if(view.getId() == R.id.previousPage_b){
            ((MainActivity) getActivity()).setPreviousPage();
        }
    }
}

//Pojo class...
