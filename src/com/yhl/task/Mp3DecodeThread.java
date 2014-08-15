package com.yhl.task;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.os.Environment;
import android.util.Log;

import com.yhl.jni.Libmad;

public class Mp3DecodeThread extends Thread {
	
	private static final String TAG = Mp3DecodeThread.class.getSimpleName();
	private int mHandle = -1;
	private static final int BUFFER_SIZE = 4096;
	private short [] mBuffer = new short[BUFFER_SIZE];
	private String outPath = Environment.getExternalStorageDirectory().getPath() + "/test.pcm";
	
	
	public Mp3DecodeThread(String fileName) {
		mHandle = Libmad.openFile(fileName);
	}
	
	@Override
	public void run() {
		super.run();
		
		int size = 0;
		BufferedOutputStream bos = null;
		try {
			File pcmFile = new File(outPath);
			FileOutputStream fos = new FileOutputStream(pcmFile);
			bos = new BufferedOutputStream(fos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while ((size = Libmad.readSamplesInShortBuffer(mHandle, mBuffer, BUFFER_SIZE)) > 0) {
			Log.i(TAG, "decoded size = " + size);
			if (bos != null) {
				byte [] buffer = shortArray2ByteArray(mBuffer, size);
				try {
					bos.write(buffer);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private byte [] shortArray2ByteArray(short [] shorts, int len) {
		
		if (shorts == null || shorts.length == 0) {
			return null;
		}
		
		int size = shorts.length;
		
		int j = size;
		short num = 0;

		len = Math.min(len, size);
		byte [] bytes = new byte[len+len];
		
		for (int i = 0; i < len; i++) {
			
			j = i + i;
			num = shorts[i];
			
			bytes[j] = (byte)((num >> 8) & 0xff);
			bytes[j+1] = (byte)(num & 0xff);
		}
		
		return bytes;
	}
}
