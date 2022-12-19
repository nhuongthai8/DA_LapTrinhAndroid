package com.example.da_laptrinhandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da_laptrinhandroid.Model.LoaiSP;
import com.example.da_laptrinhandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLSP extends BaseAdapter {

    ArrayList<LoaiSP> arrayListLSP;
    Context context;

    public AdapterLSP(ArrayList<LoaiSP> arrayListLSP, Context context) {
        this.arrayListLSP = arrayListLSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLSP.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLSP.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txttenlsp;
        ImageView imglsp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_listview_lsp,null);
            viewHolder.txttenlsp =(TextView) view.findViewById(R.id.textviewlsp);
            viewHolder.imglsp =(ImageView) view.findViewById(R.id.imageviewlsp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiSP loaiSP =(LoaiSP) getItem(i);
        viewHolder.txttenlsp.setText(loaiSP.getTenLSP());
        Picasso.get().load(loaiSP.getHinhLSP()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(viewHolder.imglsp);
        return view;
    }
}
