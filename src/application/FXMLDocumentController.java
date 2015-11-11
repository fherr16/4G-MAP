package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.*;

public class FXMLDocumentController {
	
    @FXML // fx:id="loginButton"
    private Button loginButton;
    
    @FXML //fx:id="createButton"
    private Button createButton;
    
    @FXML //fx:id="ForgotHyperLink"
    private Hyperlink forgotLink;
    
    @FXML //fx:id="usernameField"
    private TextField usernameField;

    @FXML //loginButton
    private void loginButtonAction(ActionEvent event) throws IOException{
        System.out.println("Clicked Login");
        
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("Account.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
       // newStage.hide();
       // newStage.setScene(home_page_scene);
       // newStage.show();
        System.out.println(usernameField.getText());
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

}
