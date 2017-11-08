
package com.wix.androidshadow;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.views.view.ReactViewGroup;

public class RNReactNativeAndoridShadowModule extends ReactContextBaseJavaModule {
    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 17.5f;
    private static final String TAG = "ReactNativeJS";
    private final ReactApplicationContext reactContext;
//    private ReactViewGroup targetView;
    private ReactViewGroup parentView;

    public RNReactNativeAndoridShadowModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AndroidShadowManager";
    }

    @ReactMethod
    public void applyShadowForView(final Integer tag, final ReadableMap param) {
        Log.d(TAG,"AndroidShadowManager applyShadowForView! tag: " + tag);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        UIManagerModule uiManager = reactContext.getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(new UIBlock() {

            @Override
            public void execute(NativeViewHierarchyManager nvhm) {
                ReactViewGroup targetView = (ReactViewGroup) nvhm.resolveView(tag);
                Log.d(TAG,"AndroidShadowManager view w = " + targetView.getWidth() + " h = " + targetView.getHeight());
//                targetView.setBackgroundColor(Color.CYAN);
                targetView.getViewTreeObserver().addOnGlobalLayoutListener(new OutlineAdjuster(targetView,param));

            }
        });
    }

    private ReactRootView findReactRoot(View v) {
        while (v.getParent() != null) {
            ((ViewGroup) v).setClipChildren(false);
            ((ViewGroup) v).setClipToPadding(false);
            Log.d(TAG,"has parent: " + v.getClass().getName());
            if (v instanceof ReactRootView) {
                Log.d(TAG,"has root");
                return (ReactRootView) v;
            }
            v = (View) v.getParent();
        }
        Log.d(TAG,"no root");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class OutlineAdjuster implements ViewTreeObserver.OnGlobalLayoutListener {
        private ReactViewGroup mTargetView;
        private ReadableMap mParam;

        public OutlineAdjuster(ReactViewGroup mTargetView, ReadableMap mParam) {
            this.mTargetView = mTargetView;

            this.mParam = mParam;
        }

        @Override
        public void onGlobalLayout() {
            final float dens = mTargetView.getResources().getDisplayMetrics().density;
            Log.d(TAG,"AndroidShadowManager onGlobalLayout w = " + mTargetView.getWidth() + " h = " + mTargetView.getHeight());
            Log.d(TAG,"AndroidShadowManager onGlobalLayout pad top = " + mTargetView.getPaddingTop() + " right = " + mTargetView.getPaddingRight());
            mTargetView.setOutlineProvider(new ViewOutlineProvider() {

                @Override
                public void getOutline(View view, Outline outline) {
                    Log.d(TAG,"AndroidShadowManager getOutline w = " + view.getWidth());

                    int insetX = (int) (mParam.getDouble("insetX") * dens);
                    int insetY = (int) (mParam.getDouble("insetY") * dens);
                    int offsetX = (int) (mParam.getDouble("offsetX") * dens);
                    int offsetY = (int) (mParam.getDouble("offsetY") * dens);
                    float cornerRad = (float) (mParam.getDouble("cornerRadius") * dens);
                    float elevation = (float) (mParam.getDouble("elevation") * dens);

                    mTargetView.setElevation(elevation);

                    Rect bounds = new Rect(0,0,view.getWidth(),view.getHeight());
                    bounds.inset(insetX,insetY);
                    outline.setRoundRect(bounds,cornerRad);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        outline.offset(offsetX,offsetY);
                    }
                }
            });


        }
    }

}