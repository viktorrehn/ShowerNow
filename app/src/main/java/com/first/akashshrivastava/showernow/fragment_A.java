package com.first.akashshrivastava.showernow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by akashshrivastava on 31/07/16.
 */
public class fragment_A extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        Button upButton = (Button) view.findViewById(R.id.nextPage_a);
        upButton.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View view) {
        ((MainActivity)getActivity()).setNextPage();
    }
}
