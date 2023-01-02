package com.example.da_laptrinhandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.da_laptrinhandroid.Util.CheckConnection;

public class ThongTinActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        Mapping();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionToolbar();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Mapping() {
        toolbar = findViewById(R.id.toolbar_thongtin);
    }
}