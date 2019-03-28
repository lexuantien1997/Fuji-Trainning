package DTO;

import java.io.Serializable;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String customerId;
	private String customerName;
	private String sex;
	private String birthday;
	private String email;
	private String address;
	private String deleteYMD;
	private String insertYMD;
	private String insertPsnYMD;
	private String updateYMD;
	private String updatePsnYMD;

	public String getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getSex() {
		return sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getDeleteYMD() {
		return deleteYMD;
	}

	public String getInsertYMD() {
		return insertYMD;
	}

	public String getInsertPsnYMD() {
		return insertPsnYMD;
	}

	public String getUpdateYMD() {
		return updateYMD;
	}

	public String getUpdatePsnYMD() {
		return updatePsnYMD;
	}

	private Customer(CustomerBuilder cusBuilder) {
		this.customerId = cusBuilder.customerId;
		this.customerName = cusBuilder.customerName;
		this.sex = cusBuilder.sex;
		this.birthday = cusBuilder.birthday;
		this.email = cusBuilder.email;
		this.address = cusBuilder.address;
		this.deleteYMD = cusBuilder.deleteYMD;
		this.insertYMD = cusBuilder.insertYMD;
		this.insertPsnYMD = cusBuilder.insertPsnYMD;
		this.updateYMD = cusBuilder.updateYMD;
		this.updatePsnYMD = cusBuilder.updatePsnYMD;
	}

	//Builder Class
	public static class CustomerBuilder{

		private String customerId;
		private String customerName;
		private String sex;
		private String birthday;
		private String email;
		private String address;
		private String deleteYMD;
		private String insertYMD;
		private String insertPsnYMD;
		private String updateYMD;
		private String updatePsnYMD;
		
		public Customer build(){
			return new Customer(this);
		}

		public CustomerBuilder setCustomerId(String customerId) {
			this.customerId = customerId;
			return this;
		}

		public CustomerBuilder setCustomerName(String customerName) {
			this.customerName = customerName;
			return this;
		}

		public CustomerBuilder setSex(String sex) {
			this.sex = sex;
			return this;
		}

		public CustomerBuilder setBirthday(String birthday) {
			this.birthday = birthday;
			return this;
		}

		public CustomerBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public CustomerBuilder setAddress(String address) {
			this.address = address;
			return this;
		}

		public CustomerBuilder setDeleteYMD(String deleteYMD) {
			this.deleteYMD = deleteYMD;
			return this;
		}

		public CustomerBuilder setInsertYMD(String insertYMD) {
			this.insertYMD = insertYMD;
			return this;
		}

		public CustomerBuilder setInsertPsnYMD(String insertPsnYMD) {
			this.insertPsnYMD = insertPsnYMD;
			return this;
		}

		public CustomerBuilder setUpdateYMD(String updateYMD) {
			this.updateYMD = updateYMD;
			return this;
		}

		public CustomerBuilder setUpdatePsnYMD(String updatePsnYMD) {
			this.updatePsnYMD = updatePsnYMD;
			return this;
		}
	}
	
}
