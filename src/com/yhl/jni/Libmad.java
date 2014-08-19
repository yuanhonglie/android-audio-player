package com.yhl.jni;

public class Libmad {
	
	static{
		System.loadLibrary("mad");
	}

	public static native int openFile(String fileName);
	public static native int readSamplesInFloatBuffer(int handle, Object buffer, int size);
	public static native int readSamplesInShortBuffer(int handle, short [] buffer, int size);
	public static native void closeFile(int handle);
	
	public static native int getSampleRate(int handle);
	public static native long getDurationTime(int handle);
	public static native int getMode(int handle);
	
	public static class Mp3Info{
		public int sampleRate;
		public long durationTime;
		public int mode;
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("sampleRate = ").append(sampleRate).append("|");
			sb.append("durationTime = ").append(durationTime).append("|");
			sb.append("mode = ").append(mode);
			return sb.toString();
		}
	}
}
