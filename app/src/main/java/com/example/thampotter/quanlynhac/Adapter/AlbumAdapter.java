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
import com.example.thampotter.quanlynhac.Model.Album;
import com.example.thampotter.quanlynhac.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{

    Context context;
    ArrayList<Album> albumArrayList;

    public AlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_album,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        holder.tvCaSiAlbum.setText(album.getTenCaSiAlbum());
        holder.tvTenAlbum.setText(album.getTenAlbum());
        Picasso.with(context).load(album.getHinhAlbum()).into(holder.imghinhalbum);

    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imghinhalbum;
        TextView tvTenAlbum, tvCaSiAlbum;

        public ViewHolder(View itemView) {
            super(itemView);
            imghinhalbum=itemView.findViewById(R.id.imghinhalbum);
            tvTenAlbum = itemView.findViewById(R.id.tvtenalbum);
            tvCaSiAlbum = itemView.findViewById(R.id.tvtencasialbum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("idalbum",albumArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
