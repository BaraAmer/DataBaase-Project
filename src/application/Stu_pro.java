package application;

public class Stu_pro  {
	
	private int ids;
	private String sname;
	private  int idt;
	private  String year;
	private String phone;
	 

	 
	 public Stu_pro (int ids, String sname, int idt, String year, String phone) {
		 this.ids = ids;
		 this.sname = sname;
		 this.idt =idt;
		 this.year = year;
		 this.phone = phone;

	 }



	public int getIds() {
		return ids;
	}



	public void setIds(int ids) {
		this.ids = ids;
	}



	public String getSname() {
		return sname;
	}



	public void setSname(String  sname) {
		this.sname = sname;
	}



	public int getIdt() {
		return idt;
	}



	public void setIdt(int idt) {
		this.idt = idt;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
