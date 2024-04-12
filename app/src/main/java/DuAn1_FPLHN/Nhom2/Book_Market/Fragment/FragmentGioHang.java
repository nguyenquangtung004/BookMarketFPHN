package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.GioHangAdapter;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.GioHangDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.HoaDonDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TaiKhoanDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.GioHang;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.TaiKhoan;
import DuAn1_FPLHN.Nhom2.Book_Market.R;


public class FragmentGioHang extends Fragment {

    private RecyclerView recyclerGioHang;
    private GioHangDAO gioHangDAO;
    private HoaDonDAO hoaDonDAO;
    private TextView tv_tongtien;
    private TextView btn_dathang;
    private int tongtien = 0;
    private String tongsanpham = "";
    private ArrayList<GioHang> list = new ArrayList<>();
    private LinearLayout linear_giohangtrong;
    private GioHangAdapter gioHangAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.fragment_g_io_hang, container, false);

        linear_giohangtrong = view.findViewById(R.id.linear_giohangtrong);

        recyclerGioHang = view.findViewById(R.id.recyclerGioHang);
        tv_tongtien = view.findViewById(R.id.tv_tongtien);
        btn_dathang = view.findViewById(R.id.btn_dathang);

        gioHangDAO = new GioHangDAO(getContext());
        hoaDonDAO = new HoaDonDAO(getContext());

        loadDataGioHang();
        tinhTongTien();

        if (list.size() > 0) {
            linear_giohangtrong.setVisibility(View.GONE);
        }

        btn_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHangAdapter.notifyDataSetChanged();
                if (list.size() <= 0) {
                    Toast.makeText(getContext(), "Bạn chưa chọn sản phẩm nào", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lấy mã khách hàng
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("ThongTinTaiKhoan", MODE_PRIVATE);
                int matk = sharedPreferences.getInt("matk", 0);

                TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(getContext());
                TaiKhoan taiKhoan = taiKhoanDAO.getThongTinTaiKhoan(matk);

                showDiaLogDatHang(taiKhoan);
            }
        });

        return view;

    }

    private void loadDataGioHang() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerGioHang.setLayoutManager(linearLayoutManager);

        list = gioHangDAO.getDSGioHang();
        gioHangAdapter = new GioHangAdapter(getContext(), list, new GioHangAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged() {
                // Khi dữ liệu thay đổi, tính lại tổng tiền và cập nhật TextView
                tinhTongTien();
                updateTongTienTextView();
            }
        });
        recyclerGioHang.setAdapter(gioHangAdapter);
        // Sau khi cập nhật danh sách giỏ hàng, tính lại tổng tiền và tổng số sản phẩm
        tinhTongTien();
        updateTongTienTextView();
    }

    private void showDiaLogDatHang(TaiKhoan taiKhoan) {
        BottomSheetDialog dialogTT = new BottomSheetDialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_xacnhan_thanhtoan, null);
        dialogTT.setContentView(view);
        dialogTT.setCanceledOnTouchOutside(false);

        EditText ed_hoten = view.findViewById(R.id.ed_hoten);
        EditText ed_sdt = view.findViewById(R.id.ed_sdt);
        EditText ed_diachi = view.findViewById(R.id.ed_diachi);
        TextView btn_dathang = view.findViewById(R.id.btn_dathang);
        TextView btn_huy = view.findViewById(R.id.btn_huy);

        ed_hoten.setText(taiKhoan.getHoten());
        ed_sdt.setText(taiKhoan.getSdt());
        ed_diachi.setText(taiKhoan.getDiachi());

        btn_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int matk = taiKhoan.getMatk();
                String hoten = ed_hoten.getText().toString();
                String sdt = ed_sdt.getText().toString();
                String diachi = ed_diachi.getText().toString();

                if (hoten.isEmpty()){
                    ed_hoten.setError("Vui lòng nhập họ tên");
                    return;
                }
                if (sdt.isEmpty()){
                    ed_sdt.setError("Vui lòng nhập số điện thoại");
                    return;
                }
                if (diachi.isEmpty()){
                    ed_diachi.setError("Vui lòng nhập địa chỉ");
                    return;
                }

                // Lấy gày hiện tại
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String ngay = simpleDateFormat.format(currentTime);

                HoaDon hoaDon = new HoaDon(ngay, matk, hoten, sdt, diachi , tongtien, tongsanpham, 0);

                boolean checkThemHD = hoaDonDAO.themHoaDon(hoaDon);
                if (checkThemHD){
                    Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    boolean checkClear = gioHangDAO.clearGioHang();
                    if (checkClear){
                        loadDataGioHang();
                        tongtien = 0;
                        tongsanpham = "";
                        tv_tongtien.setText(tongtien + " VNĐ");
                        linear_giohangtrong.setVisibility(View.VISIBLE);
                        dialogTT.dismiss();
                    }
                } else {
                    Toast.makeText(getContext(), "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogTT.show();
    }

    public void tinhTongTien() {
        tongtien = 0; // Reset tổng tiền về 0 trước khi tính lại
        tongsanpham = ""; // Reset chuỗi tổng sản phẩm về rỗng
        for (GioHang gioHang : list) {
            int soluong = gioHang.getSoluong();
            int gia = gioHang.getGiasp();
            tongtien += (soluong * gia);
            tongsanpham += gioHang.getTensp() + " x" + gioHang.getSoluong() + " ";
        }
    }

    private void updateTongTienTextView() {
        tv_tongtien.setText(tongtien + " VNĐ");
    }
}