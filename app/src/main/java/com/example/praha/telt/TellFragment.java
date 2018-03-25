package com.example.praha.telt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class TellFragment extends Fragment {

    Button sms,mail,whatsapp;
    public TellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v1= inflater.inflate(R.layout.fragment_tell, container, false);

        sms = (Button)v1.findViewById(R.id.btnsms);
        mail = (Button)v1.findViewById(R.id.btnmail);
        whatsapp = (Button)v1.findViewById(R.id.btnwhatsapp);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.setData(Uri.parse("smsto:"));
                smsIntent.putExtra("sms_body","Check out the Extract and Translate app for your smartphone. Download it today.");
                startActivity(smsIntent);

            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO = {""};
                String[] CC = {""};
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out Extract and Translate App");
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey,\n I just downloaded Extract and Translate app on my smartphone. \n It allows to extract text from images and convert them to various other languages.It also provides the option for speech.\n Downlaod it today!");

                try {
                    // the user can choose the email client
                    startActivity(Intent.createChooser(emailIntent, "Choose an email client from..."));

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "No email client installed.",Toast.LENGTH_LONG).show();
                }
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = "Check out the Extract and Translate app for your smartphone. Download it today.";
                waIntent.setPackage("com.whatsapp");
                if (waIntent != null) {
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } else {
                    Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        return v1;
    }


}
