package application;
import javafx.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        
        boolean confirm = false;
        
        //Checks if both Passwords match
        if(originalPassword.getText().matches(copyPassword.getText())){
        	System.out.println("Password Match");
        }
        
        String user = validateUsername(usernameField.getText());
        String password = validatePassword(originalPassword.getText());
        
        createAccount(user);
                
        if(confirm){
	        
	        Parent home_page_parent = FXMLLoader.load(getClass().getResource("Account.fxml")); //New Scene
	        Scene home_page_scene = new Scene(home_page_parent);
	        
	        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
	        newStage.hide();
	        newStage.setScene(home_page_scene);
	        newStage.show();
        }
        else{
        	System.out.println("here");
        }
	}
	
	/**
	 * Checks that the Username is valid, by checking that there is no space
	 * @param userName
	 * @return hashed version of userName
	 */
	public String validateUsername(String userName){
		
		//Transform the username into a hash
		return  "1";
	}
	
	/**
	 * Checks that the Password is valid, by checking that it fits the regular expression
	 * @param password
	 * @return
	 */
	public String validatePassword (String password){
		return password;
	}
	
	/**
	 * Create Account
	 * @param userName
	 */
	public void createAccount(String userName){
		 try
	    	{
	    	    FileWriter writer = new FileWriter(userName+".csv");
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
