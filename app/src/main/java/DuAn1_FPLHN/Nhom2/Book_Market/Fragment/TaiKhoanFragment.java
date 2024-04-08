package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TaiKhoanDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.DangNhap;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.TaiKhoan;
import DuAn1_FPLHN.Nhom2.Book_Market.R;


public class TaiKhoanFragment extends Fragment {
    private TextView tv_hoten, tv_taikhoan, tv_sdt, tv_diachi;
    private LinearLayout btn_dangxuat, btn_doimatkhau, btn_doithongtin, btn_CSKH;
    private TaiKhoan taiKhoan;
    private TaiKhoanDAO taiKhoanDAO;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_tai_khoan, container, false);

        tv_hoten = view.findViewById(R.id.tv_hoten);
        tv_taikhoan = view.findViewById(R.id.tv_taikhoan);
        tv_sdt = view.findViewById(R.id.tv_sdt);
        tv_diachi = view.findViewById(R.id.tv_diachi);

        btn_doimatkhau = view.findViewById(R.id.btn_doimatkhau);
        btn_dangxuat = view.findViewById(R.id.btn_dangxuat);
        btn_doithongtin = view.findViewById(R.id.btn_doiThongTin);
        btn_CSKH = view.findViewById(R.id.btn_CSKH);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ThongTinTaiKhoan", MODE_PRIVATE);
        int matk = sharedPreferences.getInt("matk", 0);
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        taiKhoan = taiKhoanDAO.getThongTinTaiKhoan(matk);


        tv_hoten.setText(taiKhoan.getHoten());
        tv_taikhoan.setText(taiKhoan.getTaikhoan());
        tv_sdt.setText(taiKhoan.getSdt());
        tv_diachi.setText(taiKhoan.getDiachi());

        //btn dang xuat
        btn_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences myPrefs = getContext().getSharedPreferences("Activity", MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getContext(), DangNhap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        //btn doi mat khau
        btn_doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDoiMK(matk);
            }
        });
        //btn doi thong tin
        btn_doithongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDoiThongTinTK(taiKhoan);
            }
        });
        //btn CSKH
        btn_CSKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCSKH();
            }
        });

        return view;
    }

    //showDiaLogDoi Mat Khau
    private void showDialogDoiMK(int matk){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_doi_matkhau, null);
        BottomSheetDialog dialogAdd = new BottomSheetDialog(getContext());
        dialogAdd.setContentView(view);
        dialogAdd.setCanceledOnTouchOutside(false);

        EditText ed_matkhaucu = view.findViewById(R.id.ed_matkhaucu);
        EditText ed_matkhaumoi = view.findViewById(R.id.ed_matkhaumoi);
        EditText ed_confirm_matkhaumoi = view.findViewById(R.id.ed_confirm_matkhaumoi);

        TextView btn_add = view.findViewById(R.id.btn_add);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matkhaucu = ed_matkhaucu.getText().toString();
                String matkhaumoi = ed_matkhaumoi.getText().toString();
                String confirm_matkhaumoi = ed_confirm_matkhaumoi.getText().toString();

                if (matkhaucu.isEmpty()){
                    ed_matkhaucu.setError("Vui lòng nhập mật khẩu cũ");
                    return;
                }
                if (matkhaumoi.isEmpty()){
                    ed_matkhaumoi.setError("Vui lòng nhập mật khẩu mới");
                    return;
                }
                if (confirm_matkhaumoi.isEmpty()){
                    ed_confirm_matkhaumoi.setError("Vui lòng nhập lại mật khẩu mới");
                    return;
                }
                if (!confirm_matkhaumoi.equals(matkhaumoi)){
                    ed_confirm_matkhaumoi.setError("Mật khẩu không trùng nhau");
                    return;
                }

                TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(getContext());
                int check = taiKhoanDAO.doiMatKhau(matk, matkhaucu, matkhaumoi);
                if (check == -1){
                    Toast.makeText(getContext(), "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                } else if (check == 0){
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    dialogAdd.dismiss();
                } else {
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    dialogAdd.dismiss();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdd.dismiss();
            }
        });
        dialogAdd.show();
    }
    //showDialog Doi thong tin tai khoan
    private void showDialogDoiThongTinTK(TaiKhoan taiKhoan){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_doi_thongtin, null);
        BottomSheetDialog dialogEdit = new BottomSheetDialog(getContext());
        dialogEdit.setContentView(view);
        dialogEdit.setCanceledOnTouchOutside(false);

        EditText ed_hoten = view.findViewById(R.id.ed_hoten);
        EditText ed_sdt = view.findViewById(R.id.ed_sdt);
        EditText ed_diachi = view.findViewById(R.id.ed_diachi);

        TextView btn_capnhat = view.findViewById(R.id.btn_capnhat);
        TextView btn_huy = view.findViewById(R.id.btn_huy);

        ed_hoten.setText(taiKhoan.getHoten());
        ed_sdt.setText(taiKhoan.getSdt());
        ed_diachi.setText(taiKhoan.getDiachi());

        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer matk = taiKhoan.getMatk();
                String taikhoan = taiKhoan.getTaikhoan();
                String matkhau = taiKhoan.getMatkhau();
                String loaitaikhoan = taiKhoan.getLoaitaikhoan();

                String hoten_moi = ed_hoten.getText().toString();
                String sdt_moi = ed_sdt.getText().toString();
                String diachi_moi = ed_diachi.getText().toString();

                if (hoten_moi.isEmpty()){
                    ed_hoten.setError("Vui lòng nhập họ tên");
                    return;
                }
                if (sdt_moi.isEmpty()){
                    ed_sdt.setError("Vui lòng nhập sđt");
                    return;
                }
                if (diachi_moi.isEmpty()){
                    ed_diachi.setError("Vui lòng nhập địa chỉ");
                    return;
                }

                TaiKhoan taiKhoan_moi = new TaiKhoan(matk, taikhoan, matkhau, hoten_moi, sdt_moi, diachi_moi, loaitaikhoan);
                boolean check = taiKhoanDAO.doiThongTinTK(taiKhoan_moi);
                if (check){
                    Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    dialogEdit.dismiss();
                    reloadFragment();
                } else {
                    Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEdit.dismiss();
            }
        });

        dialogEdit.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callCSKH();
            }else {
                Toast.makeText(getContext(), "Quyền gọi điện bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //callCSKH
    private void callCSKH() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0877956178"));
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.CALL_PHONE}, 1);
        } else {
            startActivity(callIntent);
        }

    }
    //reload Fragment
    private void reloadFragment(){
        TaiKhoanFragment taiKhoanFragment = new TaiKhoanFragment();
        getFragmentManager().beginTransaction().replace(R.id.fcv_am, taiKhoanFragment).commit();
    }
}