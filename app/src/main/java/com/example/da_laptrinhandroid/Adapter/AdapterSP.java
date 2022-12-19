package com.example.da_laptrinhandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_laptrinhandroid.Model.SanPham;
import com.example.da_laptrinhandroid.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSP extends RecyclerView.Adapter<AdapterSP.ItemHolder> {

    Context context;
    ArrayList<SanPham> arraySP;

    public AdapterSP(Context context, ArrayList<SanPham> arraySP) {
        this.context = context;
        this.arraySP = arraySP;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        SanPham sanPham = arraySP.get(position);
        holder.txttensp.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: "+decimalFormat.format(sanPham.getGiatien())+"Đ");
        Picasso.get().load(sanPham.getHinhsp()).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arraySP.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView txttensp,txtgiasp;


        public ItemHolder(View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.imageview_sp);
            txtgiasp = (TextView) itemView.findViewById(R.id.textviewgiasp);
            txttensp = (TextView) itemView.findViewById(R.id.textviewtensp);
        }
    }
}
