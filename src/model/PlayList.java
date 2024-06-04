// Zyad Elmaghraby 

package model;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlayList implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Song> songs;
	private MediaPlayer mediaPlayer;
	private boolean playing;

	public PlayList() {
		songs = new ArrayList<>();
		playing = false;
	}

	public void addSong(Song songToAdd) {
		songs.add(songToAdd);
		playNextSong();
	}

	public String playingNow() {
		if (songs.isEmpty())
			return "";
		return songs.get(0).getTitle();
	}

	public String playingNext() {
		if (songs.size() < 1)
			return "";
		return songs.get(1).getTitle();
	}

	public void playNextSong() {
		if (songs.size() > 0 && !playing) {
			Song nextSong = songs.get(0);
			String path = nextSong.getFilename();

			// Need a File and URI object so the path works on all OSs
			File file = new File(path);
			URI uri = file.toURI();

			// Play one mp3 and and have code run when the song ends
			Media media = new Media(uri.toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			playing = true;
			mediaPlayer.setOnEndOfMedia(new Waiter());
		}
	}

	private class Waiter implements Runnable {
		@Override
		public void run() {
			try {
				mediaPlayer.stop();
				playing = false;
				// wait two seconds
				Thread.sleep(2000);
				songs.remove(0);
				playNextSong();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void writePlaylist() {
		try {
			FileOutputStream bytesToDisk = new FileOutputStream("playlist.ser");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(songs);
			outFile.close();
		} catch (IOException ioe) {
			System.out.println("Writing objects failed");
		}
	}

	public void readPlaylist() {
		FileInputStream rawBytes;
		try {
			rawBytes = new FileInputStream("playlist.ser");
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			@SuppressWarnings("unchecked")
			ArrayList<Song> savedPlaylist = (ArrayList<Song>) inFile.readObject();
			inFile.close();
			songs = (ArrayList<Song>) savedPlaylist;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) { // if playlist.ser is empty
			writePlaylist(); // update playlist.ser to have empty playlist
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
