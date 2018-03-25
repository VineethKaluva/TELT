package com.example.praha.telt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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


public class Login extends AppCompatActivity {

    EditText usernameEt,passwordEt;
    TextView forgotPassword;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Login");


        usernameEt = (EditText)findViewById(R.id.etUsername);
        passwordEt = (EditText)findViewById(R.id.etPassword);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
    }

    public void OnLogin(View view){
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();

        BackgroundWorkerLogin backgroundWorker = new BackgroundWorkerLogin(this);
        backgroundWorker.execute(username, password);


    }


    public void OnForgotPassword(View view){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

}


class BackgroundWorkerLogin extends AsyncTask<String,Void,BackgroundWorkerLogin.Wrapper> {

    Context context;
    AlertDialog alertDialog;
    ProgressDialog loading;
    SessionManager session;


    BackgroundWorkerLogin(Context ctx) {
        context = ctx;
    }

    public class Wrapper{
        public String username;
        public String result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(context, "Please Wait", null, true, true);
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
        session = new SessionManager(context.getApplicationContext());
    }

    @Override
    protected Wrapper doInBackground(String... params) {

        String login_url = "http://textextraction.96.lt/login.php";


        try {
            String username = params[0];
            String password = params[1];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                    + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
            Wrapper w = new Wrapper();
            w.username = username;
            w.result = result;

            return w;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(Wrapper w)  {

       // super.onPostExecute(w.result);
        loading.dismiss();
        String s =w.result.trim();
        if(s.equalsIgnoreCase("Login successful")) {

            alertDialog.setMessage("Login successful");
            alertDialog.show();
            session.createLoginSession(w.username,"");
            Intent i = new Intent(context.getApplicationContext(),HomePage.class);
            context.startActivity(i);

        }
        else
        {
            alertDialog.setMessage(w.result);
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}