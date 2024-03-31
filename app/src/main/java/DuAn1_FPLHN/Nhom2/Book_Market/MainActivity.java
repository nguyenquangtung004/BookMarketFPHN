package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import DuAn1_FPLHN.Nhom2.Book_Market.DAO.TaiKhoanDAO;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.GIoHangFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.HoaDonFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.QuanLyHoaDon;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.QuanLySanPham;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.QuanLyTaiKhoan;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.QuanLyTheLoai;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.ThongKeDoanhThu;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.ThongTSallerFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.CuaHangFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.ThongTinApp;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    ImageView imgAvt;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        View headerLayout = navigationView.getHeaderView(0);
        TextView tv_hoten = headerLayout.findViewById(R.id.tv_hoten);
        TextView tv_taikhoan = headerLayout.findViewById(R.id.tv_taikhoan);

        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences("ThongTinTaiKhoan", MODE_PRIVATE);
        Integer maTK = sharedPreferences.getInt("matk", 0);
        String hoten = sharedPreferences.getString("hoten", "");
        String taikhoan = sharedPreferences.getString("taikhoan", "");
        String loaiTK = sharedPreferences.getString("loaitaikhoan", "");
//        tv_hoten.setText(hoten);
//        tv_taikhoan.setText(taikhoan);


//        Thực Hiện Việc Thêm ToolBar
        /*Đặt thanh hành động của activity thành thanh toolbar -> Sử dụng thanh hành động thay vì ActionBar mặc định*/
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trang chủ");
        /*Tạo một đối tượng ActionBarDrawerToggle một lớp thiết kế để kết hợp với action bar và DrawerLayout
         * Trong đó có các tham số:
         * MainActivity.this: Context của Activity.
         * drawerLayout: DrawerLayout được sử dụng trong giao diện.
         * toolbar: Toolbar được sử dụng trong giao diện.
         * */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
