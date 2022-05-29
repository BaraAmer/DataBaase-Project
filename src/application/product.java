package application;

public class product {
		 private String ware_house;
		 private int serial_n;
		 private int price;
		 private int amount;
		 private String description ;
		 
		 public product(int serial_n,String ware_house,String description,int price,int amount) {
			 this.ware_house = ware_house ;
			 this.serial_n = serial_n ;
			 this.price = price ;
			 this.amount = amount ;
			 this.description = description ;
		 }

		public String getWare_house() {
			return ware_house;
		}

		public void setWare_house(String ware_house) {
			this.ware_house = ware_house;
		}

		public int getSerial_n() {
			return serial_n;
		}

		public void setSerial_n(int serial_n) {
			this.serial_n = serial_n;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		 
		 
		 
 
	}