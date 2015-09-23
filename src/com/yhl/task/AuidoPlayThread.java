package com.yhl.task;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import com.yhl.jni.Libmad;
import com.yhl.jni.SoundTouch;
import com.yhl.jni.Libmad.Mp3Info;

public class AuidoPlayThread extends Thread {
	
	private static final String TAG = AuidoPlayThread.class.getSimpleName();
	private int mHandle = -1;
	private static final int BUFFER_SIZE_SHORT = 4096;
	private short [] mBuffer = new short[BUFFER_SIZE_SHORT];
	private boolean inited = false;
	private Mp3Info mInfo = new Mp3Info();
	
	private AudioTrack mAudioTrack;
	
	public AuidoPlayThread(String fileName) {
		mHandle = Libmad.openFile(fileName);
		inited = false;
	}

	@Override
	public synchronized void run() {
		super.run();
		
		int size = 0;
		while ((size = Libmad.readSamplesInShortBuffer(mHandle, mBuffer,
				BUFFER_SIZE_SHORT)) > 0) {
			if (!inited) {
				mInfo.sampleRate = Libmad.getSampleRate(mHandle);
				mInfo.durationTime = Libmad.getDurationTime(mHandle);
				mInfo.mode = Libmad.getMode(mHandle);
				Log.i(TAG, "Mp3Info = " + mInfo);
				initSoundTouch(mInfo.sampleRate, 1);
				inited = true;
				int encoding = AudioFormat.ENCODING_PCM_16BIT;
				int bufferSizeInBytes = AudioTrack.getMinBufferSize(mInfo.sampleRate, AudioFormat.CHANNEL_OUT_MONO, encoding);
				mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mInfo.sampleRate, AudioFormat.CHANNEL_OUT_MONO, encoding, bufferSizeInBytes*4, AudioTrack.MODE_STREAM);
			}
			
			
			SoundTouch.putSamples(mBuffer, size);
			int readSize = 0;
			do {
				short[] audioBuffer = SoundTouch.receiveSamples();
				writeBuffer(audioBuffer, audioBuffer.length);
				readSize = audioBuffer.length;
			} while (readSize != 0);
			
			if (mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_PLAYING) {
				Log.i(TAG, "start to play-->");
				mAudioTrack.play();
			}
		}
		
		Libmad.closeFile(mHandle);
	}

	private void initSoundTouch(int sampleRate, int channel) {
		SoundTouch.setChannels(channel);
		SoundTouch.setSampleRate(sampleRate);
		SoundTouch.setTempoChange((float)-20);
		SoundTouch.setPitchSemiTones(0);
		SoundTouch.setRateChange((float)0);
	}
	
	
	public void audioPlay() {
		if (mAudioTrack != null) {
			mAudioTrack.play();
		}
	}
	
	public void audioPause() {
		if (mAudioTrack != null) {
			mAudioTrack.pause();
		}
	}
	
	
	public void audioStop() {
		if (mAudioTrack != null) {
			mAudioTrack.stop();
		}
	}
	
	public boolean isPlaying() {
		if (mAudioTrack != null) {
			int state = mAudioTrack.getPlayState();
			Log.i(TAG, "isPlaying state = " + state);
			return AudioTrack.PLAYSTATE_PLAYING == state;
		}
		return false;
	}
	
	public boolean isStoped() {
		if (mAudioTrack != null) {
			int state = mAudioTrack.getPlayState();
			Log.i(TAG, "isStoped state = " + state);
			return AudioTrack.PLAYSTATE_STOPPED == state;
		}
		return true;
	}
	
	public boolean isPaused() {
		if (mAudioTrack != null) {
			int state = mAudioTrack.getPlayState();
			Log.i(TAG, "isPaused state = " + state);
			return AudioTrack.PLAYSTATE_PAUSED == state;
		}
		return false;
	}
	
	public void audioRelease() {
		if (mAudioTrack != null) {
			mAudioTrack.release();
		}
	}

	private void writeBuffer(short [] buffer, int size) {
		if (mAudioTrack != null && size > 0) {
			mAudioTrack.write(buffer, 0, size);
		}
	}
	
}
