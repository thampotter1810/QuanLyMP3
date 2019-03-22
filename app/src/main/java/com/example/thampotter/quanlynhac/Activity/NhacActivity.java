package com.example.thampotter.quanlynhac.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thampotter.quanlynhac.Adapter.AdapterMainViewPager;
import com.example.thampotter.quanlynhac.Fragment.Fragment_NhacCaNhan;
import com.example.thampotter.quanlynhac.Fragment.Fragment_NhacChiaSe;
import com.example.thampotter.quanlynhac.Fragment.Fragment_Trang_Chu;
import com.example.thampotter.quanlynhac.R;
import com.example.thampotter.quanlynhac.Service.APIRetrofitClient;
import com.example.thampotter.quanlynhac.Service.APIService;
import com.example.thampotter.quanlynhac.Service.DataService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhacActivity extends AppCompatActivity {

    String url = "https://androidzing.000webhostapp.com/Server/dangnhap.php";
    TabLayout tabLayout;
    ViewPager viewPager;
    int REQUEST_CODE =  123;
    String realpath = "";
    String userName = "";
    String songName = "";
    int chiase;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhac);

        /*
        * Lấy username người đăng nhập của activity trước
        * Lấy để hỗ trợ cho việc upload nhạc cá nhân
        * */

        Intent intentUser = getIntent();
        userName = intentUser.getStringExtra("username");

        anhXa();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.iconquanly);    //Icon muốn hiện thị
        actionBar.setDisplayUseLogoEnabled(true);

        init();

    }

    private void init() {
        AdapterMainViewPager adapterMainViewPager = new AdapterMainViewPager(getSupportFragmentManager());
        adapterMainViewPager.addFragment(new Fragment_Trang_Chu(),"Trang Chủ");
        adapterMainViewPager.addFragment(new Fragment_NhacCaNhan(),"Nhạc cá nhân");
        adapterMainViewPager.addFragment(new Fragment_NhacChiaSe(),"Nhạc Khác");
        viewPager.setAdapter(adapterMainViewPager);
        tabLayout.setupWithViewPager(viewPager);
        /*tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(R.drawable.iconsearch);*/
    }

    public void anhXa(){
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbarnhacactivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_chon,menu);

        /*MenuItem menuItem = menu.findItem(R.id.menusearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);*/

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuupload){
            if (userName.equals("null")){
                Toast.makeText(this, "Mời bạn đăng nhập trước khi upload file!", Toast.LENGTH_SHORT).show();
                hienDangNhap();
            }else{
                Intent intent = new Intent(NhacActivity.this,SelectFileActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        }if (item.getItemId() == R.id.menusearch){
            Intent intent = new Intent(NhacActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void hienDangNhap() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dang_nhap);

        final EditText edname = dialog.findViewById(R.id.eddialogusername);
        final EditText edpass = dialog.findViewById(R.id.eddialogpassword);
        TextView tvdangky = dialog.findViewById(R.id.tvdangnhapdangky);
        Button btncancel = dialog.findViewById(R.id.btndangnhaphuy);
        Button btnlogin = dialog.findViewById(R.id.btndangnhapdangnhap);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NhacActivity.this,DangKyThanhVienActivity.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Đăng nhập hệ thống bằng dialog

                RequestQueue requestQueue = Volley.newRequestQueue(NhacActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("success")){
                                    Toast.makeText(NhacActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                                    userName = edname.getText().toString().trim();
                                     dialog.dismiss();
                                }else {
                                    Toast.makeText(NhacActivity.this, "Vui lòng kiểm tra lại tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(NhacActivity.this, "Vui lòng kiểm tra lại kết nối internet của bạn và thử lại!", Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username",edname.getText().toString().trim());
                        params.put("password",edpass.getText().toString().trim());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        dialog.show();
    }


    private void hienThongBao() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom);

        //ánh xạ
        final TextView tvtenfile = dialog.findViewById(R.id.tvtenfile);
        final CheckBox cbchiase = dialog.findViewById(R.id.cbchiase);

        Button btnHuy = dialog.findViewById(R.id.btndialoghuy);
        Button btnXacNhan = dialog.findViewById(R.id.btndialogxacnhan);

        //Lấy tên file và hiển thị vào dialog
        String filename = realpath.substring(realpath.lastIndexOf("/")+1);
        tvtenfile.setText(filename.toString().trim());
        String[] songname = filename.split("\\.");
        songName = songname[0];
        if (cbchiase.isChecked()){
            chiase = 1;
        }else {
            chiase = 0;
        }

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NhacActivity.this, "bạn đã lấy được file", Toast.LENGTH_SHORT).show();


                File file = new File(realpath);
                String filepath = file.getAbsolutePath();

                Log.e("DUONGDAN", filepath);
                /*final String[] split = file.getPath().split(":");//split the path.
                filepath = split[1];//assign it to a string(your choice).

                Log.e("PPPPPPPP",filepath);
                String[] mangtenfile = filepath.split("\\.");
                filepath = mangtenfile[0] + System.currentTimeMillis() + mangtenfile[1];*/
                Log.e("NAME",filepath);

                Log.e("PATH",filepath);

                RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),file);

                //upfile
                MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file",filepath,body);

                DataService dataService = APIService.getService();
                Call<String> callback = dataService.UpLoadNhac(part);
                Log.e("PART",callback.toString());
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String kq = response.body();
                        Log.e("MESSAGEUPLOAD",kq);
                        if (response != null){

                            if (kq.length() > 0){
                                DataService service = APIService.getService();
                                Call<String> call1back = service.InsertData(userName,chiase,songName,APIService.baseURL + "NhacCaNhan/" + kq);
                                call1back.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String result = response.body();
                                        if (result.equals("Success")){
                                            Toast.makeText(NhacActivity.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();

                                        }else {
                                            Toast.makeText(NhacActivity.this, "Thêm dữ liệu không thành công!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(NhacActivity.this, "Đã xảy ra lỗi của server", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(NhacActivity.this, "lỗi", Toast.LENGTH_SHORT).show();
                        Log.e("ERR",t.getMessage());
                    }
                });
                dialog.dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){

            realpath = data.getStringExtra("name");

            Toast.makeText(this, ""+realpath, Toast.LENGTH_SHORT).show();
            hienThongBao();

            /*try {
                //InputStream inputStream = getContentResolver().openInputStream(uri);
                Toast.makeText(this, ""+realpath, Toast.LENGTH_SHORT).show();
                //hienThongBao(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /*
    * Lấy đường dẫn từ uri
    * cách này chỉ lấy được khi phiên bản Android thấp hơn API 19.
    *
    * */
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void dangNhap(String url){

    }
}
