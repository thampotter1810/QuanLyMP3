package com.example.thampotter.quanlynhac.Activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.thampotter.quanlynhac.Adapter.DanhSachTheLoaiTheoChuDeAdapter;
import com.example.thampotter.quanlynhac.Model.ChuDe;
import com.example.thampotter.quanlynhac.Model.TheLoai;
import com.example.thampotter.quanlynhac.R;
import com.example.thampotter.quanlynhac.Service.APIService;
import com.example.thampotter.quanlynhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTheLoaiActivity extends AppCompatActivity {

    ChuDe chuDe;
    RecyclerView recyclerViewtheloai;
    Toolbar toolbartheloai;
    DanhSachTheLoaiTheoChuDeAdapter danhSachTheLoaiTheoChuDeAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_the_loai);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LayDuLieu();
        anhXa();
        LayData();
    }

    private void LayData() {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callback = dataService.GetTheLoaiTheoChuDe(chuDe.getIdChuDe());
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> mangTheLoai = (ArrayList<TheLoai>) response.body();
               // Log.e("THELOAI",mangTheLoai.get(0).getTenTheLoai());
                danhSachTheLoaiTheoChuDeAdapter = new DanhSachTheLoaiTheoChuDeAdapter(DanhSachTheLoaiActivity.this,mangTheLoai);
                recyclerViewtheloai.setLayoutManager(new GridLayoutManager(DanhSachTheLoaiActivity.this,2));
                recyclerViewtheloai.setAdapter(danhSachTheLoaiTheoChuDeAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        recyclerViewtheloai = findViewById(R.id.recyclerviewtheloaitheochude);
        toolbartheloai = findViewById(R.id.toolbartheloaitheochude);
        setSupportActionBar(toolbartheloai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        toolbartheloai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void LayDuLieu() {
        Intent intent = getIntent();
        if (intent.hasExtra("chude")){
            chuDe = (ChuDe) intent.getSerializableExtra("chude");
        }
    }
}
