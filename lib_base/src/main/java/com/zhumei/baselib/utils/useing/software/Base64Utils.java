package com.zhumei.baselib.utils.useing.software;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Base64Utils {

    /**
     * 通过Base64 Bitmap转String
     * @param bitmap
     * @return
     */
    public static String convertBitmapToString(Bitmap bitmap){
        // outputstream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bitmap != null){
            // 压缩比率50
            bitmap.compress(Bitmap.CompressFormat.PNG , 50 , baos);
        }
        // 转为byte数组
        byte[] appicon = baos.toByteArray();
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }

    /**
     * 通过Base64 String转Bitmap
     * @param str
     * @return
     */
    public static Bitmap convertStringToBitmap(String str){
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        }catch (Exception e){
            return null;
        }
    }
}
