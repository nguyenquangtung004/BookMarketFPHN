package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class ManHinhWelcome extends AppCompatActivity {
    private TextView animatedTextView;
    private String textChaoMung = "Chào cậu \nchào mừng đến với \nứng dụng của chúng tớ";
    private int charIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_welcome);
        animatedTextView = findViewById(R.id.animatedTextView);

        // Bắt đầu hiển thị từng chữ một
        animateText();
    }
    private void animateText() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (charIndex < textChaoMung.length()) {
                    String currentText = textChaoMung.substring(0, charIndex + 1);
                    animatedTextView.setText(currentText);
                    charIndex++;
                    animateText(); // Tiếp tục hiển thị từng chữ
                }
            }
        }, 200); // Đợi 200ms trước khi hiển thị chữ tiếp theo
    }

}