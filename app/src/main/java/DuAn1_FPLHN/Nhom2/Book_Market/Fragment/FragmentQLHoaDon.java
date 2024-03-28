package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_quanly_hoadon,container,false);

        recyclerHoaDon = view.findViewById(R.id.recyclerHoaDon);
        EditText ed_search = view.findViewById(R.id.ed_timkiem);

        hoaDonDAO = new HoaDonDAO(getContext());

        //loadDataHoaDon

        list = hoaDonDAO.getDSHoaDon();
        listTemp = hoaDonDAO.getDSHoaDon();
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.clear();
                for (HoaDon hoaDon : listTemp){
                    if (String.valueOf(hoaDon.getMahd()).contains(s.toString()) || hoaDon.getHoten().contains(s.toString())){
                        list.add(hoaDon);
                    }
                    hoaDonAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
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

        HoaDonAdapter hoaDonAdapter = new HoaDonAdapter(getContext(), list);
        recyclerHoaDon.setAdapter(hoaDonAdapter);
    }
}
