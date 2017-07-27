package com.sm366.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Locale;

import org.apache.commons.io.FileUtils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

//文件操作

public class FileManager {

	public static final String TAG = "FileManager";
	private String myRoot;

	private static FileManager instance;

	public static FileManager getInstance() {
		if (instance == null) {
			instance = new FileManager();
		}

		return instance;
	}

	private FileManager() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String sdcard = Environment.getExternalStorageDirectory().getPath();
			myRoot = sdcard + "/kingdee/assistant/";
			File file = new File(myRoot);
			if (!file.exists()) {
				file.mkdirs();
			}
		} else {
			Log.e(TAG, "sdcard没有挂载!!!");
		}
	}
    public static String getAvaliableSpace(){
    	String result=null;
    	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
    		File file=Environment.getExternalStorageDirectory();
    		StatFs statFs=new StatFs(file.getPath());
    		long availableBlock=statFs.getAvailableBlocks();
    		long blockSize=statFs.getBlockSize();
    		long availableSize=availableBlock*blockSize/1024;//单位KB
    		long availableSize1=availableSize/1024;//单位MB
    		Log.i("the space of your SD card"," the data is "+availableSize1);
    	    if(availableSize1<18){
    	    	result="您的存储卡所剩的空间不多，这会影响您的日志的附件的效果，请及时清理";
    	    }else{
    	    	result=null;
    	    }
    	}
    	return result;
    }
	
	public String getMyRoot() {
		return myRoot;
	}

	// 判断文件类型
	public static String getMIMEType(File f) {
		String end = f.getName().substring(f.getName().lastIndexOf(".") + 1, f.getName().length())
				.toLowerCase(Locale.getDefault());
		String type = "";
		if (end.equals("mp3") || end.equals("aac") || end.equals("aac") || end.equals("amr") || end.equals("mpeg")
				|| end.equals("mp4")) {
			type = "audio";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg")) {
			type = "image";
		} else {
			type = "*";
		}
		type += "/*";
		return type;
	}

	/**
	 * 将bitmap写入文件
	 * */
	public static void saveImageToSD(String filePath, Bitmap bmp) {
		File file = new File(filePath);
		FileOutputStream fOut = null;
		try {
			file.createNewFile();
			fOut = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 70, fOut);
			fOut.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fOut != null) {
					fOut.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 重命名文件
	 * @param path
	 *            父目�?	 * @param name
	 *            之前的文件名
	 * @param rename
	 *            新文件名
	 */
	public static boolean renameFile(String source, String dest) {
		File file = new File(source);
		if (file.exists()) {
			return file.renameTo(new File(dest));
		}
		return false;
	}

	/**
	 * 复制
	 * */
	public static void copyFile(InputStream is, String dest) {
		try {
			FileUtils.copyInputStreamToFile(is, new File(dest));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void copyFile(String source, String dest) {
		try {
			FileUtils.copyFile(new File(source), new File(dest));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*** 删除文件 ***/
	public static void deleteFile(File file) {
		if (!file.exists())
			return;
		if (file.isFile()) {
			file.delete();
			return;
		}
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteFile(files[i]);
		}
		file.delete();
		Log.d(TAG, "删除文件成功:" + file);
	}

	/*** 删除子目录/
	public static void deleteChildren(File dir) {
		if (!dir.exists() || !dir.isDirectory()) {
			Log.e(TAG, dir.getPath() + "is not a directory!");
			return;
		}
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteFile(files[i]);
		}
		Log.d(TAG, "清空文件夹成功 dir = " + dir.getPath());
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/*** 转换文件大小单位(b/kb/mb/gb) ***/
	public static String FormetFileSize(File file) {// 转换文件大小
		long fileS = 0;
		if (file.isFile()) {
			fileS = FileUtils.sizeOf(file);
		}
		if (file.isDirectory()) {
			fileS = FileUtils.sizeOfDirectory(file);
		}

		DecimalFormat df = new DecimalFormat("#0");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
}
