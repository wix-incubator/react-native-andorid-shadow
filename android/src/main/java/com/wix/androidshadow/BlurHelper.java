package com.wix.androidshadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by zachik on 10/10/2017.
 */

public class BlurHelper {

    public static Bitmap blur(Context ctx, Bitmap image,float scale,float radius) {
        int width = Math.round(image.getWidth() * scale);
        int height = Math.round(image.getHeight() * scale);

        if (radius <= 0f) {
            return image;
        }
        radius = Math.min(radius,25f);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(ctx);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

//        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
//        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        for (int i = 0; i < 2; i++) {
            Allocation tmpIn = Allocation.createFromBitmap(rs, outputBitmap);
            Allocation tmpOut = Allocation.createTyped(rs, tmpIn.getType());

            theIntrinsic.setRadius(radius);
            theIntrinsic.setInput(tmpIn);
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);
        }

//        return outputBitmap;
        return Bitmap.createScaledBitmap(outputBitmap, image.getWidth(), image.getHeight(), false);
    }
}
