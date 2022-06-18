package application;

import java.sql.*;
import java.awt.TextField;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class homepage implements Initializable {
	ArrayList<product> data;
	   ObservableList<product> dataList;
	   
private String dbURL;
		private String dbUsername = "root";
		private String dbPassword = "bbb123";
		private String URL = "127.0.0.1";
		private String port = "3306";
		private String dbName = "student_project";
		private Connection con;

	@FXML private Button btn_emp;
	@FXML private Button btn_clients;
	@FXML private Button btn_product;
	 @FXML private Button Hback;
	
	@FXML private TableView <product> table;
	@FXML private TableColumn<product, Integer> serial_n;
	@FXML private TableColumn<product, String> ware_house;
	@FXML private TableColumn<product, String> description;
	@FXML private TableColumn<product, Integer> price;
	@FXML private TableColumn<product, Integer> amount;
	

	@FXML void gotoclients(ActionEvent event) {
		Stage window = (Stage) btn_clients.getScene().getWindow();
		try {
		     FXMLLoader loader = new FXMLLoader(getClass().getResource("clients.fxml"));
		        Parent root2 = (Parent) loader.load();
		       
		        	 Scene sc = new Scene(root2);
		        	 window.setX(50);
		        	 window.setY(50);
		        	 window.setScene(sc);
		        	 window.show();
		        	}catch(Exception e) {
		        	System.out.println("The program fail to load layout");
		        	}
		    		

	}//end function gotoclients
	
	@FXML void gotoproducts(ActionEvent event) {
		Stage window = (Stage)btn_product.getScene().getWindow();
		try {
		     FXMLLoader loader = new FXMLLoader(getClass().getResource("Fproduct.fxml"));
		        Parent root2 = (Parent) loader.load();
		       
		        	 Scene sc = new Scene(root2);
		        	 window.setX(50);
		        	 window.setY(50);
		        	 window.setScene(sc);
		        	 window.show();
		        	}catch(Exception e) {
		        	System.out.println("The program fail to load layout");
		        	}

	    }
	

	public void backtohome(ActionEvent event) {
		Stage window=(Stage) Hback.getScene().getWindow();
		try {
		     FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
		        Parent root2 = (Parent) loader.load();
		       
		        	 Scene sc=new Scene(root2);
		        	 
		        	 window.setScene(sc);
		        	 window.show();
		        	}catch(Exception e) {
		        	System.out.println("Theprogram fail to load layout");
		        	}
		    		

	}

	
private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from product";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new product(
					Integer.parseInt(rs.getString(1)),
					rs.getString(2),
					rs.getString(3),
					Integer.parseInt(rs.getString(4)),
					Integer.parseInt(rs.getString(5))));
		
		rs.close();
		stmt.close();
		
	

		con.close();
		System.out.println("Connection closed" + data.size());
			
	}//end getdata


	
private void connectDB() throws ClassNotFoundException, SQLException {
		
		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");
	
		con = DriverManager.getConnection(dbURL, p);

	}//end connectDB
	
@Override
public void initialize(URL location, ResourceBundle resources) {
		
				
	}


@FXML void gotoemploye(ActionEvent event){
	
	Stage window=(Stage) btn_emp.getScene().getWindow();
	try {
	     FXMLLoader loader=new FXMLLoader(getClass().getResource("Infofemp.fxml"));
	        Parent root2 = (Parent) loader.load();
	       
	        	 Scene sc=new Scene(root2);
	        	 window.setX(50);
	        	 window.setY(50);
	        	 window.setScene(sc);
	        	 window.show();
	        	}catch(Exception e) {
	        	System.out.println("Theprogram fail to load layout");
	        	}
	
	
}

@FXML void gotopageofstu(ActionEvent event) {
	Stage window=(Stage) btn_emp.getScene().getWindow();
	try {
	     FXMLLoader loader=new FXMLLoader(getClass().getResource("screen.fxml"));
	        Parent root2 = (Parent) loader.load();
	       
	        	 Scene sc=new Scene(root2);
	        	 window.setX(50);
	        	 window.setY(50);
	        	 window.setScene(sc);
	        	 window.show();
	        	}catch(Exception e) {
	        	System.out.println("Theprogram fail to load layout");
	        	}
	
}



}// end class
