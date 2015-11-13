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
	
	@Override
	  public void start(Stage stage) throws Exception {
			System.out.println("1");
			Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));      
			System.out.println("2");
	        Scene scene = new Scene(root); 
	        
	        stage.setScene(scene);
	        stage.setTitle("GUI Test");
	        stage.show();
	        System.out.println("Hello");
	    }
	
	public static void main(String[] args) {
		//launch(args);
		pass.generate();
		encrypt.main(args);
		encrypt.alter("ehsahentthisissomethingdifferent", "1234567890a");
		encrypt.main(args);
		try {
			hash.hash();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			byte[] passHash = hash.hash();
			String x = passHash.toString();
			encrypt.alter("hellothereniceto", x);
			encrypt.main(args);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void helloWorld(String[] args) {
		System.out.println("Hello World");
	}
	
	public static void fabianIsHere(){
	System.out.println("Fabian is here too");
	}
	
	public static void kevinisHere(){
		System.out.println("Kevin is here too");
	}
	
	public static void mateuszisHere(){
		System.out.println("Matt is on top yo!");
	}
}
