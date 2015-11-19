package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import javafx.*;

public class FXMLDocumentController {
	
	@FXML // fx:id="testButton"
    private Button testButton;
	
    @FXML // fx:id="loginButton"
    private Button loginButton;
    
    @FXML //fx:id="createButton"
    private Button createButton;
    
    @FXML //fx:id="ForgotHyperLink"
    private Hyperlink forgotLink;
    
    @FXML //fx:id="usernameField"
    private TextField usernameField;
    
    @FXML //fx:id="usernameField"
    private PasswordField passwordField;
    
    private LoginFileReader loginReader = new LoginFileReader();
    
    String testing;
    AES encrypt = new AES();
    String pass;

    @FXML //loginButton
    private void loginButtonAction(ActionEvent event) throws Exception{        
        boolean usernameBool = validateUsername(usernameField.getText());
        
        try{
        	 if(usernameBool){
             	String user = null,password = null,fileName = null, hFileName;
             	//Create the file reader
             	Hash hash = new Hash();
             	String userName = usernameField.getText();
             	String passwordName = passwordField.getText();
             	String hPassword = hash.sha256(passwordName);
             
             	hFileName = hash.sha256(userName);
 	
             	String currentFile = hFileName+".csv";
            	String backUpFile = File.separator+"Users"+File.separator+"fabianherrera"+File.separator+"Documents"+File.separator+"DePauw"+File.separator+"Backups"+File.separator+hFileName+".csv";
            	
            	if(!compareBackUpToCurrent(backUpFile,currentFile))
            	{
            		System.out.println("Your file has been corrupted, attempting to repare.");
            		updateCurrentFile(currentFile, backUpFile);
            		System.out.println("File Restored");
            	}
            	
             	//Checks if username and password are correct
             	if(!loginReader.LoginFile(hFileName, hPassword)){
             		alertMessage();
             		return;
             	}
             	
             	if(!loginReader.viewList(passwordName)){
             		alertMessage();
             		return;
             	}
             	             	
             	ArrayList<Website> sites = loginReader.getViewList();
             		
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));    
             	Parent home_page_parent =(Parent)loader.load(); //New Scene
             	Scene home_page_scene = new Scene(home_page_parent);
             	FXMLAccountController controller = (FXMLAccountController)loader.getController();
             	controller.setUserName(userName);
           		controller.setPassword(passwordName);
           		controller.setUserPage();
           		controller.setListView(sites);
           		controller.addListener();
           		controller.addListenerAction(event);
             		
           		Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
           		newStage.hide();
           		newStage.setScene(home_page_scene);
           		newStage.show();
        	 }
        }
        catch(IOException e){
        	alertMessage();
        }
    }
    
	/**
	 * Displays Error Message
	 */
	public void alertMessage(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Username or Password is invalid");
		alert.setHeaderText(null);
		alert.setContentText("Username or Password is invalid");
		
		alert.showAndWait();
	}
    
    /**
     * Validates User making sure they are no spaces
     * @param userName
     * @return
     */
    public boolean validateUsername(String userName) {	
		PasswordValidator validator = new PasswordValidator();
		if(validator.validateUserName(userName)){
			return true;
		}
		
		return false;
	}
    
    @FXML //createButton
    private void createButtonAction(ActionEvent event) throws IOException{
        
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("AccountCreation.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();

    }
    
	public boolean compareBackUpToCurrent(String backUp, String current){
		 try
	    	{
			 FileReader first = new FileReader(backUp);
			 FileReader second = new FileReader(current);
			 
			 BufferedReader backUps = new BufferedReader(first);
			 BufferedReader original = new BufferedReader(second);
			 
			 String backUpLine = "";
			 String currentLine = "";
			 
			 while((backUpLine = backUps.readLine()) != null && (currentLine = original.readLine()) != null)
			 {
				 if(!backUpLine.equals(currentLine))
				 {
					 return false;
				 }
			 }
		    	
	    	}
	        catch(IOException e)
	    	{
	    	     e.printStackTrace();
	    	} 
		 return true;
		 
	}
	
	public void updateCurrentFile(String oldFile, String newFile){
		 try
	    	{
			    FileWriter fileWritter = new FileWriter(oldFile,false);
		        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		        
		        String line = "";
		    	BufferedReader fileReader = new BufferedReader(new FileReader(newFile));
		    	
		        while ((line = fileReader.readLine()) != null) {
		            bufferWritter.write(line);
		            bufferWritter.flush();
		            bufferWritter.newLine();
		        }
		        bufferWritter.close();
		    	
	    	}
	        catch(IOException e)
	    	{
	    	     e.printStackTrace();
	    	} 
		 
	}
    
    @FXML //Hyperlink
    private void hyperLinkAction(ActionEvent event) throws IOException{
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("AccountForgot.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();
    	
    }
}