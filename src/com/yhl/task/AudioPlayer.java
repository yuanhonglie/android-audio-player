package com.yhl.task;

public class AudioPlayer {

	
	
	
	public static class Options {
		float newTempo = 0;
		int pitch = 0;
		float newRate = 0;
		
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
		public int mode;
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("sampleRate = ").append(sampleRate).append("|");
			sb.append("mode = ").append(mode);
			return sb.toString();
		}
	}
}
