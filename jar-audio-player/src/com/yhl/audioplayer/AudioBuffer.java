package com.yhl.audioplayer;

import com.yhl.audioplayer.AudioPlayer.Mp3Info;
import com.yhl.audioplayer.AudioPlayer.Options;
import com.yhl.jni.Mp3Decoder;

public class AudioBuffer {
	private int mHandler = -1;
	
	public AudioBuffer(String filePath) {
		mHandler = Mp3Decoder.openFile(filePath);
	}
	
	public Mp3Info getMp3Info() {
		Mp3Info info = new Mp3Info();
		info.sampleRate = Mp3Decoder.getSampleRate(mHandler);
		return info;
	}
	
	public void setSoundTouchOptions(Options options) {
		Mp3Decoder.setSoundTouchOptions(mHandler, options.newTempo, options.pitch, options.newRate);
	}
	
	public int prepareBufferInShort(int size) {
		return Mp3Decoder.prepareBufferInShort(mHandler, size);
	}
	
	public short[] readSamples() {
		return Mp3Decoder.readSamples(mHandler);
	}
	
	public void release() {
		if (mHandler != -1) {
			Mp3Decoder.closeFile(mHandler);
			mHandler = -1;
		}
	}
}
