package com.yhl.task;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import com.yhl.task.AudioPlayer.Mp3Info;
import com.yhl.task.AudioPlayer.Options;

public class AudioPlayThread extends Thread {
	
	private static final String TAG = AudioPlayThread.class.getSimpleName();
	private static final int BUFFER_SIZE_SHORT = 4096;
	private AudioBuffer mAudioBuffer;
	private AudioTrack mAudioTrack;
	
	public AudioPlayThread(String fileName) {
		this(fileName, null);
	}

	public AudioPlayThread(String fileName, Options options) {
		mAudioBuffer = new AudioBuffer(fileName);
		setOptions(options);
	}
	
	public void setOptions(Options options) {
		if (options != null) {
			mAudioBuffer.setSoundTouchOptions(options);
		}
	}
	
	@Override
	public synchronized void run() {
		super.run();
		try {
			while (mAudioBuffer.prepareBufferInShort(BUFFER_SIZE_SHORT) > 0) {
				createAudioTrackIfNeed();
				handleAudioCtrl();
				short[] buffer = null;

				do {
					buffer = mAudioBuffer.readSamples();
					writeBuffer(buffer, buffer.length);
				} while (buffer != null && buffer.length > 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Log.i(TAG, "finally call release");
			audioRelease();
		}
	}
	
	/**
	 * 创建AudioTrack
	 */
	private void createAudioTrackIfNeed() {
		if (mAudioTrack == null) {
			Mp3Info mInfo = mAudioBuffer.getMp3Info();
			int bufferSizeInBytes = AudioTrack.getMinBufferSize(mInfo.sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
			mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mInfo.sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes*4, AudioTrack.MODE_STREAM);
			mAudioTrack.play();
		}
	}
	
	/**
	 * 往AudioTrack写入PCM数据
	 * @param buffer
	 * @param size
	 */
	private void writeBuffer(short [] buffer, int size) {
		if (mAudioTrack != null && size > 0) {
			mAudioTrack.write(buffer, 0, size);
		}
	}
	
	private void handleAudioCtrl() {
		if (mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_STOPPED) {
			throw new RuntimeException("stop audio player");
		} else if (mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_PAUSED) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void audioPlay() {
		if (isInitialized()) {
			mAudioTrack.play();
			this.notifyAll();
		}
	}
	
	public void audioPause() {
		if (isInitialized()) {
			mAudioTrack.pause();
		}
	}
	
	public void audioStop() {
		if (isInitialized()) {
			mAudioTrack.stop();
		}
	}
	
	public void audioRelease() {
		mAudioBuffer.release();
		if (mAudioTrack != null) {
			mAudioTrack.release();
		}
	}
	
	public boolean isPlaying() {
		if (mAudioTrack != null) {
			int state = mAudioTrack.getPlayState();
			return AudioTrack.PLAYSTATE_PLAYING == state;
		}
		return false;
	}
	
	public boolean isStopped() {
		if (mAudioTrack != null) {
			int state = mAudioTrack.getPlayState();
			return AudioTrack.PLAYSTATE_STOPPED == state;
		}
		return true;
	}
	
	public boolean isPaused() {
		if (mAudioTrack != null) {
			int state = mAudioTrack.getPlayState();
			return AudioTrack.PLAYSTATE_PAUSED == state;
		}
		return false;
	}

	public boolean isInitialized() {
		return mAudioTrack != null && (mAudioTrack.getState() == AudioTrack.STATE_INITIALIZED);
	}
	
	public int getPlayState() {
		if (mAudioTrack != null) {
			return mAudioTrack.getPlayState();
		}
		return 0;
	}
}
