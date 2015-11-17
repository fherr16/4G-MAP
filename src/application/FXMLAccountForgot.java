package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
    
    @FXML
    private TextArea hintField;
    
    AES encrypt = new AES();
    Hash hash = new Hash();
    int counter = 0;
	
	@FXML //Submit Action
	private void submitButtonAction(ActionEvent event) throws IOException{
		
		String user = null, hashUser = null, hint = null;
		
        boolean usernameBool = validateUsername(userName.getText());
        try{
        	
        	if(usernameBool)
        	{		
        		user = userName.getText();
        		hashUser = hash.sha256(user);
        		
        		BufferedReader fileReader = new BufferedReader(new FileReader(hashUser+".csv"));
        		String line = "";
        		
             	while ((line = fileReader.readLine()) != null)
             	{
             		if(counter == 1)
             		{
             			hint = line.trim();
             			counter++;
             		}
             		counter++;
             	}
        	}
        }catch(IOException e){
        	alertMessage();
        }
        
        counter = 0;
        
        hintField.setText(hint);
		
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
	
	public void alertMessage(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("File Does Not Exist");
		alert.setHeaderText(null);
		alert.setContentText("The Username you entered does not exist");
		
		alert.showAndWait();
	}
	
	public boolean validateUsername(String userName) {
		
		PasswordValidator validator = new PasswordValidator();
		
		if(validator.validateUserName(userName)){
			return true;
		}
		
		return false;
	}	
}
