package com.example.thampotter.quanlynhac.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class BaiHatCaNhanAdapter extends RecyclerView.Adapter<BaiHatCaNhanAdapter.ViewHolder> {

    Context context;
    ArrayList<Baihat> listbaihat;

    public BaiHatCaNhanAdapter(Context context, ArrayList<Baihat> listbaihat) {
        this.context = context;
        this.listbaihat = listbaihat;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_nhac_ca_nhan,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Baihat baihat = listbaihat.get(position);
        holder.tvtencasi.setText(baihat.getCasi());
        holder.tvtenbaihat.setText(baihat.getTenbaihat());
        holder.tvluotthich.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return listbaihat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvtenbaihat, tvtencasi,tvluotthich;
        ImageView imgdel, imgedit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtenbaihat = itemView.findViewById(R.id.tvtenbaihatcanhan);
            tvtencasi = itemView.findViewById(R.id.tvtencasicanhan);
            tvluotthich = itemView.findViewById(R.id.tvluotthichcanhan);
            imgdel = itemView.findViewById(R.id.imgdelete);
            imgedit = itemView.findViewById(R.id.imgedit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,PhatNhacActivity.class);
                    intent.putExtra("baihat",listbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });



            imgedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "Chuc nang sua thong tin", Toast.LENGTH_SHORT).show();
                    hienedit(listbaihat.get(getPosition()).getTenbaihat().toString(),listbaihat.get(getPosition()).getIdbaihat());

                }
            });

            imgdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("TENBAIXOA",listbaihat.get(getPosition()).getTenbaihat()+"");
                    xacNhanXoa(listbaihat.get(getPosition()).getTenbaihat());
                }
            });
        }

        private void hienedit(final String tenbaihat, final String idbaihat) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialogedit_custom);

            final EditText edtenbaihat = dialog.findViewById(R.id.edtenbaihatedit);

            Button btnOk = dialog.findViewById(R.id.btnxacnhanedit);
            Button btnhuyedit = dialog.findViewById(R.id.btnhuyedit);

            edtenbaihat.setText(tenbaihat.trim().toString());

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataService dataService = APIService.getService();
                    Call<String> callback = dataService.EditSong(idbaihat,edtenbaihat.getText().toString().trim());
                    Log.e("TENBAIHATEDIT",edtenbaihat.getText().toString().trim());
                    Log.e("IDBAIHATEDIT",idbaihat.toString());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.e("EDIT", response.body());
                            if (response.body() != null){
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }

            });

            btnhuyedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        public void xacNhanXoa(String ten){
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("Bạn có muốn xóa bài hát "+ten +" không?");
            dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String link = listbaihat.get(getPosition()).getLinkbaihat().substring(listbaihat.get(getPosition()).getLinkbaihat().lastIndexOf("/"));
                    link = APIService.baseURL+"NhacCaNhan"+link;
                    Log.e("LINKKKK",link);
                    xoaBaiHat(listbaihat.get(getPosition()).getIdbaihat(),link);
                }
            });
            dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }

        public void xoaBaiHat(String id, String link){
            DataService dataService = APIService.getService();
            Call<String> call = dataService.XoaBaiHat(id,link);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("DELETE", response.body()+"");
                    if (response.body().length() > 0){
                        Toast.makeText(context, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Không thể xóa bài hát", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }

}
