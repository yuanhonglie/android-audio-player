package com.yhl.audioplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.yhl.data.Song;
import com.yhl.task.AuidoPlayThread;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	private String mp3FilePath = Environment.getExternalStorageDirectory().getPath() + File.separator +"hotel.mp3";
	private SongAdapter mAdapter;
	AuidoPlayThread mAudioPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		List<Song> songs = scanSongs();
		ListView listView = (ListView)findViewById(R.id.lv_songs);
		mAdapter = new SongAdapter(this, songs);
		listView.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
	}
	
	
	public List<Song> scanSongs() {
		List<Song> songs = new ArrayList<Song>();
		
		// 查询媒体数据库
		Cursor cursor = getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		
		// 遍历媒体数据库
		if (cursor.moveToFirst()) {
			do {
				// 歌曲编号
//				int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
				
				// 歌曲id
//				int trackId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
				
				// 歌曲标题
				String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				// 歌曲的专辑名：MediaStore.Audio.Media.ALBUM
				String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
				// 歌曲的歌手名： MediaStore.Audio.Media.ARTIST
				String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
				// 歌曲文件的路径 ：MediaStore.Audio.Media.DATA
				String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				// 歌曲的minetype：MediaStore.Audio.Media.MIME_TYPE
				String mime = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
				// 歌曲的总播放时长：MediaStore.Audio.Media.DURATION
				int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
				// 歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
				long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
				Log.i(TAG, "mimeType = " + mime);
				// 歌曲文件显示名字
				String disName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
				songs.add(new Song(title, album, artist, url, disName, mime, size, duration));
				Log.i(TAG, disName);// 打印出歌曲名字
			} while (cursor.moveToNext());
		}
		cursor.close();
		
		return songs;
	}

	public class SongAdapter extends BaseAdapter {

		private List<Song> mSongs;
		private Context mContext;
		public SongAdapter(Context context, List<Song> songs) {
			mSongs = songs;
			mContext = context;
		}
		
		
		@Override
		public int getCount() {
			return mSongs.size();
		}

		@Override
		public Object getItem(int position) {
			return mSongs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mHolder;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.song_item_layout_ll, null);
				mHolder = new ViewHolder();
				mHolder.title = (TextView)convertView.findViewById(R.id.tv_displayName);
				mHolder.artist = (TextView)convertView.findViewById(R.id.tv_artist);
				mHolder.btnPlay = (Button)convertView.findViewById(R.id.btn_play);
				convertView.setTag(mHolder);
			} else {
				mHolder = (ViewHolder)convertView.getTag();
			}
			
			Song song = mSongs.get(position);
			mHolder.title.setText(song.getTitle());
			mHolder.artist.setText(song.getArtist());
			mHolder.btnPlay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Song song = (Song)v.getTag();
					if (mAudioPlayer == null) {
						Log.i(TAG, "song = " + song);
						playSong(song);
						((Button)v).setText(R.string.pause);
					} else if (mAudioPlayer.isPaused()) {
						Log.i(TAG, "handle audio play");
						mAudioPlayer.audioPlay();
						((Button)v).setText(R.string.pause);
					} else if (mAudioPlayer.isPlaying()) {
						Log.i(TAG, "handle audio pause");
						mAudioPlayer.audioPause();
						((Button)v).setText(R.string.play);
					}
				}
			});
			mHolder.btnPlay.setTag(song);
			
			return convertView;
		}
		
		class ViewHolder {
			TextView title;
			TextView artist;
			Button btnPlay;
		}
		
	}
	
	
	private void playSong(Song song) {
		mAudioPlayer = new AuidoPlayThread(song.getUrl());
		mAudioPlayer.start();
	}
}
