package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class DangNhap extends AppCompatActivity {
    private TextView tvDangKi,tvDangNhap;
    private String textViewDN ="Đăng Nhập";
    private int soKiTu = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        tvDangKi = findViewById(R.id.tv_signup);
        tvDangNhap = findViewById(R.id.tv_login);

        animationText();


        tvDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKi.class);
                startActivity(intent);
            }
        });
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
        },200);
    }
}