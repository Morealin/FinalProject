package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Team {
	private final SimpleIntegerProperty TeamID;
	private final SimpleStringProperty TeamName;
	private final SimpleStringProperty WinLoss;
	private final SimpleIntegerProperty GamesBehind;
	private final SimpleStringProperty WinPercent;
	
	public Team(int TeamID, String TeamName, String WinLoss, int GamesBehind, String WinPercent) {
		this.TeamID = new SimpleIntegerProperty(TeamID);
		this.TeamName = new SimpleStringProperty(TeamName);
		this.WinLoss = new SimpleStringProperty(WinLoss);
		this.GamesBehind = new SimpleIntegerProperty(GamesBehind);
		this.WinPercent = new SimpleStringProperty(WinPercent);
	}

	public int getTeamID() {
		return TeamID.get();
	}

	public String getTeamName() {
		return TeamName.get();
	}

	public String getWinLoss() {
		return WinLoss.get();
	}

	public int getGamesBehind() {
		return GamesBehind.get();
	}

	public String getWinPercent() {
		return WinPercent.get();
	}
}
