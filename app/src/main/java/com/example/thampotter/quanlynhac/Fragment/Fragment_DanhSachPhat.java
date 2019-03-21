package com.example.thampotter.quanlynhac.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thampotter.quanlynhac.Activity.PhatNhacActivity;
import com.example.thampotter.quanlynhac.Adapter.DanhSachPhatAdapter;
import com.example.thampotter.quanlynhac.R;

public class Fragment_DanhSachPhat extends Fragment {


    View view;
    RecyclerView recyclerviewdanhsachphat;
    Context context;
    DanhSachPhatAdapter danhSachPhatAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.danh_sach_phat_fragment,container,false);
        recyclerviewdanhsachphat = view.findViewById(R.id.recyclerviewfragmentdanhsachphat);
        if (PhatNhacActivity.listbaiHat.size() > 0){
            danhSachPhatAdapter = new DanhSachPhatAdapter(getActivity(),PhatNhacActivity.listbaiHat);
            recyclerviewdanhsachphat.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerviewdanhsachphat.setAdapter(danhSachPhatAdapter);
        }


        return view;
    }
}
