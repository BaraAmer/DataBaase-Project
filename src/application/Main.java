package application;
	
import static javafx.stage.Modality.NONE;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	
	

	
	// begin of the program 
	public static void main(String[] args) {
		launch(args);
	}// end main function 
	
	
	@Override
	public void start(Stage stage)  {
		
		
		try {
			 Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
				Scene scene = new Scene(root,900,623);
			   stage.setTitle("Telent Program"); 
			    stage.setScene(scene);
			    stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end start
	
	
	
	




	
	
}//end class
