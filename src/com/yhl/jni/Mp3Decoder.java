package com.yhl.jni;

public class Mp3Decoder {
	private static final String TAG = "Libmad";
	static{
		System.loadLibrary("decoder");
	}

	public static native int openFile(String fileName);
	public static native int readSamplesInFloatBuffer(int handle, Object buffer, int size);
	public static native int readSamplesInShortBuffer(int handle, short [] buffer, int size);
	public static native void closeFile(int handle);
	
	public static native int getSampleRate(int handle);
	public static native int getMode(int handle);
}
