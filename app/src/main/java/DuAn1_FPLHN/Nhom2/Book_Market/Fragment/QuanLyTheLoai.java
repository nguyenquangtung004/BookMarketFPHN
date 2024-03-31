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

    private RecyclerView recyclerTheLoai;
    private TheLoaiDAO theLoaiDAO;
    private TheLoaiAdapter theLoaiAdapter;
    private ArrayList<TheLoai> list;
    private ArrayList<TheLoai> listTemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_the_loai, container, false);

        recyclerTheLoai = view.findViewById(R.id.recyclerTheLoai);
        EditText ed_search = view.findViewById(R.id.ed_timkiem);


        theLoaiDAO = new TheLoaiDAO(getContext());

        list = theLoaiDAO.getDSTheLoai();
        listTemp = theLoaiDAO.getDSTheLoai();
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
                    theLoaiAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTheLoai.setLayoutManager(linearLayoutManager);
        theLoaiAdapter = new TheLoaiAdapter(getContext(), list);
        recyclerTheLoai.setAdapter(theLoaiAdapter);

        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThemTheLoai();
            }
        });

        return view;
    }
    private void loadDataLoaiSach(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTheLoai.setLayoutManager(linearLayoutManager);

        ArrayList<TheLoai> list = theLoaiDAO.getDSTheLoai();

        TheLoaiAdapter theLoaiAdapter = new TheLoaiAdapter(getContext(), list);
        recyclerTheLoai.setAdapter(theLoaiAdapter);
    }

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