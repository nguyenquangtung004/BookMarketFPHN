package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.DanhMucFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.DoiMatKhauFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.HoaDonFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.LichSuMHFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.ThongTSallerFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.TopSanPhamFragment;
import DuAn1_FPLHN.Nhom2.Book_Market.Fragment.TrangChuFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    ProgressDialog progressDialog;
    private FragmentContainerView fragmentContainerView;
    private TextView tvUser;
    ImageView imgAvt;
//    private final ThongTSallerFragment thongTSallerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open,R.string.close);
//        Cho phép hiển thị biểu tượng mặc định cho DrawerLayout
        toggle.setDrawerIndicatorEnabled(true);
        /*Đồng bộ trạng thái của DrawerLayout và biểu tượng mở đóng -> Giúp thay đổi trạng thái khi DrawerLayout mở hoặc đóng*/
        toggle.syncState();
        /*Đăng kí một DrawerListener để lắng nghe sự kiện mở hoặc đóng*/
        drawerLayout.addDrawerListener(toggle);

        callFragment(new TrangChuFragment());

        /////
        View headerLayout = findViewById(R.id.dl_am);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                progressDialog.show();
                if (item.getItemId() == R.id.nav_trangchu){
                    callFragment(new TrangChuFragment());
                    toolbar.setTitle("Trang Chủ");
                }
                if (item.getItemId() == R.id.nav_danhmuc){
                    callFragment(new DanhMucFragment());
                    toolbar.setTitle("Danh Mục");
                }
                if (item.getItemId() == R.id.nav_giohang){
                    startActivity(new Intent(getApplicationContext(), GioHangActivity.class));
                }
                if (item.getItemId() == R.id.sub_Top){
                    callFragment(new TopSanPhamFragment());
                    toolbar.setTitle("Sản phẩm bán chạy");
                }
                if (item.getItemId() == R.id.nav_HoSo){
                    callFragment(new ThongTSallerFragment());
                    toolbar.setTitle("Thông tin khách hàng");
                }
                if (item.getItemId()== R.id.nav_HoaDon){
                    callFragment(new HoaDonFragment());
                    toolbar.setTitle("Hóa Đơn");
                }
                if (item.getItemId() == R.id.nav_lichsumuahang){
                    progressDialog.dismiss();
                    callFragment(new LichSuMHFragment());
                    toolbar.setTitle("Lịch sử màn hình");
                }
                if (item.getItemId() == R.id.sub_Logout){
                    Logout();
                }
                if (item.getItemId() == R.id.sub_Pass){
                    callFragment(new DoiMatKhauFragment());
                    toolbar.setTitle("Đổi mật khẩu");
                }
                return false;
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bt_trangchu){
                    callFragment(new TrangChuFragment());
                    toolbar.setTitle("Trang chủ");
                }
                if (item.getItemId() == R.id.bt_banchay){
                    callFragment(new TopSanPhamFragment());
                    toolbar.setTitle("Sản phẩm bán chạy");
                }
                if (item.getItemId() == R.id.bt_danhmuc){
                    callFragment(new DanhMucFragment());
                    toolbar.setTitle("Danh mục");
                }
                if (item.getItemId() == R.id.bt_nguoidung){
                    callFragment(new ThongTSallerFragment());
                    toolbar.setTitle("Hồ sơ");
                }
                return true;
            }
        });
    }

    private void callFragment(Fragment fragment) {
        /*Bắt đầu một giao dịch FragmentTransaction thực hiện thay đổi trên FragmentManager*/
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        /*Thay thế fragment hiện tại trong fcv_am = fragment mới được chuyển vào như là một tham số.
        Sau đó commit để áp dụng giao dịch*/
        manager.replace(R.id.fcv_am,fragment).commit();
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
        fragmentContainerView = findViewById(R.id.fcv_am);
        imgAvt = navigationView.getHeaderView(0).findViewById(R.id.imgAvt);
        tvUser = navigationView.getHeaderView(0).findViewById(R.id.tvUser);
        setThongtin();
    }
    private void setThongtin() {

    }
//    Thực hiện đăng xuất
    private void Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn đăng xuất tài khoản không ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, DangNhap.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}