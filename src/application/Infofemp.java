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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

public class Infofemp implements Initializable  {
	
	 ArrayList<Emp> data;
	   ObservableList<Emp> datatable;
	   
	   // variables to join with data base 
	    private String dbURL;
		private String dbUsername = "root";
		private String dbPassword = "bbb123";
		private String URL = "127.0.0.1";
		private String port = "3306";
		private String dbName = "student_project";
		private Connection con;
		
		
		// to join with scene bulider
	  @FXML private TableView<Emp> temp;
	  
	  @FXML private TableColumn<Emp, Integer> eid;
	    @FXML private TableColumn<Emp,String> ename;
	    @FXML private TableColumn<Emp,String> specialization;
	    @FXML private TableColumn<Emp,String> address;
	    @FXML private TableColumn<Emp,String> birthdate;
	    @FXML private TableColumn<Emp, Integer> evaluation;
	    @FXML private TableColumn<Emp,String> gender;
	    @FXML private TableColumn<Emp, Integer> nofexperiancess;
	    @FXML private TableColumn<Emp, Integer> salary;
	    @FXML private TableColumn<Emp, Integer> taxvalue;
	    @FXML private TableColumn<Emp,String> dayoffice;
	    @FXML private TableColumn<Emp, Integer> dnumber;
	    @FXML private TableColumn<Emp,String> phone1;
	    @FXML private TableColumn<Emp,String> phone2;
	    
	    @FXML private TextField txt_search;	    
	    @FXML private TextField txt_child; 
	    @FXML private TextField p1;
	    @FXML private TextField p2;
	    @FXML private TextField txt_name;
	    @FXML private TextField text_id;
	    @FXML private TextField txt_sp;
	    @FXML private TextField txt_address;
	    @FXML private TextField txt_bdate;
	    @FXML private TextField txt_gender;
	    @FXML private TextField txt_eva;
	    @FXML private TextField txt_exper;
	    @FXML private TextField txt_sal;
	    @FXML private TextField txt_tax;
	    @FXML private TextField txt_doff;
	    @FXML private TextField txt_nofdep;
	    @FXML private TextField txt_moseM;

	    
	    @FXML private Button btn_demp;
	    @FXML private Button btn_tod;
	    
	    @FXML private Button btnbac;
	    @FXML private Label eeror;


	

