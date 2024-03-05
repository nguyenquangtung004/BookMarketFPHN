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
        tvWelcome = findViewById(R.id.tv_wc_cm);

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
        tvWelcome.setAlpha(0f);
        tvWelcome.animate()
                .translationY(0)
                .alpha(1f)
                .setDuration(2700)
                .setStartDelay(0);
    }


}