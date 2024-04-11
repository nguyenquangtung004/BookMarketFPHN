package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import DuAn1_FPLHN.Nhom2.Book_Market.DAO.GioHangDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.GioHang;

public class ChiTietSanPham extends AppCompatActivity {
    //Khai Báo Các Thành Phần Giao Diện
    private TextView tv_tenSP, tv_giaSP, tv_motaSP, tv_soluong, tv_soluongtonkho;
    private ImageView img_anhSP, img_cong, img_tru, img_back;
    private TextView btn_themGioHang;
    private GioHangDAO gioHangDAO;
    private GioHang gioHang;
    private int soluong = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Log.d("ChiTietSanPham", "onCreate() đã được gọi");
        gioHangDAO = new GioHangDAO(this);
        //Ánh Xạ Giao Diện
        tv_tenSP = findViewById(R.id.tv_tenSP);
        tv_giaSP = findViewById(R.id.tv_giaSP);
        tv_motaSP = findViewById(R.id.tv_motasp);
        img_anhSP = findViewById(R.id.img_anhSP);
        btn_themGioHang = findViewById(R.id.btn_themGioHang);
        tv_soluong = findViewById(R.id.tv_soluong);
        tv_soluongtonkho = findViewById(R.id.tv_solgHangTon);

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

        if (getIntent().getBundleExtra("dataSP") != null){
            Bundle bundle = getIntent().getBundleExtra("dataSP");
            Log.d("ChiTietSanPham", "Bundle từ Intent không null");

            byte[] byte_anhSP = bundle.getByteArray("anhsp");
            String tenSP = bundle.getString("tensp");
            String motaSP = bundle.getString("motasp");
            Integer giaSP = bundle.getInt("giasp");
            Integer solgTonKho = bundle.getInt("soluongtonkho");
            Log.d("ChiTietSanPham", "Bundle từ Intent không null. Giá trị:");
            Log.d("ChiTietSanPham", "anhsp: " + Arrays.toString(byte_anhSP));
            Log.d("ChiTietSanPham", "tensp: " + tenSP);
            Log.d("ChiTietSanPham", "motasp: " + motaSP);
            Log.d("ChiTietSanPham", "giasp: " + giaSP);
            Log.d("ChiTietSanPham", "soluongtonkho: " + solgTonKho);

            Bitmap bitmapAnhSP = ConvertData.ConvertBitmap(byte_anhSP);
            img_anhSP.setImageBitmap(bitmapAnhSP);

            tv_tenSP.setText("Tên sản phẩm: "+tenSP);
            tv_giaSP.setText("Giá sản phẩm: "+String.valueOf(giaSP));
            tv_motaSP.setText("Mô tả: "+motaSP);
            tv_soluongtonkho.setText("Số lượng tồn kho: " + String.valueOf(solgTonKho));
            //Viết ở đây 1000 để test
            gioHang = new GioHang(tenSP, byte_anhSP, giaSP, soluong,solgTonKho);
            Log.d("ChiTietSanPham", "Khởi tạo đối tượng GioHang với các thông số sau:\n" +
                    "tenSP: " + tenSP + "\n" +
                    "giaSP: " + giaSP + "\n" +
                    "soluong: " + soluong + "\n" +
                    "soluongtonkho: " + solgTonKho);
            // Cập nhật số lượng hàng tồn kho ngay lập tức


            btn_themGioHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gioHang.getSoLuongTonKho() > 0) {
                        boolean themGioHang = gioHangDAO.themGioHang(gioHang);
                        if (themGioHang){
                            Log.d("ChiTietSanPham", "Thêm giỏ hàng thành công");
                            Toast.makeText(ChiTietSanPham.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("ChiTietSanPham", "Thêm giỏ hàng thất bại");
                            Toast.makeText(ChiTietSanPham.this, "Thêm giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("ChiTietSanPham", "Sản phẩm này đã hết hàng");
                        Toast.makeText(ChiTietSanPham.this, "Sản phẩm này đã hết hàng", Toast.LENGTH_SHORT).show();
                    }
                }

            });
            img_tru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Nếu giá trị số lượng lớn hơn 1 thì giảm giá trị số lượng
                    //xuống 1 đơn vị
                    if (soluong > 1) {
                        soluong--;
                        gioHang.setSoluong(soluong);
                        gioHang.setSoLuongTonKho(gioHang.getSoLuongTonKho() + 1);
                        tv_soluong.setText(String.valueOf(soluong));
                        tv_soluongtonkho.setText("Số lượng tồn kho: " + String.valueOf(gioHang.getSoLuongTonKho()));
                        Log.d("ChiTietSanPham", "Số lượng giảm xuống: " + soluong);
                    }
                }
            });

            //Ngược Lại
            img_cong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (soluong < gioHang.getSoLuongTonKho()) {
                        soluong++;
                        gioHang.setSoluong(soluong);
                        gioHang.setSoLuongTonKho(gioHang.getSoLuongTonKho() - 1);
                        tv_soluong.setText(String.valueOf(soluong));
                        tv_soluongtonkho.setText("Số lượng tồn kho: " + String.valueOf(gioHang.getSoLuongTonKho()));
                        Log.d("ChiTietSanPham", "Số lượng tăng lên: " + soluong);
                    } else {
                        Toast.makeText(ChiTietSanPham.this, "Không đủ hàng trong kho", Toast.LENGTH_SHORT).show();
                        Log.d("ChiTietSanPham", "Không đủ hàng trong kho");
                    }
                }
            });

        }



    }
}