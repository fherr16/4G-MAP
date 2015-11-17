package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class FXMLAccountController {
	
	public ArrayList<Website> sites;
	
	public  ObservableList data ;
	     
	
	private String username;
	private String Master;
	private static PasswordGenerator pass = new PasswordGenerator();
	private static AES encrypt = new AES();

	
	@FXML //fx:id="CellList"
	private TableView showSites;
	
    @FXML // fx:id="logoutButton"
    private Button logoutButton;
    
    @FXML // fx:id="logoutButton"
    private Text titlePage;
    
    @FXML // fx:id="addDataButton"
    private Button addData;
    
    @FXML // fx:id="addDataButton"
    private Button generatePassword;
    
    @FXML
    private TableColumn<Website, String> webName;
    @FXML
    private TableColumn<Website,String> webPass;

    @FXML //loginButton
    private void logoutButtonAction(ActionEvent event) throws IOException{
    	logoutButton.setDisable(true);
        System.out.println("Selected");
        
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("Sample.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();
    }
    
    @FXML //loginButton
    private void passwordGenerateButtonAction(ActionEvent event) throws Exception{
    	String password = null;
    	
		byte[] encryption = null;
		    	
    	password = pass.generate();
    	
    	encryption = encrypt.encrypt(password, Master);
    }
    
     void setUserName(String name){
    	username = name;
    }
     void setPassword(String pass){
    	 Master = pass;
     }
    
     void setUserPage(){
    	titlePage.setText("User: " + username);
    }
     
    void setListView(){
    	System.out.println("VIEW");
    	webName.setCellValueFactory(
                 new PropertyValueFactory<Website, String>("name"));
    	webPass.setCellValueFactory(
                new PropertyValueFactory<Website, String>("pass"));
    	Website temp = new Website("KEVIN","YEAN");
    	sites = new ArrayList();
    	sites.add(temp);
    	data =  FXCollections.observableArrayList(sites);
    	showSites.setItems(data);
    	
    }
    
    public class appendToFile {
    	 
    	   public void append (String fileName, String website, String password) {
    	 
    	      BufferedWriter writer = null;
    	 
    	      try {
    	         writer = new BufferedWriter(new FileWriter(fileName, true));
    	     writer.write(website + "," + password);
    	     writer.newLine();
    	     writer.flush();
    	      } catch (IOException ioe) {
    	     ioe.printStackTrace();
    	      } finally {                       // always close the file
    	     if (writer != null) try {
    	        writer.close();
    	     } catch (IOException ioe2) {

    	     }
    	      } 
    	   } 
    	}   
}