package com.example.praha.telt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


public class DeleteAccount  extends AppCompatActivity{
    Toolbar toolbar;
    EditText usernameEt,mailEt;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteaccount);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Delete Account");
        alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Account Deletion Status");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        usernameEt = (EditText)findViewById(R.id.etusername);
        mailEt = (EditText)findViewById(R.id.etmail);
    }

    public void OnYes(View view){
        String username = usernameEt.getText().toString();
        String mail = mailEt.getText().toString();
        BackgroundWorkerDelete backgroundWorker = new BackgroundWorkerDelete(this);
        backgroundWorker.execute(username,mail);
    }

    public void OnNo(View view){
        alertDialog.setMessage("Account is not deleted");
        alertDialog.show();
        Intent intent = new Intent(this, HomePage.class);
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

class BackgroundWorkerDelete extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    ProgressDialog loading;


    BackgroundWorkerDelete(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String register_url = "http://textextraction.96.lt/delete.php";

        try {
            String name = params[0];
            String mailadd = params[1];

            URL url = new URL(register_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")+ "&"
                    + URLEncoder.encode("emailID", "UTF-8") + "=" + URLEncoder.encode(mailadd, "UTF-8");
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
    protected void onPreExecute() {

        super.onPreExecute();
        loading = ProgressDialog.show(context, "Please Wait", null, true, true);
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Account Deletion Status");

    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        loading.dismiss();

        if(result.equalsIgnoreCase("Record deleted successfully")) {
            alertDialog.setMessage("Account deleted!");
            alertDialog.show();
            Intent i = new Intent(context.getApplicationContext(),MainActivity.class);
            context.startActivity(i);

        }
        else  {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

