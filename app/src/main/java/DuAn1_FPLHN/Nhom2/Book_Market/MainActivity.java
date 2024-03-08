package DuAn1_FPLHN.Nhom2.Book_Market;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private TextView tvUser;
    ImageView imgAvt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        //Thực Hiện Việc Thêm ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trang chủ");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open,R.string.close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    private void anhXa() {
        drawerLayout = findViewById(R.id.dl_am);
        toolbar = findViewById(R.id.tb_am);
        navigationView = findViewById(R.id.nv_am);
        bottomNavigationView = findViewById(R.id.bnv_am);
        imgAvt = navigationView.getHeaderView(0).findViewById(R.id.imgAvt);
        tvUser = navigationView.getHeaderView(0).findViewById(R.id.tvUser);
        setThongtin();
    }


    private void setThongtin() {

    }
}