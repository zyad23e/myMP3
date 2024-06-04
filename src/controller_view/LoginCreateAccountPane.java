// Zyad Elmaghraby
package controller_view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.AccountCollection;
import model.Jukebox;
import model.JukeboxAccount;

public class LoginCreateAccountPane extends GridPane {

	private Jukebox jukebox;
	private AccountCollection accounts;

	private Button loginButton;
	private Button createAccountButton;
	private Label promptMessage;
	private Label errorMessage;
	private Label usernameLabel;
	private Label passwordLabel;
	private TextField username;
	private PasswordField password;
	private BorderPane window;

	public LoginCreateAccountPane(BorderPane window, AccountCollection accounts, Jukebox jukebox) {
		this.jukebox = jukebox;
		this.window = window;
		this.accounts = accounts;
		createView();
	}

	private void createView() {
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setVgap(5);
		this.setHgap(5);

		promptMessage = new Label("Login or Create Account");

		usernameLabel = new Label("Username");
		passwordLabel = new Label("Password");

		username = new TextField();
		password = new PasswordField();

		errorMessage = new Label();

		loginButton = new Button("Login");
		loginButton.setOnAction((event) -> {
			String userText = username.getText();
			String passText = password.getText();
			JukeboxAccount account = accounts.canLogIn(userText, passText); // returns account if credentials exist
			if (account == null) {
				errorMessage.setText("Invalid Username and/or Password.");
			} else {
				jukebox.login(account);
				window.setCenter(new LoggedInView(window, accounts, jukebox)); // login as that account, change view
			}
		});

		createAccountButton = new Button("Create Account");
		createAccountButton.setOnAction((event) -> {
			String userText = username.getText();
			String passText = password.getText();
			if (accounts.hasAccount(userText)) {
				errorMessage.setText("Account with this name is already created");
			} else {
				JukeboxAccount newAccount = accounts.addAccount(userText, passText);
				errorMessage.setText("Successful Account Creation");
				jukebox.login(newAccount);
				window.setCenter(new LoggedInView(window, accounts, jukebox)); // login as that account, change view
			}
		});

		this.add(promptMessage, 0, 0);
		this.add(usernameLabel, 0, 1);
		this.add(username, 1, 1);
		this.add(passwordLabel, 0, 2);
		this.add(password, 1, 2);
		this.add(createAccountButton, 0, 3);
		this.add(loginButton, 1, 3);
		this.add(errorMessage, 0, 4, 2, 1);
		jukebox.playNextSong(); // does nothing if playlist is empty
	}

}
