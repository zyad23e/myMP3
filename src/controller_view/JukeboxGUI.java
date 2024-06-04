// Zyad Elmaghraby 

package controller_view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.AccountCollection;
import model.Jukebox;

public class JukeboxGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	Jukebox jukebox;
	AccountCollection accounts;
	BorderPane songs;
	LoginCreateAccountPane loginPane;
	BorderPane everything;

	@Override
	public void start(Stage primaryStage) throws Exception {
		jukebox = new Jukebox(); // starts with new PlayList()
		accounts = readAccounts();
		startPrompt();
		LayoutGUI();

		Scene scene = new Scene(everything, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				exitPrompt();
			}
		});
	}

	private void startPrompt() {
		Alert prompt = new Alert(AlertType.CONFIRMATION);
		prompt.setHeaderText("Read saved data?");
		Optional<ButtonType> response = prompt.showAndWait();

		if (response.get() == ButtonType.OK)
			jukebox.readPlaylist(); // use saved playlist
		if (response.get() == ButtonType.CANCEL)
			accounts.resetSongCounts(); // otherwise keep new PlayList() and reset songCounts
	}

	private void exitPrompt() {
		Alert prompt = new Alert(AlertType.CONFIRMATION);
		prompt.setHeaderText("Save data?");
		Optional<ButtonType> response = prompt.showAndWait();

		if (response.get() == ButtonType.OK) {
			writeAccounts();
			jukebox.writePlaylist(); // save playlist and accounts' songsPlayedToday
		}
	}

	private void LayoutGUI() {
		everything = new BorderPane();
		loginPane = new LoginCreateAccountPane(everything, accounts, jukebox);
		songs = new SongSelector(jukebox);
		everything.setCenter(loginPane);
	}

	public AccountCollection readAccounts() {
		FileInputStream rawBytes;
		try {
			rawBytes = new FileInputStream("accounts.ser");
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			AccountCollection list = (AccountCollection) inFile.readObject();
			inFile.close();
			return (AccountCollection) list;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new AccountCollection();
	}

	public void writeAccounts() {
		try {
			FileOutputStream bytesToDisk = new FileOutputStream("accounts.ser");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(accounts);
			outFile.close();
		} catch (IOException ioe) {
			System.out.println("Writing objects failed");
		}
	}
}
