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
import java.io.FileReader;
import java.io.IOException;
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
    
    
    String testing;
    AES encrypt = new AES();
    	//Delimiter use
    
        private static final String COMMA_DELIMITER = ",";

        //Student attributes index
        private static final int WEBNAME_IDX = 0;
        private static final int PASSWORD_IDX = 1;



    @FXML //loginButton
    private void loginButtonAction(ActionEvent event) throws Exception{
        System.out.println("Clicked Login");
        
        boolean usernameBool = validateUsername(usernameField.getText());
        
        try{
        	 if(usernameBool){
             	String user = null,password = null,fileName = null, hFileName;
             	//Create the file reader
             	Hash hash = new Hash();
             	String userName = usernameField.getText();
             	String passwordName = passwordField.getText();
             	fileName = userName+passwordName;
             	hFileName = hash.sha256(fileName);
         		
             	BufferedReader fileReader = new BufferedReader(new FileReader(hFileName+".csv"));
             	
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
                        String original = encrypt.decrypt(encryptedBytes, passwordName);
                        System.out.println(original);
                    	
                    	Website temp = new Website(websiteName,original);
                    	sites.add(temp);
    				}
                }
             	
             	FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));    
                Parent home_page_parent =(Parent)loader.load(); //New Scene
                Scene home_page_scene = new Scene(home_page_parent);
                FXMLAccountController controller = (FXMLAccountController)loader.getController();
                controller.setUserName(userName);
                controller.setPassword(passwordName);
                controller.setUserPage();
                controller.setListView(sites);
                 
                Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
                newStage.hide();
                newStage.setScene(home_page_scene);
                newStage.show();
             }
             else{
             	alertMessage();
             }
        }
        catch(IOException e){
        	System.out.println(e);
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
		System.out.println(validator.validateUserName(userName));
		if(validator.validateUserName(userName)){
			return true;
		}
		
		return false;
	}
    
    @FXML //createButton
    private void createButtonAction(ActionEvent event) throws IOException{
        System.out.println("Selected");
        
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("AccountCreation.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();

    }
    
    @FXML //Hyperlink
    private void hyperLinkAction(ActionEvent event) throws IOException{
    	System.out.println("Link Click");
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("AccountForgot.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();
    	
    }
    void setString(String da){
    	testing = da;
    }
    
    @FXML //createButton
    private void TestButton(ActionEvent event) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));    
        Parent home_page_parent =(Parent)loader.load(); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        FXMLAccountController controller = (FXMLAccountController)loader.getController();
         
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();
        System.out.println();
    }

}