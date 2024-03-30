package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.HoaDonKHAdapter;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.HoaDonDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;
import DuAn1_FPLHN.Nhom2.Book_Market.R;

public class HoaDonFragment extends Fragment {
    RecyclerView recyclerHoaDon;
    ArrayList<HoaDon> list = new ArrayList<>();
    HoaDonDAO hoaDonDAO;
    int matk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_hoa_don, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ThongTinTaiKhoan", MODE_PRIVATE);
        matk = sharedPreferences.getInt("matk", 0);

        recyclerHoaDon = view.findViewById(R.id.recyclerHD);
        hoaDonDAO = new HoaDonDAO(getContext());

        loadDataHoaDon();

        return view;
    }
    public void loadDataHoaDon() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerHoaDon.setLayoutManager(linearLayoutManager);

        list = hoaDonDAO.getDSHoaDonTheoKH(matk);
        HoaDonKHAdapter hoaDonKHAdapter = new HoaDonKHAdapter(getContext(), list);
        recyclerHoaDon.setAdapter(hoaDonKHAdapter);
    }

}