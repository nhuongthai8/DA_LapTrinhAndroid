package com.example.da_laptrinhandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

    View footerview;
    boolean loading = false;
    boolean outofdata = false;
    mainHandler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_pham);
        Mapping();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdLSP();
            ActionToolbar();
            GetData(page);
            LoadData();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
            finish();
        }

    }

    private void LoadData() {
        listViewtp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSP.class);
                intent.putExtra("thongtinsp",arraySP.get(i));
                startActivity(intent);
            }
        });
        listViewtp.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }
            //item1+sphienlen=tongsoluong => layout đang ở vị trí cuối cùng
            //tongsoluongitem khi vừa load layout sp =0
            //loading==false chống việc load sp mới dẫn đến crash app, load xong ms cho load tiếp
            //outofdata==false khi chưa load hết dữ liệu sẽ load tiếp
            @Override
            public void onScroll(AbsListView absListView, int item1, int sphienlen, int tongsoluongitem) {
                if(item1 + sphienlen == tongsoluongitem && tongsoluongitem !=0 && loading == false &&outofdata == false){
                    loading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    //menu giỏ hàng góc phải
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),com.example.da_laptrinhandroid.ShoppingCart.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
                if(response!=null &&response.length() != 2){
                    listViewtp.removeFooterView(footerview);
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
                }else{
                    outofdata = true;
                    listViewtp.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã load hết dữ liệu");
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
        //progress bar
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mainHandler = new mainHandler();
    }

    public class mainHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewtp.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    loading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mainHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mainHandler.obtainMessage(1);
            mainHandler.sendMessage(message);
            super.run();
        }
    }
}