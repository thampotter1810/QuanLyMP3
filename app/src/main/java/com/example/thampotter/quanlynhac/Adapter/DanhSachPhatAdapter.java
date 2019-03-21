package com.example.thampotter.quanlynhac.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thampotter.quanlynhac.Model.Baihat;
import com.example.thampotter.quanlynhac.R;

import java.util.ArrayList;

public class DanhSachPhatAdapter extends RecyclerView.Adapter<DanhSachPhatAdapter.ViewHolder>{

    Context context;
    ArrayList<Baihat> listbaihat;

    public DanhSachPhatAdapter(Context context, ArrayList<Baihat> listbaihat) {
        this.context = context;
        this.listbaihat = listbaihat;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_danh_sach_phat,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Baihat baihat = listbaihat.get(position);
        holder.tvtenbaihat.setText(baihat.getTenbaihat().toString());
        holder.tvtencasi.setText(baihat.getCasi().toString());
        holder.tvthutu.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return listbaihat.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvthutu, tvtenbaihat, tvtencasi;

        public ViewHolder(View itemView) {
            super(itemView);
            tvthutu = itemView.findViewById(R.id.tvthutubaihat);
            tvtenbaihat = itemView.findViewById(R.id.tvtenbaihatdanhsachphat);
            tvtencasi = itemView.findViewById(R.id.tvtencasidanhsachphat);
        }
    }
}
