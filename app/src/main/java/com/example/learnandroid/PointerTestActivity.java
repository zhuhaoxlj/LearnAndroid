package com.example.learnandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learnandroid.widget.PointerView;

public class PointerTestActivity extends AppCompatActivity {

    private PointerView mPointerView;
    private TextView mTextAngle, mTextRadius, mTextAngleOffset, mTextPointerWidth, mTextPointerHeight;
    private SeekBar mSeekBarAngle, mSeekBarRadius, mSeekBarAngleOffset, mSeekBarPointerWidth, mSeekBarPointerHeight;

    // Constants for angle range
    private static final int ANGLE_MIN = -90;
    private static final int ANGLE_MAX = 90;
    private static final int ANGLE_RANGE = ANGLE_MAX - ANGLE_MIN;

    /**
     * Start this activity from anywhere in the app
     *
     * @param context The context to start the activity from
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, PointerTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointer_test);

        // Initialize views
        mPointerView = findViewById(R.id.pointerView);
        
        mTextAngle = findViewById(R.id.textAngle);
        mTextRadius = findViewById(R.id.textRadius);
        mTextAngleOffset = findViewById(R.id.textAngleOffset);
        mTextPointerWidth = findViewById(R.id.textPointerWidth);
        mTextPointerHeight = findViewById(R.id.textPointerHeight);
        
        mSeekBarAngle = findViewById(R.id.seekBarAngle);
        mSeekBarRadius = findViewById(R.id.seekBarRadius);
        mSeekBarAngleOffset = findViewById(R.id.seekBarAngleOffset);
        mSeekBarPointerWidth = findViewById(R.id.seekBarPointerWidth);
        mSeekBarPointerHeight = findViewById(R.id.seekBarPointerHeight);
        
        // Configure the angle SeekBar for -90 to 90 range
        mSeekBarAngle.setMax(ANGLE_RANGE);
        // Set initial position to 0 (middle of the range)
        mSeekBarAngle.setProgress(ANGLE_RANGE / 2);
        
        // Update initial text values
        updateAngleText(0);

        // Setup SeekBar listeners
        setupAngleSeekBar();
        setupRadiusSeekBar();
        setupAngleOffsetSeekBar();
        setupPointerWidthSeekBar();
        setupPointerHeightSeekBar();
    }

    private void setupAngleSeekBar() {
        mSeekBarAngle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // Convert the progress (0 to ANGLE_RANGE) to our angle range (ANGLE_MIN to ANGLE_MAX)
                    int angle = progress + ANGLE_MIN;
                    
                    // Update the pointer angle
                    mPointerView.setAngle(angle);
                    updateAngleText(angle);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });
    }
    
    private void updateAngleText(int angle) {
        mTextAngle.setText("角度 (" + ANGLE_MIN + "° 到 " + ANGLE_MAX + "°): " + angle + "°");
    }

    private void setupRadiusSeekBar() {
        mSeekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // Update the pointer radius
                    float radius = progress;
                    mPointerView.setRadius(radius);
                    mTextRadius.setText("半径: " + progress + "dp");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });
    }

    private void setupAngleOffsetSeekBar() {
        mSeekBarAngleOffset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // Update the pointer angle offset (0-360 degrees)
                    float offset = progress;
                    mPointerView.setAngleOffset(offset);
                    mTextAngleOffset.setText("图片旋转角度: " + progress + "°");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });
    }
    
    private void setupPointerWidthSeekBar() {
        mSeekBarPointerWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // Get current height
                    int height = mSeekBarPointerHeight.getProgress();
                    // Convert dp to pixels
                    float density = getResources().getDisplayMetrics().density;
                    int widthPixels = (int) (progress * density);
                    int heightPixels = (int) (height * density);
                    
                    // Update the pointer dimensions
                    mPointerView.setPointerDimensions(widthPixels, heightPixels);
                    mTextPointerWidth.setText("指针宽度: " + progress + "dp");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });
    }
    
    private void setupPointerHeightSeekBar() {
        mSeekBarPointerHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // Get current width
                    int width = mSeekBarPointerWidth.getProgress();
                    // Convert dp to pixels
                    float density = getResources().getDisplayMetrics().density;
                    int widthPixels = (int) (width * density);
                    int heightPixels = (int) (progress * density);
                    
                    // Update the pointer dimensions
                    mPointerView.setPointerDimensions(widthPixels, heightPixels);
                    mTextPointerHeight.setText("指针高度: " + progress + "dp");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });
    }
} 