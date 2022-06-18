package application;

import java.sql.*;
import javafx.scene.control.TextField;
import static javafx.stage.Modality.NONE;
import java.net.URL;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Fclients implements Initializable {
	ArrayList<clients> data;
	   ObservableList<clients> dataList;
	   
private String dbURL;
		private String dbUsername = "root";
		private String dbPassword = "bbb123";
		private String URL = "127.0.0.1";
		private String port = "3306";
		private String dbName = "student_project";
		private Connection con;
		
		 @FXML private TableView<clients> ctable;
		 @FXML private TableColumn<clients, Integer> cid;
		 @FXML private TableColumn<clients, String> cname;
		 @FXML private TableColumn<clients, Integer> cbill;
		 @FXML private TextField Tsearch;
		 @FXML private Label sbox;
		 @FXML private TextField Tcid;
		 @FXML private TextField Tcname;
         @FXML private TextField Tcbill;
         @FXML private Button cadd;
         @FXML private Button cdelete;
         @FXML private Button Cback;
         
         @FXML private TextField txt_cid;
         @FXML private TextField txt_pnum;
         @FXML private TextField txt_des;
         @FXML private TextField txt_type;
         
         @FXML private Button btn_n;
         @FXML private TextField txt_n;
         @FXML private Button btn_t;
         @FXML private TextField txt_t;
         @FXML private Button btn_c;
         @FXML private TextField txt_c;


         


		 
		
private void getData() throws SQLException, ClassNotFoundException {
				// TODO Auto-generated method stub
				
				String SQL;
						
				connectDB();
				System.out.println("Connection established");

				SQL = "select * from clients";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);


				while ( rs.next() ) 
					data.add(new clients(
							Integer.parseInt(rs.getString(1)),
							rs.getString(2),
							Integer.parseInt(rs.getString(3))));
				
				rs.close();
				stmt.close();
				
			

				con.close();
				System.out.println("Connection closed" + data.size());
					
			} //end getdata

	 
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
public void initialize(URL arg0, ResourceBundle arg1) {
		data = new ArrayList<clients>();
		try {
			getData();
			
			dataList = FXCollections.observableArrayList(data);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		cid.setCellValueFactory(new PropertyValueFactory<clients,Integer>("cid"));
		cname.setCellValueFactory(new PropertyValueFactory<clients,String>("cname"));
		cbill.setCellValueFactory(new PropertyValueFactory<clients,Integer>("bill"));
		ctable.setItems(dataList);
		 ///////////////////////////////////////////////////////////////////////////////
		 cid.setCellFactory(TextFieldTableCell.<clients,Integer>
		 forTableColumn(new IntegerStringConverter()));
		 ////////////////////////////////////////////////////////////////
		 cname.setCellFactory(TextFieldTableCell.<clients>forTableColumn());
		 cname.setOnEditCommit(        
	        		(CellEditEvent<clients,String> t) -> {
	                       ((clients) t.getTableView().getItems().get(
	        	                        t.getTablePosition().getRow())
	        	                        ).setCname(t.getNewValue()); //display only
	                       
	                     updateCname( t.getRowValue().getCid(),t.getNewValue());
	        		});
		 ///////////////////////////////////////////////////////////////////////////////
		 cbill.setCellFactory(TextFieldTableCell.<clients,Integer>
		 forTableColumn(new IntegerStringConverter()));
		 cbill.setOnEditCommit(        
	        		(CellEditEvent<clients, Integer> t) -> {
	                       ((clients) t.getTableView().getItems().get(
	        	                        t.getTablePosition().getRow())
	        	                        ).setBill(t.getNewValue());
	                       
	                 updateCbill( t.getRowValue().getCid(),t.getNewValue());
	        		});
		 ////////////////////////////////search///////////////////////////////////////////
		 FilteredList <clients>  filterdata = new FilteredList<>(dataList,b-> true);
		 Tsearch.textProperty().addListener((observable,oldValue,newValue)->{
				
				filterdata.setPredicate(clients -> {
					
					if (newValue == null || newValue.isEmpty() )
						return true;
					
					String lcase = newValue.toLowerCase();
					
					if(clients.getCname().toLowerCase().indexOf(lcase) != -1)
						return true;
					else if (String.valueOf(clients.getCid()).indexOf(lcase) != -1)
						return true ;
						else 
							return false;
				});	
				});
		    SortedList<clients> sort = new SortedList<>(filterdata);
			sort.comparatorProperty().bind(ctable.comparatorProperty());
			
			
			ctable.setItems(sort);
		 ////////////////////////////////////add///////////////////////////////////
			try {
			       
				 cadd.setOnAction((ActionEvent e) -> {
			            clients rc;
			        	rc = new clients(
			                    Integer.valueOf(Tcid.getText()),
			                    Tcname.getText(),
			                    Integer.valueOf(Tcbill.getText()));
			            		
			        	dataList.add(rc);
			        	
			        	insertData(rc);
			        	Tcid.clear();
			        	Tcname.clear();
			        	Tcbill.clear();
			  
			        });
		       }catch(Exception e) {
		    	   
		       }
		 ///////////////////////////////delete//////////////////////////////////////////////
			
			

		      
		 ////////////////////////////////problems///////////////////////////
			ctable.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
				
				txt_cid.setText(String.valueOf(ctable.getSelectionModel().getSelectedItem().getCid()));
				
				
				try {
				txt_pnum.setText(String.valueOf(getPnum(ctable.getSelectionModel().getSelectedItem().getCid())));
				txt_des.setText(getPdes(ctable.getSelectionModel().getSelectedItem().getCid()));
				txt_type.setText(getPtype(ctable.getSelectionModel().getSelectedItem().getCid()));
					
				}catch (Exception e) {
					System.out.println("error");
				
				}
				
				
				
				}
			});
			////////////////////////////////////////////////////////////////
			btn_n.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
				
				try {
				txt_n.setText(String.valueOf(getNp()));
				
				}catch (Exception e) {
					System.out.println("error");
				}
				
				}
			});
			////////////////////////////////////////////////////////////////
			btn_t.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
				
				try {
				txt_t.setText(String.valueOf(getTbills()));
				
				}catch (Exception e) {
					System.out.println("error");
				}
				
				}
			});
			/////////////////////////////////////////////////////////////////
			btn_c.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
				
				try {
				txt_c.setText(String.valueOf(getNc()));
				
				}catch (Exception e) {
					System.out.println("error");
				}
				
				}
			});
			///////////////////////////////////////////////////////////////////

			
	}
	
	 @FXML public  void cdel(ActionEvent event) {
		 ObservableList<clients> selectedRows = ctable.getSelectionModel().getSelectedItems();
	      	ArrayList <clients> rows = new ArrayList<>(selectedRows);
	      	rows.forEach(row -> {
	      		ctable.getItems().remove(row); 
	      		 deleteRow(row); 
	      		ctable.refresh();
	      		});   
	    }
	
	
