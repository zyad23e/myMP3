// Zyad Elmaghraby
package model;

import java.io.Serializable;

public class Song implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String artist;
	private String playtime;
	private String filename;

	public Song(String title, String artist, String playtime, String filename) {
		this.title = title;
		this.artist = artist;
		this.playtime = playtime;
		this.filename = filename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getPlaytime() {
		return playtime;
	}

	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
