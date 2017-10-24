package com.wix.androidshadow;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactViewManager;

/**
 * Created by zachik on 10/10/2017.
 */

public class ShadowParentViewManager extends ReactViewManager {


    @Override
    public String getName() {
        return "ShadowParentView";
    }

    @Override
    public ShadowParentView createViewInstance(ThemedReactContext reactContext) {
        return new ShadowParentView(reactContext);
    }

    @ReactProp(name = "shadowStyle")
    public void setShadowStyle(ShadowParentView view, @Nullable ReadableMap shadowStyle) {
        view.setShadowParams(shadowStyle);
    }

    @ReactProp(name = "shadowRadius", defaultFloat = 0f)
    public void setShadowRadius(ShadowParentView view, float shadowRadius) {
        Log.d("ReactNativeJS","ShadowParentView setShadowRadius!!!!! shadowRadius = " + shadowRadius );
        view.setShadowRadius(shadowRadius);
    }
    @ReactProp(name = "shadowOpacity", defaultFloat = 0.4f)
    public void setShadowOpacity(ShadowParentView view, float shadowOpacity) {
        Log.d("ReactNativeJS","ShadowParentView setShadowOpacity!!!!! shadowOpacity = " + shadowOpacity );
        view.setShadowOpacity(shadowOpacity);
    }

    @ReactProp(name = "shadowColor")
    public void setShadowColor(ShadowParentView view,int color) {
        Log.d("ReactNativeJS","ShadowParentView setShadowColor!!!!! color = " + color );
        view.setShadowColor(color);
    }

    @ReactProp(name = "shadowOffset")
    public void setShadowOffset(ShadowParentView view, @Nullable ReadableMap shadowOffset) {
        view.setShadowOffset(shadowOffset);
    }

}
