package com.yhl.task;

import com.yhl.task.AudioPlayer.Options;

public interface IAudioPlayer {
	void setData(String path);
	void setOptions(Options options);
	Options getOptions();
	void play();
	void pause();
	void stop();
	void release();
	int getPlayState();
	boolean isPlaying();
	boolean isPaused();
}