//        Cho phép hiển thị biểu tượng mặc định cho DrawerLayout
        toggle.setDrawerIndicatorEnabled(true);
        /*Đồng bộ trạng thái của DrawerLayout và biểu tượng mở đóng -> Giúp thay đổi trạng thái khi DrawerLayout mở hoặc đóng*/
        toggle.syncState();
        /*Đăng kí một DrawerListener để lắng nghe sự kiện mở hoặc đóng*/
        drawerLayout.addDrawerListener(toggle);

        callFragment(new CuaHangFragment());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                progressDialog.show();
                // Trang Chủ Người Mua và Người Bán Có
                if (item.getItemId() == R.id.mQLTrangChu) {
                    callFragment(new CuaHangFragment());
                    toolbar.setTitle("Trang Chủ");
                }
                /*<---------------------->*/

                // Quản Lý Sản Phẩm đã lên giao diện của Admin
                if (item.getItemId() == R.id.mQLSanPham) {
                    callFragment(new QuanLySanPham());
                    toolbar.setTitle("Quản lý sản phẩm");
                }
                /*<---------------------->*/

                //Quản Lý Thể Loại của admin
                if (item.getItemId() == R.id.mQLTheLoai) {
                    callFragment(new QuanLyTheLoai());
                    toolbar.setTitle("Quản lý thể loại");
                }
                /*<---------------------->*/

                //Quản Lý Hóa Đơn của Admin
                if (item.getItemId() == R.id.mQLHoaDon) {
                    callFragment(new QuanLyHoaDon());
                    toolbar.setTitle("Quản lý hóa đơn");
                }
                /*<---------------------->*/

                //Quản Lý Tài Khoản của Admin
                if (item.getItemId() == R.id.mQLTaiKhoan) {
                    callFragment(new QuanLyTaiKhoan());
                    toolbar.setTitle("Quản lý tài khoản ");
                }
                /*<---------------------->*/

                //Thống Kê Doanh Thu của Admin
                if (item.getItemId() == R.id.mThongKe) {
                    callFragment(new ThongKeDoanhThu());
                    toolbar.setTitle("Thống kê doanh thu ");
                }
                /*<---------------------->*/

                //Đổi mật khẩu của người mua
                if (item.getItemId() == R.id.mDoiMatKhau) {
                    showDialogDoiMK(maTK);
                    Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                }
                /*<---------------------->*/

                //Thông tin đội ngũ phát triển ứng dựng
                if (item.getItemId() == R.id.mThongTinApp) {
                    callFragment(new ThongTinApp());
                    toolbar.setTitle("Thông tin đội ngũ phát triển");
                }
                /*<---------------------->*/
                //Đăng Xuất
                if (item.getItemId() == R.id.mDangXuat) {
                    SharedPreferences myPrefs = getSharedPreferences("Activity", MODE_PRIVATE);
                    SharedPreferences.Editor editor = myPrefs.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, DangNhap.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                /*<---------------------->*/
                return false;
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bt_trangchu) {
                    callFragment(new CuaHangFragment());
                    toolbar.setTitle("Trang chủ");
                }
                if (item.getItemId() == R.id.bt_giohang){
                   callFragment(new GIoHangFragment());
                   toolbar.setTitle("Giỏ Hàng");
                }
                //Thông tin tài khoản của người dùng
                if (item.getItemId() == R.id.bt_taikhoan) {
                    callFragment(new ThongTSallerFragment());
                    toolbar.setTitle("Thông tin khách hàng");
                }
                /*<---------------------->*/
                //Thông tin hóa đơn của người dùng
                if (item.getItemId() == R.id.bt_hoadon) {
                    callFragment(new HoaDonFragment());
                    toolbar.setTitle("Hóa Đơn");
                }
                /*<---------------------->*/

                return true;
            }
        });
        // Ẩn chức năng dành cho người mua
        if (!loaiTK.equals("admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mQLHoaDon).setVisible(false); // Ẩn chúc năng
            menu.findItem(R.id.mQLSanPham).setVisible(false);
            menu.findItem(R.id.mQLTaiKhoan).setVisible(false);
            menu.findItem(R.id.mQLTheLoai).setVisible(false);
            menu.findItem(R.id.mThongKe).setVisible(false);
        }

        //Ẩn chức năng dành cho admin
        if (!loaiTK.equals("buyer")){
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.bt_giohang).setVisible(false);
            menu.findItem(R.id.bt_hoadon).setVisible(false);
        }


    }

    private void showDialogDoiMK(Integer maTK) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_doi_matkhau, null);
        BottomSheetDialog dialogAdd = new BottomSheetDialog(this);
        dialogAdd.setContentView(view);
        dialogAdd.setCanceledOnTouchOutside(false);

        EditText ed_matkhaucu = view.findViewById(R.id.ed_matkhaucu);
        EditText ed_matkhaumoi = view.findViewById(R.id.ed_matkhaumoi);
        EditText ed_confirm_matkhaumoi = view.findViewById(R.id.ed_confirm_matkhaumoi);

        Button btn_add = view.findViewById(R.id.btn_add);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        // Xử lý sự kiện khi nhấn nút "Thay đổi"
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các trường nhập liệu
                String matkhaucu = ed_matkhaucu.getText().toString();
                String matkhaumoi = ed_matkhaumoi.getText().toString();
                String confirm_matkhaumoi = ed_confirm_matkhaumoi.getText().toString();

                if (matkhaucu.isEmpty()) {
                    ed_matkhaucu.setError("Vui lòng nhập mật khẩu cũ");
                    return;
                }
                if (matkhaumoi.isEmpty()) {
                    ed_matkhaumoi.setError("Vui lòng nhập mật khẩu mới");
                    return;
                }
                if (confirm_matkhaumoi.isEmpty()) {
                    ed_confirm_matkhaumoi.setError("Vui lòng nhập lại mật khẩu mới");
                    return;
                }
                if (!confirm_matkhaumoi.equals(matkhaumoi)) {
                    ed_confirm_matkhaumoi.setError("Mật khẩu không trùng nhau");
                    return;
                }

                TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(MainActivity.this);
                int check = taiKhoanDAO.doiMatKhau(maTK, matkhaucu, matkhaumoi);
                if (check == -1) {
                    Toast.makeText(MainActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                } else if (check == 0) {
                    Toast.makeText(MainActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    dialogAdd.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
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
    }

    private void callFragment(Fragment fragment) {
        /*Bắt đầu một giao dịch FragmentTransaction thực hiện thay đổi trên FragmentManager*/
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        /*Thay thế fragment hiện tại trong fcv_am = fragment mới được chuyển vào như là một tham số.
        Sau đó commit để áp dụng giao dịch*/
        manager.replace(R.id.fcv_am, fragment).commit();
//        Đóng thanh điều hướng trong DrawerLayout
        drawerLayout.close();
        progressDialog.dismiss();
    }

    //    Thực hiện ánh xạ
    private void anhXa() {
        progressDialog = new ProgressDialog(this);
        drawerLayout = findViewById(R.id.dl_am);
        toolbar = findViewById(R.id.tb_am);
        navigationView = findViewById(R.id.nv_am);
        bottomNavigationView = findViewById(R.id.bnv_am);
        frameLayout = findViewById(R.id.fcv_am);
        imgAvt = navigationView.getHeaderView(0).findViewById(R.id.imgAvt);
    }


}




