package com.example.praha.telt;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button login,register;
    AlertDialog alertDialog;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertDialog=new AlertDialog.Builder(this).create();
        
        login = (Button)findViewById(R.id.bLogin);
        register = (Button)findViewById(R.id.bRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = checkInternetConnection();
                if(check){
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
                else
                    login.setEnabled(false);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = checkInternetConnection();
                if (check) {
                    startActivity(new Intent(MainActivity.this, Register.class));
                }
                else
                   register.setEnabled(false);
            }
        });

    }

    public boolean checkInternetConnection() {

        ConnectivityManager connect = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connect.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            alertDialog.setTitle("INTERNET STATUS");
            alertDialog.setMessage("Please check your network connectivity and try again");
            alertDialog.show();
            return false;
        }
        else {
            alertDialog.setTitle("INTERNET STATUS");
            alertDialog.setMessage("Connected");
            alertDialog.show();
            return true;
        }

    }

}
