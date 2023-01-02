package com.example.da_laptrinhandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.da_laptrinhandroid.Util.CheckConnection;

public class LienHeActivity extends AppCompatActivity {

    Toolbar toolbartp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        Mapping();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionToolbar();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
            finish();
        }
    }



    private void Mapping() {
        toolbartp = findViewById(R.id.toolbar_lienhe);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbartp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}