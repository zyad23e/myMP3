// Zyad Elmaghraby 

package model;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountCollection implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<JukeboxAccount> accounts;

	public AccountCollection() {
		accounts = new ArrayList<>();
	}

	public ArrayList<JukeboxAccount> getAccounts() {
		return accounts;
	}

	public JukeboxAccount addAccount(String username, String password) {
		JukeboxAccount newAccount = new JukeboxAccount(username, password);
		accounts.add(newAccount);
		return newAccount;
	}

	public boolean hasAccount(String username) {
		for (JukeboxAccount a : accounts) {
			if (a.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	public JukeboxAccount canLogIn(String username, String password) {
		for (JukeboxAccount a : accounts) {
			if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
				return a;
			}
		}
		return null;
	}

	public void resetSongCounts() {
		for (JukeboxAccount a : accounts) {
			a.resetSongCount();
		}
	}
}
