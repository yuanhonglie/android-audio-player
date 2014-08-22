package com.yhl.audioplayer;

import java.io.File;

import com.yhl.task.Mp3DecodeThread;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	private String mp3FilePath = Environment.getExternalStorageDirectory().getPath() + File.separator +"hotel.mp3";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.btn_start).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_start) {
			Mp3DecodeThread thread = new Mp3DecodeThread(mp3FilePath);
			thread.start();
		}
	}

//	
//	private void initAudioPlayer() {  
//		int samplerate = Libmad.getAudioSamplerate();  
//        System.out.println("samplerate = " + samplerate);  
//        samplerate = samplerate / 2;  
//        // 声音文件一秒钟buffer的大小  
//        int mAudioMinBufSize = AudioTrack.getMinBufferSize(samplerate,  
//                AudioFormat.CHANNEL_CONFIGURATION_STEREO,  
//                AudioFormat.ENCODING_PCM_16BIT);  
//  
//        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, // 指定在流的类型  
//                // STREAM_ALARM：警告声  
//                // STREAM_MUSCI：音乐声，例如music等  
//                // STREAM_RING：铃声  
//                // STREAM_SYSTEM：系统声音  
//                // STREAM_VOCIE_CALL：电话声音  
//                  
//                samplerate,// 设置音频数据的采样率  
//                AudioFormat.CHANNEL_CONFIGURATION_STEREO,// 设置输出声道为双声道立体声  
//                AudioFormat.ENCODING_PCM_16BIT,// 设置音频数据块是8位还是16位  
//                mAudioMinBufSize, AudioTrack.MODE_STREAM);// 设置模式类型，在这里设置为流类型  
//        // AudioTrack中有MODE_STATIC和MODE_STREAM两种分类。  
//        // STREAM方式表示由用户通过write方式把数据一次一次得写到audiotrack中。  
//        // 这种方式的缺点就是JAVA层和Native层不断地交换数据，效率损失较大。  
//        // 而STATIC方式表示是一开始创建的时候，就把音频数据放到一个固定的buffer，然后直接传给audiotrack，  
//        // 后续就不用一次次得write了。AudioTrack会自己播放这个buffer中的数据。  
//        // 这种方法对于铃声等体积较小的文件比较合适。  
//    } 
}
