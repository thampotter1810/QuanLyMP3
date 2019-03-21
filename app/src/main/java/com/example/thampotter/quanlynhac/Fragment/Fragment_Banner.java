package com.example.thampotter.quanlynhac.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thampotter.quanlynhac.Adapter.BannerAdapter;
import com.example.thampotter.quanlynhac.Model.QuangCao;
import com.example.thampotter.quanlynhac.R;
import com.example.thampotter.quanlynhac.Service.APIService;
import com.example.thampotter.quanlynhac.Service.DataService;


import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment{

    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter adapter;
    Runnable runnable;
    Handler handler;
    int curentitem;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bannner,container,false);
        anhXa();

        //hiện progressbar
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang tải");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        GetData();

        return view;
    }

    private void anhXa() {
        viewPager = view.findViewById(R.id.viewpagerquangcao);
        circleIndicator = view.findViewById(R.id.indicatordefault);
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<QuangCao>> call = dataService.GetDataBanner();
        Log.e("BANNNER",call.toString());
        call.enqueue(new Callback<List<QuangCao>>() {
            @Override
            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
                ArrayList<QuangCao> banners = (ArrayList<QuangCao>) response.body();
                adapter = new BannerAdapter(getActivity(),banners);
                viewPager.setAdapter(adapter);
                circleIndicator.setViewPager(viewPager);

                //ẩn processbar
                progressDialog.dismiss();
                //set tự động chạy sau một khoảng thời gian nào đó
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        curentitem = viewPager.getCurrentItem();
                        curentitem++;
                        if (curentitem >= 5){
                            curentitem = 0;
                        }
                        viewPager.setCurrentItem(curentitem, true);
                        handler.postDelayed(runnable,4500);
                    }
                };
                handler.postDelayed(runnable,4500);
            }

            @Override
            public void onFailure(Call<List<QuangCao>> call, Throwable t) {
                Toast.makeText(getActivity(), "Vui lòng kiểm tra lại kết nối!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }

}