public void ExecuteStatement(String SQL) throws SQLException {

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();
		
			 
		}
		catch(SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");
			  
		}
		
		
	}
	
public void updateCname(int Cid, String Cname) {
		
		try {
			System.out.println("update  product set ware_house = '"+Cname + "' where serial_n = "+Cid);
			connectDB();
			ExecuteStatement("update product set ware_house = '"+Cname + "' where serial_n = "+Cid+";");
			con.close();
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}

public void updateCbill(int Cid, int Cbill) {
	
	try {
		System.out.println("update product set price  = "+Cbill + " where serial_n = "+Cid);
		connectDB();
		ExecuteStatement("update  Student set price = "+Cbill+ " where serial_n= "+Cid+";");
		con.close();
		System.out.println("Connection closed");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
}

private void insertData(clients rc){

	
	try {
			System.out.println("Insert into clients values("+rc.getCid()+",'"+rc.getCname()+"',"+ rc.getBill()+")");
			
			connectDB();
			ExecuteStatement("Insert into clients values("+rc.getCid()+",'"+rc.getCname()+"',"+ rc.getBill()+");");
			con.close();
			System.out.println("Connection closed" + data.size());
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}

private void deleteRow(clients row) {
	// TODO Auto-generated method stub
	
	try {
		System.out.println("delete from  clients where cid="+row.getCid() + ";");
		connectDB();
		ExecuteStatement("delete from  clients where cid= "+row.getCid()+ ";");
		con.close();
		System.out.println("Connection closed");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
}

public void backtohome(ActionEvent event) {
	Stage window=(Stage) Cback.getScene().getWindow();
	try {
	     FXMLLoader loader=new FXMLLoader(getClass().getResource("homepage.fxml"));
	        Parent root2 = (Parent) loader.load();
	       
	        	 Scene sc=new Scene(root2);
	        	 
	        	 window.setScene(sc);
	        	 window.show();
	        	}catch(Exception e) {
	        	System.out.println("Theprogram fail to load layout");
	        	}
	    		

}

private int getPnum(int cid) throws SQLException, ClassNotFoundException {
	// TODO Auto-generated method stub
	
	String SQL;	
	connectDB();
	System.out.println("Connection established");

	SQL = " select p.pnum from problem  p where p.cid = "+cid;
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(SQL);
	
	int pnum=0;
	
	while(rs.next())
	pnum=Integer.parseInt(rs.getString(1));	


	con.close();
	System.out.println("pnum"+pnum);
	
	return pnum;
		
}//end getdata

private String getPdes(int cid) throws SQLException, ClassNotFoundException {
	// TODO Auto-generated method stub
	
	String SQL;	
	connectDB();
	System.out.println("Connection established");

	SQL = " select P.pdescription from problem  p where p.cid = "+cid;
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(SQL);
	
	String pdes = "";
	
	while(rs.next())
	pdes = rs.getString(1);	


	con.close();
	System.out.println("pdescription"+pdes);
	
	return pdes;
		
}//end getdata

private String getPtype(int cid) throws SQLException, ClassNotFoundException {
	// TODO Auto-generated method stub
	
	String SQL;	
	connectDB();
	System.out.println("Connection established");

	SQL = " select P.type_d from problem  p where p.cid = "+cid;
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(SQL);
	
	String ptype = "";
	
	while(rs.next())
	ptype = rs.getString(1);	


	con.close();
	System.out.println("ptype_d"+ptype);
	
	return ptype;
		
}//end getdata

private int getTbills() throws SQLException, ClassNotFoundException {
	// TODO Auto-generated method stub
	
	String SQL;	
	connectDB();
	System.out.println("Connection established");

	SQL = "select sum(C.bill) from clients C";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(SQL);
	
	int Tbill= 0;
	
	while(rs.next())
	Tbill = Integer.parseInt(rs.getString(1));	
 

	con.close();
	System.out.println("Total bills :"+Tbill);
	
	return Tbill;
		
}//end getdata

private int getNp() throws SQLException, ClassNotFoundException {
	// TODO Auto-generated method stub
	
	String SQL;	
	connectDB();
	System.out.println("Connection established");

	SQL = "select count(pnum) from clients C , problem P where C.cid = P.cid";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(SQL);
	
	int Tbill= 0;
	
	while(rs.next())
	Tbill = Integer.parseInt(rs.getString(1));	
 

	con.close();
	System.out.println("Number of problems :"+Tbill);
	
	return Tbill;
		
}//end getdata

private int getNc() throws SQLException, ClassNotFoundException {
	// TODO Auto-generated method stub
	
	String SQL;	
	connectDB();
	System.out.println("Connection established");

	SQL = "select count(C.cid) from clients C";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(SQL);
	
	int Tbill= 0;
	
	while(rs.next())
	Tbill = Integer.parseInt(rs.getString(1));	
 

	con.close();
	System.out.println("Number of clients :"+Tbill);
	
	return Tbill;
		
}//end getdata

}


