package com.example.praha.telt;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import static android.content.Intent.getIntent;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView username;
    Button start;
    SessionManager session;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeView =  inflater.inflate(R.layout.fragment_home, container, false);

        session = new SessionManager(getContext().getApplicationContext());
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        start = (Button)homeView.findViewById(R.id.btnstart);
        username = (TextView)homeView.findViewById(R.id.tvUser);

        username.setText("Welcome "+name);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SelectImage.class);
                startActivity(i);
            }
        });


        return  homeView;

    }

}
