package com.example.thampotter.quanlynhac.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thampotter.quanlynhac.Model.Playlist;
import com.example.thampotter.quanlynhac.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }

    class ViewHolder{
        TextView tvtenplaylist;
        ImageView imgbackground, imgplaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_playlist,null);
            viewHolder = new ViewHolder();
            viewHolder.tvtenplaylist = convertView.findViewById(R.id.tvtenplaylist);
            viewHolder.imgplaylist = convertView.findViewById(R.id.imgplaylist);
            viewHolder.imgbackground = convertView.findViewById(R.id.imgbackgroundplaylist);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getHinhAnh()).into(viewHolder.imgbackground);
        Picasso.with(getContext()).load(playlist.getIcon()).into(viewHolder.imgplaylist);
        viewHolder.tvtenplaylist.setText(playlist.getTen());
        return convertView;
    }
}
