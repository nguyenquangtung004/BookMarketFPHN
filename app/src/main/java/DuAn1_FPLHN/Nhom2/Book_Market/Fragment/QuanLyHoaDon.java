package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.HoaDonAdapter;
import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.HoaDonKHAdapter;
import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.SanPhamKHadpter;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.HoaDonDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.SanPham;
import DuAn1_FPLHN.Nhom2.Book_Market.R;

public class QuanLyHoaDon extends Fragment {
    private RecyclerView recyclerHoaDon;
    private HoaDonDAO hoaDonDAO;
    private HoaDonAdapter hoaDonAdapter;
    private ArrayList<HoaDon> list;
    private ArrayList<HoaDon> listTemp;
    private HoaDonKHAdapter hoaDonKHAdapter;
    LinearLayout btn_qlhoadon_tatca, btn_dangxuly, btn_daxacnhan, btn_dagiao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.fragment_quan_ly_hoa_don, container, false);

        recyclerHoaDon = view.findViewById(R.id.recyclerHoaDon);
        EditText ed_search = view.findViewById(R.id.ed_timkiem);
        btn_qlhoadon_tatca = view.findViewById(R.id.btn_qlhoadon_tatca);
        btn_dangxuly = view.findViewById(R.id.btn_dangxuly);
        btn_daxacnhan = view.findViewById(R.id.btn_daxacnhan);
        //btn_dagiao = view.findViewById(R.id.btn_dagiao);

        hoaDonDAO = new HoaDonDAO(getContext());

//        loadDataHoaDon();

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
                    if ( String.valueOf(hoaDon.getMahd()).contains(s.toString()) || hoaDon.getHoten().contains(s.toString())){
                        list.add(hoaDon);
                    }
                    hoaDonKHAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //phan loai hoa don
        phanLoaiHoaDon();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerHoaDon.setLayoutManager(linearLayoutManager);
        hoaDonKHAdapter = new HoaDonKHAdapter(getContext(), list);
        recyclerHoaDon.setAdapter(hoaDonKHAdapter);

        return view;
    }


    private void phanLoaiHoaDon() {
        //Button 1 : Đã Hoàn Thiện Việc Thay Đổi Màu Tất Cả
        btn_qlhoadon_tatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_qlhoadon_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_blue_1));
                btn_dangxuly.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_daxacnhan.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                //btn_dagiao.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                list.clear();
                list.clear();
                for (HoaDon hoaDon : listTemp) {
                    String trangThai = String.valueOf(hoaDon.getTrangthai());
                    if (trangThai != null && trangThai.contains("")){
                        list.add(hoaDon);
                    }
                    //hoaDonKHAdapter.notifyDataSetChanged();
                }
                hoaDonKHAdapter.notifyDataSetChanged();
            }
        });
        // Button Đang xử lý
        btn_dangxuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_qlhoadon_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_dangxuly.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_blue_1));
                btn_daxacnhan.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                //btn_dagiao.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                list.clear();
                for (HoaDon hoaDon : listTemp) {
                    if (hoaDon.getTrangthai() == 0) {
                        list.add(hoaDon);
                    }
                }
                hoaDonKHAdapter.notifyDataSetChanged();
            }
        });
        // Button Đã xác nhận
        btn_daxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_qlhoadon_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_dangxuly.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_daxacnhan.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_blue_1));
                //btn_dagiao.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                list.clear();
                for (HoaDon hoaDon : listTemp) {
                    if (hoaDon.getTrangthai() == 1) {
                        list.add(hoaDon);
                    }
                }
                hoaDonKHAdapter.notifyDataSetChanged();
            }
        });

// Button Đã giao
//        btn_dagiao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn_qlhoadon_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
//                btn_dangxuly.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
//                btn_daxacnhan.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
//                btn_dagiao.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_blue_1));
//                list.clear();
//                for (HoaDon hoaDon : listTemp) {
//                    if (hoaDon.getTrangthai() == 2) {
//                        list.add(hoaDon);
//                    }
//                }
//                hoaDonKHAdapter.notifyDataSetChanged();
//            }
//        });
    }

    }
