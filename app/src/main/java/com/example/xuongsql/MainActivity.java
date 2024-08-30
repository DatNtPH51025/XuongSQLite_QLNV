package com.example.xuongsql;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.xuongsql.Fragment.CapNhatFrag;
import com.example.xuongsql.Fragment.QuanLyFrag;
import com.example.xuongsql.Fragment.QuanLyThanhVienFrag;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    QuanLyFrag quanly;
    QuanLyThanhVienFrag quanlytv;
    CapNhatFrag capnhat;
    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Khởi tạo các View
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar_view);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);

        // Lấy tên người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String userName = sharedPreferences.getString("ten", "Tên người dùng"); // Giá trị mặc định nếu không tìm thấy

        // Lấy header từ NavigationView
        View headerView = navigationView.getHeaderView(0); // Lấy header đầu tiên
        TextView userNameTextView = headerView.findViewById(R.id.user_name);
        userNameTextView.setText("Xin chào, "+ userName); // Thiết lập tên người dùng

        // Setup drawer
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.chuoi_open,
                R.string.chuoi_dong
        );
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        fm = getSupportFragmentManager();
        quanly = new QuanLyFrag();
        quanlytv = new QuanLyThanhVienFrag();
        capnhat = new CapNhatFrag();

        // Get user role and check if information needs to be updated
        SharedPreferences sha = getSharedPreferences("data", MODE_PRIVATE);
        String chucvu = sha.getString("chucvu", "");
        boolean isInfoUpdated = sha.getBoolean("isInfoUpdated", false); // This flag should be set after the user updates their info

        // If not Admin and info not updated, force user to update info
        if (!chucvu.equals("Admin") && !isInfoUpdated) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, capnhat).commit();
            toolbar.setTitle("Cập nhật tài khoản");
            // Disable other menu items
            navigationView.getMenu().findItem(R.id.qlpb).setEnabled(false);
            navigationView.getMenu().findItem(R.id.qlnv).setEnabled(false);
        } else {
            // Admin or info already updated, load the default fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, quanly).commit();
        }

        // Set navigation item selected listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.qlpb) {
                    // Hiển thị Fragment Quản lý phòng ban
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, quanly).commit();
                    toolbar.setTitle("Quản lý phòng ban");
                } else if (item.getItemId() == R.id.qlnv) {
                    // Hiển thị Fragment Quản lý nhân viên
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, quanlytv).commit();
                    // Reset "maphongban" nếu cần
                    SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putInt("maphongban", 0);
                    editor.apply();
                    toolbar.setTitle("Quản lý nhân viên");
                } else if (item.getItemId() == R.id.cntk) {
                    // Hiển thị Fragment Cập nhật tài khoản
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, capnhat).commit();
                    toolbar.setTitle("Cập nhật tài khoản");
                } else if (item.getItemId() == R.id.dangxuat) {
                    // Xử lý đăng xuất
                    handleLogout();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    // Hàm xử lý đăng xuất
    private void handleLogout() {
        // Xóa thông tin đăng nhập của người dùng
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Xóa tất cả dữ liệu lưu trữ trong SharedPreferences
        editor.apply();

        // Chuyển đến màn hình đăng nhập
        Intent intent = new Intent(this, DangNhapActivity.class);
        startActivity(intent);
        finish(); // Kết thúc Activity hiện tại để người dùng không thể quay lại bằng nút "Quay lại"
    }


    public void onInfoUpdated() {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putBoolean("isInfoUpdated", true);
        editor.apply();

        // Enable other menu items again
        navigationView.getMenu().findItem(R.id.qlpb).setEnabled(true);
        navigationView.getMenu().findItem(R.id.qlnv).setEnabled(true);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, quanly).commit();
        toolbar.setTitle("Quản lý phòng ban");
    }


}