package com.first.akashshrivastava.showernow;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by akashshrivastava on 03/11/16.
 */

public class fragment_D extends android.support.v4.app.Fragment implements View.OnClickListener {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);

        Button upButton = (Button) view.findViewById(R.id.nextPage_d);
        upButton.setOnClickListener(this);

        Button previousButton = (Button) view.findViewById(R.id.previousPage_d);
       previousButton.setOnClickListener(this);
        return view;

    }




    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.previousPage_d) {
            ((MainActivity)getActivity()).setPreviousPage();
        }
        else if (view.getId() == R.id.nextPage_d){
            ((MainActivity)getActivity()).setNextPage();
        }
    }

}
