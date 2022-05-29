package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class login implements Initializable {

	   @FXML private TextField txt_user;
	    @FXML private PasswordField pass;
	    @FXML private Button but_log;
	    @FXML private Label incorrect;
	    

	    @FXML
	    void but_logac(ActionEvent event) {
	    	Stage window=(Stage) but_log.getScene().getWindow();
	    	
	    	//if user name and password correct then allow to enter the program
	    	if (txt_user.getText().toString().equals("Telent") && pass.getText().toString().equals("bara")) 
	    	{
	    		try {
	     FXMLLoader loader=new FXMLLoader(getClass().getResource("homepage.fxml"));
	        Parent root2 = (Parent) loader.load();
	       
	        	 Scene sc=new Scene(root2);
	        	 window.setX(50);
	        	 window.setY(50);
	        	 window.setScene(sc);
	        	 window.show();
	        	}catch(Exception e) {
	        	System.out.println("Theprogram fail to load layout");
	        	}
	    		
	    		incorrect.setText("");
	    		
	    	}	
	    	else 
	    		incorrect.setText("incorrect Username or Password");
	    	

	    } // end function but_logac

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		incorrect.setText("");
		
	}// end initalize 
	
	
	

}//end class
