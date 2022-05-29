package application;

import static javafx.stage.Modality.NONE;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;


import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;

public class screen implements Initializable {
	
	 ArrayList<Stu_pro> data;
   ObservableList<Stu_pro> datatable;
    
// variables to join with data base 
    private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "baraamer70";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "student_project";
	private Connection con;

	// to join with scene bulider
	 @FXML  TableView<Stu_pro> taview;	
	    
	    @FXML private TableColumn<Stu_pro,Integer> t1 ;
	    @FXML private TableColumn<Stu_pro,String> t2;
	    @FXML private TableColumn<Stu_pro,Integer> t3;
	    @FXML private TableColumn<Stu_pro,String> t4;
	    @FXML private TableColumn<Stu_pro,String> t5;    
	    @FXML private TextField txt_ids;
	    @FXML private TextField txt_year;
	    @FXML private TextField txt_idt;
	    @FXML private TextField txt_name;
	    @FXML private TextField txt_phone;	    
	    @FXML private Button btn_add;
	    @FXML private Button btn_dele;
	    @FXML private Button btn_ref;
	    @FXML private Button btn_clear;
	    
	    @FXML private TextField txt_n_year;
	    @FXML private TextField txt_n_tr;
	    
	    @FXML private Label eror3;
	    
	    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		data = new ArrayList<Stu_pro>();
		try {
			getToTable();// get data from data base
			datatable = FXCollections.observableArrayList(data);
			
		}catch(Exception e) {
			e.printStackTrace();
			eror3.setVisible(true);
		}
		
		// match tables columns with columns in data base and make values editable
		
