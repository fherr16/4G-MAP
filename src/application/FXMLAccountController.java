package application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


public class FXMLAccountController {
	
	public ArrayList<Website> sites;
	
	public  ObservableList data ;
	     
	
	private String username;
	
	@FXML //fx:id="CellList"
	private TableView showSites;
	
    @FXML // fx:id="logoutButton"
    private Button logoutButton;
    
    @FXML // fx:id="logoutButton"
    private Text titlePage;
    
    @FXML
    private TableColumn<Website, String> webName;
    @FXML
    private TableColumn<Website,String> webPass;

    @FXML //loginButton
    private void logoutButtonAction(ActionEvent event) throws IOException{
    	logoutButton.setDisable(true);
        System.out.println("Selected");
        
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("Sample.fxml")); //New Scene
        Scene home_page_scene = new Scene(home_page_parent);
        
        Stage newStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        newStage.hide();
        newStage.setScene(home_page_scene);
        newStage.show();
    }
    
     void setUserName(String name){
    	username = name;
    }
    
     void setUserPage(){
    	titlePage.setText("User: " + username);
    }
     
    void setListView(){
    	System.out.println("VIEW");
    	webName.setCellValueFactory(
                 new PropertyValueFactory<Website, String>("name"));
    	webPass.setCellValueFactory(
                new PropertyValueFactory<Website, String>("pass"));
    	Website temp = new Website("KEVIN","YEAN");
    	sites = new ArrayList();
    	sites.add(temp);
    	data =  FXCollections.observableArrayList(sites);
    	showSites.setItems(data);
    	
    }

}
