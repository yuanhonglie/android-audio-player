package com.yhl.jni;

import android.media.AudioFormat;

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
	
	/*
		MAD_MODE_SINGLE_CHANNEL = 0,		// single channel
  		MAD_MODE_DUAL_CHANNEL	  = 1,		// dual channel
  		MAD_MODE_JOINT_STEREO	  = 2,		// joint (MS/intensity) stereo
  		MAD_MODE_STEREO	  = 3				// normal LR stereo
	 */
	
	public interface ChannelMode {
		int MAD_MODE_SINGLE_CHANNEL = 0;
		int MAD_MODE_DUAL_CHANNEL 	= 1;
		int MAD_MODE_JOINT_STEREO 	= 2;
		int MAD_MODE_STEREO			= 3;
	}
	
	public static int channelMode2AudioFormat(int mode) {
		int format = AudioFormat.CHANNEL_OUT_MONO;
		switch (mode) {
		case ChannelMode.MAD_MODE_SINGLE_CHANNEL:
		case ChannelMode.MAD_MODE_JOINT_STEREO:
			format = AudioFormat.CHANNEL_OUT_MONO;
			break;
		case ChannelMode.MAD_MODE_DUAL_CHANNEL:
			break;
		case ChannelMode.MAD_MODE_STEREO:
			
			break;
		default:
			break;
		}
		
		return format;
	}
}
