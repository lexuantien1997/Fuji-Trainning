package DTO;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String psnCD;
	private String userId;
	private String password;
	private String userName;
	private String deleteYMD;
	private String insertYMD;
	private String insertPsnYMD;
	private String updateYMD;
	private String updatePsnYMD;
	
	public String getPsnCD() {
		return psnCD;
	}
	public String getUserId() {
		return userId;
	}
	public String getPassword() {
		return password;
	}
	public String getUserName() {
		return userName;
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

	
	public User(UserBuilder userBuilder) {
		this.psnCD = userBuilder.psnCD;
		this.userId = userBuilder.userId;
		this.password = userBuilder.password;
		this.userName = userBuilder.userName;
		this.deleteYMD = userBuilder.deleteYMD;
		this.insertYMD = userBuilder.insertYMD;
		this.insertPsnYMD = userBuilder.insertPsnYMD;
		this.updateYMD = userBuilder.updateYMD;
		this.updatePsnYMD = userBuilder.updatePsnYMD;
	}
	
	//Builder Class
	public static class UserBuilder{

		private String psnCD;
		private String userId;
		private String password;
		private String userName;
		private String deleteYMD;
		private String insertYMD;
		private String insertPsnYMD;
		private String updateYMD;
		private String updatePsnYMD;
		
		public User build(){
			return new User(this);
		}

		public UserBuilder setPsnCD(String psnCD) {
			this.psnCD = psnCD;
			return this;
		}

		public UserBuilder setUserId(String userId) {
			this.userId = userId;
			return this;
		}

		public UserBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public UserBuilder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public UserBuilder setDeleteYMD(String deleteYMD) {
			this.deleteYMD = deleteYMD;				
			return this;
		}

		public UserBuilder setInsertYMD(String insertYMD) {
			this.insertYMD = insertYMD;
			return this;
		}

		public UserBuilder setInsertPsnYMD(String insertPsnYMD) {
			this.insertPsnYMD = insertPsnYMD;
			return this;
		}

		public UserBuilder setUpdateYMD(String updateYMD) {
			this.updateYMD = updateYMD;
			return this;
		}

		public UserBuilder setUpdatePsnYMD(String updatePsnYMD) {
			this.updatePsnYMD = updatePsnYMD;
			return this;
		}
	}
	
}
