package com.example.thampotter.quanlynhac.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thampotter.quanlynhac.Adapter.NhacChiaSeAdapter;
import com.example.thampotter.quanlynhac.Model.Baihat;
import com.example.thampotter.quanlynhac.R;
import com.example.thampotter.quanlynhac.Service.APIService;
import com.example.thampotter.quanlynhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_NhacChiaSe extends Fragment {

    View view;
    Context context;
    String username = null;
    ArrayList<Baihat> listbaihat;
    NhacChiaSeAdapter adapter;
    RecyclerView recyclerViewNhacChiaSe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nhac_chia_se, container, false);

        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        recyclerViewNhacChiaSe = view.findViewById(R.id.recyclerviewnhacchiase);

        LayDuLieuCacbaiHatDuocChiaSe(username);
        return view;
    }

    private void LayDuLieuCacbaiHatDuocChiaSe(String username) {
        DataService dataService = APIService.getService();
        Call<List<Baihat>> callback = dataService.GetDanhSachBaiHatChiaSe(username);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                listbaihat = (ArrayList<Baihat>) response.body();
                adapter = new NhacChiaSeAdapter(getActivity(),listbaihat);
                Log.e("CHIASE",listbaihat.get(0).getTenbaihat());
                recyclerViewNhacChiaSe.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewNhacChiaSe.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }
}
