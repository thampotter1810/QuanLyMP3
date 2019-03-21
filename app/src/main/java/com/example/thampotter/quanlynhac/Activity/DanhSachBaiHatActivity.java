package com.example.thampotter.quanlynhac.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thampotter.quanlynhac.Adapter.DanhsachbaihatAdapter;
import com.example.thampotter.quanlynhac.Model.Album;
import com.example.thampotter.quanlynhac.Model.Baihat;
import com.example.thampotter.quanlynhac.Model.Playlist;
import com.example.thampotter.quanlynhac.Model.QuangCao;
import com.example.thampotter.quanlynhac.Model.TheLoai;
import com.example.thampotter.quanlynhac.R;
import com.example.thampotter.quanlynhac.Service.APIService;
import com.example.thampotter.quanlynhac.Service.DataService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachBaiHatActivity extends AppCompatActivity {

    QuangCao quangCao;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewdanhsachbaihat;
    FloatingActionButton floatingActionButton;
    ImageView imgdanhsachcakhuc;
    ArrayList<Baihat> danhsachbaihat;
    DanhsachbaihatAdapter danhsachbaihatAdapter;
    Playlist playlist;
    TheLoai theLoai;
    Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bai_hat);

        //enter the code

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DataNhan();
        anhXa();
        initComponent();
        if (quangCao != null && !quangCao.getTenBaiHat().equals("")){
            ganDuLieu(quangCao.getTenBaiHat(), quangCao.getHinhBaiHat());
            layDulieuQuangCao(quangCao.getIdQuangCao());
        }
        if (playlist != null && !playlist.getTen().equals("")){
            ganDuLieu(playlist.getTen(),playlist.getHinhAnh());
            layDuLieuPlayList(playlist.getIdPlayList());
        }
        if (theLoai != null && !theLoai.getTenTheLoai().equals("")){
            Log.e("THELOAI",theLoai.getTenTheLoai());
            Log.e("HINHTHELOAI",theLoai.getHinhTheLoai());
            ganDuLieu(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            layDuLieuTheLoai(theLoai.getIdTheLoai());
        }
        if (album != null && !album.getTenAlbum().equals("")){
            ganDuLieu(album.getTenAlbum(),album.getHinhAlbum());
            layDuLieuAlbum(album.getIdAlbum());
        }

        onClick();

    }

    private void onClick() {
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhSachBaiHatActivity.this, PhatNhacActivity.class);
                intent.putExtra("danhsachbaihat",danhsachbaihat);
                startActivity(intent);
            }
        });
    }

    private void layDuLieuAlbum(String idAlbum) {
        DataService dataService = APIService.getService();
        Call<List<Baihat>> callback = dataService.GetDanhsachbaihattheoalbum(idAlbum);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                danhsachbaihat = (ArrayList<Baihat>) response.body();
                Log.e("TTTT", response.body()+"");
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhSachBaiHatActivity.this, danhsachbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });

    }

    private void layDuLieuTheLoai(String idTheLoai) {
        DataService dataService = APIService.getService();
        Call<List<Baihat>> callback = dataService.GetDanhSachBaiHatTheoTheLoai(idTheLoai);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                danhsachbaihat = (ArrayList<Baihat>) response.body();
                Log.e("THELOAI",response.body()+"");
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhSachBaiHatActivity.this, danhsachbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void layDuLieuPlayList(String idplaylist) {
        DataService dataService = APIService.getService();
        Call<List<Baihat>> callback = dataService.GetDanhsachbaihattheoplaylist(idplaylist);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                danhsachbaihat = (ArrayList<Baihat>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhSachBaiHatActivity.this, danhsachbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void layDulieuQuangCao(String idquangcao) {
        DataService dataService = APIService.getService();
        Call<List<Baihat>> callBack = dataService.GetDanhsachbaihattheoquangcao(idquangcao);
        callBack.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                danhsachbaihat = (ArrayList<Baihat>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhSachBaiHatActivity.this,danhsachbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);

                Log.e("MAAANNNNG",danhsachbaihat.get(0).getTenbaihat());
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void ganDuLieu(String ten, String hinh) {
        collapsingToolbarLayout.setTitle(ten);
        try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                collapsingToolbarLayout.setBackground(bitmapDrawable);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(hinh).into(imgdanhsachcakhuc);
    }

    private void initComponent() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void anhXa() {
        imgdanhsachcakhuc = findViewById(R.id.imgdanhsachcakhuc);
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbardanhsach);
        recyclerViewdanhsachbaihat = findViewById(R.id.recyclerviewdanhsachbaihat);
        floatingActionButton = findViewById(R.id.floattingactionbutton);
    }

    private void DataNhan() {
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("quangcao")){
                quangCao = (QuangCao) intent.getSerializableExtra("quangcao");
                Toast.makeText(getApplication(),quangCao.getTenBaiHat(),Toast.LENGTH_SHORT).show();
            }
            if (intent.hasExtra("itemplaylist")){
                playlist = (Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("idtheloai")){
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if (intent.hasExtra("idalbum")){
                album = (Album) intent.getSerializableExtra("idalbum");
            }
        }
    }
}
