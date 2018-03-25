package com.example.praha.telt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ChangePassword extends AppCompatActivity {

    EditText usernameEt,old_pass,new_pass,reenter_pass;
    Toolbar toolbar;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_changepassword);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            usernameEt = (EditText)findViewById(R.id.etUsername);
            old_pass = (EditText)findViewById(R.id.etPassword);
            new_pass = (EditText)findViewById(R.id.etNewPassword);
            reenter_pass = (EditText)findViewById(R.id.etReenterPassword);

    }

    public void OnConfirm(View view){

        String username = usernameEt.getText().toString();
        String old_password = old_pass.getText().toString();
        String new_password = new_pass.getText().toString();
        String repassword = reenter_pass.getText().toString();

        BackgroundWorkerChangePass backgroundWorker = new BackgroundWorkerChangePass(this);
        backgroundWorker.execute(username, old_password,new_password, repassword);

    }

    public void OnCancel(View view){
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
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

class BackgroundWorkerChangePass extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    ProgressDialog loading;


    BackgroundWorkerChangePass(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(context, "Please Wait", null, true, true);
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("ChangePassword Status");
    }

    @Override
    protected String doInBackground(String... params) {

        String register_url = "http://textextraction.96.lt/changepassword.php";

        try {

            String name = params[0];
            String old_pass = params[1];
            String password = params[2];
            String re_password = params[3];

            URL url = new URL(register_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                    + URLEncoder.encode("old_pass", "UTF-8") + "=" + URLEncoder.encode(old_pass, "UTF-8") + "&"
                    +URLEncoder.encode("new_pass", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                    + URLEncoder.encode("reenter_pass", "UTF-8") + "=" + URLEncoder.encode(re_password, "UTF-8") ;
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

            String result = "";
            String line = "";

            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }

            result = sb.toString();
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        loading.dismiss();
        if(result.equalsIgnoreCase("Record updated successfully")) {
            alertDialog.setMessage("Password changed!");
            alertDialog.show();
            Intent i = new Intent(context.getApplicationContext(),MainActivity.class);
            context.startActivity(i);

        }
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}


