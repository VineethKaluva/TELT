package com.example.praha.telt;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignOutFragment extends Fragment {
    Button onYes ,onNo;
    AlertDialog alertDialog;
    SessionManager session;

    public SignOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View signoutView = inflater.inflate(R.layout.fragment_sign_out, container, false);
        session = new SessionManager(getContext().getApplicationContext());

        alertDialog=new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("SignOut Status");

        onYes = (Button)signoutView.findViewById(R.id.btnYes);
        onNo = (Button)signoutView.findViewById(R.id.btnNo);

        onYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setMessage("Your account has been signed out");
                alertDialog.show();
                session.logoutUser();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // getFragmentManager().popBackStackImmediate();
                startActivity(intent);
            }

        });

        onNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setMessage("Your account is active");
                alertDialog.show();
                Intent intent = new Intent(getActivity(), HomePage.class);
                startActivity(intent);
            }
        });

        return signoutView;
    }

}
