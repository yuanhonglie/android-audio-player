package com.yhl.jni;

public class Mp3Decoder {
	private static final String TAG = "Mp3Decoder";
	static{
		System.loadLibrary("decoder");
	}

	public static native int openFile(String fileName);
	
	public static native void setSoundTouchOptions(int handle, float newTempo, int pitch, float newRate);
	public static native int prepareBufferInShort(int handle, int size);
	public static native short[] readSamples(int handle);
	
	public static native int getSampleRate(int handle);
	public static native int getMode(int handle);
	
	public static native void closeFile(int handle);
}
