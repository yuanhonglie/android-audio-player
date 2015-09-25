package com.yhl.audioplayer;

import com.yhl.audioplayer.AudioPlayer.Options;

public interface IAudioPlayer {
	void setData(String path);
	void setOptions(Options options);
	void setAudioListener(IAudioListener listener);
	void setTag(Object obj);
	Object getTag();
	Options getOptions();
	void play();
	void pause();
	void stop();
	void release();
	int getPlayState();
	boolean isPlaying();
	boolean isPaused();
}
