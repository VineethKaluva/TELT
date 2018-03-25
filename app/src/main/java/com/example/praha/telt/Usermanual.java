package com.example.praha.telt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;


public class Usermanual extends AppCompatActivity {

    TextView manual;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermanual);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        manual = (TextView)findViewById(R.id.tvmanual);
        manual.setText("DESCRIPTION : \n\n" +
                "The app extracts text from printed sources: documents, books, sign boards,etc. and allows to immediately translate them to other languages. \n" +
                "Take a picture of the text and after the text is extracted, you can immediately hear, translate or search it on the web. \n \n " +
                " 1. Camera focus mode should be set to macro/auto to enhance the OCR result.\n 2. The images can be oriented in any direction.\n 3. Put the FLASH OFF to avoid glare on the image.\n 4. Supported languages are : English, Spanish, French, German, Italian, Korean, Japanese.\n 5.Copy and save to clipboard is available. \n \n" +
                "How to use the app :\n\n" +
                "*  Take a picture of the document(page,instruction or any other text) \n *  Recognised text will appear on the screen with an option to select the source language and the target(translation) language. \n *  After you select the language and click on the translate button, the translated text will appear on the screen. \n *  To listen to the extracted and translated text, press the SPEAK button. \n *  To perform web search of both extracted and translted text, press the WEB SEARCH button. ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.empty_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;

    }
}