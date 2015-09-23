package com.yhl.task;

public interface IAudioPlayer {
	void setData(String path);
	void play();
	void pause();
	void stop();
	void release();
	int getPlayState();
	boolean isPlaying();
	boolean isPaused();
}
