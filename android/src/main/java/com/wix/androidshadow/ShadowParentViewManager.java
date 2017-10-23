package com.wix.androidshadow;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by zachik on 10/10/2017.
 */

public class ShadowParentViewManager extends ViewGroupManager<ShadowParentView> {


    @Override
    public String getName() {
        return "ShadowParentView";
    }

    @Override
    protected ShadowParentView createViewInstance(ThemedReactContext reactContext) {
        return new ShadowParentView(reactContext);
    }

    @ReactProp(name = "shadowStyle")
    public void setShadowStyle(ShadowParentView view, @Nullable ReadableMap shadowStyle) {
        view.setShadowParams(shadowStyle);
    }

}
