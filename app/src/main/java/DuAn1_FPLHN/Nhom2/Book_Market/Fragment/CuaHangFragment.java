package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import DuAn1_FPLHN.Nhom2.Book_Market.Adapter.SanPhamKHadpter;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.SanPhamDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TheLoaiDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.SanPham;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.TheLoai;
import DuAn1_FPLHN.Nhom2.Book_Market.R;


public class CuaHangFragment extends Fragment {
    ViewFlipper viewFlipper;
    RecyclerView recyclerSP, recyclerTL;
    ArrayList<SanPham> list = new ArrayList<>();
    ArrayList<TheLoai> listTL = new ArrayList<>();
    ArrayList<SanPham> listTemp = new ArrayList<>();
    SanPhamDAO sanPhamDAO;
    private SanPhamKHadpter sanPhamKHAdapter;
    private EditText ed_timkiem;
    LinearLayout btn_tatca, btn_bunpho, btn_com, btn_chao, btn_douong;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cuahang, container, false);

        viewFlipper = view.findViewById(R.id.viewFlipper);
        ed_timkiem = view.findViewById(R.id.ed_timkiem);

        btn_tatca = view.findViewById(R.id.btn_tatca);
        btn_bunpho = view.findViewById(R.id.btn_bunpho);
        btn_com = view.findViewById(R.id.btn_com);
        btn_chao = view.findViewById(R.id.btn_chao);
        btn_douong = view.findViewById(R.id.btn_douong);
        recyclerSP = view.findViewById(R.id.recyclerSP);
        sanPhamDAO = new SanPhamDAO(getContext());

//        loadDataSanPham();

        list = sanPhamDAO.getDSSanPham();
        listTemp = sanPhamDAO.getDSSanPham();

        ed_timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTensp().contains(s.toString())){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Phân loại sản phẩm
        phanLoaiSanPham();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerSP.setLayoutManager(linearLayoutManager);
        sanPhamKHAdapter = new SanPhamKHadpter(getContext(), list);
        recyclerSP.setAdapter(sanPhamKHAdapter);

        return view;
    }

    public void loadDataSanPham(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerSP.setLayoutManager(linearLayoutManager);

        list = sanPhamDAO.getDSSanPham();
        sanPhamKHAdapter = new SanPhamKHadpter(getContext(), list);
        recyclerSP.setAdapter(sanPhamKHAdapter);
    }

    public void loadDataTheLoai(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTL.setLayoutManager(linearLayoutManager);

        TheLoaiDAO theLoaiDAO = new TheLoaiDAO(getContext());
        listTL = theLoaiDAO.getDSTheLoai();
//        theLoaiKHAdapter = new TheLoaiKHAdapter(getContext(), listTL);
//        recyclerTL.setAdapter(theLoaiKHAdapter);
    }

    private void phanLoaiSanPham(){
        btn_tatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_com.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_chao.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_bunpho.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_douong.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTensp().contains("")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
            }
        });

        btn_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_com.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_chao.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_bunpho.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_douong.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTenloai().contains("Cơm")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
            }
        });

        btn_chao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_com.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_chao.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_bunpho.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_douong.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTenloai().contains("Cháo")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
            }
        });

        btn_bunpho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_com.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_chao.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_bunpho.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_douong.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTenloai().contains("Bún")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
            }
        });

        btn_douong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_com.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_chao.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_bunpho.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_douong.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTenloai().contains("Đồ uống")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}