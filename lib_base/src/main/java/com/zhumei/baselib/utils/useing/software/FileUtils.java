package com.zhumei.baselib.utils.useing.software;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * 文件相关的工具类
 */
public class FileUtils {

    /**
     * Assets获取资源
     * @param context
     * @param filename
     * @return
     * @throws IOException
     */
    public static AssetFileDescriptor getAssetFileDescription(Context context, String filename) throws IOException {
        AssetManager manager = context.getApplicationContext().getAssets();
        return manager.openFd(filename);
    }

    public static String getPath () {
        String path = Environment.getExternalStorageDirectory () + "/cache2";
        File file = new File (path);
        if (file.mkdirs ()) {
            return path;
        }
        return path;


    }

    public static File creatFile() {
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path, "netCache");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;

    }
}
