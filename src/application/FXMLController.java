package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class FXMLController implements Initializable{


	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String query1 = "Select * from standings";
		fxDataPane.setVisible(false);
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<ArrayList<String>> Scrape = new ArrayList<ArrayList<String>>();
			Document doc;
			try {
				doc = Jsoup.connect("http://espn.go.com/mens-college-basketball/conferences/standings/_/id/2/year/2012/acc-conference").get();


		    for (Element table : doc.select("table.tablehead")) {
		        for (Element row : table.select("tr")) {
		            Elements tds = row.select("td");
		            ArrayList<String> temp = new ArrayList<String>();;
		            if (tds.size() > 6) {
		            	for (int i = 0; i < 6;i++) {
			            	temp.add(tds.get(i).text());

		            	}
		                Scrape.add(temp);
		            }
		        }
		    }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection("jdbc:mysql://www.db4free.net:3306/finalproject","morealin","g?c%usFt");
			System.out.println("Connected");
			Statement data = conn.createStatement();
			ResultSet reset;
			reset = data.executeQuery(query1);
			List<Team> initialList = new ArrayList<Team>();
			while (reset.next()) {
				int TeamID = reset.getInt("TeamID");
				String TeamName = reset.getString("TeamName");
				String WinLoss = reset.getString("WinLoss");
				int GamesBehind = reset.getInt("GamesBehind");
				String WinPercent = reset.getString("WinPercent");
				
				Team TeamInstance = new Team(TeamID,TeamName,WinLoss,GamesBehind,WinPercent);
				
				initialList.add(TeamInstance);
			}
			ObservableList<Team> observableTeam = FXCollections.observableArrayList(initialList);
			PropertyValueFactory<Team,String> TeamID = new PropertyValueFactory<Team,String>("TeamID");
			PropertyValueFactory<Team,String> TeamName = new PropertyValueFactory<Team,String>("TeamName");
			PropertyValueFactory<Team,String> WinLoss = new PropertyValueFactory<Team,String>("WinLoss");
			PropertyValueFactory<Team,String> GamesBehind = new PropertyValueFactory<Team,String>("GamesBehind");
			PropertyValueFactory<Team,String> WinPercentage = new PropertyValueFactory<Team,String>("WinPercent");

			fxTeamID.setCellValueFactory(TeamID);
			fxTeamName.setCellValueFactory(TeamName);
			fxWinLoss.setCellValueFactory(WinLoss);
			fxGamesBehind.setCellValueFactory(GamesBehind);
			fxWinPercent.setCellValueFactory(WinPercentage);
			
			fxDataTable.setItems(observableTeam);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	Pane fxPaneBegin;
	
	@FXML
	Pane fxDataPane;
	
    @FXML
    private TableView<Team> fxDataTable;

    @FXML
    private TableColumn<Team, String> fxTeamID;

    @FXML
    private TableColumn<Team, String> fxTeamName;

    @FXML
    private TableColumn<Team, String> fxWinLoss;

    @FXML
    private TableColumn<Team, String> fxGamesBehind;

    @FXML
    private TableColumn<Team, String> fxWinPercent;
	
	@FXML
	private void begin_Click(ActionEvent e) {
		fxPaneBegin.setVisible(false);
		fxDataPane.setVisible(true);
	}
	public void Back_Click(ActionEvent e) {
		fxDataPane.setVisible(false);
		fxPaneBegin.setVisible(true);
	}
}
