package DAO;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import DAO.userprofile;

@ManagedBean()
@SessionScoped

public class userp {
	
	private String username;
	private String emailid;
	private String phonenumber;
	private String role;
	private String status;
	private String password;
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPhoneno() {
		return phonenumber;
	}

	public void setPhoneno(String phoneno) {
		this.phonenumber = phoneno;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String address;
	
	public ArrayList<userp> getMessages() {
        return userprofile.getuserp();
    }
	
//	@SuppressWarnings("unchecked")
//	public Object getProfileDetails(){
//		con.prepareStatement(select username,address from kp);
//		  Object ps;
//		ResultSet rs =  sql.executeQuery();
//          boolean found = false;
//          System.out.println("here");
//          Object al;
//		while (rs.next()) {
//		 userp e;
//		e.setUsername(rs.getString("username"));
//		  e.setAddress(rs.getString("address"));
//		  ((ArrayList<userp>) al).add(e);
//          found = true;
//          }
//      rs.close();
//      ResultSet con;
//	con.close();
//      if (found) {
//          return al;
//      } else {
//          return null;
//      }
//	}
	
}
