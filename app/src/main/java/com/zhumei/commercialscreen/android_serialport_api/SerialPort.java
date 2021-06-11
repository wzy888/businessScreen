/*
 * Copyright 2009 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhumei.commercialscreen.android_serialport_api;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {

    private static final String TAG = "SerialPort";
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    public SerialPort(File device, int baudrate, int flags) throws SecurityException, IOException {
        // 检查文件访问权限
        if (!device.canRead() || !device.canWrite()) {
            try {
                // 通过挂载到linux的方式，修改文件的操作权限
                Process su = Runtime.getRuntime().exec("/system/bin/su");
                String cmd = "chmod 777 " + device.getAbsolutePath() + "\n" + "exit\n";
                su.getOutputStream().write(cmd.getBytes());
                if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }
        mFd = open(device.getAbsolutePath(), baudrate, flags);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
    }

    public InputStream getInputStream() {
        return mFileInputStream;
    }
    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }

    /**
     * JNI 调用Java 的本地接口 实现串口的打开和关闭
     * 串口有五个重要的参数：串口设备名、波特率、校验位、数据位、停止位
     * 其中检验位一般默认为NONE，数据位一般默认为8，停止位默认为1
     * @param path 串口设备绝对路径
     * @param baudrate 波特率
     * @param flags 校验位
     * @return
     */
    private native static FileDescriptor open(String path, int baudrate, int flags);

    public native void close();

    static {
        // 加载jni下的C文件库
        System.loadLibrary("serial_port");
    }
}
