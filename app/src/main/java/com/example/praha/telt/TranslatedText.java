package com.example.praha.telt;


import android.app.SearchManager;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;



public class TranslatedText  extends AppCompatActivity{

    Toolbar toolbar;
    TextView transText;
    String text;

    TextToSpeech ttsobject;
    int speak,musicpos;
    SessionManager session;
    MessageDBClass dbClass;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Copy");//groupId, itemId, order, title
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Copy"){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(transText.getText());
        }
       else{
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translatedtext);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        dbClass = new MessageDBClass(getApplicationContext());

        transText = (TextView)findViewById(R.id.tvExtractedTextDisp);
        transText.setTextIsSelectable(true);
        transText.setMovementMethod(new ScrollingMovementMethod());
        
        Intent intent = getIntent();
        String transtext = intent.getStringExtra("Trans_text");
        transText.setText(transtext);

        transText.setCursorVisible(true);
        registerForContextMenu(transText);


        ttsobject = new TextToSpeech(TranslatedText.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status == TextToSpeech.SUCCESS){

                    Intent q = getIntent();
                    musicpos = q.getIntExtra("mediapos",0);
                    switch (musicpos){
                        case 0:
                            speak = ttsobject.setLanguage(Locale.UK);
                            break;
                        case 1:
                            speak = ttsobject.setLanguage(Locale.US);
                            break;
                        case 2:
                            speak = ttsobject.setLanguage(Locale.FRENCH);
                            break;
                        case 3:
                            speak = ttsobject.setLanguage(Locale.GERMAN);
                            break;
                        case 4:
                            speak = ttsobject.setLanguage(Locale.ITALIAN);
                            break;
                        case 5:
                            speak = ttsobject.setLanguage(Locale.JAPANESE);
                            break;
                        case 6:
                            speak = ttsobject.setLanguage(Locale.KOREAN);
                            break;
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), "Language not supported!!", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
    public void OnSpeak(View view){

       if(speak == TextToSpeech.LANG_MISSING_DATA || speak == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(getApplicationContext(),"feature not supported in your device",Toast.LENGTH_SHORT).show();

        }
        else {
            text = transText.getText().toString();
            ttsobject.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }

    }

    @Override
    protected void onDestroy() {

        if (ttsobject != null) {
            ttsobject.stop();
            ttsobject.shutdown();
        }
        super.onDestroy();
    }

    public void OnWebSearch(View view){
        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            String term =transText.getText().toString();
            intent.putExtra(SearchManager.QUERY, term);
            startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    public void OnGoToHome(View view){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void insertToDB(View view){

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        String translated = transText.getText().toString();
        Intent i = getIntent();
        String extracted = i.getStringExtra("extract");
        dbClass.insert(name, extracted, translated);
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
