package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.TheLoaiAdapter;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TheLoaiDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.TheLoai;
import DuAn1_FPLHN.Nhom2.Book_Market.R;


public class QuanLyTheLoai extends Fragment {
    // Khai báo các biến
    private RecyclerView recyclerTheLoai;
    private TheLoaiDAO theLoaiDAO;
    private TheLoaiAdapter theLoaiAdapter;
    private ArrayList<TheLoai> list;
    private ArrayList<TheLoai> listTemp;

    // Hàm khởi tạo view cho fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_the_loai, container, false);

        // Tìm view bởi ID
        recyclerTheLoai = view.findViewById(R.id.recyclerTheLoai);
        EditText ed_search = view.findViewById(R.id.ed_timkiem);

        // Khởi tạo DAO
        theLoaiDAO = new TheLoaiDAO(getContext());

        // Lấy danh sách thể loại từ DAO
        list = theLoaiDAO.getDSTheLoai();
        listTemp = theLoaiDAO.getDSTheLoai();

        // Thêm TextWatcher cho EditText search
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.clear();
                for (TheLoai theLoai : listTemp){
                    if ( String.valueOf(theLoai.getMaloai()).contains(s.toString()) || theLoai.getTenloai().contains(s.toString())){
                        list.add(theLoai);
                    }
                }
                theLoaiAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Thiết lập LayoutManager và Adapter cho RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTheLoai.setLayoutManager(linearLayoutManager);
        theLoaiAdapter = new TheLoaiAdapter(getContext(), list);
        recyclerTheLoai.setAdapter(theLoaiAdapter);

        // FloatingActionButton
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThemTheLoai();
            }
        });

        return view;
    }

    // Hàm tải dữ liệu loại sách và cập nhật RecyclerView
    private void loadDataLoaiSach(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTheLoai.setLayoutManager(linearLayoutManager);

        ArrayList<TheLoai> list = theLoaiDAO.getDSTheLoai();

        TheLoaiAdapter theLoaiAdapter = new TheLoaiAdapter(getContext(), list);
        recyclerTheLoai.setAdapter(theLoaiAdapter);
    }

    // Hàm hiển thị Dialog để thêm thể loại
    private void showDialogThemTheLoai(){
        BottomSheetDialog dialogThem = new BottomSheetDialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_them_theloai, null );
        dialogThem.setContentView(view);
        dialogThem.setCanceledOnTouchOutside(false);

        EditText ed_tenloai = view.findViewById(R.id.ed_tenloai);
        TextView btn_add = view.findViewById(R.id.btn_add);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = ed_tenloai.getText().toString();

                // Kiểm tra trống
                if (tenloai.isEmpty()) {
                    Toast.makeText(getContext(), "Tên thể loại không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra độ dài
                if (tenloai.length() > 20) {
                    Toast.makeText(getContext(), "Tên thể loại không được vượt quá 20 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean KiemTra = theLoaiDAO.themTheLoai(tenloai);
                if (KiemTra){
                    Toast.makeText(getContext(), "Thêm thể loại thành công", Toast.LENGTH_SHORT).show();
                    loadDataLoaiSach();
                    dialogThem.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm thể loại thất bại", Toast.LENGTH_SHORT).show();
                    dialogThem.dismiss();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThem.dismiss();
            }
        });
        dialogThem.show();
    }
}
