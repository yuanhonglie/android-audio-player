package com.yhl.jni;

public class SoundTouch {
	
	static{
		System.loadLibrary("soundtouch");
	}
	
	public static native void setSampleRate(int sampleRate);
	public static native void setChannels(int channel);
	public static native void setTempoChange(float newTempo);
	public static native void setPitchSemiTones(int pitch);
	public static native void setRateChange(float newRate);
	public static native void putSamples(short[] samples, int len);
	public static native short[] receiveSamples();
}
