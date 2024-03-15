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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class DangNhap extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user;

    TextInputEditText tiledDNTaiKhoan,tiledDNMatKhau;
    private TextView tvDangKi,tvDangNhap,tvDNFogotPS;
    private String textViewDN ="Đăng Nhập";
    private int soKiTu = 0;
    private Button btnDNTV,btnDNDN ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        tvDangKi = findViewById(R.id.tv_dn_signup);
        tvDangNhap = findViewById(R.id.tv_dn_dn);
        btnDNDN = findViewById(R.id.btn_dn_dn);
        anhxa();
        animationText();

        btnDNDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tiledDNTaiKhoan.getText().toString().trim().isEmpty() || tiledDNMatKhau.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DangNhap.this);
                    builder.setTitle("Lỗi");
                    builder.setMessage("Vui lòng nhập đầy đủ thông tin");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }else {
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    startActivity(intent);
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