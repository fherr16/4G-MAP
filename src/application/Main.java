package application;
	
import java.security.NoSuchAlgorithmException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	public static PasswordGenerator pass = new PasswordGenerator();
	public static AES encrypt = new AES();
	public static Hash hash = new Hash();
	public static String password;
	
	@Override
	  public void start(Stage stage) throws Exception {
			System.out.println("System 1");
			Parent root = FXMLLoader.load(getClass().getResource("AccountCreation.fxml"));      
			System.out.println("System 2");
	        Scene scene = new Scene(root); 
	        
	        stage.setScene(scene);
	        stage.setTitle("4G-MAP");
	        stage.show();
	    }
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		launch(args);
	}
}
