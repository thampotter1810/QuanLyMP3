package com.example.thampotter.quanlynhac.Activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.thampotter.quanlynhac.Adapter.AdapterSearch;
import com.example.thampotter.quanlynhac.Model.Baihat;
import com.example.thampotter.quanlynhac.R;
import com.example.thampotter.quanlynhac.Service.APIService;
import com.example.thampotter.quanlynhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    Toolbar toolbar;
    RecyclerView recyclerViewsearch;
    ArrayList<Baihat> listbaihat;
    AdapterSearch adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        anhXa();

        setToolBar();

        loadData();
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        /*actionBar.setLogo(R.drawable.iconquanly);    //Icon muốn hiện thị
        actionBar.setDisplayUseLogoEnabled(true);*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData() {
        DataService dataService = APIService.getService();
        Call<List<Baihat>> callback = dataService.GetAllSong("");
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                Toast.makeText(SearchActivity.this, "đã load", Toast.LENGTH_SHORT).show();
                listbaihat = new ArrayList<>();
                listbaihat = (ArrayList<Baihat>) response.body();
                adapter = new AdapterSearch(SearchActivity.this, listbaihat);
                recyclerViewsearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                recyclerViewsearch.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbartimkiembaihat);
        recyclerViewsearch = findViewById(R.id.recyclerviewtimkiem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);

        MenuItem menuItem = menu.findItem(R.id.menuseachview);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        loadDataForname(s);
        return true;
    }

    private void loadDataForname(String name) {
        DataService dataService = APIService.getService();
        Call<List<Baihat>> callback = dataService.GetAllSong(name);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {

                listbaihat = new ArrayList<>();
                listbaihat = (ArrayList<Baihat>) response.body();



                Toast.makeText(SearchActivity.this, ""+listbaihat.size() , Toast.LENGTH_SHORT).show();
                adapter = new AdapterSearch(SearchActivity.this, listbaihat);
                recyclerViewsearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                recyclerViewsearch.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }
}
