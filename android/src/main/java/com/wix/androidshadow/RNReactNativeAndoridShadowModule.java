
package com.wix.androidshadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
    private ReactViewGroup targetView;
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

        UIManagerModule uiManager = reactContext.getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(new UIBlock() {

            @Override
            public void execute(NativeViewHierarchyManager nvhm) {
                targetView = (ReactViewGroup) nvhm.resolveView(tag);
//                ((View) targetView.getParent()).setBackgroundColor(Color.RED);
                parentView = (ReactViewGroup) targetView.getParent();

                findReactRoot(targetView);
                parentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                               int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        Log.d(TAG,"onLayoutChange bottom = " + bottom + " top = " + top);
                        captureView();
                    }
                });
//                targetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        Log.d(TAG,"AndroidShadowManager onGlobalLayout: w = " + targetView.getWidth());
//                        Log.d(TAG,"AndroidShadowManager onGlobalLayout: parent w = " + parentView.getWidth());
//                    }
//                });
            }
        });
    }

    private Bitmap captureView() {
        parentView.setDrawingCacheEnabled(true);
        parentView.destroyDrawingCache();
        parentView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        Bitmap bitmap = parentView.getDrawingCache();
        Log.d(TAG,"AndroidShadowManager captureView w = " + bitmap.getWidth());
        Bitmap blurredBmp = blur(targetView.getContext(),bitmap);
        parentView.setTranslucentBackgroundDrawable(new BitmapDrawable(targetView.getResources(),blurredBmp));
        return bitmap;
    }



    public static Bitmap blur(Context ctx, Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

//        RenderScript rs = RenderScript.create(ctx);
//        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
//        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
//        theIntrinsic.setRadius(BLUR_RADIUS);
//        theIntrinsic.setInput(tmpIn);
//        theIntrinsic.forEach(tmpOut);
//        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
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


}