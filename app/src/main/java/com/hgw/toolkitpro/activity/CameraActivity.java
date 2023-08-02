package com.hgw.toolkitpro.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.hgw.toolkitpro.R;
import com.hgw.toolkitpro.fragment.CameraFragment;
import com.hgw.toolkitpro.fragment.GalleryFragment;
import com.hgw.toolkitpro.fragment.VideoFragment;

public class CameraActivity extends AppCompatActivity {
    // 定义常量变量来存储资源 ID
    private static final int MENU_GALLERY_ID = R.id.menu_gallery;
    private static final int MENU_CAMERA_ID = R.id.menu_camera;
    private static final int MENU_VIDEO_ID = R.id.menu_video;

    private static final int REQUEST_CODE_PERMISSIONS = 100;
    private Bundle savedInstanceState;
    private String[] requiredPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // 检查相册访问权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果权限尚未授予，请求权限
            ActivityCompat.requestPermissions(this, requiredPermissions, REQUEST_CODE_PERMISSIONS);
        } else {
            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == MENU_GALLERY_ID) {
                        showFragment(new GalleryFragment());
                        return true;
                    } else if (itemId == MENU_CAMERA_ID) {
                        // 切换到拍照页面
                        showFragment(new CameraFragment());
                        return true;
                    } else if (itemId == MENU_VIDEO_ID) {
                        // 切换到视频录制页面
                        showFragment(new VideoFragment());
                        return true;
                    }
                    return false;
                }
            });
            // 默认显示相册页面
            showFragment(new GalleryFragment());
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    // 处理权限请求的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bottomNavigationView = findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == MENU_GALLERY_ID) {
                            showFragment(new GalleryFragment());
                            return true;
                        } else if (itemId == MENU_CAMERA_ID) {
                            // 切换到拍照页面
                            showFragment(new CameraFragment());
                            return true;
                        } else if (itemId == MENU_VIDEO_ID) {
                            // 切换到视频录制页面
                            showFragment(new VideoFragment());
                            return true;
                        }
                        return false;
                    }
                });
                // 默认显示相册页面
                showFragment(new GalleryFragment());
            } else {
                // 用户拒绝了相册访问权限，可以在此处给出解释或其他操作
                Toast.makeText(this, "READ_EXTERNAL_STORAGE permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}