package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class DangKi extends AppCompatActivity {

    private  TextView tvDkTitle;
    private  String tvstrDK= "Đăng Kí Tài Khoản";
    private int soKiTu = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        tvDkTitle = findViewById(R.id.tv_dk_title);
        // Bắt đầu hiển thị từng chữ một
        animateText();
    }

    private void animateText() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (soKiTu < tvstrDK.length()){
                    String vanBanHT = tvstrDK.substring(0, soKiTu + 1);
                    tvDkTitle.setText(vanBanHT);
                    soKiTu++;
                    animateText();
                }
            }
            },200);
    }

}