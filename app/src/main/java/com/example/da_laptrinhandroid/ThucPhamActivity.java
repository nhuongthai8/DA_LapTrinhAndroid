package com.example.da_laptrinhandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.da_laptrinhandroid.Adapter.AdapterThP;
import com.example.da_laptrinhandroid.Model.SanPham;
import com.example.da_laptrinhandroid.Util.CheckConnection;
import com.example.da_laptrinhandroid.Util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThucPhamActivity extends AppCompatActivity {

    Toolbar toolbartp;
    ListView listViewtp;
    AdapterThP adapterThP;
    ArrayList<SanPham> arraySP;

    int idSP = 0;
    int page =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_pham);
        Mapping();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdLSP();
            ActionToolbar();
            GetData(page);
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
            finish();
        }

    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String path = Server.pathSP+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id =0;
                String tensp="";
                int giatien=0;
                String noidung="";
                int soluong =0;
                String dvt ="";
                String hinhsp="";
                int idlsp=0;
                if(response!=null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("idSP");
                            tensp = jsonObject.getString("TenSP");
                            giatien = jsonObject.getInt("GiaTien");
                            noidung = jsonObject.getString("NoiDung");
                            soluong = jsonObject.getInt("SoLuong");
                            dvt = jsonObject.getString("DVT");
                            hinhsp = jsonObject.getString("HinhSP");
                            idlsp = jsonObject.getInt("idLSP");
                            arraySP.add(new SanPham(id,tensp,giatien,noidung,soluong,dvt,hinhsp,idlsp));
                            adapterThP.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("idLSP",String.valueOf(idSP));
                return params;
            }
        };
        requestQueue.add(stringRequest);
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

    private void GetIdLSP() {
        idSP = getIntent().getIntExtra("idlsp",-1);
        Log.d("giatrilsp",idSP+"");
    }

    private void Mapping(){
        toolbartp = (Toolbar) findViewById(R.id.toolbar_thucpham);
        listViewtp = (ListView) findViewById(R.id.listview_thucpham);
        arraySP = new ArrayList<>();
        adapterThP = new AdapterThP(getApplicationContext(),arraySP);
        listViewtp.setAdapter(adapterThP);
    }
}