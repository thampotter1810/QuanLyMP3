package com.example.thampotter.quanlynhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thampotter.quanlynhac.Activity.DanhSachBaiHatActivity;
import com.example.thampotter.quanlynhac.Model.Playlist;
import com.example.thampotter.quanlynhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhsachcacplaylistAdapter extends RecyclerView.Adapter<DanhsachcacplaylistAdapter.ViewHolder>{

    Context context;
    ArrayList<Playlist> list;

    public DanhsachcacplaylistAdapter(Context context, ArrayList<Playlist> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_cac_playlist,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Playlist playlist = list.get(position);
        Picasso.with(context).load(playlist.getHinhAnh()).into(holder.imgdanhsachcacplaylist);
        holder.tvtendanhsachcacplaylist.setText(playlist.getTen());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgdanhsachcacplaylist;
        TextView tvtendanhsachcacplaylist, tvtencasidanhsachcacplaylist;

        public ViewHolder(View itemView) {
            super(itemView);

            imgdanhsachcacplaylist = itemView.findViewById(R.id.imageviewdanhsachcacplaylist);
            tvtendanhsachcacplaylist = itemView.findViewById(R.id.tvtendanhsachcacplaylist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DanhSachBaiHatActivity.class);
                    intent.putExtra("itemplaylist",list.get(getPosition()));

                    Toast.makeText(context,list.get(getPosition()).getTen(),Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                }
            });
        }
    }
}
