package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
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
		launch(args);
	}
	
	public static void helloWorld(String[] args) {
		System.out.println("Hello World");
	}
}
