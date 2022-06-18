package application;

import static javafx.stage.Modality.NONE;

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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Fproduct implements Initializable {

	
	ArrayList<product> data;
	   ObservableList<product> dataList;
	   
private String dbURL;
		private String dbUsername = "root";
		private String dbPassword = "bbb123";
		private String URL = "127.0.0.1";
		private String port = "3306";
		private String dbName = "student_project";
		private Connection con;


	
	@FXML private TableView<product> table;
	@FXML private TableColumn<product, Integer> serial_n;
	@FXML private TableColumn<product, String> ware_house;
	@FXML private TableColumn<product, String> description;
	@FXML private TableColumn<product, Integer> price;
	@FXML private TableColumn<product, Integer> amount;
	
	@FXML private TextField T_serial_n;
    @FXML private TextField T_ware_house;
    @FXML private TextField T_description;
    @FXML private TextField T_price;
    @FXML private TextField T_amount;
    @FXML private TextField Psearch;

    @FXML private Button Add;
    @FXML private Button Delete;
 	@FXML private Button Refresh;
 	@FXML private Button Clear;
 	@FXML private Button Pback;
 	
 	@FXML private TextField txt1;
    @FXML private TextField txt2;
    @FXML private Button but_st;

 	
 
	
	
private void getData() throws SQLException, ClassNotFoundException {
		
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
public void initialize(URL arg0, ResourceBundle arg1) {

data = new ArrayList<product>();
	try {
		getData();
	
		dataList = FXCollections.observableArrayList(data);
	
	}catch(Exception e) {
		e.printStackTrace();
	}
				
				
				serial_n.setCellValueFactory(new PropertyValueFactory<product,Integer>("serial_n"));
				ware_house.setCellValueFactory(new PropertyValueFactory<product,String>("ware_house"));
				description.setCellValueFactory(new PropertyValueFactory<product,String>("description"));
				price.setCellValueFactory(new PropertyValueFactory<product,Integer>("price"));
				amount.setCellValueFactory(new PropertyValueFactory<product,Integer>("amount"));
				 table.setItems(dataList);
				 /////////////////////////////////////////////////////////////////
				 serial_n.setCellFactory(TextFieldTableCell.<product,Integer>
				 forTableColumn(new IntegerStringConverter()));
				 ////////////////////////////////////////////////////////////////
				 ware_house.setCellFactory(TextFieldTableCell.<product>forTableColumn());
				 ware_house.setOnEditCommit(        
			        		(CellEditEvent<product,String> t) -> {
			                       ((product) t.getTableView().getItems().get(
			        	                        t.getTablePosition().getRow())
			        	                        ).setWare_house(t.getNewValue()); //display only
			                       
			                 updateWare_house( t.getRowValue().getSerial_n(),t.getNewValue());
			        		});
				 ///////////////////////////////////////////////////////////////
				 description.setCellFactory(TextFieldTableCell.<product>forTableColumn());
				 description.setOnEditCommit(        
			        		(CellEditEvent<product,String> t) -> {
			                       ((product) t.getTableView().getItems().get(
			        	                        t.getTablePosition().getRow())
			        	                        ).setDescription(t.getNewValue()); //display only
			                       
			                 updateDescription( t.getRowValue().getSerial_n(),t.getNewValue());
			        		});
				 ///////////////////////////////////////////////////////////////
				 price.setCellFactory(TextFieldTableCell.<product,Integer>
				 forTableColumn(new IntegerStringConverter()));
				 price.setOnEditCommit(        
			        		(CellEditEvent<product, Integer> t) -> {
			                       ((product) t.getTableView().getItems().get(
			        	                        t.getTablePosition().getRow())
			        	                        ).setPrice(t.getNewValue());
			                       
			                 updatePrice( t.getRowValue().getSerial_n(),t.getNewValue());
			        		});
				 ///////////////////////////////////////////////////////////////
				 amount.setCellFactory(TextFieldTableCell.<product,Integer>
				 forTableColumn(new IntegerStringConverter()));
				 amount.setOnEditCommit(        
			        		(CellEditEvent<product, Integer> t) -> {
			                       ((product) t.getTableView().getItems().get(
			        	                        t.getTablePosition().getRow())
			        	                        ).setAmount(t.getNewValue());
			                       
			                 updateAmount( t.getRowValue().getSerial_n(),t.getNewValue());
			        		});
				 /////////////////////////add///////////////////////////////////////
				 try {
				       
					 Add.setOnAction((ActionEvent e) -> {
				            product rc;
				        	rc = new product(
				                    Integer.valueOf(T_serial_n.getText()),
				                    T_ware_house.getText(),
				                    T_description.getText(),
				                    Integer.valueOf(T_price.getText()),
				                    Integer.valueOf(T_amount.getText()));
				            		
				        	dataList.add(rc);
				        	
				        	insertData(rc);
				        	T_serial_n.clear();
				        	T_ware_house.clear();
				        	T_description.clear();
				        	T_price.clear();
				        	T_amount.clear();
				        });
			       }catch(Exception e) {
			    	   
			       }
				 //////////////////////////delete///////////////////////////////////
				 Delete.setOnAction((ActionEvent e) -> {        	 
			      	 ObservableList <product> selectedRows = table.getSelectionModel().getSelectedItems();
			      	ArrayList<product> rows = new ArrayList<>(selectedRows);
			      	rows.forEach(row -> {
			      		table.getItems().remove(row); 
			      		deleteRow(row); 
			      		table.refresh();
			      		});   
			      });
				 ////////////////////////////refresh////////////////////////////////////
				 Refresh.setOnAction((ActionEvent e) -> {        	 
				       	table.refresh();  
				       });
				 ////////////////////////////clear////////////////////////////////////
				 Clear.setOnAction((ActionEvent e) -> {
			    		showDialog(NONE , table);
			    });
				 /////////////////////////////search/////////////////////////////////
				 FilteredList <product>  filterdata = new FilteredList<>(dataList,b-> true);
				 Psearch.textProperty().addListener((observable,oldValue,newValue)->{
						
						filterdata.setPredicate(clients -> {
							
							if (newValue == null || newValue.isEmpty() )
								return true;
							
							String lcase = newValue.toLowerCase();
							
							if(clients.getWare_house().toLowerCase().indexOf(lcase) != -1)
								return true;
							else if (String.valueOf(clients.getSerial_n()).indexOf(lcase) != -1)
								return true ;
							else if(clients.getDescription().toLowerCase().indexOf(lcase) != -1)
								return true;
							
								else 
									return false;
						});	
						});
				    SortedList<product> sort = new SortedList<>(filterdata);
					sort.comparatorProperty().bind(table.comparatorProperty());
					
					table.setItems(sort);
					//////////////////////////////////////////////////////////////////////
					but_st.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
						
						try {
						txt1.setText(String.valueOf(getPsum()));
						txt2.setText(String.valueOf(getTprice()));
						
						}catch (Exception e) {
							System.out.println("error");
						}
						
						}
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

public void updateWare_house(int serial_n, String ware_house) {
	
	try {
		System.out.println("update  product set ware_house = '"+ware_house + "' where serial_n = "+serial_n);
		connectDB();
		ExecuteStatement("update product set ware_house = '"+ware_house + "' where serial_n = "+serial_n+";");
		con.close();
		System.out.println("Connection closed");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
}

public void updateDescription(int serial_n, String description) {
	
	try {
		System.out.println("update  product set description = '"+description + "' where serial_n = "+serial_n);
		connectDB();
		ExecuteStatement("update product set description = '"+description + "' where serial_n = "+serial_n+";");
		con.close();
		System.out.println("Connection closed");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
}

public void updatePrice(int serial_n, int price) {
	
	try {
		System.out.println("update product set price  = "+price + " where serial_n = "+serial_n);
		connectDB();
		ExecuteStatement("update  Student set price = "+price+ " where serial_n= "+serial_n+";");
		con.close();
		System.out.println("Connection closed");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
}

public void updateAmount(int serial_n, int amount) {
	
	try {
		System.out.println("update product set amount  = "+amount + " where serial_n = "+serial_n);
		connectDB();
		ExecuteStatement("update  Student set amount = "+amount+ " where serial_n= "+serial_n+";");
		con.close();
		System.out.println("Connection closed");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
}

private void deleteRow(product row) {
	// TODO Auto-generated method stub
	
	try {
		System.out.println("delete from  product where serial_n="+row.getSerial_n() + ";");
		connectDB();
		ExecuteStatement("delete from  product where serial_n="+row.getSerial_n() + ";");
		con.close();
		System.out.println("Connection closed");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
}

private void showDialog( Modality modality,TableView<product> table) {
	// Create a Stage with specified owner and modality
	Stage stage = new Stage();
//	stage.initOwner(owner);
	stage.initModality(modality);
//	Label modalityLabel = new Label(modality.toString());
	
	Button yesButton = new Button("Confirm");
	yesButton.setOnAction(e -> {
		for (product row:dataList) {
			deleteRow(row);
			table.refresh();
		}
		table.getItems().removeAll(dataList);
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

private void insertData(product rc){

	
	try {
			System.out.println("Insert into product values("+rc.getSerial_n()+",'"+rc.getWare_house()+"','"+ rc.getDescription() +"',"+ rc.getPrice()+","+rc.getAmount()+")");
			connectDB();
			ExecuteStatement("Insert into product values("+rc.getSerial_n()+",'"+rc.getWare_house()+"','"+ rc.getDescription() +"',"+ rc.getPrice()+","+rc.getAmount()+");");
			con.close();
			System.out.println("Connection closed " + data.size());
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}

public void backtohome(ActionEvent event) {
	Stage window=(Stage) Pback.getScene().getWindow();
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

private int getPsum() throws SQLException, ClassNotFoundException {
	// TODO Auto-generated method stub
	
	String SQL;	
	connectDB();
	System.out.println("Connection established");

	SQL = "select sum(P.amount) from product P ";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(SQL);
	
	int psum=0;
	
	while(rs.next())
	psum = Integer.parseInt(rs.getString(1));	
 

	con.close();
	System.out.println("pnum"+psum);
	
	return psum;
		
}//end getdata

private int getTprice() throws SQLException, ClassNotFoundException {
	// TODO Auto-generated method stub
	
	String SQL;	
	connectDB();
	System.out.println("Connection established");

	SQL = "select sum(P.price) from product P ;";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(SQL);
	
	int psum = 0;
	
	while(rs.next())
	psum = Integer.parseInt(rs.getString(1));	
 

	con.close();
	System.out.println("Total price :"+psum);
	
	return psum;
		
}//end getdata

}
