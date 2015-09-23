package com.yhl.task;

import com.yhl.jni.Libmad;
import com.yhl.jni.Libmad.Mp3Info;
import com.yhl.jni.SoundTouch;
import com.yhl.task.AudioPlayer.Options;

public class AudioBuffer {
	private int mMadHandle = -1;
	private int mStHandle = -1;
	
	public AudioBuffer(String filePath) {
		mMadHandle = Libmad.openFile(filePath);
		//TODO create soundtouch instance
//		mStHandle = SoundTouch.create();
		//TODO get mp3 sample rate, init soundtouch;
	}
	
	public Mp3Info getMp3Info() {
		Mp3Info info = new Mp3Info();
		info.sampleRate = Libmad.getSampleRate(mMadHandle);
		info.durationTime = Libmad.getDurationTime(mMadHandle);
		info.mode = Libmad.getMode(mMadHandle);
		return info;
	}
	
	public int readPcmBuffer(short [] buffer, int size) {
		return Libmad.readSamplesInShortBuffer(mMadHandle, buffer,size);
	}
	
	public void setTransformOption(Options options) {
		SoundTouch.setTempoChange(options.tempoChange);
		SoundTouch.setPitchSemiTones(options.pitchTones);
		SoundTouch.setRateChange(options.rateChange);
	}
	
	public void putPcmBuffer(short [] buffer, int size) {
		SoundTouch.putSamples(buffer, size);
	}
	
	public short[] receiveSamples() {
		return SoundTouch.receiveSamples();
	}
	
	public void release() {
		if (mMadHandle != -1) {
			Libmad.closeFile(mMadHandle);
			mMadHandle = -1;
		}
		//TODO release soundtouch instance;
		
	}
}
