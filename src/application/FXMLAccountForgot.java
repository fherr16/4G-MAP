package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FXMLAccountForgot {
	
	@FXML //fx-id:submitButton
	private Button submitButton;
	
	@FXML //fx:id-backButton
	private Button backButton;
	
    @FXML
    private TextField userName;
    
    
	
	@FXML //Submit Action
	private void submitButtonAction(ActionEvent event) throws IOException{
		
		String user = null;
		
        boolean usernameBool = validateUsername(userName.getText());

        if(usernameBool)
        {		
        	
        }
		
	}
	
	@FXML //Back Action
	private void backButtonAction(ActionEvent event) throws IOException{
		backButton.setDisable(true);
        System.out.println("Selected");
        
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("Sample.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();
	}
	
	public boolean validateUsername(String userName) {
		
		PasswordValidator validator = new PasswordValidator();
		System.out.println(validator.validateUserName(userName));
		
		if(validator.validateUserName(userName)){
			return true;
		}
		
		return false;
	}	
}
