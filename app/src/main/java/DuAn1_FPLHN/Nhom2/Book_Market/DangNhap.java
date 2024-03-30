package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TaiKhoanDAO;

public class DangNhap extends AppCompatActivity {
    TextInputEditText tiledDNTaiKhoan,tiledDNMatKhau;
    private TextView tvDangKi,tvDangNhap,tvDNFogotPS;
    private String textViewDN ="Đăng Nhập";
    private int soKiTu = 0;
    private Button btnDNDN ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(this);

        tvDangKi = findViewById(R.id.tv_dn_signup);
        tvDangNhap = findViewById(R.id.tv_dn_dn);
        tvDNFogotPS = findViewById(R.id.tv_dn_forgotps);
        btnDNDN = findViewById(R.id.btn_dn_dn);
        tiledDNTaiKhoan = findViewById(R.id.tiedt_dn_tk);
        tiledDNMatKhau = findViewById(R.id.tiedt_dn_ps);

        anhxa();
        animationText();

        btnDNDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taikhoan = tiledDNTaiKhoan.getText().toString();
                String matkhau = tiledDNMatKhau.getText().toString();

                if (taikhoan.isEmpty()){
                    Toast.makeText(DangNhap.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (matkhau.isEmpty()){
                    Toast.makeText(DangNhap.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (taiKhoanDAO.checkDangNhap(taikhoan, matkhau)){
                        startActivity(new Intent(DangNhap.this, MainActivity.class));
                        Toast.makeText(DangNhap.this, "Hello, wellcome to App Order Food", Toast.LENGTH_SHORT).show();
                } else {
                        Toast.makeText(DangNhap.this, "Tài khoản hặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKi.class);
                startActivity(intent);
            }
        });


        tvDNFogotPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhap.this, QuenMatKhau.class));
            }
        });


    }

    private void anhxa() {
        tiledDNTaiKhoan = findViewById(R.id.tiedt_dn_tk);
        tiledDNMatKhau = findViewById(R.id.tiedt_dn_ps);
    }

    private void animationText() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (soKiTu < textViewDN.length()){
                    String vanBanHT = textViewDN.substring(0, soKiTu + 1);
                    tvDangNhap.setText(vanBanHT);
                    soKiTu++;
                    animationText();
                }
            }
        },500);
    }
    ///
}