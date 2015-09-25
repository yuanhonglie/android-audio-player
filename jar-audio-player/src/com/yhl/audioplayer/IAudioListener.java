package com.yhl.audioplayer;


public interface IAudioListener{
	void onPlay(AudioPlayer ap);
	void onPause(AudioPlayer ap);
    void onCompletion(AudioPlayer ap);
}
