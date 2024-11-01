package DuAn1_FPLHN.Nhom2.Book_Market.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;

import DuAn1_FPLHN.Nhom2.Book_Market.ConvertData;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.SanPhamDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TheLoaiDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.SanPham;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.TheLoai;
import DuAn1_FPLHN.Nhom2.Book_Market.R;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<SanPham> list;
    // Khởi tạo adapter với context và danh sách sản phẩm
    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    // Tạo ViewHolder mới
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolder(view);
    }
    // Gán dữ liệu cho ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SanPham sanPham = list.get(position);
        byte[] image = sanPham.getAnhsp();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.img_anhsp.setImageBitmap(bitmap);
        holder.tv_masp.setText("Mã: " + sanPham.getMasp());
        holder.tv_tensp.setText(sanPham.getTensp());
        holder.tv_loaisp.setText("Loại: " + sanPham.getTenloai());
        String motasp = sanPham.getMotasp();
        if (motasp.length() > 40){
            String mota = motasp.substring(0, 40);
            holder.tv_motasp.setText("Mô tả: " + mota);
        } else {
            holder.tv_motasp.setText("Mô tả: " + motasp);
        }
        holder.tv_giasp.setText("Giá: "+sanPham.getGiasp() + " VNĐ");
        holder.tv_soluongTonKho.setText("Số lượng tồn kho: " + sanPham.getSoLuongTonKho());

        // Thiết lập menu context cho từng item
        holder.img_chucnang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.img_chucnang);
                popupMenu.inflate(R.menu.menu_chucnang);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_edit){
                            showDialogSuaSP(sanPham);
                        }
                        if (item.getItemId() == R.id.menu_delete){
                            int maSP = sanPham.getMasp();
                            showDialogDelete(maSP);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }
    // Trả về số lượng item trong list
    @Override
    public int getItemCount() {
        return list.size();
    }
    // ViewHolder giữ các view cần thiết để hiển thị một item
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_anhsp, img_chucnang;
        TextView tv_tensp, tv_motasp, tv_giasp, tv_loaisp, tv_masp,tv_soluongTonKho;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            img_anhsp= itemView.findViewById(R.id.img_anhsp);
            tv_masp = itemView.findViewById(R.id.tv_masp);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            tv_motasp = itemView.findViewById(R.id.tv_motasp);
            tv_giasp = itemView.findViewById(R.id.tv_giasp);
            tv_loaisp = itemView.findViewById(R.id.tv_loaisp);
            tv_soluongTonKho = itemView.findViewById(R.id.tv_soluongTonKho);
            img_chucnang = itemView.findViewById(R.id.img_chucnang);
        }
    }

    // Hiển thị dialog để sửa thông tin sản phẩm
    private void showDialogSuaSP(SanPham sanPham){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sua_sanpham, null);
        BottomSheetDialog sheetDialog = new BottomSheetDialog(context);
        sheetDialog.setContentView(view);
        sheetDialog.setCanceledOnTouchOutside(false);

        // anh xa
        EditText ed_tenSP = sheetDialog.findViewById(R.id.ed_tenSP);
        EditText ed_motaSP = sheetDialog.findViewById(R.id.ed_motaSP);
        EditText ed_giaSP = sheetDialog.findViewById(R.id.ed_giaSP);
        EditText ed_soLgTonKho = sheetDialog.findViewById(R.id.ed_solgTonKhoSP);
        TextView tv_maSP = sheetDialog.findViewById(R.id.tv_maSP);
        ImageView img_sanpham = sheetDialog.findViewById(R.id.img_sanpham);
        Spinner spn_loaiSP = sheetDialog.findViewById(R.id.spn_loaiSP);
        TextView btn_edit = sheetDialog.findViewById(R.id.btn_edit);
        TextView btn_cancel = sheetDialog. findViewById(R.id.btn_cancel);

        SanPhamDAO sanPhamDAO = new SanPhamDAO(context);

        tv_maSP.setText("Mã sản phẩm: " + sanPham.getMasp());
        ed_tenSP.setText(sanPham.getTensp());
        ed_motaSP.setText(sanPham.getMotasp());
        ed_giaSP.setText(String.valueOf(sanPham.getGiasp()));
        ed_soLgTonKho.setText(String.valueOf(sanPham.getSoLuongTonKho()));
        img_sanpham.setImageBitmap(ConvertData.ConvertBitmap(sanPham.getAnhsp()));

        getDataTheLoai(spn_loaiSP);// đổ dữ liệu vào Spinner, set dữ liệu cho Spn
        int index = 0;
        int postion = -1;
        ArrayList<HashMap<String, Object>> listHM = getDataTheLoai(spn_loaiSP);
        for (HashMap<String, Object> item : listHM){
            if (item.get("tenloai").equals(sanPham.getTenloai())){
                postion = index;
            }
            index++;
        }
        spn_loaiSP.setSelection(postion);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maSP = sanPham.getMasp();
                String tenSP = ed_tenSP.getText().toString();
                String giaSP = ed_giaSP.getText().toString();
                String motaSP = ed_motaSP.getText().toString();
                String soLuongTonKho = ed_soLgTonKho.getText().toString();
                // Mã loại
                HashMap<String, Object> hs = (HashMap<String, Object>) spn_loaiSP.getSelectedItem();
                int maloai = (int) hs.get("maloai");
                // Ảnh sản phẩm
                byte[] anhSP = sanPham.getAnhsp();

                if (tenSP.isEmpty()) {
                    ed_tenSP.setError("Nhập tên sản phẩm");
                    return;
                }
                // Kiểm tra giá sản phẩm
                if (giaSP.isEmpty()) {
                    ed_giaSP.setError("Nhập giá sản phẩm");
                    return;
                } else if (Integer.parseInt(giaSP) < 1000) {
                    ed_giaSP.setError("Giá sản phẩm phải lớn hơn 1000");
                    return;
                }
                // Kiểm tra mô tả sản phẩm
                if (motaSP.isEmpty()) {
                    ed_motaSP.setError("Nhập mô tả sản phẩm");
                    return;
                } else if (motaSP.length() < 12) {
                    ed_motaSP.setError("Mô tả sản phẩm phải chứa ít nhất 12 kí tự");
                    return;
                }
                // Kiểm tra số lượng tồn kho
                if (soLuongTonKho.isEmpty()) {
                    ed_soLgTonKho.setError("Nhập số lượng tồn kho");
                    return;
                } else if (Integer.parseInt(soLuongTonKho) < 100) {
                    ed_soLgTonKho.setError("Số lượng tồn kho phải lớn hơn 100");
                    return;
                }
                SanPham sanPham = new SanPham(maSP, tenSP, anhSP, motaSP, Integer.parseInt(giaSP), maloai,Integer.parseInt(soLuongTonKho));
                boolean check = sanPhamDAO.suaSanPham(sanPham);
                if (check){
                    notifyDataSetChanged();
                    list.clear();
                    list = sanPhamDAO.getDSSanPham();
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    sheetDialog.dismiss();
                } else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    sheetDialog.dismiss();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog.dismiss();
            }
        });
        sheetDialog.show();
    }

    // Hiển thị dialog xác nhận xóa sản phẩm
    private void showDialogDelete(int maSP){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setIcon(R.drawable.logo_delete);
        dialogDelete.setTitle("Bạn có chắc chắn muốn xóa sản phẩm này không ?");
        dialogDelete.setPositiveButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //
        dialogDelete.setNegativeButton("XÓA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
                int check = sanPhamDAO.xoaSanPham(maSP);
                if (check == 1){
                    list.clear();
                    list = sanPhamDAO.getDSSanPham();
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogDelete.create();
        dialogDelete.show();
    }

    // Lấy dữ liệu thể loại từ database và đổ vào Spinner
    private ArrayList<HashMap<String, Object>> getDataTheLoai(Spinner spnLoaiSP) {
        TheLoaiDAO theLoaiDAO = new TheLoaiDAO(context);
        ArrayList<TheLoai> list = theLoaiDAO.getDSTheLoai();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        // Đổi ds TL sang thành HashMap
        for (TheLoai theLoai : list){
            HashMap<String, Object> loai = new HashMap<>();
            loai.put("maloai", theLoai.getMaloai());
            loai.put("tenloai", theLoai.getTenloai());
            listHM.add(loai);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listHM, android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spnLoaiSP.setAdapter(simpleAdapter);

        return listHM;
    }

}
