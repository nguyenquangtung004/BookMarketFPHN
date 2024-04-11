package DuAn1_FPLHN.Nhom2.Book_Market.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.ChiTietSanPham;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.SanPham;
import DuAn1_FPLHN.Nhom2.Book_Market.R;

public class SanPhamKHadpter extends RecyclerView.Adapter<SanPhamKHadpter.ViewHolder> {
    private final Context context;
    private final ArrayList<SanPham> list;

    // Khởi tạo adapter với context và danh sách sản phẩm
    public SanPhamKHadpter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    // Tạo ViewHolder mới
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham_kh, parent, false);
        return new ViewHolder(view);
    }

    // Gán dữ liệu cho ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SanPham sanPham = list.get(position);
        // Chuyển byte array về bitmap và đặt ảnh cho ImageView
        byte[] image = sanPham.getAnhsp();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0, image.length);
        holder.img_anhsp.setImageBitmap(bitmap);
        // Đặt text cho các TextView
        holder.tv_tensp.setText(sanPham.getTensp());
        String motasp = sanPham.getMotasp();
        if (motasp.length() > 40){
            String mota = motasp.substring(0, 40);
            holder.tv_motasp.setText("Mô tả: " + mota + "...");
        } else {
            holder.tv_motasp.setText("Mô tả: " + motasp);
        }
        holder.tv_giasp.setText(sanPham.getGiasp() + " VNĐ");

        holder.linear_sanpham_kh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một bundle và đưa dữ liệu vào
                Bundle bundle = new Bundle();
                bundle.putString("tensp", sanPham.getTensp());
                bundle.putInt("giasp", sanPham.getGiasp());
                bundle.putString("motasp", sanPham.getMotasp());
                bundle.putByteArray("anhsp", sanPham.getAnhsp());
                bundle.putInt("soluongtonkho",sanPham.getSoLuongTonKho());

                // Khởi tạo intent, đặt bundle vào và khởi động Activity
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("dataSP", bundle);

                context.startActivity(intent);
            }
        });
    }
    // Trả về số lượng item trong list
    @Override
    public int getItemCount() {
        return list.size();
    }
    // ViewHolder giữ các view cần thiết để hiển thị một item
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_anhsp;
        TextView tv_tensp, tv_motasp, tv_giasp;
        LinearLayout linear_sanpham_kh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_anhsp = itemView.findViewById(R.id.img_anhsp);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            tv_motasp = itemView.findViewById(R.id.tv_motasp);
            tv_giasp = itemView.findViewById(R.id.tv_giasp);
            linear_sanpham_kh = itemView.findViewById(R.id.linear_sanpham_kh);


        }
    }


}
