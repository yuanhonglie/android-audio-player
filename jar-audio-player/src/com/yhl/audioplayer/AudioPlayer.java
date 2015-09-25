package com.yhl.audioplayer;

import android.os.Handler;
import android.util.Log;

public class AudioPlayer implements IAudioPlayer, IAudioListener{
	private static final String TAG = "AudioPlayer";
	private AudioPlayThread player;
	private Options mOptions;
	private IAudioListener mListener;
	private Object mTag;
	private Handler mHandler;
	
	public AudioPlayer() {
		mOptions = new Options();
		try {
			mHandler = new Handler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AudioPlayer(Options options) {
		mOptions = options;
		try {
			mHandler = new Handler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setData(String path) {
		if (player != null) {
			if (player.isAlive()) {
				player.audioStop();
			} else {
				player.audioRelease();
			}
		}
		player = new AudioPlayThread(path, mOptions);
		player.setAudioListener(this);
	}

	@Override
	public void setOptions(Options options) {
		mOptions = options;
		if (player != null) {
			player.setOptions(options);
		}
	}
	
	@Override
	public void setAudioListener(IAudioListener listener) {
		mListener = listener;
	}
	
	@Override
	public void setTag(Object obj) {
		mTag = obj;
	}

	@Override
	public Object getTag() {
		return mTag;
	}
	
	@Override
	public Options getOptions() {
		return mOptions;
	}
	
	@Override
	public void play() {
		if (player != null && !player.isAlive()) {
			player.start();
		} else if (player != null && player.isPaused()) {
			player.audioPlay();
		} else {
			if (player == null) {
				throw new RuntimeException("audio data was not set, setData must be called first !!");
			}
		}
	}

	@Override
	public void pause() {
		if (player != null && player.isPlaying()) {
			player.audioPause();
		}
	}

	@Override
	public void stop() {
		if (player != null) {
			if (player.isAlive()) {
				player.audioStop();
			} else {
				player.audioRelease();
			}
		}
	}

	@Override
	public void release() {
		stop();
	}

	@Override
	public int getPlayState() {
		if (player != null && player.isAlive()) {
			return player.getPlayState();
		}
		return 0;
	}

	@Override
	public boolean isPlaying() {
		if (player != null && player.isAlive()) {
			return player.isPlaying();
		}
		return false;
	}

	@Override
	public boolean isPaused() {
		if (player != null && player.isAlive()) {
			return player.isPaused();
		}
		return false;
	}
	
	//============================================================
	public static class Options {
		public float newTempo = 0;
		public int pitch = 0;
		public float newRate = 0;
		
		public Options() {}
		
		public Options(float newTempo, int pitch, float newRate) {
			this.newTempo = newTempo;
			this.pitch = pitch;
			this.newRate = newRate;
		}
		
		@Override
		public String toString() {
			return newTempo + "|" + pitch + "|" + newRate;
		}
	}
	
	public static class Mp3Info{
		public int sampleRate;
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("sampleRate = ").append(sampleRate).append("|");
			return sb.toString();
		}
	}

	@Override
	public void onPlay(AudioPlayer ap) {
		Log.i(TAG, "onPlay-->");
		Runnable r = new Runnable() {
			@Override
			public void run() {
				if (mListener != null) {
					mListener.onPlay(AudioPlayer.this);
				}
			}
		};
		if (mHandler == null) {
			r.run();
		} else {
			mHandler.post(r);
		}
	}

	@Override
	public void onPause(AudioPlayer ap) {
		Log.i(TAG, "onPause-->");
		Runnable r = new Runnable() {
			@Override
			public void run() {
				if (mListener != null) {
					mListener.onPause(AudioPlayer.this);
				}
			}
		};
		if (mHandler == null) {
			r.run();
		} else {
			mHandler.post(r);
		}
	}

	@Override
	public void onCompletion(AudioPlayer ap) {
		Log.i(TAG, "onCompletion-->");
		Runnable r = new Runnable() {
			@Override
			public void run() {
				if (mListener != null) {
					mListener.onCompletion(AudioPlayer.this);
				}
				mTag = null;
			}
		};
		if (mHandler == null) {
			r.run();
		} else {
			mHandler.post(r);
		}
	}
}
