package com.example.learnandroid.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.learnandroid.R;


/**
 * Custom view that displays a rotating pointer around a center point.
 * The pointer rotates in a semi-circular range with a specified radius.
 */
public class PointerView extends View {

    private Bitmap mPointerBitmap;
    private final Paint mPaint;
    private final Matrix mMatrix;

    // Center point coordinates (will be initialized in onSizeChanged)
    private float mCenterX;
    private float mCenterY;

    // Rotation parameters
    private float mRadius = 100f; // Default radius
    private float mAngle = 0f; // Current angle in degrees (-90 to 90 for semi-circle)

    // Pointer dimensions
    private int mPointerWidth = 9;
    private int mPointerHeight = 56;

    // New field for angle offset
    private float mAngleOffset = 0f; // Default offset, adjust if needed

    public PointerView(Context context) {
        this(context, null);
    }

    public PointerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMatrix = new Matrix();

        // Read custom attributes
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PointerView, defStyleAttr, 0);

            try {
                mRadius = a.getDimension(R.styleable.PointerView_pointerRadius, mRadius);
                mAngle = a.getFloat(R.styleable.PointerView_pointerAngle, mAngle);
                
                // Get custom pointer dimensions (convert dp to pixels)
                float density = getResources().getDisplayMetrics().density;
                mPointerWidth = (int) a.getDimension(R.styleable.PointerView_pointerWidth, mPointerWidth * density);
                mPointerHeight = (int) a.getDimension(R.styleable.PointerView_pointerHeight, mPointerHeight * density);

                // Load pointer drawable if specified
                int pointerDrawableResId = a.getResourceId(R.styleable.PointerView_pointerDrawable, 0);
                if (pointerDrawableResId != 0) {
                    setPointerResource(pointerDrawableResId, mPointerWidth, mPointerHeight);
                }
            } finally {
                a.recycle();
            }
        }

        // If pointer bitmap is still null, create a default pointer
        if (mPointerBitmap == null) {
            createDefaultPointer();
        }
    }

    /**
     * Creates a default pointer bitmap if none is provided
     */
    private void createDefaultPointer() {
        // Create a simple red pointer bitmap
        Bitmap bitmap = Bitmap.createBitmap(mPointerWidth, mPointerHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);

        // Draw a simple triangle pointer
        float halfWidth = mPointerWidth / 2f;

        // Create a path for the triangle
        android.graphics.Path path = new android.graphics.Path();
        path.moveTo(halfWidth, 0);                // Top point
        path.lineTo(0, mPointerHeight);           // Bottom left
        path.lineTo(mPointerWidth, mPointerHeight); // Bottom right
        path.close();

        // Draw filled triangle
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);

        mPointerBitmap = bitmap;
    }

    /**
     * Set the pointer image resource ID with specified dimensions
     *
     * @param resId Resource ID of the pointer image
     * @param width Desired width of the pointer in pixels
     * @param height Desired height of the pointer in pixels
     */
    public void setPointerResource(int resId, int width, int height) {
        try {
            Resources res = getResources();
            Bitmap originalBitmap = BitmapFactory.decodeResource(res, resId);
            
            if (originalBitmap != null) {
                // Scale the bitmap to the requested size
                mPointerBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true);
                // Recycle the original bitmap if we created a new one
                if (mPointerBitmap != originalBitmap) {
                    originalBitmap.recycle();
                }
            } else {
                // Log error to help with debugging
                android.util.Log.e("PointerView", "Failed to decode bitmap resource with ID: " + resId);
            }
        } catch (Exception e) {
            android.util.Log.e("PointerView", "Error loading pointer bitmap", e);
        }
        invalidate();
    }

    /**
     * Set the pointer image resource ID
     *
     * @param resId Resource ID of the pointer image
     */
    public void setPointerResource(int resId) {
        setPointerResource(resId, mPointerWidth, mPointerHeight);
    }
    
    /**
     * Set the dimensions of the pointer
     *
     * @param width Width in pixels
     * @param height Height in pixels
     */
    public void setPointerDimensions(int width, int height) {
        mPointerWidth = width;
        mPointerHeight = height;
        
        // If we have a bitmap already, resize it
        if (mPointerBitmap != null) {
            Bitmap newBitmap = Bitmap.createScaledBitmap(mPointerBitmap, width, height, true);
            // Recycle the old bitmap if it's different
            if (newBitmap != mPointerBitmap) {
                mPointerBitmap.recycle();
                mPointerBitmap = newBitmap;
            }
        } else {
            // Create a default pointer with the new dimensions
            createDefaultPointer();
        }
        
        invalidate();
    }

    /**
     * Set the pointer image from an existing bitmap
     *
     * @param bitmap Bitmap to use as pointer
     */
    public void setPointerBitmap(Bitmap bitmap) {
        mPointerBitmap = bitmap;
        invalidate();
    }

    /**
     * Set the rotation radius
     *
     * @param radius Radius in pixels
     */
    public void setRadius(float radius) {
        mRadius = radius;
        invalidate();
    }

    /**
     * Get the current radius
     *
     * @return Current radius in pixels
     */
    public float getRadius() {
        return mRadius;
    }

    /**
     * Set the rotation angle (-90 to 90 degrees, where -90 is left horizontal and 90 is right horizontal)
     *
     * @param angle Angle in degrees
     */
    public void setAngle(float angle) {
        // Constrain angle to -90 to 90 degrees range
        mAngle = Math.max(-90, Math.min(90, angle));
        invalidate();
    }

    /**
     * Get the current angle
     *
     * @return Current angle in degrees (-90 to 90)
     */
    public float getAngle() {
        return mAngle;
    }


    /**
     * Set the angle offset for pointer direction
     *
     * @param offset Offset in degrees
     */
    public void setAngleOffset(float offset) {
        mAngleOffset = offset;
        invalidate();
    }

    /**
     * Get the current angle offset
     *
     * @return Current angle offset in degrees
     */
    public float getAngleOffset() {
        return mAngleOffset;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Set center position at the bottom center for semi-circular gauge
        mCenterX = w / 2f;
        mCenterY = h; // Position the center point at the bottom of the view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPointerBitmap == null) {
            return;
        }

        // Calculate the position of the pointer based on angle and radius
        // For our semi-circle, angle -90° is horizontal left, 0° is straight up, 90° is horizontal right
        
        // Position the pointer at the end of the radius
        // We start from 0 degrees (pointing up) and rotate according to mAngle
        double pointerRadians = Math.toRadians(mAngle);
        float pointerX = mCenterX + (float) (mRadius * Math.sin(pointerRadians));
        float pointerY = mCenterY - (float) (mRadius * Math.cos(pointerRadians));

        // Reset the matrix and set up transformations
        mMatrix.reset();

        // Calculate the pivot point for rotation (center of the bitmap's width, at the bottom of the bitmap)
        float pivotX = mPointerBitmap.getWidth() / 2f;
        float pivotY = mPointerBitmap.getHeight();

        // First translate the bitmap so that its bottom center is at the origin (0,0)
        mMatrix.postTranslate(-pivotX, -pivotY);

        // Then rotate the bitmap - using the configurable angle offset + the actual angle
        mMatrix.postRotate(mAngle + mAngleOffset);

        // Finally, translate the bitmap to its position on the circle
        mMatrix.postTranslate(pointerX, pointerY);

        // Draw the pointer
        canvas.drawBitmap(mPointerBitmap, mMatrix, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (int) (mRadius * 2) + getPaddingLeft() + getPaddingRight();
        int desiredHeight = (int) (mRadius + mPointerHeight) + getPaddingTop() + getPaddingBottom();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        // Width calculation
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        // Height calculation
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }
} 