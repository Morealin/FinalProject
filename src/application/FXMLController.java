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
import javafx.scene.control.TextField;


public class FXMLController implements Initializable{

	public List<Team> initial = new ArrayList<Team>();


	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String query1 = "Select * from standings";
		fxDataPane.setVisible(false);
		fxPaneStanding.setVisible(false);
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Document doc;
			try {
				doc = Jsoup.connect("http://espn.go.com/mens-college-basketball/conferences/standings/_/id/2/year/2012/acc-conference").get();

        		int i = 1;
		    for (Element table : doc.select("table.tablehead")) {
		        for (Element row : table.select("tr")) {
		            Elements tds = row.select("td");
		            if (tds.size() > 6) {
		            	if (tds.get(0).text() != "ACC") {
		            	int TeamID = i;
		            	i++;
        				String TeamName = tds.get(0).text();
        				String WinLoss = tds.get(1).text();
        				try {
        					int GamesBehind = Integer.parseInt(tds.get(2).text());
	        				String WinPercent = tds.get(3).text();
	        				
	        				Team TeamInstance = new Team(TeamID,TeamName,WinLoss,GamesBehind,WinPercent);
	        				
	        				initial.add(TeamInstance);
        				} catch (Exception e) {
        					int GamesBehind = 0;
	        				String WinPercent = tds.get(3).text();
	        				
	        				Team TeamInstance = new Team(TeamID,TeamName,WinLoss,GamesBehind,WinPercent);
	        				
	        				initial.add(TeamInstance);
        				}
		            	}
		            }
		        }
		    }
			ObservableList<Team> observableScrape = FXCollections.observableArrayList(initial);
		    PropertyValueFactory<Team,String> TeamID = new PropertyValueFactory<Team,String>("TeamID");
			PropertyValueFactory<Team,String> TeamName = new PropertyValueFactory<Team,String>("TeamName");
			PropertyValueFactory<Team,String> WinLoss = new PropertyValueFactory<Team,String>("WinLoss");
			PropertyValueFactory<Team,String> GamesBehind = new PropertyValueFactory<Team,String>("GamesBehind");
			PropertyValueFactory<Team,String> WinPercentage = new PropertyValueFactory<Team,String>("WinPercent");

			fxTeamID1.setCellValueFactory(TeamID);
			fxTeamName1.setCellValueFactory(TeamName);
			fxWinLoss1.setCellValueFactory(WinLoss);
			fxGamesBehind1.setCellValueFactory(GamesBehind);
			fxWinPercent1.setCellValueFactory(WinPercentage);
			
			fxDataTable1.setItems(observableScrape);
		    
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
	Pane fxPaneStanding;
	
    @FXML
    private TableView<Team> fxDataTable;
    
    @FXML
    private TableView<Team> fxDataTable1;

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
    private TableColumn<Team, String> fxTeamID1;

    @FXML
    private TableColumn<Team, String> fxTeamName1;

    @FXML
    private TableColumn<Team, String> fxWinLoss1;

    @FXML
    private TableColumn<Team, String> fxGamesBehind1;

    @FXML
    private TableColumn<Team, String> fxWinPercent1;
    
    @FXML
    private TextField fxIdTextBox1;
    
    @FXML
    private TextField fxIdTextBox;
	
	@FXML
	private void begin_Click(ActionEvent e) {
		fxPaneBegin.setVisible(false);
		fxDataPane.setVisible(true);
	}
	public void Back_Click(ActionEvent e) {
		fxDataPane.setVisible(false);
		fxPaneStanding.setVisible(false);
		fxPaneBegin.setVisible(true);
		fxDataTable.refresh();
	}
	@FXML
	public void Standing_Click(ActionEvent e) {
		fxPaneBegin.setVisible(false);
		fxPaneStanding.setVisible(true);
	}
	public void Follow_Click(ActionEvent e) {
		  Connection connection = null;
		try {
			int TeamID = initial.get(Integer.parseInt(fxIdTextBox.getText())-1).getTeamID();
			String TeamName = initial.get(Integer.parseInt(fxIdTextBox.getText())-1).getTeamName();
			String WinLoss = initial.get(Integer.parseInt(fxIdTextBox.getText())-1).getWinLoss();
			int GamesBehind = initial.get(Integer.parseInt(fxIdTextBox.getText())-1).getGamesBehind();
			String WinPercent = initial.get(Integer.parseInt(fxIdTextBox.getText())-1).getWinPercent();
			
			connection = DriverManager.getConnection("jdbc:mysql://www.db4free.net:3306/finalproject","morealin","g?c%usFt");
	          Statement statement = connection.createStatement();
	          String insert = "INSERT INTO finalproject.standings (TeamId,TeamName,WinLoss,GamesBehind,WinPercent) VALUES ('" + TeamID + "','" + TeamName + "','" + WinLoss + "','" + GamesBehind + "','" + WinPercent + "');";
	          statement.executeUpdate(insert);
	          System.out.println("Inserted");
//	          String selectInitial = "SELECT * FROM finalproject.standings";
//              statement.executeQuery(selectInitial);
	          fxDataTable.refresh();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
            if (connection != null) try { connection.close(); } catch(Exception e1) {}
    }
	}
	public void Delete_Click(ActionEvent e) {
		  Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://www.db4free.net:3306/finalproject","morealin","g?c%usFt");
	          String deleteExcess = "DELETE FROM standings WHERE TeamID = " + fxIdTextBox1.getText() + ";";
	          Statement statement = connection.createStatement();
	          statement.executeUpdate(deleteExcess);
	          System.out.println("Deleted");
//	          String selectInitial = "SELECT * FROM finalproject.standings";
//              statement.executeQuery(selectInitial);
	          fxDataTable.refresh();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
            if (connection != null) try { connection.close(); } catch(Exception e1) {}
    }
	}
}
