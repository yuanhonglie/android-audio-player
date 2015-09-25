package com.yhl.audioplayer.demo;

public class Song {

	private String title;
	
	private String album;
	
	private String artist;
	
	private String url;
	
	private String displayName;
	
	private String mimeType;
	
	private long size;
	
	private int duration;
	
	public Song(String title, String album, String artist, String url, String displayName, String mime, long size, int duration) {
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.url = url;
		this.displayName = displayName;
		this.mimeType = mime;
		this.size = size;
		this.duration = duration;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("title = ").append(title).append("|");
		sb.append("album = ").append(album).append("|");
		sb.append("artist = ").append(artist).append("|");
		sb.append("url = ").append(url).append("|");
		sb.append("disName = ").append(displayName).append("|");
		sb.append("size = ").append(size).append("|");
		sb.append("duration = ").append(duration);
		
		return sb.toString();
	}
}