		t1.setCellValueFactory(new PropertyValueFactory<Stu_pro,Integer>("ids"));
		
		
		t2.setCellValueFactory(new PropertyValueFactory<Stu_pro, String>("sname"));
		t2.setCellFactory(TextFieldTableCell.<Stu_pro>forTableColumn());
		 t2.setOnEditCommit(        
	        		(CellEditEvent<Stu_pro, String> t) -> {
	                       ((Stu_pro) t.getTableView().getItems().get(
	        	                        t.getTablePosition().getRow())
	        	                        ).setSname(t.getNewValue()); //display only
	                       
	                 updateName( t.getRowValue().getIds(),t.getNewValue());
	        		});
	        
		
		t3.setCellValueFactory(new PropertyValueFactory<Stu_pro, Integer>("idt"));
		t3.setCellFactory(TextFieldTableCell.<Stu_pro,Integer>
		forTableColumn(new IntegerStringConverter()));
		t3.setOnEditCommit(        
        		(CellEditEvent<Stu_pro, Integer> t) -> {
                       ((Stu_pro) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setIdt(t.getNewValue());
                 updateIDT( t.getRowValue().getIds(),t.getNewValue());
        		});
		
		
		t4.setCellValueFactory(new PropertyValueFactory<Stu_pro, String>("year"));
		t4.setCellFactory(TextFieldTableCell.<Stu_pro>forTableColumn());
		 t4.setOnEditCommit(        
	        		(CellEditEvent<Stu_pro, String> t) -> {
	                       ((Stu_pro) t.getTableView().getItems().get(
	        	                        t.getTablePosition().getRow())
	        	                        ).setYear(t.getNewValue()); //display only
	                       
	                 updateYear( t.getRowValue().getIds(),t.getNewValue());
	        		});
		 
		
		t5.setCellValueFactory(new PropertyValueFactory<Stu_pro, String>("phone"));
		t5.setCellFactory(TextFieldTableCell.<Stu_pro>forTableColumn());
		 t5.setOnEditCommit(        
	        		(CellEditEvent<Stu_pro, String> t) -> {
	                       ((Stu_pro) t.getTableView().getItems().get(
	        	                        t.getTablePosition().getRow())
	        	                        ).setPhone(t.getNewValue()); //display only
	                       
	                 updatePhone( t.getRowValue().getIds(),t.getNewValue());
	        		});

		
		
		// show data in table 
		 taview.setItems(datatable);
		 
		 
		// when click row in table Department then should display 
			// Number of students in this selected year & 
		 // Number of students which the id_employee train them
		 
		 taview.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					try {
						 String query1;
						 String query2;
							
						   	connectDB();

						   	query1 = "select count(s.ids)\r\n" + 
						   			"        from employee_w e , student s \r\n" + 
						   			"        where e.eid=s.idt and s.idt="+taview.getSelectionModel().getSelectedItem().getIdt();
						   	Statement stmt = con.createStatement();
						   	ResultSet rs = stmt.executeQuery(query1);
						   	
						   	if ( rs.next() ) 
						   		txt_n_tr.setText(String.valueOf(rs.getString(1)));			
						   
						   	rs.close();
						   	stmt.close();
						   	
						   	query2="select count(s.ids)\r\n" + 
						   			"        from student s \r\n" + 
						   			"        where s.year="+taview.getSelectionModel().getSelectedItem().getYear();
						   	Statement stmt2 = con.createStatement();
						   	ResultSet rs2 = stmt2.executeQuery(query2);	   	
						   	
						   	if ( rs2.next() ) 
						   		txt_n_year.setText(String.valueOf(rs2.getString(1)));	
						   	
						   	rs2.close();
						   	stmt2.close();
						   	
						   	con.close();
					}catch (SQLException e) {
						e.printStackTrace();
						eror3.setVisible(true);
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
						eror3.setVisible(true);
					}
					
				}// end handle
		 });//end setOnMouseClicked 
		 
		 
       try {
       // add student 
		 btn_add.setOnAction((ActionEvent e) -> {
	            Stu_pro rc;
	        	rc = new Stu_pro(
	                    Integer.valueOf(txt_ids.getText()),
	                    txt_name.getText(),
	                    Integer.valueOf(txt_idt.getText()),
	            		txt_year.getText(), 
	            		txt_phone.getText());
	        	
	        	datatable.add(rc);//insert in table view
	        	insertData(rc); // insert in Data Base 
	        	
	        	txt_ids.clear();
	        	txt_name.clear();
	        	txt_idt.clear();
	        	txt_year.clear();
	        	txt_phone.clear();
	        });
       }catch(Exception e) {
    	   eror3.setVisible(true);
       }
       
       btn_dele.setOnAction((ActionEvent e) -> {        	 
      	 ObservableList<Stu_pro> selectedRows = taview.getSelectionModel().getSelectedItems();
      	ArrayList<Stu_pro> rows = new ArrayList<>(selectedRows);
      	rows.forEach(row -> {
      		taview.getItems().remove(row); 
      		deleteRow(row); 
      		taview.refresh();
      		});   
      });
       
       btn_ref.setOnAction((ActionEvent e) -> {        	 
       	taview.refresh();  
       });
       
    	btn_clear.setOnAction((ActionEvent e) -> {
    		showDialog( NONE, taview);
    		

    });
      
		
	}// end initialize 
	
	
    // show dialog to inform clear or not     
       private void showDialog( Modality modality,   TableView<Stu_pro> table) {
   		Stage stage = new Stage();
   		stage.initModality(modality);
  
   		Button yesButton = new Button("Confirm");
   		yesButton.setOnAction(e -> {
   			for (Stu_pro row:datatable) {
           			deleteRow(row);
           			table.refresh();
           		}
           		table.getItems().removeAll(datatable);
       			stage.close();

   			});
   		
   		Button noButton = new Button("Cancel");
   		noButton.setOnAction(e -> stage.close());

   		HBox root = new HBox();
   		root.setPadding(new Insets(10, 10, 10, 10));
   		root.setAlignment(Pos.CENTER);
           root.setSpacing(10);

   		root.getChildren().addAll(yesButton, noButton);
   		Scene scene = new Scene(root, 200, 100);
   		stage.setScene(scene);
   		stage.setTitle("Confirm Delete?");
   		stage.show();
   		}
	
	// function to update Name of student
	 public void updateName(int snum, String name) {
			
			try {
				System.out.println("update  student set sname = '"+name + "' where ids = "+snum);
				connectDB();
				ExecuteStatement("update  Student set sname = '"+name + "' where ids= "+snum+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				}
		} // end function updateName
	 
	 
	// function to update IDT trainer  of student
	 public void updateIDT(int snum, int idt) {
			
			try {
				System.out.println("update  student set idt = "+idt + " where ids = "+snum);
				connectDB();
				ExecuteStatement("update  Student set idt = "+idt + " where ids= "+snum+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				}
		}// end function updateIDT
	 
	 
	// function to update Year of student
	 public void updateYear(int snum, String year) {
			
			try {
				System.out.println("update  student set year= '"+year + "' where ids = "+snum);
				connectDB();
				ExecuteStatement("update  Student set year = '"+year + "' where ids= "+snum+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				}
		}// end function updateYear
	 
	 
	 // function to phone  Year of student
	 public void updatePhone(int snum, String phone) {
			
			try {
				System.out.println("update  student set phone = '"+phone + "' where ids = "+snum);
				connectDB();
				ExecuteStatement("update  Student set phone = '"+phone + "' where ids= "+snum+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				}
		}// end function updatePhone
	 
	 
	 // function to execute statment in data base 
	 public void ExecuteStatement(String SQL) throws SQLException {

			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(SQL);
				stmt.close();
			
				 
			}
			catch(SQLException s) {
				s.printStackTrace();
				System.out.println("SQL statement is not executed!");
				eror3.setVisible(true);
			}
	
		}// end function ExecuteStatement
	 
	 
	 // insert into tables in data base 
		private void insertData(Stu_pro rc) {
			
			try {
					System.out.println("Insert into Student values("+rc.getIds()+",'"+rc.getSname()+"',"+ rc.getIdt() +",'"+ rc.getYear()+"','"+rc.getPhone()+"')");
					
					connectDB();
					ExecuteStatement("Insert into Student values("+rc.getIds()+",'"+rc.getSname()+"',"+ rc.getIdt() +",'"+ rc.getYear()+"','"+rc.getPhone()+"');");
					con.close();
					System.out.println("Connection closed" + data.size());
					
					} catch (SQLException e) {
						e.printStackTrace();
						eror3.setVisible(true);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						eror3.setVisible(true);
					}
			}// end function insertData
		
		
		// 
		private void deleteRow(Stu_pro row) {
			// TODO Auto-generated method stub
			
			try {
				System.out.println("delete from  student where ids="+row.getIds() + ";");
				connectDB();
				ExecuteStatement("delete from  student where ids="+row.getIds() + ";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eror3.setVisible(true);
				}
		}

		 // get Data from data base to put them in Table View
	private void getToTable() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;		
		
		connectDB();
		System.out.println("Connection established");

		SQL = "select * from student ";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);


		while ( rs.next() ) 
			data.add(new Stu_pro(
					Integer.parseInt(rs.getString(1)),
					rs.getString(2),
					Integer.parseInt(rs.getString(3)),
					rs.getString(4),
					rs.getString(5)));
		
		rs.close();
		stmt.close();
		
	

		con.close();
		System.out.println("Connection closed" + data.size());
			
	}//end function  getdata
	
	
	// function to connect with eclipse and MySql
private void connectDB() throws ClassNotFoundException, SQLException {
		
		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");
	
		con = DriverManager.getConnection (dbURL, p);

	}//end connectDB


@FXML // // function to go Home page
  public void backhomepage(ActionEvent event) {
	Stage window=(Stage) btn_add.getScene().getWindow();
	try {
	     FXMLLoader loader=new FXMLLoader(getClass().getResource("homepage.fxml"));
	        Parent root2 = (Parent) loader.load();
	       
	        	 Scene sc=new Scene(root2);
	        	 window.setScene(sc);
	        	 window.setX(50);
	        	 window.setY(50);
	        	 window.show();
	        	}catch(Exception e) {
	        	System.out.println("The program fail to load layout");
	        	}

}// end function  backhomepage





	

}// end class 
