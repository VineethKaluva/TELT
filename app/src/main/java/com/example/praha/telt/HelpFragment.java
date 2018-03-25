package com.example.praha.telt;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {
   // String[] help={"User Manual","FAQs","Any Suggestions?","Terms and Conditions"};
    Button usermanual,faq,suggestreport,tandc;


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_help, container, false);

        usermanual = (Button)v.findViewById(R.id.btnusermanual);
        faq = (Button)v.findViewById(R.id.btnfaq);
        suggestreport = (Button)v.findViewById(R.id.btnsuggestreport);
        tandc = (Button)v.findViewById(R.id.btntandc);

        usermanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Usermanual.class));
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
                webPageIntent.setData(Uri.parse("http://textextraction.96.lt/faq.html"));
                startActivity(webPageIntent);
            }
        });

        tandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
                webPageIntent.setData(Uri.parse("http://textextraction.96.lt/terms.html"));
                startActivity(webPageIntent);
            }
        });


        suggestreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Suggest/Report");
               // builder.setMessage("Attention");
                builder.setPositiveButton("Send a suggestion",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
                                webPageIntent.setData(Uri.parse("http://textextraction.96.lt/suggest.html"));
                                startActivity(webPageIntent);

                            }
                        });

                builder.setNeutralButton("Report an error",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
                                webPageIntent.setData(Uri.parse("http://textextraction.96.lt/report.html"));
                                startActivity(webPageIntent);

                            }
                        });
                builder.show();
            }
        });


    /*   ArrayAdapter adapter=new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_list_item_1,help);
        ListView list=(ListView) v.findViewById(R.id.usermanual);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View parentRow = (View) v.getParent();
                ListView listView = (ListView) parentRow.getParent();
                position = listView.getPositionForView(parentRow);

                switch (position) {
                    case 0:

                        break;
                    case 1:


                        break;
                    case 2:

                    case 3:

                        break;
                }

            }
        }); */
        return v;
    }
}
