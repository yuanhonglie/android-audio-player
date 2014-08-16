package com.yhl.jni;

public class Libmad {
	
	static{
		System.loadLibrary("mad");
	}

	public static native int openFile(String fileName);
	public static native int readSamplesInFloatBuffer(int handle, float [] buffer, int size);
	public static native int readSamplesInShortBuffer(int handle, short [] buffer, int size);
	public static native void closeFile(int handle);
	
}
