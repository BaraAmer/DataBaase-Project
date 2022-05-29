package application;

public class problem {
	 private int pnum;
	 private int cid;
	 private String pdescription;
	 private String type_d ;
	 
	public problem(int pnum, int cid, String pdescription, String type_d) {
		super();
		this.pnum = pnum;
		this.cid = cid;
		this.pdescription = pdescription;
		this.type_d = type_d;
	}

	public int getPnum() {
		return pnum;
	}

	public void setPnum(int pnum) {
		this.pnum = pnum;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getPdescription() {
		return pdescription;
	}

	public void setPdescription(String pdescription) {
		this.pdescription = pdescription;
	}

	public String getType_d() {
		return type_d;
	}

	public void setType_d(String type_d) {
		this.type_d = type_d;
	}
	
	 
	 

}
