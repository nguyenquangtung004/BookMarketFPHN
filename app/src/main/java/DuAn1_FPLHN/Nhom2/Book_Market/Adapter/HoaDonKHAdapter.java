package DuAn1_FPLHN.Nhom2.Book_Market.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.DAO.HoaDonDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;
import DuAn1_FPLHN.Nhom2.Book_Market.R;

public class HoaDonKHAdapter extends RecyclerView.Adapter<HoaDonKHAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<HoaDon> list;

    private int mahd;
    private int matk;
    private HoaDonDAO hoaDonDAO;

    public HoaDonKHAdapter(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon_kh, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final HoaDon hoaDon = list.get(holder.getAdapterPosition());
        hoaDonDAO = new HoaDonDAO(context);

        holder.tv_mahd.setText("Mã HĐ: " + hoaDon.getMahd());
        holder.tv_ngaylap.setText(hoaDon.getNgaylap());
        holder.tv_tongsanpham.setText(hoaDon.getTongsanpham());
        holder.tv_hoten.setText("Người nhận: " + hoaDon.getHoten());
        holder.tv_sdt.setText("SĐT: " + hoaDon.getSdt());
        holder.tv_diachi.setText("Giao đến: " + hoaDon.getDiachi());
        holder.tv_tongtien.setText("Số tiền: " + hoaDon.getTongtien() + " VNĐ");

        if (hoaDon.getTrangthai() == 0){
            holder.tv_trangthai.setText("Chưa nhận hàng");
            holder.btn_doitrangthai.setVisibility(View.VISIBLE);
            holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.black_pearl));
        } else {
            holder.tv_trangthai.setText("Đã nhận hàng");
            holder.btn_doitrangthai.setVisibility(View.GONE);
            holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.anakiwa));
        }

        // Lấy mã hóa đơn
        int mahd = hoaDon.getMahd();
        // Lấy mã khách hàng
        SharedPreferences sharedPreferences = context.getSharedPreferences("ThongTinTaiKhoan", MODE_PRIVATE);
        int matk = sharedPreferences.getInt("matk", 0);

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy mã hóa đơn
                int mahd = hoaDon.getMahd();
                // Lấy mã khách hàng
                SharedPreferences sharedPreferences = context.getSharedPreferences("ThongTinTaiKhoan", MODE_PRIVATE);
                int matk = sharedPreferences.getInt("matk", 0);
                Log.d("HoaDonKHAdapter", "Deleting order with ID: " + mahd + " for user with ID: " + matk);
                showDiaLogDelete(mahd, matk);
            }
        });

        holder.btn_doitrangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra xem người dùng đã nhấn vào nút này hay chưa
                if (!holder.isButtonClicked) {
                    // Nếu chưa, thực hiện thay đổi trạng thái và cập nhật giao diện
                    boolean KiemTra = hoaDonDAO.thayDoiTrangThai(hoaDon);
                    if (KiemTra) {
                        list.clear();
                        list.addAll(hoaDonDAO.getDSHoaDon());
                        notifyDataSetChanged();
                        Log.d("HoaDonKHAdapter", "Successfully changed the status of order with ID: " + hoaDon.getMahd());  // Thêm log ở đây
                    } else {
                        Toast.makeText(context, "Thay đổi trạng thái thất bại!", Toast.LENGTH_SHORT).show();
                        Log.d("HoaDonKHAdapter", "Failed to change the status of order with ID: " + hoaDon.getMahd());  // Thêm log ở đây
                    }
                    // Đặt giá trị của biến isButtonClicked thành true để biết rằng người dùng đã nhấn vào nút này
                    holder.isButtonClicked = true;
                }
            }
        });

        // Thiết lập giá trị mặc định cho biến isButtonClicked
        holder.isButtonClicked = false;

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_mahd, tv_ngaylap, tv_hoten, tv_sdt, tv_diachi, tv_tongtien, tv_trangthai, tv_tongsanpham;
        ImageView img_delete;
        TextView btn_doitrangthai;
        boolean isButtonClicked;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_mahd = itemView.findViewById(R.id.tv_mahd);
            tv_hoten = itemView.findViewById(R.id.tv_hoten);
            tv_sdt = itemView.findViewById(R.id.tv_sdt);
            tv_diachi = itemView.findViewById(R.id.tv_diachi);
            tv_tongtien = itemView.findViewById(R.id.tv_tongtien);
            tv_tongsanpham = itemView.findViewById(R.id.tv_tongsanpham);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai);
            tv_ngaylap = itemView.findViewById(R.id.tv_ngaylap);
            img_delete = itemView.findViewById(R.id.img_delete);
            btn_doitrangthai = itemView.findViewById(R.id.btn_thaydoiTT);

        }
    }

    private void showDiaLogDelete(int mahd, int matk){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setIcon(R.drawable.logo_delete);
        dialogDelete.setTitle("Bạn có chắc chắn muốn xóa hóa đơn này không ?");
        dialogDelete.setPositiveButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("HoaDonKHAdapter", "Deletion cancelled for order with ID: " + mahd + " for user with ID: " + matk);
            }
        });
        dialogDelete.setNegativeButton("XÓA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                boolean check = hoaDonDAO.xoaHoaDonTheoKH(mahd, matk);
                if (check){
                    list.clear();
                    list = hoaDonDAO.getDSHoaDonTheoKH(matk);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa hóa đơn thành công", Toast.LENGTH_SHORT).show();
                    Log.d("HoaDonKHAdapter", "Successfully deleted order with ID: " + mahd + " for user with ID: " + matk);
                } else {
                    Toast.makeText(context, "Xóa hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                    Log.d("HoaDonKHAdapter", "Failed to delete order with ID: " + mahd + " for user with ID: " + matk);
                }
            }
        });
        dialogDelete.create();
        dialogDelete.show();
    }

}
