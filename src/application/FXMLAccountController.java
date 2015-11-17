package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.xml.bind.DatatypeConverter;

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
import javafx.scene.control.TextField;
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
	private StringBuffer encryptedPass = new StringBuffer();
	private FileInputStream decryption;


    private static final String COMMA_DELIMITER = ",";

    //Student attributes index
    private static final int WEBNAME_IDX = 0;
    private static final int PASSWORD_IDX = 1;
	
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
    private TextField description;
    
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
    	
    	System.out.println("Password generated: " + password);
    	
    	encryption = encrypt.encrypt(password, Master);
    	
    	String encrypted = DatatypeConverter.printHexBinary(encryption);
    	
        
    	
    	System.out.println("Password encrypted: " + encrypted);
    	System.out.println("Password. decrypted: " + encrypt.decrypt(encryption, Master));
        
        
    	Hash hash = new Hash();
    	
    	String encryptedText = description.getText();
    	String fileName = username+Master;
     	String hFileName = hash.sha256(fileName);
     	String des = description.getText();
    	append(hFileName,des,encrypted);
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
     
    void setListView(ArrayList<Website> sites){
    	System.out.println("VIEW");
    	webName.setCellValueFactory(
                 new PropertyValueFactory<Website, String>("name"));
    	webPass.setCellValueFactory(
                new PropertyValueFactory<Website, String>("pass"));
    	data =  FXCollections.observableArrayList(sites);
    	showSites.setItems(data);
    	
    	
    }
    
    public void append (String fileName, String website, String hexPassword) throws Exception {
    	 
    	      BufferedWriter writer = null;
    	      
    	      System.out.println("Writing");
    	 
    	      try {
    	         writer = new BufferedWriter(new FileWriter(fileName+".csv", true));
    	     writer.write(website + "," + hexPassword);
    	     writer.newLine();
    	     writer.flush();
    	     System.out.println("Finished Writing");
    	     updateList(fileName);
    	      } catch (IOException ioe) {
    	     ioe.printStackTrace();
    	      } finally {                       // always close the file
    	     if (writer != null) try {
    	        writer.close();
    	     } catch (IOException ioe2) {

    	     }
    	      } 
    	   } 
    
    public void updateList(String filename) throws Exception{
     try{
    	BufferedReader fileReader = new BufferedReader(new FileReader(filename+".csv"));
     	
     	String line = "";
     	
     	//Read the CSV file header to skip it
        System.out.println("READ");
        //Read the file line by line starting from the second line
        ArrayList<Website> sites = new ArrayList();
        while ((line = fileReader.readLine()) != null) {
            //Get all tokens available in line
            String[] tokens = line.split(COMMA_DELIMITER);
            if (tokens.length > 0) {
            	String websiteName = ((tokens[WEBNAME_IDX]));
            	String websitePass = ((tokens[PASSWORD_IDX]));
            	
                byte[] encryptedBytes = DatatypeConverter.parseHexBinary(websitePass);
                String original = encrypt.decrypt(encryptedBytes, Master);
            	
            	Website temp = new Website(websiteName,original);
            	sites.add(temp);
            	setListView(sites);
			}
        }
     }catch(IOException e){
    	 
     }
    }
}