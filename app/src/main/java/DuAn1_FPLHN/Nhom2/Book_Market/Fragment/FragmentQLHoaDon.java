package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.HoaDonAdapter;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.HoaDonDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;
import DuAn1_FPLHN.Nhom2.Book_Market.R;

public class FragmentQLHoaDon extends Fragment {

    private RecyclerView recyclerHoaDon;
    private HoaDonDAO hoaDonDAO;
    private HoaDonAdapter hoaDonAdapter;
    private ArrayList<HoaDon> list;
    private ArrayList<HoaDon> listTemp;
    Button btnTatCa,btnChuaXacNhan,btnDaXacNhan;

    // Định nghĩa các giá trị Integer cho các trạng thái
    private static final int TRANG_THAI_ĐA_XAC_NHAN = 1;
    private static final int TRANG_THAI_CHUA_XAC_NHAN = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.fragment_quan_ly_hoa_don, container, false);

        // Khởi tạo các thành phần
        recyclerHoaDon = view.findViewById(R.id.recyclerHoaDon);
        EditText ed_search = view.findViewById(R.id.ed_timkiem);
        btnChuaXacNhan = view.findViewById(R.id.btn_chuaxn);
        btnDaXacNhan = view.findViewById(R.id.btn_daxn);
        hoaDonDAO = new HoaDonDAO(getContext());

        // Load dữ liệu hóa đơn
        list = hoaDonDAO.getDSHoaDon();
        listTemp = hoaDonDAO.getDSHoaDon();
        Log.d("FragmentQLHoaDon", "Đã tải " + list.size() + " hóa đơn từ cơ sở dữ liệu");

        // Xử lý tìm kiếm
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Tìm kiếm và cập nhật kết quả
                list.clear();
                for (HoaDon hoaDon : listTemp){
                    if ( String.valueOf(hoaDon.getMahd()).contains(s.toString()) || hoaDon.getHoten().contains(s.toString())){
                        list.add(hoaDon);
                    }
                    hoaDonAdapter.notifyDataSetChanged();
                }
                Log.d("FragmentQLHoaDon", "Kết quả tìm kiếm được cập nhật");
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Xử lý khi người dùng chọn các trạng thái khác nhau
        btnDaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lọc dữ liệu cho tất cả trạng thái
                filterData(TRANG_THAI_ĐA_XAC_NHAN);
                Log.d("FragmentQLHoaDon", "Đã lọc dữ liệu cho trạng thái 'Đã xác nhận'");
            }
        });

        btnChuaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lọc dữ liệu cho trạng thái "Chưa Xác nhận"
                filterData(TRANG_THAI_CHUA_XAC_NHAN);
                Log.d("FragmentQLHoaDon", "Đã lọc dữ liệu cho trạng thái 'Chưa xác nhận'");
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerHoaDon.setLayoutManager(linearLayoutManager);
        hoaDonAdapter = new HoaDonAdapter(getContext(), list);
        recyclerHoaDon.setAdapter(hoaDonAdapter);

        return view;
    }

    private void loadDataHoaDon(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerHoaDon.setLayoutManager(linearLayoutManager);
        list = hoaDonDAO.getDSHoaDon();
        Log.d("FragmentQLHoaDon", "Đã tải " + list.size() + " hóa đơn từ cơ sở dữ liệu");

        HoaDonAdapter hoaDonAdapter = new HoaDonAdapter(getContext(), list);
        recyclerHoaDon.setAdapter(hoaDonAdapter);
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
        Log.d("FragmentQLHoaDon", "Đã lọc " + filteredList.size() + " hóa đơn theo trạng thái");
    }


}