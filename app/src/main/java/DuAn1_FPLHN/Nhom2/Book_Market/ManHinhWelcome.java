package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ManHinhWelcome extends AppCompatActivity {
    TextView tvWelcome, tvSlide;
    LottieAnimationView lottie;
    private String[] quotes = {"Đọc sách hôm nay, thành công mai sau.",
            "Cả thế giới mở ra với tôi khi tôi học đọc."};
    private int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_welcome);
        lottie = findViewById(R.id.lottie_welcome);
        tvWelcome = findViewById(R.id.tv_wc_cm);
        tvSlide = findViewById(R.id.tv_wc_sl);
        animationImage();
        animationText();
        animationText2();

    }

    private void animationText2() {
        final Handler handler = new Handler();
        final int delay = 1400;
        handler.postDelayed(new Runnable() {
            public void run() {
                // Hiển thị đoạn trích hiện tại
                tvSlide.setText(quotes[currentIndex]);

                // Tăng chỉ số hoặc quay lại nếu đã đến cuối mảng
                currentIndex = (currentIndex + 1) % quotes.length;

                // Lập lại animation sau khoảng thời gian delay
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    private void animationImage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ManHinhWelcome.this, DangNhap.class);
                startActivity(intent);
            }
        }, 5000);
    }

    private void animationText() {
        tvWelcome.setAlpha(0f);
        tvWelcome.animate()
                .translationY(0)
                .alpha(1f)
                .setDuration(2700)
                .setStartDelay(0);
    }


}