package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TaiKhoanDAO;

public class QuenMatKhau extends AppCompatActivity {

    EditText ed_taikhoan, ed_matkhaumoi, ed_matkhaumoi_confirm;
    TextView btn_quenmatkhau, btn_quenmatkhau_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);

        //anh xa
        ed_taikhoan = findViewById(R.id.ed_taikhoan);
        ed_matkhaumoi = findViewById(R.id.ed_matkhaumoi);
        ed_matkhaumoi_confirm = findViewById(R.id.ed_matkhaumoi_confirm);
        btn_quenmatkhau = findViewById(R.id.btn_quenmatkhau);
        btn_quenmatkhau_tv = findViewById(R.id.btn_quenmatkhau_tv);


        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(this);

        btn_quenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taikhoan = ed_taikhoan.getText().toString();
                String matkhaumoi = ed_matkhaumoi.getText().toString();
                String matkhaumoi_confirm = ed_matkhaumoi_confirm.getText().toString();

                if (taikhoan.isEmpty()){
                    ed_taikhoan.setError("Vui long nhap tai khoan");
                    return;
                }
                if (matkhaumoi.isEmpty()){
                    ed_matkhaumoi.setError("Vui long nhap mat khau moi");
                    return;
                }
                if (matkhaumoi_confirm.isEmpty()){
                    ed_matkhaumoi_confirm.setError("Vui long nhap lai mat khau");
                    return;
                }
                if (!matkhaumoi.equals(matkhaumoi_confirm)){
                    ed_matkhaumoi_confirm.setError("Mat khau khong trung nhau");
                    return;
                }
                int check = taiKhoanDAO.quenMatKhau(taikhoan, matkhaumoi);
                if (check == -1){
                    Toast.makeText(QuenMatKhau.this, "Khong ton tai tai khoan nhu tren", Toast.LENGTH_SHORT).show();
                }else if (check == 0){
                    Toast.makeText(QuenMatKhau.this, "Thay doi mat khau that bai", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(QuenMatKhau.this, "Thay doi mat khau thanh cong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(QuenMatKhau.this, DangNhap.class));
                }
            }
        });

        btn_quenmatkhau_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuenMatKhau.this, DangNhap.class);
                startActivity(intent);
            }
        });
    }
}