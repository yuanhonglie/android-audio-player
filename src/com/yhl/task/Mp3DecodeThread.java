package com.yhl.task;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import android.os.Environment;
import android.util.Log;

import com.yhl.jni.Libmad;
import com.yhl.jni.Libmad.Mp3Info;

public class Mp3DecodeThread extends Thread {
	
	private static final String TAG = Mp3DecodeThread.class.getSimpleName();
	private int mHandle = -1;
	private static final int BUFFER_SIZE_SHORT = 4096;
	private static final int BUFFER_SIZE_BYTE = BUFFER_SIZE_SHORT + BUFFER_SIZE_SHORT;
	private short [] mBuffer = new short[BUFFER_SIZE_SHORT];
	private String outPath = Environment.getExternalStorageDirectory().getPath() + "/test.pcm";
	private boolean inited = false;
	private Mp3Info mInfo = new Mp3Info();
	
	private AudioBuffer [] mAudioBuffers = new AudioBuffer[2];
	private int flag;
	
	public Mp3DecodeThread(String fileName) {
		mHandle = Libmad.openFile(fileName);
		inited = false;
		flag = 0;
	}
	
	@Override
	public synchronized void run() {
		super.run();
		
		int size = 0;
		BufferedOutputStream bos = null;
		try {
			File pcmFile = new File(outPath);
			FileOutputStream fos = new FileOutputStream(pcmFile);
			bos = new BufferedOutputStream(fos);
			while ((size = Libmad.readSamplesInShortBuffer(mHandle, mBuffer, BUFFER_SIZE_SHORT)) > 0) {
				if (!inited) {
					mInfo.sampleRate = Libmad.getSampleRate(mHandle);
					mInfo.durationTime = Libmad.getDurationTime(mHandle);
					mInfo.mode = Libmad.getMode(mHandle);
					Log.i(TAG, "Mp3Info = " + mInfo);
					inited = true;
				}
				
				if (bos != null) {
					byte [] buffer = shortArray2ByteArray(mBuffer, size);
					try {
						bos.write(buffer);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				try {
					this.wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		
		int index = (flag + 1) % 2;
		mAudioBuffers[index].size = len + len;
		byte [] bytes = mAudioBuffers[index].getWriteBuffer();
		
		for (int i = 0; i < len; i++) {
			
			j = i + i;
			num = shorts[i];
			
			bytes[j] = (byte)(num & 0xff);
			bytes[j+1] = (byte)((num >> 8) & 0xff);
		}
		
		return bytes;
	}
	
	public byte [] read() {
		flag += 1;
		flag %= 2;
		this.notify();
		return mAudioBuffers[flag].getReadBuffer();
	}
	
	public static class AudioBuffer {
		byte [] buffer = new byte[BUFFER_SIZE_BYTE];
		int size;
		
		public byte[] getReadBuffer() {
			return Arrays.copyOfRange(buffer, 0, size);
		}
		
		public byte[] getWriteBuffer() {
			return buffer;
		}
	}
}
