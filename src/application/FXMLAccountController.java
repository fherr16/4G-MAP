package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
 	int count = 0;


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
    private TextField generatedPassword;
    
    @FXML
    private TableColumn<Website, String> webName;
    @FXML
    private TableColumn<Website,String> webPass;
    @FXML
    private TextField passwordClipBoard;

    @FXML //loginButton
    private void logoutButtonAction(ActionEvent event) throws IOException{
    	logoutButton.setDisable(true);
        
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("Sample.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();
    }
    
    void addListener(){
   	 showSites.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
       	 @Override
       	 public void changed (ObservableValue<?> observable, Object oldvalue, Object newValue) {
       		 int loc = (data.indexOf(newValue));
       		 passwordClipBoard.setText(sites.get(loc).getPass());
       	 }
        });
   }
    
    @FXML //loginButton
    private void passwordGenerateButtonAction(ActionEvent event) throws Exception{
    	String password = null;
  	  	
		byte[] encryption = null;
		byte[] websiteEncryption = null;
		
    	String plainText = description.getText();
		
		if(!validateDescription(plainText)){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Description");
			alert.setHeaderText(null);
			alert.setContentText("Missing Description");
			
			alert.showAndWait();
			return;
		}
		    	
    	password = pass.generate();
    	generatedPassword.setText(password);
    	
    	
    	encryption = encrypt.encrypt(password, Master);
    	websiteEncryption = encrypt.encrypt(plainText, Master);
    	
    	String encrypted = DatatypeConverter.printHexBinary(encryption);
        String websiteEncrypted = DatatypeConverter.printHexBinary(websiteEncryption);
        
        
    	Hash hash = new Hash();
    	
    	String encryptedText = description.getText();
    	String fileName = username;
     	String hFileName = hash.sha256(fileName);
     	String des = description.getText();
    	append(hFileName,websiteEncrypted,encrypted);
    }
    /**
     * Validates User making sure they are no spaces
     * @param userName
     * @return
     */
    public boolean validateDescription(String userName) {	
		PasswordValidator validator = new PasswordValidator();
		if(validator.validateHint(userName)){
			return true;
		}
		
		return false;
	}
    
    
    /**
     * This is the action for the deleteButton
     * 
     * @param event
     * @throws Exception
     */
    @FXML //deleteButton
    private void deleteEntryButtonAction(ActionEvent event) throws Exception{
    	String file = null;
    	int num = 0;
    	String line = null;
    	Hash hash = new Hash();
    	String fileName = username;
    	file = hash.sha256(fileName);
    	//Where is num being stored for the line number? 
    	line = passwordClipBoard.getText();
    	
    	removeLineFromFile(file, line);
    }
    
    /**
     * Method removes an entry from the file. 
     * 
     * Totally not done yet. Problem: Could not delete the file. 
     * May be using file and cannot overwrite the lock on the file. 
     * 
     * @param file
     * @param i
     */
    
    public void removeLineFromFile(String file, String lineRemove) {
	    try {
	      file = file+".csv";
	      File inFile = new File (file);
	      
	      //mainly here for testing
	      if (!inFile.isFile()) {
	        System.out.println("Parameter is not an existing file");
	        return;
	      }

	      //Construct the new file that will later be renamed to the original filename.
	      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

	      BufferedReader br = new BufferedReader(new FileReader(file));
	      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

	      String line = null;
	      int count = 0;
	      //Read from the original file and write to the new unless content matches data to be removed.
	      while ((line = br.readLine()) != null) {
	    	 // if (count == i) {

	        if (!line.trim().equals(lineRemove)) {

	          pw.println(line);
	          pw.flush();
	        }
	    	  count++;
	      }
	      pw.close();
	      br.close();

	      //Delete the original file
	      if (!inFile.delete()) {
	        System.out.println("Could not delete file");
	        return;
	      }

	      //Rename the new file to the filename the original file had.
	      if (!tempFile.renameTo(inFile))
	        System.out.println("Could not rename file");

	    }
	    catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	    }
	    catch (IOException ex) {
	      ex.printStackTrace();
	    }
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
     	webName.setCellValueFactory(
                  new PropertyValueFactory<Website, String>("name"));
     	webPass.setCellValueFactory(
                 new PropertyValueFactory<Website, String>("pass"));
     	this.sites = sites;
     	data =  FXCollections.observableArrayList(sites);
     	showSites.setItems(data);
     	
     	
     }
    
    public void append (String fileName, String hexWebsite, String hexPassword) throws Exception {
    	 
    	      BufferedWriter writer = null;
    	          	 
    	      try {
    	         writer = new BufferedWriter(new FileWriter(fileName+".csv", true));
    	         writer.write(hexWebsite + "," + hexPassword);
    	         writer.newLine();
    	         writer.flush();
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
        //Read the file line by line starting from the second line
        ArrayList<Website> sites = new ArrayList();
        while ((line = fileReader.readLine()) != null) {
            //Get all tokens available in line
        	if(count >= 2){
        		String[] tokens = line.split(COMMA_DELIMITER);
        		if (tokens.length > 0) {
        			String websiteName = ((tokens[WEBNAME_IDX]));
        			String websitePass = ((tokens[PASSWORD_IDX]));
        			
        			byte[] encryptedBytes = DatatypeConverter.parseHexBinary(websitePass);
        			String original = encrypt.decrypt(encryptedBytes, Master);
            	
        			byte[] encryptedWeb = DatatypeConverter.parseHexBinary(websiteName);
        			String website = encrypt.decrypt(encryptedWeb, Master);
                
        			Website temp = new Website(website,original);
        			sites.add(temp);
        			setListView(sites);
        		}
        	}
        	else
        		count++;
        }
        count = 0;
     }catch(IOException e){
    	 
     }
    }
}