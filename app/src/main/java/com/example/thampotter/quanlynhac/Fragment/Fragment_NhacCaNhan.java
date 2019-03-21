package com.example.thampotter.quanlynhac.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thampotter.quanlynhac.Adapter.BaiHatCaNhanAdapter;
import com.example.thampotter.quanlynhac.Model.Baihat;
import com.example.thampotter.quanlynhac.R;
import com.example.thampotter.quanlynhac.Service.APIService;
import com.example.thampotter.quanlynhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_NhacCaNhan extends Fragment{

    View view;

    RecyclerView recyclerViewNhacCaNhan;
    ArrayList<Baihat> listBaiHatCaNhan;
    BaiHatCaNhanAdapter adapter;
    String username = "null";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nhac_ca_nhan,container, false);

        anhXa();

        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");

        LayDuLieuNhacCaNhan(username);

        return view;
    }



    private void LayDuLieuNhacCaNhan(String username) {
        DataService dataService = APIService.getService();
        Call<List<Baihat>> callback = dataService.Getdanhsachbaihatcanhan(username);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                listBaiHatCaNhan = (ArrayList<Baihat>) response.body();
                adapter = new BaiHatCaNhanAdapter(getActivity(),listBaiHatCaNhan);
                recyclerViewNhacCaNhan.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewNhacCaNhan.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });

    }


    private void anhXa() {
       recyclerViewNhacCaNhan = view.findViewById(R.id.recyclerviewdanhsachbaihatcanhan);
    }
}
