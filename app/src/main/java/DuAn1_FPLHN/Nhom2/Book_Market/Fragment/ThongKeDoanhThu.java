package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import DuAn1_FPLHN.Nhom2.Book_Market.DAO.HoaDonDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.SanPhamDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TaiKhoanDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.ThongKeDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;
import DuAn1_FPLHN.Nhom2.Book_Market.R;


public class ThongKeDoanhThu extends Fragment {

    private EditText ed_ngaybatdau, ed_ngayketthuc;
    private TextView tv_tongtaikhoan, tv_tongsanpham, tv_tonghoadon, tv_tongdoanhthu, tv_slhoadon, tv_doanthu;
    private TextView btn_thongke;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_thong_ke_doanh_thu, container, false);


        ed_ngaybatdau = view.findViewById(R.id.ed_ngaybatdau);
        ed_ngayketthuc = view.findViewById(R.id.ed_ngayketthuc);

        tv_doanthu = view.findViewById(R.id.tv_doanhthu);
        tv_tonghoadon = view.findViewById(R.id.tv_tonghoadon);
        tv_tongsanpham = view.findViewById(R.id.tv_tongsanpham);
        tv_tongdoanhthu = view.findViewById(R.id.tv_tongdoanhthu);
        tv_tongtaikhoan = view.findViewById(R.id.tv_tongtaikhoan);
        tv_slhoadon = view.findViewById(R.id.tv_slhoadon);

        btn_thongke = view.findViewById(R.id.btn_thongke);

        Calendar calendar = Calendar.getInstance();//lay ngay hien tai
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(getContext());
        SanPhamDAO sanPhamDAO = new SanPhamDAO(getContext());
        HoaDonDAO hoaDonDAO = new HoaDonDAO(getContext());
        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());



        //Ngay bat dau
        ed_ngaybatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if (dayOfMonth <10 ){
                            ngay = "0" + dayOfMonth;
                        }else {
                            ngay = String.valueOf(dayOfMonth);
                        }
                        if ( (month + 1) < 10){
                            thang = "0" + (month+1);
                        } else {
                            thang = String.valueOf(month+1);
                        }
                        ed_ngaybatdau.setText(year + "/" +thang +"/" +ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONDAY),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();

            }
        });


        //ngay ket thuc
        ed_ngayketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker viewr, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if (dayOfMonth < 10){
                            ngay = "0" + dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }
                        if ( (month + 1) < 10){
                            thang = "0" + (month+1);
                        } else {
                            thang = String.valueOf(month+1);
                        }
                        ed_ngayketthuc.setText(year + "/" + thang + "/" + ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        tv_tongtaikhoan.setText(taiKhoanDAO.getDSTaiKhoan().size() + "tài khoản");
        tv_tongsanpham.setText(sanPhamDAO.getDSSanPham().size() + "sản phẩm");
        tv_tonghoadon.setText(hoaDonDAO.getDSHoaDon().size() + " đơn");
        tv_tongdoanhthu.setText(thongKeDAO.getTongDoanhThu() + " VNĐ");

        btn_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());

                String ngaybatdau = ed_ngaybatdau.getText().toString();
                String ngayketthuc = ed_ngayketthuc.getText().toString();

                int doanhthu = thongKeDAO.getDoanhThu(ngaybatdau, ngayketthuc);
                ArrayList<HoaDon> listHD = thongKeDAO.getHoaDonTheoNgay(ngaybatdau, ngayketthuc);

                tv_doanthu.setText(doanhthu + "VND");
                tv_slhoadon.setText(listHD.size() + "đơn");
            }
        });

        return view;
    }
}