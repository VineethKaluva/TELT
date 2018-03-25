package com.example.praha.telt;

import android.app.SearchManager;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
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


import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;

public class TextExtraction extends AppCompatActivity {

    Toolbar toolbar;
    TextView dispText;
    String outputPath;
    Spinner spinnerLanguage,spinnerTo;
    String text,input;
    Language from,to;

    TextToSpeech ttsobject;
    int result,pos;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Copy");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Paste");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Copy"){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(dispText.getText());
        }
        else if(item.getTitle()=="Paste"){
            ClipboardManager clipMan = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            dispText.setText(clipMan.getText());
        }else{
            return false;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textextraction);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dispText = (TextView)findViewById(R.id.tvExtractedTextDisp);
        dispText.setTextIsSelectable(true);
        dispText.setMovementMethod(new ScrollingMovementMethod());

        dispText.setCursorVisible(true);
        registerForContextMenu(dispText);

        spinnerLanguage = (Spinner)findViewById(R.id.spinner);
        spinnerTo = (Spinner)findViewById(R.id.tospinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);


        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    from = Language.ENGLISH;
                }
                if (position == 1) {
                    from = Language.ENGLISH;
                }
                if (position == 2) {
                    from = Language.FRENCH;
                }
                if (position == 3) {
                    from = Language.GERMAN;
                }
                if (position == 4) {
                    from = Language.ITALIAN;
                }
                if (position == 5) {
                    from = Language.JAPANESE;
                }
                if (position == 6) {
                    from = Language.KOREAN;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(adapter2);

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    to = Language.ENGLISH;
                    pos = position;
                }
                if (position == 1) {
                    to = Language.ENGLISH;
                    pos = position;
                }
                if (position == 2) {
                    to = Language.FRENCH;
                    pos = position;
                }
                if (position == 3) {
                    to = Language.GERMAN;
                    pos = position;
                }
                if (position == 4) {
                    to = Language.ITALIAN;
                    pos = position;
                }
                if (position == 5) {
                    to = Language.JAPANESE;
                    pos = position;
                }
                if (position == 6) {
                    to = Language.KOREAN;
                    pos = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ttsobject = new TextToSpeech(TextExtraction.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){

                    spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                result = ttsobject.setLanguage(Locale.UK);
                            }
                            if (position == 1) {
                                result = ttsobject.setLanguage(Locale.US);
                            }
                            if (position == 2) {
                                result = ttsobject.setLanguage(Locale.FRENCH);
                            }
                            if (position == 3) {
                                result = ttsobject.setLanguage(Locale.GERMAN);
                            }
                            if (position == 4) {
                                result = ttsobject.setLanguage(Locale.ITALIAN);
                            }
                            if (position == 5) {
                                result = ttsobject.setLanguage(Locale.JAPANESE);
                            }
                            if (position == 6) {
                                result = ttsobject.setLanguage(Locale.KOREAN);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                else {
                    Toast.makeText(getApplicationContext(), "feature not supported in your device", Toast.LENGTH_SHORT).show();

                }
            }
        });

        String imageUrl = "unknown";

        Bundle extras = getIntent().getExtras();
        if( extras != null) {
            imageUrl = extras.getString("IMAGE_PATH" );
            outputPath = extras.getString( "RESULT_PATH" );
        }

        // Starting recognition process
        new AsyncProcessTask(this).execute(imageUrl, outputPath);

    }

    public void updateResults(Boolean success) {
        if (!success)
            return;
        try {
            StringBuffer contents = new StringBuffer();

            FileInputStream fis = openFileInput(outputPath);
            try {
                Reader reader = new InputStreamReader(fis, "UTF-8");
                BufferedReader bufReader = new BufferedReader(reader);
                String text = null;
                while ((text = bufReader.readLine()) != null) {
                    contents.append(text).append(System.getProperty("line.separator"));
                }
            } finally {
                fis.close();
            }

            displayMessage(contents.toString());
        } catch (Exception e) {
            displayMessage("Error: " + e.getMessage());
        }
    }

    public void displayMessage( String text )
    {
        dispText.post(new MessagePoster(text));
    }


    class MessagePoster implements Runnable {
        public MessagePoster( String message )
        {
            _message = message;
        }

        public void run() {
            dispText.append( _message + "\n" );
        }

        private final String _message;
    }


    public void OnTranslate(final View view){

        input =  dispText.getText().toString();
        class bgStuff extends AsyncTask<Void, Void, Void> {
            String translatedText = "";


            @Override
            protected Void doInBackground(Void... params) {
                // TODO Auto-generated method stub

                try {
                    String text = input;
                    translatedText = translate(text);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    translatedText = e.toString();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // TODO Auto-generated method stub
              //  Toast.makeText(getApplicationContext(),"onpost :"+translatedText,Toast.LENGTH_LONG).show();
                Intent next = new Intent(view.getContext(), TranslatedText.class);
                next.putExtra("Trans_text", translatedText);
                next.putExtra("extract",input);
                next.putExtra("mediapos",0);
                startActivity(next);
                super.onPostExecute(result);
            }

        }


        new bgStuff().execute();
    }

    public String translate(String text) throws Exception{

        Translate.setClientId("PrahalyaReddy_19");
        Translate.setClientSecret("OQaiExWAhangrBg542gSWkbPHFkVPPjcVw/Em15xLfE=");

        String translatedText = "";

        translatedText = Translate.execute(text,Language.AUTO_DETECT,to);
        return translatedText;
    }

    public void OnSpeak(View view){

        if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(getApplicationContext(),"feature not supported in your device",Toast.LENGTH_SHORT).show();

        }
        else {
            text = dispText.getText().toString();
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
            String term =dispText.getText().toString();
            intent.putExtra(SearchManager.QUERY, term);
            startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }
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
