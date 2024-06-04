// Zyad Elmaghraby
package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Jukebox;
import model.JukeboxAccount;
import model.Song;

class JukeboxTest {

	Jukebox jukebox = new Jukebox();
	Song testSong = new Song("Capture", "Game Freak", "5", "songfiles/Capture.mp3");
	JukeboxAccount river = new JukeboxAccount("River", "333");
	JukeboxAccount chris = new JukeboxAccount("Chris", "1");
	JukeboxAccount devon = new JukeboxAccount("Devon", "22");
	JukeboxAccount ryan = new JukeboxAccount("Ryan", "4444");

	@Test
	void testGetters() {
		assertEquals(jukebox.getLoggedInUser(), null);

		JukeboxAccount aJBA = new JukeboxAccount("id", "pass");
		assertTrue(aJBA.getUsername().equals("id"));
		assertTrue(aJBA.getPassword().equals("pass"));
		assertTrue(aJBA.getSongsPlayedToday() == 0);
		assertTrue(aJBA.canPlayAnotherSong());

		JukeboxAccount aJBA2 = new JukeboxAccount("12345", "67890");
		assertTrue(aJBA2.getUsername().equals("12345"));
		assertTrue(aJBA2.getPassword().equals("67890"));
		assertTrue(aJBA2.getSongsPlayedToday() == 0);
		assertTrue(aJBA2.canPlayAnotherSong());
	}

	@Test
	void testSongsPlayed() {
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in
		jukebox.login(river);
		assertEquals(jukebox.getLoggedInUser(), river);

		assertTrue(river.canPlayAnotherSong());
		assertTrue(river.getSongsPlayedToday() == 0);
		jukebox.logout();
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in
	}

	@Test
	void testThreeSongsPlayedInARow() {
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in
		jukebox.login(chris);
		assertEquals(jukebox.getLoggedInUser(), chris);

		assertTrue(chris.canPlayAnotherSong());
		chris.increaseSongCount();
		chris.increaseSongCount();
		chris.increaseSongCount();
		assertFalse(chris.canPlayAnotherSong()); // cannot add a fourth song before the next day
		jukebox.logout();
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in

		jukebox.login(chris);
		assertFalse(chris.canPlayAnotherSong()); // logging out and logging back in doesn't affect song limit
		assertTrue(chris.getSongsPlayedToday() == 3);
		jukebox.logout();
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in
	}

	@Test
	void testPlayingTwoSongsThenLoggingOut() {
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in
		jukebox.login(devon);
		assertEquals(jukebox.getLoggedInUser(), devon);

		devon.increaseSongCount();
		devon.increaseSongCount();
		assertTrue(devon.getSongsPlayedToday() == 2);
		jukebox.logout();
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in

		jukebox.login(devon);
		assertTrue(devon.canPlayAnotherSong()); // devon can still play another song
		assertTrue(devon.getSongsPlayedToday() == 2);
		devon.increaseSongCount();
		assertFalse(devon.canPlayAnotherSong()); // now has reached limit of 3 songs
		jukebox.logout();
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in
	}

	@Test
	void testSongsPlayedWithoutLoggingIn() {
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in
		assertEquals(jukebox.getLoggedInUser(), null);

		assertTrue(ryan.getSongsPlayedToday() == 0); // logging in doesnt affect songsPlayedToday
		assertTrue(ryan.canPlayAnotherSong());
		jukebox.login(ryan);
		assertEquals(jukebox.getLoggedInUser(), ryan);
		assertTrue(ryan.getSongsPlayedToday() == 0); // logging in doesnt affect songsPlayedToday
		assertTrue(ryan.canPlayAnotherSong());
		jukebox.logout();
		assertFalse(jukebox.addToPlaylist(testSong)); // cannot play a song if no one is logged in
	}
}
