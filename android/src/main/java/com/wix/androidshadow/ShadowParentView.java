package com.wix.androidshadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.AsyncTask;
import android.support.annotation.AnyThread;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import com.facebook.react.views.view.ReactViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by zachik on 10/10/2017.
 */

public class ShadowParentView extends ReactViewGroup {
    private static final String TAG = "ReactNativeJS";

    private int shadowPadding = 18;
    private float shadowRadius = 25f;
    //    private float shadowOpacity = 1f;
    private int shadowColor;
    private boolean hasShadowColor = false;

    public void setBlurInBG(boolean blurInBG) {
        this.blurInBG = blurInBG;
    }

    private boolean blurInBG = true;
    private float shadowOffsetX = 0f;
    private float shadowOffsetY = 0f;
    //    private Paint shadowPaint;
    private boolean firstLayout = true;

    private NinePatchDrawable shadowDrawable;

    public ShadowParentView(Context context) {
        super(context);
//        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setShadowParams(ReadableMap readableMap) {
//        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
//        while (iterator.hasNextKey()) {
//            String key = iterator.nextKey();
//            ReadableType type = readableMap.getType(key);
//            Log.d(TAG,"ShadowParentView setShadowParams! key = " + key + " typ = " + type);
//        }

//        shadowRadius = (float) readableMap.getDouble("shadowRadius");
//        shadowColor = Color.parseColor(readableMap.getString("shadowColor"));
//        shadowOpacity = (float) readableMap.getDouble("shadowOpacity");
//        if (readableMap.hasKey("shadowOffset")) {
//            ReadableMap offsetMap = readableMap.getMap("shadowOffset");
//            shadowOffsetX = (float) offsetMap.getDouble("width");
//            shadowOffsetY = (float) offsetMap.getDouble("height") * getResources().getDisplayMetrics().density;
//        }
//
//        Log.d(TAG,"ShadowParentView setShadowParams! shadowRadius = " + shadowRadius + " shadowOffsetY = " + shadowOffsetY);
//
//
//        shadowPaint.setColorFilter(new PorterDuffColorFilter(shadowColor, PorterDuff.Mode.SRC_IN));
//        shadowPaint.setAlpha((int) (shadowOpacity * 255));
    }

    public void setShadowRadius(float radius) {
//        this.shadowRadius = dpToPx(radius);
    }
    public void setShadowOpacity(float opacity) {
//        this.shadowPaint.setAlpha((int) (opacity * 255));
    }
    public void setShadowColor(int color) {
        shadowColor = color;
        hasShadowColor = true;
//        shadowPaint.setColorFilter(new PorterDuffColorFilter(shadowColor, PorterDuff.Mode.SRC_IN));
    }

    public void setShadowOffset(ReadableMap offsetMap) {
//        shadowOffsetX = dpToPx(  (float) offsetMap.getDouble("width"));
//        shadowOffsetY = dpToPx( (float) offsetMap.getDouble("height"));
    }

    public void setShadowImageUri(String uriStr) {
        shadowDrawable = (NinePatchDrawable) ResourceDrawableIdHelper.getInstance().getResourceDrawable(getContext(),uriStr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "ShadowParentView onLayout! w = " + getWidth() + " changed = " + changed);
        try {
            ((ViewGroup) getParent()).setClipChildren(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (firstLayout && getWidth() > 0 && shadowDrawable != null){
            firstLayout = false;
            decodeShadowBounds();

        }
    }

    private void decodeShadowBounds() {
        if (hasShadowColor) {
            shadowDrawable.setColorFilter(new PorterDuffColorFilter(shadowColor, PorterDuff.Mode.SRC_IN));
        }
        Rect pad = new Rect();
        shadowDrawable.getPadding(pad);

        View view = getChildAt(0);
        Rect bounds = new Rect(view.getLeft()-pad.left, view.getTop()-pad.top,
                view.getRight()+pad.right, view.getBottom()+pad.bottom);

        shadowPadding = Math.max(Math.max(pad.left,pad.top),Math.max(pad.right,pad.bottom));

        shadowDrawable.setBounds(bounds);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG,"ShadowParentView dispatchDraw! " );

        drawShadow(canvas);

        super.dispatchDraw(canvas);
    }

    private void drawShadow(Canvas canvas) {
        if (shadowDrawable != null) {

            Rect newRect = canvas.getClipBounds();
            Log.d(TAG,"ShadowParentView has blur! " + newRect);
            newRect.inset(-shadowPadding, -shadowPadding);
            canvas.clipRect (newRect, Region.Op.REPLACE);

            shadowDrawable.draw(canvas);
        }
    }


}
