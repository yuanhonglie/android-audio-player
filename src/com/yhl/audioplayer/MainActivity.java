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
//	private String mp3FilePath = Environment.getExternalStorageDirectory().getPath() + File.separator +"inner.mp3";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.btn_start).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
//        // �����ļ�һ����buffer�Ĵ�С  
//        int mAudioMinBufSize = AudioTrack.getMinBufferSize(samplerate,  
//                AudioFormat.CHANNEL_CONFIGURATION_STEREO,  
//                AudioFormat.ENCODING_PCM_16BIT);  
//  
//        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, // ָ������������  
//                // STREAM_ALARM��������  
//                // STREAM_MUSCI��������������music��  
//                // STREAM_RING������  
//                // STREAM_SYSTEM��ϵͳ����  
//                // STREAM_VOCIE_CALL���绰����  
//                  
//                samplerate,// ������Ƶ���ݵĲ�����  
//                AudioFormat.CHANNEL_CONFIGURATION_STEREO,// �����������Ϊ˫����������  
//                AudioFormat.ENCODING_PCM_16BIT,// ������Ƶ���ݿ���8λ����16λ  
//                mAudioMinBufSize, AudioTrack.MODE_STREAM);// ����ģʽ���ͣ�����������Ϊ������  
//        // AudioTrack����MODE_STATIC��MODE_STREAM���ַ��ࡣ  
//        // STREAM��ʽ��ʾ���û�ͨ��write��ʽ������һ��һ�ε�д��audiotrack�С�  
//        // ���ַ�ʽ��ȱ�����JAVA���Native�㲻�ϵؽ������ݣ�Ч����ʧ�ϴ�  
//        // ��STATIC��ʽ��ʾ��һ��ʼ������ʱ�򣬾Ͱ���Ƶ���ݷŵ�һ���̶���buffer��Ȼ��ֱ�Ӵ���audiotrack��  
//        // �����Ͳ���һ�δε�write�ˡ�AudioTrack���Լ��������buffer�е����ݡ�  
//        // ���ַ������������������С���ļ��ȽϺ��ʡ�  
//    } 
}
