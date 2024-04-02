package DuAn1_FPLHN.Nhom2.Book_Market.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<HoaDon> list;

    public HoaDonAdapter(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HoaDonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon, parent, false);
        return new ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull HoaDonAdapter.ViewHolder holder, int position) {
//        final HoaDon hoaDon = list.get(holder.getAdapterPosition());
//
//        HoaDonDAO hoaDonDAO =new HoaDonDAO(context);
//
//        holder.tv_mahd.setText("Mã HĐ: "+ hoaDon.getMahd());
//        holder.tv_tongsanpham.setText("Số lượng: "+hoaDon.getTongsanpham());
//        holder.tv_matk.setText("Mở tài khoản: "+ hoaDon.getMatk());
//        holder.tv_ngaylap.setText(hoaDon.getNgaylap());
//        holder.tv_hoten.setText("Người nhận: " + hoaDon.getHoten());
//        holder.tv_sdt.setText("SĐT: " + hoaDon.getSdt());
//        holder.tv_diachi.setText("Giao đến: " + hoaDon.getDiachi());
//        holder.tv_tongtien.setText("Số tiền: " + hoaDon.getTongtien() + " VNĐ");
//
//        if (hoaDon.getTrangthai() == 0) {
//            holder.tv_trangthai.setText("Đang xử lý");
//            holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.light_blue_1));
//        } else if (hoaDon.getTrangthai() == 1) {
//            holder.tv_trangthai.setText("Đã xác nhận");
//            holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.light_blue_1));
//        } else {
//            holder.tv_trangthai.setText("Đã giao");
//            holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.natural_blue));
//        }
//
//        holder.img_xoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int maHD = hoaDon.getMahd();
//                showDiaLogDeleteHD(maHD);
//            }
//        });
//
//        holder.btn_doitrangthai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean KiemTra = hoaDonDAO.thayDoiTrangThai(hoaDon);
//                if (KiemTra){
//                    list.clear();
//                    list = hoaDonDAO.getDSHoaDon();
//                    notifyDataSetChanged();
//                }else {
//                    Toast.makeText(context, "Thay đổi trạng thái thất bại !", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }
@Override
public void onBindViewHolder(@NonNull HoaDonAdapter.ViewHolder holder, int position) {
    final HoaDon hoaDon = list.get(holder.getAdapterPosition());

    HoaDonDAO hoaDonDAO = new HoaDonDAO(context);

    holder.tv_mahd.setText("Mã HĐ: " + hoaDon.getMahd());
    holder.tv_tongsanpham.setText("Số lượng: " + hoaDon.getTongsanpham());
    holder.tv_matk.setText("Mở tài khoản: " + hoaDon.getMatk());
    holder.tv_ngaylap.setText(hoaDon.getNgaylap());
    holder.tv_hoten.setText("Người nhận: " + hoaDon.getHoten());
    holder.tv_sdt.setText("SĐT: " + hoaDon.getSdt());
    holder.tv_diachi.setText("Giao đến: " + hoaDon.getDiachi());
    holder.tv_tongtien.setText("Số tiền: " + hoaDon.getTongtien() + " VNĐ");

    if (hoaDon.getTrangthai() == 0) {
        holder.tv_trangthai.setText("Đang xử lý");
        holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.light_blue_1));
    } else if (hoaDon.getTrangthai() == 1) {
        holder.tv_trangthai.setText("Đã xác nhận");
        holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.light_blue_1));
    } else if (hoaDon.getTrangthai() == 2){
        holder.tv_trangthai.setText("Đã giao");
        holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.natural_blue));
    }

    // Xử lý sự kiện khi nhấn vào nút btn_doitrangthai
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
                } else {
                    Toast.makeText(context, "Thay đổi trạng thái thất bại!", Toast.LENGTH_SHORT).show();
                }
                // Đặt giá trị của biến isButtonClicked thành true để biết rằng người dùng đã nhấn vào nút này
                holder.isButtonClicked = true;
            }
        }
    });

    // Thiết lập giá trị mặc định cho biến isButtonClicked
    holder.isButtonClicked = false;

    // Xử lý sự kiện khi nhấn vào nút xóa
    holder.img_xoa.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int maHD = hoaDon.getMahd();
            showDiaLogDeleteHD(maHD);
        }
    });
}

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_mahd, tv_ngaylap, tv_hoten, tv_sdt, tv_diachi, tv_tongtien, tv_tongsanpham, tv_trangthai, tv_matk, btn_doitrangthai;
        ImageView img_xoa;
        boolean isButtonClicked; // Biến để kiểm tra xem người dùng đã nhấn vào nút btn_doitrangthai hay chưa
        public ViewHolder(@NonNull View itemview){
            super(itemview);

            tv_mahd= itemview.findViewById(R.id.tv_mahd);
            tv_hoten = itemview.findViewById(R.id.tv_hoten);
            tv_sdt = itemview.findViewById(R.id.tv_sdt);
            tv_diachi = itemview.findViewById(R.id.tv_diachi);
            tv_tongtien = itemview.findViewById(R.id.tv_tongtien);
            tv_tongsanpham = itemview.findViewById(R.id.tv_tongsanpham);
            tv_matk = itemview.findViewById(R.id.tv_matk);
            tv_trangthai = itemview.findViewById(R.id.tv_trangthai);
            btn_doitrangthai = itemview.findViewById(R.id.btn_doiTT);
            tv_ngaylap = itemview.findViewById(R.id.tv_ngaylap);
            img_xoa = itemview.findViewById(R.id.img_xoa);
        }
    }



    private void showDiaLogDeleteHD(int maHD){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setIcon(R.drawable.logo_delete);
        dialogDelete.setTitle("Bạn có chắc chắn muốn xóa hóa đơn mã " + maHD + " không ?");
        dialogDelete.setPositiveButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogDelete.setNegativeButton("XÓA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                boolean check = hoaDonDAO.xoaHoaDon(maHD);
                if (check){
                    list.clear();
                    list = hoaDonDAO.getDSHoaDon();
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
}
