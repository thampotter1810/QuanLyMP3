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

import com.example.thampotter.quanlynhac.Activity.PhatNhacActivity;
import com.example.thampotter.quanlynhac.Model.Baihat;
import com.example.thampotter.quanlynhac.R;
import com.example.thampotter.quanlynhac.Service.APIService;
import com.example.thampotter.quanlynhac.Service.DataService;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachbaihatAdapter extends RecyclerView.Adapter<DanhsachbaihatAdapter.ViewHolder> {

    Context context;
    ArrayList<Baihat> mangbaihat;

    public DanhsachbaihatAdapter(Context context, ArrayList<Baihat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_bai_hat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Baihat baihat = mangbaihat.get(position);
        holder.tvtencasi.setText(baihat.getCasi());
        holder.tvtenbaihat.setText(baihat.getTenbaihat());
        holder.tvindex.setText(position + 1 + "");

    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvindex, tvtenbaihat,tvtencasi;
        ImageView imgluotthich;

        public ViewHolder(View itemView) {
            super(itemView);
            tvindex = itemView.findViewById(R.id.tvdanhsachindex);
            tvtenbaihat = itemView.findViewById(R.id.tvtenbaihat);
            tvtencasi = itemView.findViewById(R.id.tvtencasi);
            imgluotthich = itemView.findViewById(R.id.imgluotthichdanhsachbaihat);

            imgluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgluotthich.setImageResource(R.drawable.iconloved);
                    DataService dataService = APIService.getService();
                    Call<String> callback = dataService.CapNhatLuotThich("1",mangbaihat.get(getPosition()).getIdbaihat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String rs = response.body().toString();
                            if (rs.equals("ok")){
                                Toast.makeText(context, "Đã thích", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imgluotthich.setEnabled(true);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,PhatNhacActivity.class);
                    intent.putExtra("baihat",mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
