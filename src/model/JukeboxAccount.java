// Zyad Elmaghraby 

package model;

import java.io.Serializable;
import java.time.LocalDate;

public class JukeboxAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	private LocalDate today;
	private LocalDate lastSongAdded;
	private int songsPlayedToday;

	public JukeboxAccount(String username, String password) {
		this.username = username;
		this.password = password;
		songsPlayedToday = 0;
		today = LocalDate.now();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSongsPlayedToday() {
		return songsPlayedToday;
	}

	public boolean canPlayAnotherSong() {
		today = LocalDate.now(); // update the date for the check

		if (songsPlayedToday < 3) {
			return true;
		} else {
			if (today.getYear() > lastSongAdded.getYear() || today.getDayOfYear() > lastSongAdded.getDayOfYear()) {
				songsPlayedToday = 0; // then allow 3 more songs to be played
				return true;
			} else
				return false;
		}
	}

	public void increaseSongCount() {
		songsPlayedToday++;
		lastSongAdded = LocalDate.now();
	}

	public void resetSongCount() {
		songsPlayedToday = 0;
	}
}
