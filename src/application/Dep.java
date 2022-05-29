package application;

public class Dep {
	
	private int dnumber;
	private String named;
	private String location;
	private int eid;
	private String startdate;
	
	
	public Dep(int dnumber, String named, String location, int eid, String startdate) {
		super();
		this.dnumber = dnumber;
		this.named = named;
		this.location = location;
		this.eid = eid;
		this.startdate = startdate;
	}


	public int getDnumber() {
		return dnumber;
	}


	public void setDnumber(int dnumber) {
		this.dnumber = dnumber;
	}


	public String getNamed() {
		return named;
	}


	public void setNamed(String named) {
		this.named = named;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public int getEid() {
		return eid;
	}


	public void setEid(int eid) {
		this.eid = eid;
	}


	public String getStartdate() {
		return startdate;
	}


	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	
	
	

}
