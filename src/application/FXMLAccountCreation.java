package application;
import javafx.event.ActionEvent;
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
        
        
        String user = null,password = null, fileName = null, hFileName = null;
        
        boolean usernameBool = validateUsername(usernameField.getText());
        boolean passwordBool = validatePassword(originalPassword.getText());
        boolean matchingBool = validateMatchingPassword(originalPassword.getText(),(copyPassword.getText()));
        
        if(usernameBool && passwordBool && matchingBool){ //Checks that both Textfields are valid 
        	//Hashes password and user for file creation
        	Hash hash = new Hash();
        	String userName = usernameField.getText();
        	String passwordName = originalPassword.getText();
        	fileName = userName + passwordName;
        	hFileName = hash.sha256(fileName);
        	
            createAccount(hFileName);
            successMessage("You have sucesfully created an account. Click Ok to be redirected to your Account Page.");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));    
            Parent home_page_parent =(Parent)loader.load(); //New Scene
            Scene home_page_scene = new Scene(home_page_parent);
            FXMLAccountController controller = (FXMLAccountController)loader.getController();
            controller.setUserName(userName);
            controller.setUserPage();
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
		System.out.println(validator.validateUserName(userName));
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
		System.out.println(validator.validate(password));
		if(validator.validate(password)){
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
	public void createAccount(String fileName){
		 try
	    	{
	    	    FileWriter writer = new FileWriter(fileName+".csv");
	    	    System.out.println("File Created");
	    	    writer.flush();
	    	    writer.close();
	    	}
	        catch(IOException e)
	    	{
	    	     e.printStackTrace();
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
        System.out.println("Selected");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
        Parent home_page_parent =(Parent)loader.load(); //New Scene
        //Parent home_page_parent = FXMLLoader.load(getClass().getResource("Sample.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        FXMLDocumentController controller = (FXMLDocumentController)loader.getController();
        controller.setString("D");
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();
	}
}
