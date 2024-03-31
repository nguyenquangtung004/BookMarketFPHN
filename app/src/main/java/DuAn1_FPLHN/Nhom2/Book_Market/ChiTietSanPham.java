package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import DuAn1_FPLHN.Nhom2.Book_Market.DAO.GioHangDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.GioHang;

public class ChiTietSanPham extends AppCompatActivity {
    //Khai Báo Các Thành Phần Giao Diện
    private TextView tv_tenSP, tv_giaSP, tv_motaSP, tv_soluong;
    private ImageView img_anhSP, img_cong, img_tru, img_back;
    private TextView btn_themGioHang, btn_cuaHang;
    private GioHangDAO gioHangDAO;
    private int soluong = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        gioHangDAO = new GioHangDAO(this);
        //Ánh Xạ Giao Diện
        tv_tenSP = findViewById(R.id.tv_tenSP);
        tv_giaSP = findViewById(R.id.tv_giaSP);
        tv_motaSP = findViewById(R.id.tv_motasp);
        img_anhSP = findViewById(R.id.img_anhSP);
        btn_themGioHang = findViewById(R.id.btn_themGioHang);
        tv_soluong = findViewById(R.id.tv_soluong);
        img_back = findViewById(R.id.img_back);
        img_cong = findViewById(R.id.img_cong);
        img_tru = findViewById(R.id.img_tru);

        //Nút Quay Trở Về
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Số lượng sản phẩm
        tv_soluong.setText(String.valueOf(soluong));
        img_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nếu giá trị số lượng lớn hơn 1 thì giảm giá trị số lượng
                //xuống 1 đơn vị
                if (soluong > 1) {
                    soluong --;
                    tv_soluong.setText(String.valueOf(soluong));
                }
            }
        });

        //Ngược Lại
        img_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluong ++;
                tv_soluong.setText(String.valueOf(soluong));
            }
        });

        if (getIntent().getBundleExtra("dataSP") != null){
            Bundle bundle = getIntent().getBundleExtra("dataSP");

            byte[] byte_anhSP = bundle.getByteArray("anhsp");
            String tenSP = bundle.getString("tensp");
            String motaSP = bundle.getString("motasp");
            Integer giaSP = bundle.getInt("giasp");

            Bitmap bitmapAnhSP = ConvertData.ConvertBitmap(byte_anhSP);
            img_anhSP.setImageBitmap(bitmapAnhSP);

            tv_tenSP.setText("Tên sản phẩm: "+tenSP);
            tv_giaSP.setText("Giá sản phẩm: "+String.valueOf(giaSP));
            tv_motaSP.setText("Mô tả: "+motaSP);

            btn_themGioHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GioHang gioHang = new GioHang(tenSP, byte_anhSP, giaSP, soluong);
                    boolean themGioHang = gioHangDAO.themGioHang(gioHang);
                    if (themGioHang){
                        Toast.makeText(ChiTietSanPham.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChiTietSanPham.this, "Thêm giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}