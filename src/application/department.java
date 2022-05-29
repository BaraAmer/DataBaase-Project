package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class department implements Initializable {
	
	   ArrayList<Dep> data;
	   ObservableList<Dep> datatable;
	   
	   public int eidinf;
	   
	// variables to join with data base 
	 private String dbURL;
		private String dbUsername = "root";
		private String dbPassword = "bbb123";
		private String URL = "127.0.0.1";
		private String port = "3306";
		private String dbName = "student_project";
		private Connection con;
		
	@FXML private Label l_name;

    public Label getL_name() {
		return l_name;
	}

	public Label getL_ndep() {
		return l_ndep;
	}

	public Label getL_loca() {
		return l_loca;
	}

	public Label getL_mana() {
		return l_mana;
	}
	public void setL_mana(Label l_mana) {
		this.l_mana = l_mana;
	}
	public Label getL_startman() {
		return l_startman;
	}

	public Label getL_id() {
		return l_id;
	}

	
	// to join with scene bulider
	@FXML private Label l_id;
    @FXML private Label l_ndep;
    @FXML private Label l_loca;
    
    @FXML private TableView<Dep> tdep;
    
    @FXML private TableColumn<Dep,Integer> t1;
    @FXML private TableColumn<Dep,String> t2;
    @FXML private TableColumn<Dep,Integer> t3;//manager
    @FXML private TableColumn<Dep,String> t4; //location
    @FXML private TableColumn<Dep,String> t5; // start manage
    
    @FXML private Label l_mana;
    @FXML private Label l_startman;
    @FXML private Label l_numberE;
    
    @FXML private TextField txt_numberD;
    @FXML private TextField txt_ND;
    @FXML private TextField txt_ID_MAna;
    @FXML private TextField txt_loc;
    @FXML private TextField txt_DateM;
    @FXML private Button back_emp;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {// when initalize this page
		data = new ArrayList<Dep>();
		try {
			getTOTable();// get data from data base 
			datatable = FXCollections.observableArrayList(data);// convert data to Observable to allow to modify
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		// match tables columns with columns in data base and make values editable
		t1.setCellValueFactory(new PropertyValueFactory<Dep,Integer>("dnumber"));
		
		t2.setCellValueFactory(new PropertyValueFactory<Dep, String>("named"));
		t2.setCellFactory(TextFieldTableCell.<Dep>forTableColumn());
		 t2.setOnEditCommit(        
	        		(CellEditEvent<Dep, String> t) -> {
	                       ((Dep) t.getTableView().getItems().get(
	        	                        t.getTablePosition().getRow())
	        	                        ).setNamed(t.getNewValue()); //display only
	                       // updata in Data Base   
	                       updateNameDepartment( t.getRowValue().getDnumber(),t.getNewValue());
	        		});
		
		 t3.setCellValueFactory(new PropertyValueFactory<Dep, Integer>("eid"));
			t3.setCellFactory(TextFieldTableCell.<Dep,Integer>
			forTableColumn(new IntegerStringConverter()));
			t3.setOnEditCommit(        
	        		(CellEditEvent<Dep, Integer> t) -> {
	                       ((Dep) t.getTableView().getItems().get(
	        	                        t.getTablePosition().getRow())
	        	                        ).setEid(t.getNewValue());
	                       // updata in Data Base   
	                       updateManager( t.getRowValue().getDnumber(),t.getNewValue());
	        		});
		
			t4.setCellValueFactory(new PropertyValueFactory<Dep, String>("location"));
			t4.setCellFactory(TextFieldTableCell.<Dep>forTableColumn());
			 t4.setOnEditCommit(        
		        		(CellEditEvent<Dep, String> t) -> {
		                       ((Dep) t.getTableView().getItems().get(
		        	                        t.getTablePosition().getRow())
		        	                        ).setLocation(t.getNewValue()); //display only
		                       // updata in Data Base   
		                       updateLocation( t.getRowValue().getDnumber(),t.getNewValue());
		        		});	
			
			 t5.setCellValueFactory(new PropertyValueFactory<Dep, String>("startdate"));
				t5.setCellFactory(TextFieldTableCell.<Dep>forTableColumn());
				 t5.setOnEditCommit(        
			        		(CellEditEvent<Dep, String> t) -> {
			                       ((Dep) t.getTableView().getItems().get(
			        	                        t.getTablePosition().getRow())
			        	                        ).setStartdate(t.getNewValue()); //display only
			                       // updata in Data Base   
			         updateStartMana( t.getRowValue().getDnumber(),t.getNewValue());
			        		});
				 
				// show data in table 
				 tdep.setItems(datatable);
				 
				 
				// when click row in table Department then should display 
				// Number of Employees in this selected Department
				 tdep.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						try {
							 String SQL;
								
							   	connectDB();
							   	System.out.println("Labels established");

							   	SQL = "select count(*) from  employee_w e ,department d  where e.dnumber =d.dnumber and d.dnumber="+tdep.getSelectionModel().getSelectedItem().getDnumber();
							   	Statement stmt = con.createStatement();
							   	ResultSet rs = stmt.executeQuery(SQL);


							   	if ( rs.next() ) 
							   		l_numberE.setText(String.valueOf(rs.getString(1)));			
							   
							   	rs.close();
							   	stmt.close();
							   	
							   	con.close();
						}catch (SQLException e) {
							e.printStackTrace();
						}catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						
					}// end handle
					 
	 
				 }); // end function setOnMouseClicked
				 
			
				 
		
		
	}// end initialize
	
	
	// function add department in table and Data base
	  @FXML public void adddep(ActionEvent event) {
		  Dep depa; // class with information of table columns in table 
		  depa = new Dep(
                  Integer.valueOf(txt_numberD.getText().toString()),
                  txt_ND.getText().toString(),
                  txt_loc.getText().toString(),
                  Integer.valueOf(txt_ID_MAna.getText().toString()),
          		txt_DateM.getText().toString());
      	
      	datatable.add(depa);// insert in table view
      	
      	insertData(depa); // insert in Data Base 
      	
      	txt_numberD.clear();
      	txt_ID_MAna.clear();
      	txt_loc.clear();
      	txt_ND.clear();
      	txt_DateM.clear();
	    }// end function adddep
	  

	// function Delete department in table and Data base
	    @FXML public void deledep(ActionEvent event) {
	   	 ObservableList<Dep> selectedRows = tdep.getSelectionModel().getSelectedItems();
	      	ArrayList<Dep> rows = new ArrayList<>(selectedRows);
	      	rows.forEach(row -> {
	      		tdep.getItems().remove(row); 
	      		deleteRow(row); 
	      		tdep.refresh();
	      		});   
	     
	    }

	 // function Refresh department in table and Data base
	    @FXML public void refresh(ActionEvent event) {
	    	tdep.refresh();
	    }
	
	
	
	 // function update NameDepartment for Department selected 
	 public void updateNameDepartment(int dnumber, String name) {
			
			try {
				System.out.println("update department d  set d.named = '"+name + "' where d.dnumber = "+dnumber);
				connectDB();
				ExecuteStatement("update department d  set d.named ='"+name + "' where d.dnumber= "+dnumber+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					
				}
		}// end function NameDepartment
	 
	 
	 // function update NameDepartment for Department selected 
	 public void updateManager(int dnumber, int eid) {
			
			try {
				System.out.println("update department d  set d.eid = '"+eid+ "' where d.dnumber = "+dnumber);
				connectDB();
				ExecuteStatement("update department d  set d.eid ='"+eid + "' where d.dnumber= "+dnumber+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					
				}
		}// end function Manager
	 
	 // function update LocationDepartment for Department selected 
	 public void updateLocation(int dnumber, String loc) {
			
			try {
				System.out.println("update department d  set d.location = '"+ loc + "' where d.dnumber = "+dnumber);
				connectDB();
				ExecuteStatement("update department d  set d.location ='"+ loc + "' where d.dnumber= "+dnumber+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					
				}
		}// end function Location
	 
	 // function update StartMana for Department selected 
	 public void updateStartMana(int dnumber, String date) {
			
			try {
				System.out.println("update department d  set d.startdate = '"+ date + "' where d.dnumber = "+dnumber);
				connectDB();
				ExecuteStatement("update department d  set d.startdate ='"+ date + "' where d.dnumber= "+dnumber+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		}// end function StartMana
	 
	 
	 // function to execute in data base
	 public void ExecuteStatement(String SQL) throws SQLException {

			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(SQL);
				stmt.close();
			
				 
			}
			catch(SQLException s) {
				s.printStackTrace();
				  
			}	
		}// end function ExecuteStatement
	
	
	 // get Data from data base to put them in Table View
	private void getTOTable() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();

		SQL = "select * from department ";
		Statement stmt = con.createStatement();
		ResultSet da = stmt.executeQuery(SQL);


		while ( da.next() ) 
			data.add(new Dep(
					Integer.parseInt(da.getString(1)),
					da.getString(2),
					da.getString(3),
					Integer.parseInt(da.getString(4)),
					da.getString(5)));
		
		da.close();
		stmt.close();

		con.close();
	
			
	}//end getTOTable
	
	
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

	
	
	
@FXML // function to go back page
public void backtoemp(ActionEvent event) {
	Stage window=(Stage) back_emp.getScene().getWindow();
	try {
	     FXMLLoader loader=new FXMLLoader(getClass().getResource("Infofemp.fxml"));
	        Parent root2 = (Parent) loader.load();
	       
	        	 Scene sc=new Scene(root2);
	        	 window.setScene(sc);
	        	 window.show();
	        	}catch(Exception e) {
	        	System.out.println("Theprogram fail to load layout");
	        	}
}	// end function backtoemp


// function to insert into tables in data base 
private void insertData(Dep rc) {
	
	try {
			System.out.println(" insert into department (dnumber,named,location,eid,startdate) values ("+rc.getDnumber()+",'"+rc.getNamed()+"','"+ rc.getLocation() +"',"+ rc.getEid()+",'"+rc.getStartdate()+"');");
			
			connectDB();
			ExecuteStatement(" insert into department (dnumber,named,location,eid,startdate) values ("+rc.getDnumber()+",'"+rc.getNamed()+"','"+ rc.getLocation() +"',"+ rc.getEid()+",'"+rc.getStartdate()+"');");
			con.close();
			System.out.println("Connection closed" + data.size());
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}// end function

// function to delete row in Data Base
private void deleteRow(Dep dep) {
	
	try {
		System.out.println("  delete from department d where d.dnumber="+dep.getDnumber() + ";");
		connectDB();
		ExecuteStatement(" delete from department d where d.dnumber="+dep.getDnumber() + ";");
		con.close();
		System.out.println("Connection closed");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
}// end function deleteRow





}// end class
