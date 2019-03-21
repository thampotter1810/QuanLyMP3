package com.example.thampotter.quanlynhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thampotter.quanlynhac.Activity.DanhSachBaiHatActivity;
import com.example.thampotter.quanlynhac.Model.TheLoai;
import com.example.thampotter.quanlynhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachTheLoaiTheoChuDeAdapter extends RecyclerView.Adapter<DanhSachTheLoaiTheoChuDeAdapter.ViewHolder>{

    Context context;
    ArrayList<TheLoai> list;

    public DanhSachTheLoaiTheoChuDeAdapter(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_the_loai_theo_chu_de,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TheLoai theLoai = list.get(position);
        Picasso.with(context).load(theLoai.getHinhTheLoai()).into(holder.imgtheloaitheochude);
        holder.tvtentheloaitheochude.setText(theLoai.getTenTheLoai());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgtheloaitheochude;
        TextView tvtentheloaitheochude;
        public ViewHolder(View itemView) {
            super(itemView);
            imgtheloaitheochude = itemView.findViewById(R.id.imgtheloaitheochude);
            tvtentheloaitheochude = itemView.findViewById(R.id.tvtentheloaitheochude);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("idtheloai", list.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
