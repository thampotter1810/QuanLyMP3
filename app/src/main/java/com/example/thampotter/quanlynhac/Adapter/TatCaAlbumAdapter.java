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

public class TatCaAlbumAdapter extends RecyclerView.Adapter<TatCaAlbumAdapter.ViewHolder>{

    Context context;
    ArrayList<Album> listAlbum;

    public TatCaAlbumAdapter(Context context, ArrayList<Album> listAlbum) {
        this.context = context;
        this.listAlbum = listAlbum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_tat_ca_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = listAlbum.get(position);
        Picasso.with(context).load(album.getHinhAlbum()).into(holder.imgAllAlbum);
        holder.tvTenAllAlbum.setText(album.getTenAlbum().toString());

    }

    @Override
    public int getItemCount() {
        return listAlbum.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAllAlbum;
        TextView tvTenAllAlbum;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAllAlbum = itemView.findViewById(R.id.imageviewallalbum);
            tvTenAllAlbum = itemView.findViewById(R.id.tvtenallalbum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("idalbum", listAlbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
