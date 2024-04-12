package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.HoaDonKHAdapter;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.HoaDonDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;
import DuAn1_FPLHN.Nhom2.Book_Market.R;

public class FragmentHoaDon extends Fragment {

    RecyclerView recyclerHoaDon;
    ArrayList<HoaDon> list = new ArrayList<>();
    HoaDonDAO hoaDonDAO;
    int matk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_hoa_don, container, false);

        // Lấy thông tin tài khoản người dùng
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ThongTinTaiKhoan", MODE_PRIVATE);
        matk = sharedPreferences.getInt("matk", 0);
        recyclerHoaDon = view.findViewById(R.id.recyclerHD);
        hoaDonDAO = new HoaDonDAO(getContext());
        // Load dữ liệu hóa đơn
        loadDataHoaDon();
        return view;
    }

    // Hàm này dùng để tải dữ liệu hóa đơn từ cơ sở dữ liệu
    public void loadDataHoaDon() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerHoaDon.setLayoutManager(linearLayoutManager);

        // Truy vấn cơ sở dữ liệu để lấy danh sách hóa đơn
        list = hoaDonDAO.getDSHoaDonTheoKH(matk);
        HoaDonKHAdapter hoaDonKHAdapter = new HoaDonKHAdapter(getContext(), list);
        recyclerHoaDon.setAdapter(hoaDonKHAdapter);
        // Log kết quả
        Log.d("FragmentHoaDon", "Đã tải " + list.size() + " hóa đơn từ cơ sở dữ liệu");
    }

}
