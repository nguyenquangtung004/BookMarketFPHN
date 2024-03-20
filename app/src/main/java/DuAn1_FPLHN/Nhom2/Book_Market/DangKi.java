package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TaiKhoanDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.TaiKhoan;

public class DangKi extends AppCompatActivity {

    private  TextView tvDkTitle;

    private TextInputEditText tiedt_dk_tk, tiedt_dk_ps, tiedt_dk_reps, tiedt_dk_ht, tiedt_dk_sdt, tiedt_dk_dc;
    private Button btn_dk_dk, btn_dk_tv;
    private  String tvstrDK= "Đăng Kí Tài Khoản";
    private int soKiTu = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        //anh xa
        tvDkTitle = findViewById(R.id.tv_dk_title);
        tiedt_dk_tk = findViewById(R.id.tiedt_dk_tk);
        tiedt_dk_ps = findViewById(R.id.tiedt_dk_ps);
        tiedt_dk_reps = findViewById(R.id.tiedt_dk_reps);
        tiedt_dk_ht = findViewById(R.id.tiedt_dk_ht);
        tiedt_dk_sdt = findViewById(R.id.tiedt_dk_sdt);
        tiedt_dk_dc = findViewById(R.id.tiedt_dk_dc);
        btn_dk_dk = findViewById(R.id.btn_dk_dk);
        btn_dk_tv = findViewById(R.id.btn_dk_tv);
        // Bắt đầu hiển thị từng chữ một
        animateText();

        //
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(this);
        btn_dk_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taikhoan = tiedt_dk_tk.getText().toString();
                String matkhau = tiedt_dk_ps.getText().toString();
                String matkhau_confirm = tiedt_dk_reps.getText().toString();
                String hoten = tiedt_dk_ht.getText().toString();
                String diachi = tiedt_dk_dc.getText().toString();
                String sdt = tiedt_dk_sdt.getText().toString();
                String loaitaikhoan = "khách hàng";

                //dieu kien
                if (taikhoan.isEmpty()){
                    tiedt_dk_tk.setError("Vui long nhap tai khoan");
                    return;
                }
                if (matkhau.isEmpty()){
                    tiedt_dk_ps.setError("Vui long nhap mat khau");
                    return;
                }
                if (matkhau_confirm.isEmpty()){
                    tiedt_dk_reps.setError("Vui long nhap lai mat khau");
                    return;
                }
                if (hoten.isEmpty()){
                    tiedt_dk_ht.setError("Vui long nhap ho ten");
                    return;
                }
                if (sdt.isEmpty()){
                    tiedt_dk_sdt.setError("Vui long nhap SDT");
                    return;
                }
                if (diachi.isEmpty()){
                    tiedt_dk_dc.setError("Vui long nhap dia chi");
                    return;
                }
                if (!matkhau.equals(matkhau_confirm)){
                    tiedt_dk_reps.setError("Khong trung mat khau");
                    return;
                }

                TaiKhoan taiKhoan = new TaiKhoan(taikhoan, matkhau, hoten, sdt, diachi, loaitaikhoan);

                if (taiKhoanDAO.checkDangKi(taiKhoan)){
                    Toast.makeText(DangKi.this, "Dang ki thanh cong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DangKi.this, DangNhap.class));
                }else {
                    Toast.makeText(DangKi.this, "Dang ki that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
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