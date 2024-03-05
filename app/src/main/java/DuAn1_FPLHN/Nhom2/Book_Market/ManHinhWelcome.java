package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ManHinhWelcome extends AppCompatActivity {
    TextView tvWelcome;
    LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_welcome);
        lottie = findViewById(R.id.lottie_welcome);
        tvWelcome = findViewById(R.id.animatedTextView);

        animationImage();

        animationText();




    }

    private void animationImage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ManHinhWelcome.this, DangNhap.class);
                startActivity(intent);
            }
        },9000);
    }

    private void animationText() {
        tvWelcome.setAlpha(0f); // Ẩn đối tượng ngay từ đầu

        tvWelcome.animate()
                .translationY(0)      // Di chuyển theo trục Y với khoảng cách 0 pixels (tại vị trí hiện tại)
                .alpha(1f)            // Hiển thị gradually (độ đục từ 0 đến 1)
                .setDuration(2700)
                .setStartDelay(0);
    }


}