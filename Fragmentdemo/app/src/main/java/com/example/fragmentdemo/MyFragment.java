package com.example.fragmentdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, container, false);

        TextView textView = view.findViewById(R.id.textView);
        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(v -> textView.setText("Fragment Button Clicked!"));

        return view;
    }
}