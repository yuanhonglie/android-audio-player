package com.yhl.jni;

public class Mp3Decoder {
	private static final String TAG = "Mp3Decoder";
	static{
		System.loadLibrary("decoder");
	}

	/**
	 * 打开MP3文件
	 * @param fileName
	 * @return 返回文件句柄
	 */
	public static native int openFile(String fileName);
	
	/**
	 * 设置语音输出参数
	 * @param handle 文件句柄
	 * @param newTempo
	 * @param pitch
	 * @param newRate
	 */
	public static native void setSoundTouchOptions(int handle, float newTempo, int pitch, float newRate);
	
	/**
	 * 准备PCM数据
	 * @param handle 文件句柄
	 * @param size 数据大小
	 * @return 已准备好的数据大小
	 */
	public static native int prepareBufferInShort(int handle, int size);
	
	/**
	 * 读取已做变换的PCM数据
	 * @param handle 文件句柄
	 * @return PCM数据
	 */
	public static native short[] readSamples(int handle);
	
	/**
	 * 获取采样率
	 * @param handle 文件句柄
	 * @return 采样率
	 */
	public static native int getSampleRate(int handle);
	
	/**
	 * 关闭语音文件
	 * @param handle 文件句柄
	 */
	public static native void closeFile(int handle);
}
