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
import android.util.Log;
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
    LinearLayout btn_tatca, btn_thieunhi, btn_tailieu, btn_ngungon, btn_truyen;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cuahang, container, false);
        //Thực Hiên Ánh Xạ Banner và Tìm Kiếm
        viewFlipper = view.findViewById(R.id.viewFlipper);
        ed_timkiem = view.findViewById(R.id.ed_timkiem);

        // Thực Hiện Ánh Xạ Các Nút Button Trên HorizontalScrollView
        btn_tatca = view.findViewById(R.id.btn_tatca);
        btn_thieunhi = view.findViewById(R.id.btn_thieunhi);
        btn_tailieu = view.findViewById(R.id.btn_tailieu);
        btn_ngungon = view.findViewById(R.id.btn_ngungon);
        btn_truyen = view.findViewById(R.id.btn_truyen);
        recyclerSP = view.findViewById(R.id.recyclerSP);
        sanPhamDAO = new SanPhamDAO(getContext());

        list = sanPhamDAO.getDSSanPham();
        listTemp = sanPhamDAO.getDSSanPham();

        // Cài đặt bộ lắng nghe để cập nhật danh sách khi người dùng nhập vào thanh tìm kiếm
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

        // Phân loại sản phẩm dựa trên sự lựa chọn của người dùng
        phanLoaiSanPham();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerSP.setLayoutManager(linearLayoutManager);
        sanPhamKHAdapter = new SanPhamKHadpter(getContext(), list);
        recyclerSP.setAdapter(sanPhamKHAdapter);

        return view;
    }

    // Hàm này dùng để tải lại danh sách sản phẩm từ cơ sở dữ liệu
    public void loadDataSanPham(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerSP.setLayoutManager(linearLayoutManager);

        list = sanPhamDAO.getDSSanPham();
        sanPhamKHAdapter = new SanPhamKHadpter(getContext(), list);
        recyclerSP.setAdapter(sanPhamKHAdapter);
        Log.d("CuaHangFragment", "Loaded " + list.size() + " sản phẩm từ cơ sở dữ liệu");
    }

    // Hàm này dùng để tải lại danh sách thể loại từ cơ sở dữ liệu
    public void loadDataTheLoai(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTL.setLayoutManager(linearLayoutManager);

        TheLoaiDAO theLoaiDAO = new TheLoaiDAO(getContext());
        listTL = theLoaiDAO.getDSTheLoai();
        Log.d("CuaHangFragment", "Loaded " + listTL.size() + " thể loại từ cơ sở dữ liệu");
    }

    // Hàm này dùng để phân loại sản phẩm dựa trên sự lựa chọn của người dùng
    private void phanLoaiSanPham(){
        // Button 1 : Tất cả sản phẩm
        btn_tatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_thieunhi.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_tailieu.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_ngungon.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                btn_truyen.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.echo_blue));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTensp().contains("")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
                Log.d("CuaHangFragment", "Hiển thị tất cả sản phẩm");
            }
        });

        // Button 2: Thiếu nhi
        btn_thieunhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_thieunhi.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_blue_1));
                btn_tailieu.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_ngungon.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_truyen.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTenloai().contains("Thiếu nhi")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
                Log.d("CuaHangFragment", "Hiển thị sản phẩm theo thể loại Thiếu nhi");
            }
        });

        // Button 3: Tài liệu
        btn_tailieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_thieunhi.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_tailieu.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_blue_1));
                btn_ngungon.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_truyen.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTenloai().contains("Tài liệu")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
                Log.d("CuaHangFragment", "Hiển thị sản phẩm theo thể loại Tài liệu");
            }
        });

        // Button 4: Ngụ ngôn
        btn_ngungon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_thieunhi.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_tailieu.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_ngungon.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_blue_1));
                btn_truyen.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTenloai().contains("Ngụ ngôn")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
                Log.d("CuaHangFragment", "Hiển thị sản phẩm theo thể loại Ngụ ngôn");
            }
        });

        //Button 5: Đã Hoàn Thiện Việc Thay Đổi Màu Nút Truyện
        btn_truyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_tatca.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_thieunhi.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_tailieu.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_ngungon.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.plantinum));
                btn_truyen.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_blue_1));
                list.clear();
                for (SanPham sanPham : listTemp){
                    if (sanPham.getTenloai().contains("Truyện")){
                        list.add(sanPham);
                    }
                    sanPhamKHAdapter.notifyDataSetChanged();
                }
                Log.d("CuaHangFragment", "Hiển thị sản phẩm theo thể loại Truyện");
            }
        });


    }

}