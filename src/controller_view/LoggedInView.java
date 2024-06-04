// Zyad Elmaghraby 

package controller_view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.AccountCollection;
import model.Jukebox;

public class LoggedInView extends GridPane {

	private AccountCollection accounts;
	private Jukebox jukebox;
	private SongSelector selector;

	private Button logOutButton;
	private BorderPane window;

	public LoggedInView(BorderPane window, AccountCollection accounts, Jukebox jukebox) {
		this.accounts = accounts;
		this.jukebox = jukebox;
		this.window = window;
		this.selector = new SongSelector(jukebox);
		createView();
	}

	private void createView() {
		logOutButton = new Button("Log out");
		logOutButton.setOnAction((event) -> {
			jukebox.logout();
			window.setCenter(new LoginCreateAccountPane(window, accounts, jukebox)); // go back to login screen
		});

		this.setPadding(new Insets(10, 10, 10, 10));
		this.setVgap(10);
		this.setHgap(10);
		GridPane.setHgrow(selector, Priority.ALWAYS);

		this.add(selector, 1, 0);
		this.add(logOutButton, 1, 12);
	}
}
