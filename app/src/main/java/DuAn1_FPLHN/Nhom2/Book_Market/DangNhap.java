package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
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
    private SharedPreferences sharedPreferences;
    private CheckBox checkBoxRemember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        //hoan thien ghi nho tai khoan
        sharedPreferences = getSharedPreferences("MySharedPreferences",MODE_PRIVATE);
        checkBoxRemember = findViewById(R.id.chk_dn_remember);

        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(this);

        tvDangKi = findViewById(R.id.tv_dn_signup);
        tvDangNhap = findViewById(R.id.tv_dn_dn);
        tvDNFogotPS = findViewById(R.id.tv_dn_forgotps);
        btnDNDN = findViewById(R.id.btn_dn_dn);
        tiledDNTaiKhoan = findViewById(R.id.tiedt_dn_tk);
        tiledDNMatKhau = findViewById(R.id.tiedt_dn_ps);

        anhxa();
        animationText();

        // Load thông tin tài khoản nếu đã được ghi nhớ trước đó
        if (sharedPreferences.contains("taikhoan") && sharedPreferences.contains("matkhau")) {
            tiledDNTaiKhoan.setText(sharedPreferences.getString("taikhoan", ""));
            tiledDNMatKhau.setText(sharedPreferences.getString("matkhau", ""));
            checkBoxRemember.setChecked(true);
        }

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
                // Lưu thông tin tài khoản nếu người dùng đã chọn "Ghi nhớ"
                if (checkBoxRemember.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("taikhoan", taikhoan);
                    editor.putString("matkhau", matkhau);
                    editor.apply();
                }
                if (taiKhoanDAO.checkDangNhap(taikhoan, matkhau)){
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DangNhap.this, MainActivity.class));
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