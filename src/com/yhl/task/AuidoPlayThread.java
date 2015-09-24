package com.yhl.task;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import com.yhl.task.AudioPlayer.Mp3Info;
import com.yhl.task.AudioPlayer.Options;

public class AuidoPlayThread extends Thread {
	
	private static final String TAG = AuidoPlayThread.class.getSimpleName();
	private static final int BUFFER_SIZE_SHORT = 4096;
	private AudioBuffer mAudioBuffer;
	private AudioTrack mAudioTrack;
	
	public AuidoPlayThread(String fileName) {
		mAudioBuffer = new AudioBuffer(fileName);
		Options options = new Options(0, 0, 0);
		mAudioBuffer.setTransformOption(options);
	}

	@Override
	public synchronized void run() {
		super.run();
		
		while (mAudioBuffer.prepareBufferInShort(BUFFER_SIZE_SHORT) > 0) {
			createAudioTrackIfNeed();
			
			short[] buffer = null;
			do {
				buffer = mAudioBuffer.readSamples();
				writeBuffer(buffer, buffer.length);
			} while (buffer != null && buffer.length > 0);
			
			if (mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_PLAYING) {
				Log.i(TAG, "start to play-->");
				mAudioTrack.play();
			}
		}
		
		mAudioBuffer.release();
		if (mAudioTrack != null) {
			mAudioTrack.release();
		}
	}
	
	private void createAudioTrackIfNeed() {
		if (mAudioTrack == null) {
			Mp3Info mInfo = mAudioBuffer.getMp3Info();
			int bufferSizeInBytes = AudioTrack.getMinBufferSize(mInfo.sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
			mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mInfo.sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes*4, AudioTrack.MODE_STREAM);
		}
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
