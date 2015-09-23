package com.yhl.task;


public interface IAudioListener{
	void onStart(AudioPlayer ap);
	void onPlay(AudioPlayer ap);
	void onPause(AudioPlayer ap);
    void onCompletion(AudioPlayer ap);
    void onPreProcess(AudioPlayer ap, short[] buffer);
}
