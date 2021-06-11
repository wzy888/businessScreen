package com.zhumei.baselib.utils.useing.software;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Toast;


import com.zhumei.baselib.utils.useing.hardware.ShellUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreentShotUtils
{

	private static final String TAG = "ScreentShotUtil";

	private static final String CLASS1_NAME = "android.view.SurfaceControl";

	private static final String CLASS2_NAME = "android.view.Surface";

	private static final String METHOD_NAME = "screenshot";

	private static ScreentShotUtils instance;

	private Display mDisplay;

	private DisplayMetrics mDisplayMetrics;

	private Matrix mDisplayMatrix;

	private WindowManager wm;
	
	private SimpleDateFormat format;

	private ScreentShotUtils()
	{

	}

	public static ScreentShotUtils getInstance()
	{
		synchronized (ScreentShotUtils.class)
		{
			if (instance == null)
			{
				instance = new ScreentShotUtils();
			}
		}
		return instance;
	}

	private Bitmap screenShot(int width, int height)
	{
		LogUtils.e(TAG, "android.os.Build.VERSION.SDK : " + android.os.Build.VERSION.SDK_INT);
		Class<?> surfaceClass = null;
		Method method = null;
		try
		{
			LogUtils.e(TAG, "width : " + width);
			LogUtils.e(TAG, "height : " + height);
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
			{
				
				surfaceClass = Class.forName(CLASS1_NAME);
			}
			else
			{
				surfaceClass = Class.forName(CLASS2_NAME);
			}
			method = surfaceClass.getDeclaredMethod(METHOD_NAME, int.class, int.class);
			method.setAccessible(true);
			return (Bitmap) method.invoke(null, width, height);
		}
		catch (NoSuchMethodException e)
		{
			LogUtils.e(TAG, e.toString());
		}
		catch (IllegalArgumentException e)
		{
			LogUtils.e(TAG, e.toString());
		}
		catch (IllegalAccessException e)
		{
			LogUtils.e(TAG, e.toString());
		}
		catch (InvocationTargetException e)
		{
			LogUtils.e(TAG, e.toString());
		}
		catch (ClassNotFoundException e)
		{
			LogUtils.e(TAG, e.toString());
		}
		return null;
	}

	/**
	 * Takes a screenshot of the current display and shows an animation.
	 */
	@SuppressLint("NewApi")
	public void takeScreenshot(Context context, String fileFullPath)
	{
		if(fileFullPath == ""){
			format = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = format.format(new Date(System.currentTimeMillis())) + ".png";
			fileFullPath = "/data/local/tmp/" + fileName;
		}
		
		if(ShellUtils.checkRootPermission()){
			if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
				ShellUtils.execCommand("/system/bin/screencap -p "+ fileFullPath,true);
			}
		}
		else {
			if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2 && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
				wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
				mDisplay = wm.getDefaultDisplay();
				mDisplayMatrix = new Matrix();
				mDisplayMetrics = new DisplayMetrics();
				// We need to orient the screenshot correctly (and the Surface api seems to take screenshots
				// only in the natural orientation of the device :!)
				mDisplay.getRealMetrics(mDisplayMetrics);
				float[] dims =
				{
						mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels
				};
				float degrees = getDegreesForRotation(mDisplay.getRotation());
				boolean requiresRotation = (degrees > 0);
				if (requiresRotation)
				{
					// Get the dimensions of the device in its native orientation
					mDisplayMatrix.reset();
					mDisplayMatrix.preRotate(-degrees);
					mDisplayMatrix.mapPoints(dims);
					dims[0] = Math.abs(dims[0]);
					dims[1] = Math.abs(dims[1]);
				}

				Bitmap mScreenBitmap = screenShot((int) dims[0], (int) dims[1]);
				if (requiresRotation)
				{
					// Rotate the screenshot to the current orientation
					Bitmap ss = Bitmap.createBitmap(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels,
							Bitmap.Config.ARGB_8888);
					Canvas c = new Canvas(ss);
					c.translate(ss.getWidth() / 2, ss.getHeight() / 2);
					c.rotate(degrees);
					c.translate(-dims[0] / 2, -dims[1] / 2);
					c.drawBitmap(mScreenBitmap, 0, 0, null);
					c.setBitmap(null);
					mScreenBitmap = ss;
					if (ss != null && !ss.isRecycled())
					{
						ss.recycle();
					}
				}

				// If we couldn't take the screenshot, notify the user
				if (mScreenBitmap == null)
				{
					Toast.makeText(context, "screen shot fail", Toast.LENGTH_SHORT).show();
				}

				// Optimizations
				mScreenBitmap.setHasAlpha(false);
				mScreenBitmap.prepareToDraw();

				saveBitmap2file(context, mScreenBitmap, fileFullPath);
			}
		}
		
	}

	// 
	public void saveBitmap2file(Context context, Bitmap bmp, String fileName)
	{
		int quality = 100;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		byte[] buffer = new byte[1024];
		int len = 0;
		File file = new File(fileName);
		if (!file.exists())
		{
			try
			{
				file.getParentFile().mkdir();
				file.getParentFile().createNewFile();
			}
			catch (IOException e)
			{
				LogUtils.e(TAG, e.toString());
			}
		}
		else
		{
			try
			{
				file.getParentFile().delete();
				file.getParentFile().createNewFile();
			}
			catch (IOException e)
			{
				LogUtils.e(TAG, e.toString());
			}
		}
		FileOutputStream stream = null;
		try
		{
			stream = new FileOutputStream(file);
			while ((len = is.read(buffer)) != -1)
			{
				stream.write(buffer, 0, len);
			}
			stream.flush();
		}
		catch (FileNotFoundException e)
		{
			LogUtils.e(TAG, e.toString());
		}
		catch (IOException e)
		{
			LogUtils.e(TAG, e.toString());
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					LogUtils.e(TAG, e.toString());
				}
			}
			if (stream != null)
			{
				try
				{
					stream.close();
				}
				catch (IOException e)
				{
					LogUtils.e(TAG, e.toString());
				}
			}
		}
		if (bmp != null && !bmp.isRecycled())
		{
			bmp.recycle();
		}
	}

	/**
	 * @return the current display rotation in degrees
	 */
	private float getDegreesForRotation(int value)
	{
		switch (value)
		{
			case Surface.ROTATION_90:
				return 360f - 90f;
			case Surface.ROTATION_180:
				return 360f - 180f;
			case Surface.ROTATION_270:
				return 360f - 270f;
		}
		return 0f;
	}

}
