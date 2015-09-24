package com.yhl.task;

public class AudioPlayer implements IAudioPlayer{

	private AudioPlayThread player;
	private Options mOptions;
	
	public AudioPlayer() {
		mOptions = new Options();
	}
	
	public AudioPlayer(Options options) {
		mOptions = options;
	}
	
	@Override
	public void setData(String path) {
		if (player != null) {
			//TODO do recycle
			if (player.isAlive()) {
				player.audioStop();
			} else {
				player.audioRelease();
			}
		}
		player = new AudioPlayThread(path, mOptions);
	}

	@Override
	public void setOptions(Options options) {
		mOptions = options;
		if (player != null) {
			player.setOptions(options);
		}
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
		// TODO Auto-generated method stub
		if (player != null && player.isAlive()) {
			return player.getPlayState();
		}
		return 0;
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		if (player != null && player.isAlive()) {
			return player.isPlaying();
		}
		return false;
	}

	@Override
	public boolean isPaused() {
		// TODO Auto-generated method stub
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
}
