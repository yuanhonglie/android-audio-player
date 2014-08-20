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
				int bufferSizeInBytes = AudioTrack.getMinBufferSize(mInfo.sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
				mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mInfo.sampleRate,
						AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes*4, AudioTrack.MODE_STREAM);
				mAudioTrack.play();
			}
			
			writeBuffer(mBuffer, size);
			
			try {
				sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public void audioPlay() {
		Log.i(TAG, "audioPlay 1");
		if (mAudioTrack != null) {
			Log.i(TAG, "audioPlay 2");
			mAudioTrack.play();
		}
		Log.i(TAG, "audioPlay 3");
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
	
	private byte [] shortArray2ByteArray(short [] shorts, int len) {
		
		if (shorts == null || shorts.length == 0) {
			return null;
		}
		
		int size = shorts.length;
		
		int j = size;
		short num = 0;

		len = Math.min(len, size);
		
		byte [] bytes = new byte[len+len];
		
		for (int i = 0; i < len; i++) {
			
			j = i + i;
			num = shorts[i];
			
			bytes[j] = (byte)(num & 0xff);
			bytes[j+1] = (byte)((num >> 8) & 0xff);
		}
		
		return bytes;
	}
}
