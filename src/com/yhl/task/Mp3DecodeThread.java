package com.yhl.task;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import com.yhl.jni.Libmad;
import com.yhl.jni.Libmad.Mp3Info;

public class Mp3DecodeThread extends Thread {
	
	private static final String TAG = Mp3DecodeThread.class.getSimpleName();
	private int mHandle = -1;
	private static final int BUFFER_SIZE_SHORT = 4096;
	private short [] mBuffer = new short[BUFFER_SIZE_SHORT];
	private boolean inited = false;
	private Mp3Info mInfo = new Mp3Info();
	
	private AudioTrack mAudioTrack;
	
	public Mp3DecodeThread(String fileName) {
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
				inited = true;
				int format = Libmad.channelMode2AudioFormat(mInfo.mode);
				int encoding = AudioFormat.ENCODING_PCM_16BIT;
				int bufferSizeInBytes = AudioTrack.getMinBufferSize(mInfo.sampleRate, format, encoding);
				mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mInfo.sampleRate,
						format, encoding, bufferSizeInBytes*4, AudioTrack.MODE_STREAM);
				mAudioTrack.play();
			}
			
			writeBuffer(mBuffer, size);
			
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
	
	public void audioRelease() {
		if (mAudioTrack != null) {
			mAudioTrack.release();
		}
	}

	private void writeBuffer(short [] buffer, int size) {
		if (mAudioTrack != null) {
			mAudioTrack.write(mBuffer, 0, size);
		}
	}
	
}
