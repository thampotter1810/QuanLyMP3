package com.example.thampotter.quanlynhac.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thampotter.quanlynhac.R;

import java.util.HashMap;
import java.util.Map;

public class DangKyThanhVienActivity extends AppCompatActivity {
    EditText eduser, edpassword;
    Button btnOk, btnHuy;

    String url = "https://androidzing.000webhostapp.com/Server/dangkytaikhoan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_thanh_vien);

        anhXa();

        onClick();

    }

    private void onClick() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = eduser.getText().toString().trim();
                String pass = edpassword.getText().toString();

                if (user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(DangKyThanhVienActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    themUser(url);
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void themUser(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(DangKyThanhVienActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DangKyThanhVienActivity.this,MainActivity.class));

                        }else {
                            Toast.makeText(DangKyThanhVienActivity.this, "Lỗi thêm!" +
                                    "Tài khoản này đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKyThanhVienActivity.this, "Xảy ra lỗi bất ngờ!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",eduser.getText().toString().trim());
                params.put("password",edpassword.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void anhXa() {

        eduser = findViewById(R.id.edusernamemoi);
        edpassword = findViewById(R.id.edpasswordmoi);
        btnHuy = findViewById(R.id.btnhuydangky);
        btnOk = findViewById(R.id.btndangky);
    }
}
