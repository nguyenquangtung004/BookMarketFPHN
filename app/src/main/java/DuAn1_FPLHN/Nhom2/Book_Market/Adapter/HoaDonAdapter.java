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
// Cái này là của admin
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

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.ViewHolder holder, int position) {

        final HoaDon hoaDon = list.get(holder.getAdapterPosition());

        HoaDonDAO hoaDonDAO = new HoaDonDAO(context);

        holder.tv_mahd.setText("Mã HĐ: " + hoaDon.getMahd());
        holder.tv_tongsanpham.setText(hoaDon.getTongsanpham());
        holder.tv_matk.setText("Mã tài khoản: " + hoaDon.getMatk());
        holder.tv_ngaylap.setText(hoaDon.getNgaylap());
        holder.tv_hoten.setText("Người nhận: " + hoaDon.getHoten());
        holder.tv_sdt.setText("SĐT: " + hoaDon.getSdt());
        holder.tv_diachi.setText("Giao đến: " + hoaDon.getDiachi());
        holder.tv_tongtien.setText("Số tiền: " + hoaDon.getTongtien() + " VNĐ");

        if (hoaDon.getTrangthai() == 1){
            holder.tv_trangthai.setText("Đã nhận hàng");
            holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.anakiwa));
        } else {
            holder.tv_trangthai.setText("Chưa nhận hàng");
            holder.tv_trangthai.setTextColor(ContextCompat.getColor(context, R.color.black_pearl));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_mahd, tv_ngaylap, tv_hoten, tv_sdt, tv_diachi, tv_tongtien, tv_tongsanpham, tv_trangthai, tv_matk, btn_doitrangthai;
        ImageView img_xoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_mahd = itemView.findViewById(R.id.tv_mahd);
            tv_hoten = itemView.findViewById(R.id.tv_hoten);
            tv_sdt = itemView.findViewById(R.id.tv_sdt);
            tv_diachi = itemView.findViewById(R.id.tv_diachi);
            tv_tongtien = itemView.findViewById(R.id.tv_tongtien);
            tv_tongsanpham = itemView.findViewById(R.id.tv_tongsanpham);
            tv_matk = itemView.findViewById(R.id.tv_matk);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai);

            tv_ngaylap = itemView.findViewById(R.id.tv_ngaylap);


        }
    }
    public void updateData(ArrayList<HoaDon> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
    public void refreshData() {
        HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
        ArrayList<HoaDon> newList = hoaDonDAO.getDSHoaDon(); // Đổi phương thức này tùy thuộc vào DAO của bạn
        updateData(newList);
    }



}
