package com.example.da_laptrinhandroid;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.da_laptrinhandroid.Adapter.AdapterLSP;
import com.example.da_laptrinhandroid.Adapter.AdapterSP;
import com.example.da_laptrinhandroid.Model.GioHang;
import com.example.da_laptrinhandroid.Model.LoaiSP;
import com.example.da_laptrinhandroid.Model.SanPham;
import com.example.da_laptrinhandroid.Util.CheckConnection;
import com.example.da_laptrinhandroid.Util.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<LoaiSP> arrayLSP;
    AdapterLSP adapterLSP;
    
    int id = 0;
    String tenlsp ="";
    String hinhlsp ="";

    ArrayList<SanPham> arraySP;
    AdapterSP adapterSP;

    public static ArrayList<GioHang> arrayGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping(); //ánh xạ
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDataLSP();
            GetDataNewSP();
            CatchOnItemListView();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối");
            finish();
        }

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

    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kết nối bị lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThucPhamActivity.class);
                            intent.putExtra("idlsp",arrayLSP.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kết nối bị lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThitActivity.class);
                            intent.putExtra("idlsp",arrayLSP.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kết nối bị lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DongHopActivity.class);
                            intent.putExtra("idlsp",arrayLSP.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kết nối bị lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kết nối bị lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kết nối bị lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,TimKiem.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kết nối bị lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 7:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kết nối bị lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDataNewSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathNewSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    int id =0;
                    String tensp="";
                    int giatien=0;
                    String noidung="";
                    int soluong =0;
                    String dvt ="";
                    String hinhsp="";
                    int idlsp=0;
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("idSP");
                            tensp = jsonObject.getString("TenSP");
                            giatien = jsonObject.getInt("GiaTien");
                            noidung = jsonObject.getString("NoiDung");
                            soluong = jsonObject.getInt("SoLuong");
                            dvt = jsonObject.getString("DVT");
                            hinhsp = jsonObject.getString("HinhSP");
                            idlsp = jsonObject.getInt("idLSP");
                            arraySP.add(new SanPham(id,tensp,giatien,noidung,soluong,dvt,hinhsp,idlsp));
                            adapterSP.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataLSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathLSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i=0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("idLSP");
                            tenlsp = jsonObject.getString("TenLSP");
                            hinhlsp = jsonObject.getString("HinhLSP");
                            arrayLSP.add(new LoaiSP(id,tenlsp,hinhlsp));
                            adapterLSP.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayLSP.add(4,new LoaiSP(0,"Góp Ý","https://cdn-icons-png.flaticon.com/128/3771/3771518.png"));
                    arrayLSP.add(5,new LoaiSP(0,"Thông Tin","https://cdn-icons-png.flaticon.com/128/943/943579.png"));
                    arrayLSP.add(6,new LoaiSP(0,"Tìm Kiếm","https://cdn-icons-png.flaticon.com/512/751/751381.png"));
                    arrayLSP.add(7,new LoaiSP(0,"Đăng Nhập","https://cdn-icons-png.flaticon.com/512/6555/6555213.png"));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Mapping(){
        toolbar =(Toolbar) findViewById(R.id.toolbarchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipperchinh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerchinh);
        navigationView = (NavigationView) findViewById(R.id.navigationviewchinh);
        listView = (ListView) findViewById(R.id.listviewchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawlerlayoutchinh);
        //loại sản phẩm //sidebar
        arrayLSP = new ArrayList<>();
        arrayLSP.add(0,new LoaiSP(0,"Trang Chủ","https://cdn-icons-png.flaticon.com/128/3804/3804443.png"));//side bar

        adapterLSP = new AdapterLSP(arrayLSP,getApplicationContext());
        listView.setAdapter(adapterLSP);
        //sản phẩm
        arraySP = new ArrayList<>();
        adapterSP = new AdapterSP(getApplicationContext(),arraySP);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapterSP);
        //giỏ hàng
        if(arrayGioHang!=null){
            //nếu đã có dữ liệu trong giỏ hàng thì k cần tạo mảng mới để cấp dữ liệu
        }else{
            //khi k có dữ liệu thì sẽ khởi tạo và cấp phát bộ nhớ
            arrayGioHang = new ArrayList<>();
        }
    }
    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void ActionViewFlipper(){
        ArrayList<String> quangcao = new ArrayList<>();
        quangcao.add("https://defarm.vn/wp-content/uploads/2021/07/Thuc-Pham-An-Toan-Cho-Nguoi-Tieu-Dung.jpg");
        quangcao.add("https://saschi.vn/wp-content/uploads/2021/03/y-tuong-kinh-doanh-thuc-pham-sach.png");
        quangcao.add("https://vinmec-prod.s3.amazonaws.com/images/20220114_115721_622564_pham-huu-co.max-1800x1800.jpg");
        for(int i=0;i<quangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(quangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }



}