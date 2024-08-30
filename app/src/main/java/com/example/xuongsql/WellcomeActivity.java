package com.example.xuongsql;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class WellcomeActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000; // Thời gian hiển thị màn hình chào (3 giây)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wellcome);


        // Sử dụng Handler để chuyển đến Activity chính sau một khoảng thời gian
        new Handler().postDelayed(() -> {
            // Chuyển đến MainActivity sau khi màn hình chào kết thúc
            Intent mainIntent = new Intent(WellcomeActivity.this, DangNhapActivity.class);
            startActivity(mainIntent);
            finish(); // Kết thúc SplashActivity
        }, SPLASH_DISPLAY_LENGTH);
    }
}