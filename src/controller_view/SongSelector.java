// Zyad Elmaghraby
package controller_view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.Jukebox;
import model.Song;

public class SongSelector extends BorderPane {

	private ArrayList<Song> songs;
	private TableView<Song> tableview;
	private Alert errorMessage;

	public SongSelector(Jukebox jukebox) {
		populateSongs();
		ObservableList<Song> observableList = FXCollections.observableArrayList(songs);
		errorMessage = new Alert(AlertType.INFORMATION);

		TableColumn<Song, String> titleCol = new TableColumn<>("Title");
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		TableColumn<Song, String> artistCol = new TableColumn<>("Artist");
		artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
		TableColumn<Song, Integer> timeCol = new TableColumn<>("Time");
		timeCol.setCellValueFactory(new PropertyValueFactory<>("playtime"));

		tableview = new TableView<>();

		tableview.getColumns().add(titleCol);
		tableview.getColumns().add(artistCol);
		tableview.getColumns().add(timeCol);
		tableview.setItems(observableList);

		Button add = new Button("Add Song");
		add.setOnAction((event) -> {
			int index = tableview.getSelectionModel().getSelectedIndex();
			if (index != -1) {
				Song song = songs.get(index);
				if (!jukebox.addToPlaylist(song)) {
					errorMessage.setHeaderText(jukebox.getLoggedInUser().getUsername() + " has reached the limit");
					errorMessage.showAndWait();
				}
			}
		});
		this.setBottom(add);
		this.setCenter(tableview);
	}

	private void populateSongs() {
		songs = new ArrayList<>();
		songs.add(new Song("Pokemon Capture", "Pikachu", "0:05", "songfiles/Capture.mp3"));
		songs.add(new Song("Danse Macabre", "Kevin MacLeod", "0:34", "songfiles/DanseMacabreViolinHook.mp3"));
		songs.add(new Song("Determined Tumbao", "FreePlay Music", "0:20", "songfiles/DeterminedTumbao.mp3"));
		songs.add(new Song("Longing In Their Hearts", "Bonnie Raitt", "5:00", "songfiles/LongingInTheirHearts.mp3"));
		songs.add(new Song("LopingSting", "Kevin MacLeod", "0:05", "songfiles/LopingSting.mp3"));
		songs.add(new Song("Swing Cheese", "FreePlay Music", "0:15", "songfiles/SwingCheese.mp3"));
		songs.add(new Song("The Curtain Rises", "Kevin MacLeod", "0:28", "songfiles/The CurtainRises.mp3"));
		songs.add(new Song("UntameableFire", "Pierre Langer", "4:42", "songfiles/UntameableFire.mp3"));
	}
}
