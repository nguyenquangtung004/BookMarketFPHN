package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.HoaDonAdapter;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.HoaDonDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;
import DuAn1_FPLHN.Nhom2.Book_Market.R;


public class LichSuMuaHangFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<HoaDon> danhSachDonHang;
    private HoaDonAdapter hoaDonAdapter;
    private HoaDonDAO hoaDonDAO;
    private ArrayList<HoaDon> list;
    private ArrayList<HoaDon> listTemp;

    private Button btnAll;
    private Button btnReceived;
    private Button btnNotReceived;
    // Định nghĩa các giá trị Integer cho các trạng thái
    private static final int TRANG_THAI_ĐA_XAC_NHAN = 1;
    private static final int TRANG_THAI_CHUA_XAC_NHAN = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lich_su_mua_hang, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        btnAll = view.findViewById(R.id.btnAll);
        btnReceived = view.findViewById(R.id.btnReceived);
        btnNotReceived = view.findViewById(R.id.btnNotReceived);
        danhSachDonHang = new ArrayList<>();
        hoaDonDAO = new HoaDonDAO(getContext());

        // Lấy danh sách đơn hàng từ cơ sở dữ liệu
        danhSachDonHang = hoaDonDAO.getDSHoaDon(); // Đây là một phương thức tùy thuộc vào cách bạn triển khai DAO

        // Khởi tạo adapter và thiết lập cho RecyclerView
        hoaDonAdapter = new HoaDonAdapter(getContext(), danhSachDonHang);
        recyclerView.setAdapter(hoaDonAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = hoaDonDAO.getDSHoaDon();
        listTemp = hoaDonDAO.getDSHoaDon();


        // Lắng nghe sự kiện click của các nút Button
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danhSachDonHang = hoaDonDAO.getDSHoaDon();
                hoaDonAdapter.updateData(danhSachDonHang);
            }
        });

        btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lọc dữ liệu cho tất cả trạng thái
                filterData(TRANG_THAI_ĐA_XAC_NHAN);
            }
        });

        btnNotReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData(TRANG_THAI_CHUA_XAC_NHAN);
            }
        });
        return view;
    }

    // Hàm lọc dữ liệu
    public void filterData(int trangThai) {
        // Tạo một danh sách mới để chứa kết quả lọc
        ArrayList<HoaDon> filteredList = new ArrayList<>();

        // Duyệt qua danh sách hóa đơn hiện tại
        for (HoaDon hoaDon : list) {

            // Nếu trạng thái của hóa đơn khớp với trạng thái cần lọc
            if (hoaDon.getTrangthai() == trangThai) {
                // Thêm hóa đơn vào danh sách lọc
                filteredList.add(hoaDon);
            }

        }

        // Cập nhật danh sách hiển thị với danh sách đã lọc
        hoaDonAdapter.updateData(filteredList);
    }
}