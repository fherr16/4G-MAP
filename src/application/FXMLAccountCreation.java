package application;
import javafx.event.ActionEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;

//HELLO


public class FXMLAccountCreation {
	
	@FXML //fx:id="usernameField"
	private TextField usernameField;
	
	@FXML //fx-id:originalPassword
	private PasswordField originalPassword;
	
	@FXML //fx:id="hintField"
	private TextField hintField;
	
	@FXML //fx-id:copyPassword
	private PasswordField copyPassword;
	
	@FXML //fx-id:submitButton
	private Button submitButton;
	
	@FXML //fx:id-backButton
	private Button backButton;
	
	/**
	 * Submits request to create account
	 * @param event
	 * @throws IOException
	 */
	@FXML //Submit Action
	private void submitButtonAction(ActionEvent event) throws IOException{
        
        
        String user = null,password = null, fileName = null, hFileName = null, hint = null;
        
        boolean usernameBool = validateUsername(usernameField.getText());
        boolean passwordBool = validatePassword(originalPassword.getText());
        boolean matchingBool = validateMatchingPassword(originalPassword.getText(),(copyPassword.getText()));
        boolean hintBool = validateHint(hintField.getText());
        
        if(usernameBool && passwordBool && matchingBool && hintBool){ //Checks that both Textfields are valid 
        	//Hashes password and user for file creation
        	Hash hash = new Hash();
        	String userName = usernameField.getText();
        	String passwordName = originalPassword.getText();
        	fileName = userName;
        	hFileName = hash.sha256(fileName);
        	String passwordHash = hash.sha256(passwordName);
        	hint = hintField.getText();
        	
            createAccount(hFileName, passwordHash, hint);
            createBackUpAccount(hFileName,passwordHash,hint);
            
            successMessage("You have sucesfully created an account. Click Ok to be redirected to your Account Page.");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));    
            Parent home_page_parent =(Parent)loader.load(); //New Scene
            Scene home_page_scene = new Scene(home_page_parent);
            FXMLAccountController controller = (FXMLAccountController)loader.getController();
            controller.setUserName(userName);
            controller.setPassword(passwordName);
            controller.setUserPage();
            controller.addListener();
            //controller.setListView();
            
            Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            newStage.hide();
            newStage.setScene(home_page_scene);
            newStage.show();
        }
        else{
        	if(!usernameBool)
        		alertMessage("Username is invalid make sure that the input is correct");
        	else if (!matchingBool)
        		alertMessage("Passwords do not match");
        	else if(!hintBool)
        		alertMessage("Please enter a valid hint. Only letters, and between 1-140 letters long.");
        	else
        		alertMessage("Password is invalid make sure that the input is correct");
        	return;
        }
	}
	
	/**
	 * Checks that the Username is valid, by checking that there is no space
	 * @param userName
	 * @return hashed version of userName
	 * @throws IOException 
	 */
	public boolean validateUsername(String userName) {
		
		PasswordValidator validator = new PasswordValidator();
		if(validator.validateUserName(userName)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks that the Password is valid, by checking that it fits the regular expression
	 * @param password
	 * @return
	 */
	public boolean validatePassword (String password){
		
		PasswordValidator validator = new PasswordValidator();
		if(validator.validate(password)){
			return true;
		}
		
		return false;
	}
	
	public boolean validateHint (String hint){
		
		PasswordValidator validator = new PasswordValidator();
		if(validator.validateHint(hint)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Return true if both strings match
	 * @param passwordOne
	 * @param passwordTwo
	 * @return
	 */
	public boolean validateMatchingPassword(String passwordOne, String passwordTwo){
		if(passwordOne.matches(passwordTwo))
			return true;
		return false;
	}
	
	/**
	 * Create Account
	 * @param userName
	 */
	public void createAccount(String fileName, String password, String hint){
		 try
	    	{
	    	    FileWriter writer = new FileWriter(fileName+".csv");
	    	    writer.flush();
	    	    writer.close();
	    	}
	        catch(IOException e)
	    	{
	    	     e.printStackTrace();
	    	} 
		 
		 BufferedWriter writer = null;
		 
		 try {
	         writer = new BufferedWriter(new FileWriter(fileName+".csv", true));
	         writer.write(password);
	         writer.newLine();
	         writer.write(hint);
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
	
	public void createBackUpAccount(String fileName, String password, String hint){
		 try
	    	{
	    	    FileWriter writer = new FileWriter(File.separator+"Users"+File.separator+"fabianherrera"+File.separator+"Documents"+File.separator+"DePauw"+File.separator+"Backups"+File.separator+fileName+".csv");
	    	    writer.flush();
	    	    writer.close();
	    	}
	        catch(IOException e)
	    	{
	    	     e.printStackTrace();
	    	} 
		 
		 BufferedWriter writer = null;
		 
		 try {
	         writer = new BufferedWriter(new FileWriter(File.separator+"Users"+File.separator+"fabianherrera"+File.separator+"Documents"+File.separator+"DePauw"+File.separator+ "Backups"+File.separator+fileName+".csv", true));
	         writer.write(password);
	         writer.newLine();
	         writer.write(hint);
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
	
	/**
	 * Displays Error Message
	 */
	public void alertMessage(String errorMessage){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Username or Password is invalid");
		alert.setHeaderText(null);
		alert.setContentText(errorMessage);

		alert.showAndWait();
	}

	/**
	 * Displays Success Message
	 */
	public void successMessage(String successMessage){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Account Creation");
		alert.setContentText(successMessage);

		alert.showAndWait();
		
	}
	
	/**
	 * Return to previous page
	 * @param event
	 * @throws IOException
	 */
	@FXML //Back Action
	private void backButtonAction(ActionEvent event) throws IOException{
		backButton.setDisable(true);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
        Parent home_page_parent =(Parent)loader.load(); //New Scene
        //Parent home_page_parent = FXMLLoader.load(getClass().getResource("Sample.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        FXMLDocumentController controller = (FXMLDocumentController)loader.getController();
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();
	}
}