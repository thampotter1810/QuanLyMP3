package com.example.thampotter.quanlynhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thampotter.quanlynhac.Activity.PhatNhacActivity;
import com.example.thampotter.quanlynhac.Model.Baihat;
import com.example.thampotter.quanlynhac.R;

import java.util.ArrayList;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {


    Context context;
    ArrayList<Baihat> listbaihat;

    public AdapterSearch(Context context, ArrayList<Baihat> listbaihat) {
        this.context = context;
        this.listbaihat = listbaihat;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_all_song, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Baihat baihat = listbaihat.get(position);
        holder.tvname.setText(baihat.getTenbaihat().trim().toString());

    }

    @Override
    public int getItemCount() {
        return listbaihat.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvname;
        public ViewHolder(View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvallsong);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,PhatNhacActivity.class);
                    intent.putExtra("baihat",listbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
