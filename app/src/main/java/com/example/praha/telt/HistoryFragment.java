package com.example.praha.telt;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    MessageDBClass dbClass;
    TextView disp;
    SessionManager session;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View historyview =  inflater.inflate(R.layout.fragment_history, container, false);
        session = new SessionManager(getContext().getApplicationContext());
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String username = user.get(SessionManager.KEY_NAME);

        disp = (TextView)historyview.findViewById(R.id.tv);
        dbClass = new MessageDBClass(getContext().getApplicationContext());
        Cursor c=dbClass.getData();
        int count = c.getCount();
        if (count == 0){
            disp.setText("No History");
        }
        else {
            disp.setText("");
            //move cursor to first position
            c.moveToFirst();
            //fetch all data one by one
            do {
                //we can use c.getString(0) here
                //or we can get data using column index
                String name = c.getString(c.getColumnIndex(MessageDBClass.NAME));
                String extracted = c.getString(c.getColumnIndex(MessageDBClass.EXTRACT_TEXT));
                String translated = c.getString(c.getColumnIndex(MessageDBClass.TRANSLATE_TEXT));

                if(name.equals(username)) {
                    //display on text view
                    disp.append("EXTRACTED TEXT : " + extracted + "\n TRANSLATED TEXT : " + translated + "\n\n");
                    //move next position until end of the
                }
            } while (c.moveToNext());
        }
        return historyview;
    }

}
