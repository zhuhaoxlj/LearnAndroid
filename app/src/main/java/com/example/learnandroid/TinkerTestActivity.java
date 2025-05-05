package com.example.learnandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnandroid.utils.TinkerManager;

import java.io.File;

/**
 * 测试Tinker的Activity
 */
public class TinkerTestActivity extends AppCompatActivity {

    private static final String TAG = "TinkerTestActivity";
    private TextView tvTinkerId;
    private Button btnLoadPatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinker_test);

        initViews();
        updateTinkerInfo();
    }

    private void initViews() {
        tvTinkerId = findViewById(R.id.tv_tinker_id);
        btnLoadPatch = findViewById(R.id.btn_load_patch);

        // 点击加载补丁
        btnLoadPatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPatch();
            }
        });
    }

    /**
     * 更新Tinker信息
     */
    private void updateTinkerInfo() {
//        String tinkerId = TinkerManager.getTinkerId();
//        if (tinkerId != null) {
//            tvTinkerId.setText("当前Tinker ID: " + tinkerId);
//        } else {
//            tvTinkerId.setText("Tinker未安装或未加载补丁");
//        }
    }

    /**
     * 加载补丁
     */
    private void loadPatch() {
//        String patchDir = TinkerManager.getPatchDirectory(this);
//        String patchPath = patchDir + File.separator + "patch.apk";
//        File patchFile = new File(patchPath);
//
//        if (patchFile.exists()) {
//            Log.d(TAG, "找到补丁文件，开始加载: " + patchPath);
//            TinkerManager.loadPatch(this, patchPath);
//            Toast.makeText(this, "补丁加载成功，请重启应用", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.e(TAG, "补丁文件不存在: " + patchPath);
//            Toast.makeText(this, "补丁文件不存在，请先下载补丁", Toast.LENGTH_SHORT).show();
//        }
    }
} 