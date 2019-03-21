package com.example.thampotter.quanlynhac.Activity;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.example.thampotter.quanlynhac.Adapter.TatCaAlbumAdapter;
import com.example.thampotter.quanlynhac.Model.Album;
import com.example.thampotter.quanlynhac.R;
import com.example.thampotter.quanlynhac.Service.APIService;
import com.example.thampotter.quanlynhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTatCaAlbumActivity extends AppCompatActivity {

    Toolbar toolbarallalbum;
    RecyclerView recyclerViewAllalbum;
    TatCaAlbumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tat_ca_album);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        anhXa();
        LoadData();
    }

    private void LoadData() {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.GetAllDataAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> listAlbum = (ArrayList<Album>) response.body();
                adapter = new TatCaAlbumAdapter(DanhSachTatCaAlbumActivity.this,listAlbum);

                recyclerViewAllalbum.setLayoutManager(new GridLayoutManager(DanhSachTatCaAlbumActivity.this,2));
                recyclerViewAllalbum.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        toolbarallalbum = findViewById(R.id.toolbarallalbum);
        recyclerViewAllalbum = findViewById(R.id.recyclerviewallalbum);

        setSupportActionBar(toolbarallalbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả Album");
        toolbarallalbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
