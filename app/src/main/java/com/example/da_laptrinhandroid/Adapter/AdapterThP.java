package com.example.da_laptrinhandroid.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da_laptrinhandroid.Model.SanPham;
import com.example.da_laptrinhandroid.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterThP extends BaseAdapter {
    Context contex;
    ArrayList<SanPham> arrayTP;

    public AdapterThP(Context contex, ArrayList<SanPham> arrayTP) {
        this.contex = contex;
        this.arrayTP = arrayTP;
    }

    @Override
    public int getCount() {
        return arrayTP.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayTP.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txtthucpham,txtgiathucpham,txtndthucpham;
        public ImageView hinhsp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_thucpham,null);
            viewHolder.txtthucpham =(TextView) view.findViewById(R.id.textview_thucpham);
            viewHolder.txtgiathucpham =(TextView) view.findViewById(R.id.textview_giathucpham);
            viewHolder.txtndthucpham =(TextView) view.findViewById(R.id.textview_ndthucpham);
            viewHolder.hinhsp =(ImageView) view.findViewById(R.id.imageview_thucpham);
            view.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder) view.getTag();
        }
        SanPham sanPham =(SanPham) getItem(i);
        viewHolder.txtthucpham.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiathucpham.setText("Giá: "+decimalFormat.format(sanPham.getGiatien())+"Đ");
        viewHolder.txtndthucpham.setMaxLines(2);
        viewHolder.txtndthucpham.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtndthucpham.setText(sanPham.getNoidung());
        Picasso.get().load(sanPham.getHinhsp()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(viewHolder.hinhsp);
        return view;
    }
}