	@Override // start initialize
	public void initialize(URL location, ResourceBundle resources) {
		data = new ArrayList<Emp>(); 
		
		try {
			getTOTable(); // get data from data base 
			
			// convert data to Observable to allow to modify
			datatable = FXCollections.observableArrayList(data);
			
		}catch(Exception e) {
			eeror.setVisible(true);
			System.out.println("fail to get Data");
		}
		
// match tables columns with columns in data base and make values editable
		eid.setCellValueFactory(new PropertyValueFactory<Emp,Integer>("eid"));
		
		ename.setCellValueFactory(new PropertyValueFactory<Emp, String>("ename"));
		ename.setCellFactory(TextFieldTableCell.<Emp>forTableColumn());
		ename.setOnEditCommit(        
        		(CellEditEvent<Emp, String> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setEname(t.getNewValue()); //display only
                    // updata in Data Base    
                 updateEName( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		specialization.setCellValueFactory(new PropertyValueFactory<Emp, String>("specialization"));
		specialization.setCellFactory(TextFieldTableCell.<Emp>forTableColumn());
		specialization.setOnEditCommit(        
        		(CellEditEvent<Emp, String> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setSpecialization(t.getNewValue());; //display only
        	                        // updata in Data Base 
                updateSpecial( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		address.setCellValueFactory(new PropertyValueFactory<Emp, String>("address"));
		address.setCellFactory(TextFieldTableCell.<Emp>forTableColumn());
		address.setOnEditCommit(        
        		(CellEditEvent<Emp, String> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setAddress(t.getNewValue());; //display only
        	                        // updata in Data Base      
                updateAddress( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		birthdate.setCellValueFactory(new PropertyValueFactory<Emp, String>("birthdate"));
		birthdate.setCellFactory(TextFieldTableCell.<Emp>forTableColumn());
		
		evaluation.setCellValueFactory(new PropertyValueFactory<Emp, Integer>("evaluation"));
		evaluation.setCellFactory(TextFieldTableCell.<Emp,Integer>
		forTableColumn(new IntegerStringConverter()));
		evaluation.setOnEditCommit(        
        		(CellEditEvent<Emp, Integer> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setEvaluation(t.getNewValue());; //display only
        	                        // updata in Data Base 
                updateEVA( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		
		gender.setCellValueFactory(new PropertyValueFactory<Emp, String>("gender"));
		gender.setCellFactory(TextFieldTableCell.<Emp>forTableColumn());
		
		
		nofexperiancess.setCellValueFactory(new PropertyValueFactory<Emp, Integer>("nofexperiancess"));
		nofexperiancess.setCellFactory(TextFieldTableCell.<Emp,Integer>
		forTableColumn(new IntegerStringConverter()));
		nofexperiancess.setOnEditCommit(        
        		(CellEditEvent<Emp, Integer> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setNofexperiancess(t.getNewValue());; //display only
        	                        // updata in Data Base    
                updateNOFEXP( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		salary.setCellValueFactory(new PropertyValueFactory<Emp, Integer>("salary"));
		salary.setCellFactory(TextFieldTableCell.<Emp,Integer>
		forTableColumn(new IntegerStringConverter()));
		salary.setOnEditCommit(        
        		(CellEditEvent<Emp, Integer> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setSalary(t.getNewValue());; //display only
        	                        // updata in Data Base    
                updateSalary( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		taxvalue.setCellValueFactory(new PropertyValueFactory<Emp, Integer>("taxvalue"));
		taxvalue.setCellFactory(TextFieldTableCell.<Emp,Integer>
		forTableColumn(new IntegerStringConverter()));
		taxvalue.setOnEditCommit(        
        		(CellEditEvent<Emp, Integer> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setTaxvalue(t.getNewValue());; //display only
        	                        // updata in Data Base  
                updateTax( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		
		dayoffice.setCellValueFactory(new PropertyValueFactory<Emp, String>("dayoffice"));
		dayoffice.setCellFactory(TextFieldTableCell.<Emp>forTableColumn());
		dayoffice.setOnEditCommit(        
        		(CellEditEvent<Emp, String> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setDayoffice(t.getNewValue()); //display only
                       // updata in Data Base   
                 updateDayOff( t.getRowValue().getEid(),t.getNewValue());
        		});
		  
		dnumber.setCellValueFactory(new PropertyValueFactory<Emp, Integer>("dnumber"));
		dnumber.setCellFactory(TextFieldTableCell.<Emp,Integer>
		forTableColumn(new IntegerStringConverter()));
		dnumber.setOnEditCommit(        
        		(CellEditEvent<Emp, Integer> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setDnumber(t.getNewValue());; //display only
        	                        // updata in Data Base     
                updateND( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		
		phone1.setCellValueFactory(new PropertyValueFactory<Emp, String>("phone1"));
		phone1.setCellFactory(TextFieldTableCell.<Emp>forTableColumn());
		phone1.setOnEditCommit(        
        		(CellEditEvent<Emp, String> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setPhone1(t.getNewValue()); //display only
                       // updata in Data Base  
                 updatePhone1( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		
		phone2.setCellValueFactory(new PropertyValueFactory<Emp, String>("phone2"));
		phone2.setCellFactory(TextFieldTableCell.<Emp>forTableColumn());
		phone2.setOnEditCommit(        
        		(CellEditEvent<Emp, String> t) -> {
                       ((Emp) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setPhone2(t.getNewValue()); //display only
                       // updata in Data Base   
                 updatePhone2( t.getRowValue().getEid(),t.getNewValue());
        		});
		
		
		// filter data in table view to easy search
		FilteredList<Emp>  filterdata=new FilteredList<>(datatable,b-> true);
		
		txt_search.textProperty().addListener((observable,oldValue,newValue)->{
			
		filterdata.setPredicate(Emp -> {
			
			if (newValue == null || newValue.isEmpty() )
				return true;// all data in table
			
			String lcase=newValue.toLowerCase();
			
			// search with Name And Id 
			if(Emp.getEname().toLowerCase().indexOf(lcase) != -1)
				return true;
			else if (String.valueOf(Emp.getEid()).indexOf(lcase) != -1)
				return true ;
				else 
					return false;
			
			
		});
				
		});
		
		SortedList<Emp> sort=new SortedList<>(filterdata);
		sort.comparatorProperty().bind(temp.comparatorProperty());
		
		
		temp.setItems(sort);
		
		
		btn_demp.setOnAction( (ActionEvent e) -> {
			temp.setItems(datatable);
			                                   // select the row in table view to delete 
			ObservableList<Emp> selectedRows = temp.getSelectionModel().getSelectedItems();
			   	ArrayList<Emp> rows = new ArrayList<>(selectedRows);
			   	rows.forEach(row -> {
			   		temp.getItems().remove(temp.getSelectionModel().getSelectedItem()); 
			   		deleteRow(row); 
			   		temp.refresh();
			   	});
			   	
			temp.setItems(sort);// show data in table 
			   	});
		
	 
		
		// when click row in table view then should display data in text fields 
		temp.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
			
			text_id.setText(String.valueOf(temp.getSelectionModel().getSelectedItem().getEid()));
			txt_name.setText(temp.getSelectionModel().getSelectedItem().getEname());
			txt_address.setText(temp.getSelectionModel().getSelectedItem().getAddress());
			txt_sp.setText(temp.getSelectionModel().getSelectedItem().getSpecialization());
			txt_bdate.setText(temp.getSelectionModel().getSelectedItem().getBirthdate());
			txt_doff.setText(temp.getSelectionModel().getSelectedItem().getDayoffice());
			txt_sal.setText(String.valueOf(temp.getSelectionModel().getSelectedItem().getSalary()));
			txt_eva.setText(String.valueOf(temp.getSelectionModel().getSelectedItem().getEvaluation()));
			txt_tax.setText(String.valueOf(temp.getSelectionModel().getSelectedItem().getTaxvalue()));
			txt_gender.setText(temp.getSelectionModel().getSelectedItem().getGender());
			txt_nofdep.setText(String.valueOf(temp.getSelectionModel().getSelectedItem().getDnumber()));
			txt_exper.setText(String.valueOf(temp.getSelectionModel().getSelectedItem().getNofexperiancess()));
			p1.setText(temp.getSelectionModel().getSelectedItem().getPhone1());
			p2.setText(temp.getSelectionModel().getSelectedItem().getPhone2());
			
			try {
			txt_child.setText(String.valueOf(getChildren(temp.getSelectionModel().getSelectedItem().getEid())));
				
			}catch (Exception e) {
				System.out.println("error");
				eeror.setVisible(true);
			
			}
			
			
			
			}
		});

		
		
	} // end intialize 
	
	
	@FXML
   public void AddE(ActionEvent event) {
		Emp addition = new Emp(
				Integer.valueOf(text_id.getText().toString()),
				txt_name.getText().toString(),
				txt_sp.getText().toString(),
				txt_address.getText().toString(),
				txt_bdate.getText().toString(),
				Integer.valueOf(txt_eva.getText().toString()),
				txt_gender.getText().toString(),
				Integer.valueOf(txt_exper.getText().toString()),
				Integer.valueOf(txt_sal.getText().toString()),
				Integer.valueOf(txt_tax.getText().toString()),
				txt_doff.getText().toString(),
				Integer.valueOf(txt_nofdep.getText().toString()),
				p1.getText().toString(),
				p2.getText().toString()
				);
		
		datatable.add(addition); // insert in table view 
		
		insertInDataBAse(addition); // insert Data Base 
		txt_address.clear();
    	txt_bdate.clear();
    	txt_child.clear();
        txt_doff.clear();
        txt_eva.clear();
        txt_exper.clear();
        txt_gender.clear();
        txt_name.clear();
        txt_nofdep.clear();
        txt_sal.clear();
        txt_sp.clear();
        txt_tax.clear();
        p1.clear();
        p2.clear();
        text_id.clear();
		
    } // end function AddE
	

    @FXML
   public void ClearTs(ActionEvent event) {
    	txt_address.clear();
    	txt_bdate.clear();
    	txt_child.clear();
        txt_doff.clear();
        txt_eva.clear();
        txt_exper.clear();
        txt_gender.clear();
        txt_name.clear();
        txt_nofdep.clear();
        txt_sal.clear();
        txt_sp.clear();
        txt_tax.clear();
        p1.clear();
        p2.clear();
        text_id.clear();

    }// end function ClearTs

     
    @FXML// function update children for employee selected 
    public void updateChildren(ActionEvent event) {
    	try {
    		connectDB();
    		ExecuteINDATANASE("update dependents_p p set p.Children= "+Integer.valueOf(txt_child.getText().toString())+" where p.eid = " +Integer.valueOf(text_id.getText().toString())+";");
    		con.close();
    	
    	}catch (Exception e) {
    		eeror.setVisible(true);
    		e.printStackTrace();
    	}

    }// end function updateChildren
   
    
	// function to insert into tables in data base 
    private void insertInDataBAse(Emp neww) {
		
		try {
				System.out.println("insert into employee_w values("+neww.getEid()+",'"+neww.getEname()+"','"+ neww.getSpecialization() +"' ,'"+ neww.getAddress()+"','"+neww.getBirthdate()+"',"+ neww.getEvaluation()+",'"+neww.getGender()+"',"+neww.getNofexperiancess()+","+ neww.getSalary()+","+ neww.getTaxvalue()+",'"+ neww.getDayoffice()+"',"+ neww.getDnumber()+",')");
				connectDB();
				ExecuteINDATANASE("insert into employee_w (eid,ename,specialization,address,birthdate,evaluation ,gender ,nofexperiancess ,salary ,taxvalue ,dayoffice,dnumber) values("+neww.getEid()+",'"+neww.getEname()+"','"+ neww.getSpecialization() +"','"+ neww.getAddress()+"','"+neww.getBirthdate()+"',"+ neww.getEvaluation()+",'"+neww.getGender()+"',"+neww.getNofexperiancess()+","+ neww.getSalary()+","+ neww.getTaxvalue()+",'"+ neww.getDayoffice()+"',"+ neww.getDnumber()+");");
				ExecuteINDATANASE("insert into phones (eid,phone1,phone2) values ("+neww.getEid()+",'"+neww.getPhone1()+"','"+neww.getPhone2()+"');");
				ExecuteINDATANASE("insert into dependents_p(eid,Children) values ("+neww.getEid()+","+Integer.valueOf(txt_child.getText().toString())+");");
				con.close();
				System.out.println("Connection closed" + data.size());
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
		}// end function insertInDataBAse
	
	
    // function to delete row in data base 
	private void deleteRow(Emp empo) {
		// TODO Auto-generated method stub
		
		try {
			System.out.println(" delete from employee_w e where e.eid="+empo.getEid() + ";");
			connectDB();
			ExecuteINDATANASE(" delete from employee_w e where e.eid="+empo.getEid() + ";");
			con.close();
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
				eeror.setVisible(true);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				eeror.setVisible(true);
			}
	} // end function deleteRow
	
	
	
	// function to Execute Query update
	public void ExecuteINDATANASE(String SQL) throws SQLException {

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close(); 
		}
		catch(SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");
			eeror.setVisible(true);
		}
	}// end function ExecuteINDATANASE
	
	 
	// function to update name in data base 
	 public void updateEName(int eid, String ename) {
			
			try {
				System.out.println("update employee_w e set e.ename = '"+ename + "' where e.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update employee_w e set e.ename = '"+ename + "' where e.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
		}// end function updateEName 
	
	 
	// function to update Special in data base 
	 public void updateSpecial(int eid, String specialization) {
			
			try {
				System.out.println("update employee_w e set e.specialization = '"+ specialization+ "' where e.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update employee_w e set e.specialization = '"+specialization + "' where e.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
		}// end function updateSpecial
	 
	 
	// function to update Address in data base 
	 public void updateAddress(int eid, String address) {
			
			try {
				System.out.println("update employee_w e set e.address = '"+ address+ "' where e.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update employee_w e set e.address = '"+address + "' where e.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
	}// end function updateAddress
	 
	 
	// function to update DayOff in data base 
	 public void updateDayOff(int eid, String dayoff) {
			
			try {
				System.out.println("update employee_w e set e.dayoffice = '"+ dayoff+ "' where e.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update employee_w e set e.dayoffice = '"+dayoff + "' where e.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
	}// end function updateDayOff
	 
	 
	// function to update Phone1 in data base 
	 public void updatePhone1(int eid, String ph1) {
			
			try {
				System.out.println("update phones p set p.phone1  = '"+ ph1+ "' where p.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update phones p set p.phone1 = '"+ph1 + "' where p.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
	}// end function updatePhone1
	 
	 
	// function to update Phone2 in data base
	 public void updatePhone2(int eid, String ph2) {
			
			try {
				System.out.println("update phones p set p.phone2 = '"+ ph2+ "' where p.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update phones p set p.phone2 = '"+ph2 + "' where p.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	}// end function updatePhone2
	 
	// function to update Salary in data base
	 public void updateSalary(int eid, int sal) {
			
			try {
				System.out.println("update employee_w e set e.salary = '"+ sal+ "' where e.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update employee_w e set e.salary = '"+sal + "' where e.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	}// end function updateSalary
	 
	 
	// function to update EVA in data base
	 public void updateEVA(int eid, int eva) {
			
			try {
				System.out.println("update employee_w e set e.evaluation = '"+ eva+ "' where e.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update employee_w e set e.evaluation = '"+eva + "' where e.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
	}// end function updateEVA
	 
	// function to update NOFEXP in data base
	 public void updateNOFEXP(int eid, int exp) {
			
			try {
				System.out.println("update employee_w e set e.nofexperiancess = '"+ exp+ "' where e.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update employee_w e set e.nofexperiancess = '"+exp + "' where e.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
	}// end function updateNOFEXP
	 
	 
	// function to update Tax in data base
	 public void updateTax(int eid, int tax) {
			
			try {
				System.out.println("update employee_w e set e.taxvalue = '"+ tax+ "' where e.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update employee_w e set e.taxvalue= '"+tax+ "' where e.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
	}// end function updateTax
	 
	// function to update ND in data base
	 public void updateND(int eid, int nd) {
			
			try {
				System.out.println("update employee_w e set e.dnumber = '"+ nd+ "' where e.eid= "+eid);
				connectDB();
				ExecuteINDATANASE(" update employee_w e set e.dnumber = '"+nd + "' where e.eid= "+eid+";");
				con.close();
				System.out.println("Connection closed");
				
				} catch (SQLException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					eeror.setVisible(true);
				}
	}// end function updateND
	 
	 
	 // function to get children employee in data base and show in text field
	 private int getChildren(int eid) throws SQLException, ClassNotFoundException {
			// TODO Auto-generated method stub
			
			String SQL;	
			connectDB();
		

			SQL = " select p.Children from dependents_p  p where p.eid = "+eid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			
			int child=0;
			
			while(rs.next())
			child=Integer.parseInt(rs.getString(1));	
		

			con.close();
			
			return child;
				
		}//end getChildren
	

	
	// function to get Data base to put in table view
	private void getTOTable() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String SQL;
				
		connectDB();
		
		System.out.println("Connection established");

		SQL = " select e.eid,e.ename,e.specialization,e.address,e.birthdate,e.evaluation,e.gender,e.nofexperiancess,e.salary,e.taxvalue,e.dayoffice,e.dnumber,p.phone1,p.phone2 from employee_w e,phones p where e.eid=p.eid ";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		


		while ( rs.next() ) 
			data.add(new Emp(
					Integer.parseInt(rs.getString(1)),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					Integer.parseInt(rs.getString(6)),
					rs.getString(7),
					Integer.parseInt(rs.getString(8)),
					Integer.parseInt(rs.getString(9)),
					Integer.parseInt(rs.getString(10)),
					rs.getString(11),
					Integer.parseInt(rs.getString(12)),
					rs.getString(13),
					rs.getString(14)));
		
		rs.close();
		stmt.close();
		
	

		con.close();
		System.out.println("Connection closed" + data.size());
			
	}//end getTOTable()
	
	
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

	
@FXML // function to go to Home page
public void backtohome(ActionEvent event) {
	Stage window=(Stage) btnbac.getScene().getWindow();
	try {
	     FXMLLoader loader=new FXMLLoader(getClass().getResource("homepage.fxml"));
	        Parent root2 = (Parent) loader.load();
	       
	        	 Scene sc=new Scene(root2);
	        	 
	        	 window.setScene(sc);
	        	 window.show();
	        	}catch(Exception e) {
	        	eeror.setVisible(true);
	        	eeror.setText("The program fail to load layout");;
	        	}
	    		

}

//function to move to Department Page
@FXML public void btn_todepar(ActionEvent event) {
	int n1=0;
	int n2=0;
	int n=1;
    String temp="";
    
    if ( ! txt_name.getText().isEmpty()) {
	
    	int sn=Integer.valueOf(text_id.getText().toString());
	
	Stage window=(Stage) btn_tod.getScene().getWindow();
	try {
	     FXMLLoader loader=new FXMLLoader(getClass().getResource("department.fxml"));
	        Parent root2 = (Parent) loader.load();
	        
	        // pass Data between Controllers 
	        department controller=loader.getController();
	        controller.getL_name().setText(txt_name.getText().toString());
	        controller.getL_id().setText(text_id.getText().toString());
	       
	       String SQL;
			
	   	connectDB();
	   	System.out.println("Labels established");

	   	SQL = " select e.eid,d.named,d.location,d.eid AS Manager , d.startdate  from employee_w e,department d where e.dnumber=d.dnumber and e.eid="+sn;
	   	Statement stmt = con.createStatement();
	   	ResultSet rs = stmt.executeQuery(SQL);


	   	while ( rs.next() ) {
	   		n1=Integer.valueOf(rs.getString(1));
	   	controller.getL_ndep().setText(rs.getString(2));
	   	controller.getL_loca().setText(rs.getString(3));
	   	n2=Integer.valueOf(rs.getString(4));
	   	temp=rs.getString(5);
	
	}	// end while loop
	   	if(n1 == n2) {
	   	controller.getL_mana().setText(controller.getL_ndep().getText().toString()+" Department");
   		controller.getL_startman().setText(temp);
	   	}
	   	rs.close();
	   	stmt.close();
	   	
	   	con.close();
	   	System.out.println("Connection closed labeles");
	        
	        	 Scene sc=new Scene(root2);
	        	 
	        	 window.setScene(sc);
	        	 window.show();
	        	}catch(Exception e) {
	        	System.out.println("Theprogram fail to load layout");
	        	}
    }//end if 
    
    else {
    	Stage window=(Stage) btn_tod.getScene().getWindow();
    	try {
    	     FXMLLoader loader=new FXMLLoader(getClass().getResource("department.fxml"));
    	        Parent root2 = (Parent) loader.load();
    	       
    	        	 Scene sc=new Scene(root2);
    	        	 
    	        	 window.setScene(sc);
    	        	 window.show();
    	        	}catch(Exception e) {
    	        	System.out.println("Theprogram fail to load layout");
    	        	}
    }//end else
	
	
	

}// end function to move to page Departments


@FXML // function to find employee who have highest number of experiance 
public void moeM(ActionEvent event) {
	try {
		 String SQL;
			
		   	connectDB();
		   	System.out.println("Labels established");

		   	SQL = "select max(e.nofexperiancess),e.ename from employee_w e";
		   	Statement st = con.createStatement();
		   	ResultSet da = st.executeQuery(SQL);


		   	if ( da.next() ) 
		   		txt_moseM.setText(String.valueOf(da.getString(2)));			
		   
		   	da.close();
		   	st.close();
		   	con.close();
		   	
	}catch (SQLException e) {
		e.printStackTrace();
	}catch (ClassNotFoundException e) {
		e.printStackTrace();
	}

}// end function moeM



	
}// end class Infofemp
