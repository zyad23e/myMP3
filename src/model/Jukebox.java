// Zyad Elmaghraby 
package model;

public class Jukebox {

	private JukeboxAccount loggedInUser;
	private PlayList playlist;

	public Jukebox() {
		playlist = new PlayList();
	}

	public JukeboxAccount getLoggedInUser() {
		return loggedInUser;
	}

	public void login(JukeboxAccount account) {
		loggedInUser = account;
	}

	public void logout() {
		loggedInUser = null;
	}

	public String playingNow() {
		return playlist.playingNow();
	}

	public String playingNext() {
		return playlist.playingNext();
	}

	public void playNextSong() {
		playlist.playNextSong();
	}

	public boolean addToPlaylist(Song song) {
		if (loggedInUser != null && loggedInUser.canPlayAnotherSong()) {
			playlist.addSong(song);
			loggedInUser.increaseSongCount();
			return true;
		}
		return false;
	}

	public void readPlaylist() {
		playlist.readPlaylist();
	}

	public void writePlaylist() {
		playlist.writePlaylist();
	}
}
