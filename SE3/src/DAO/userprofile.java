package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



public class userprofile {
	public static ArrayList<userp> getuserp(){
		Connection con = null;
        PreparedStatement ps = null;
		
		 try {
			 	con = DatabaseConnection.createConnection;
	            ps = con.prepareStatement("SELECT username,emailid,pass,address,phonenumber,status,type from kp where role=?");
	            ps.setString(1, "Manager");
	            ArrayList<userp> al = new ArrayList<userp>();
	            ResultSet rs = ps.executeQuery();
	            boolean found = false;
	            System.out.println("here");
	            while (rs.next()) {
	                userp e = new userp();
	                e.setUsername(rs.getString("username"));
	                e.setEmailid(rs.getString("emailid"));
	                e.setPhoneno(rs.getString("phonenumber"));
	                e.setRole(rs.getString("role"));
	                e.setStatus(rs.getString("status"));
	               e.setAddress(rs.getString("address"));
	                al.add(e);
	                found = true;
	            }
	            rs.close();
	            con.close();
	            if (found) {
	                return al;
	            } else {
	                return null;
	            }
	        } catch (Exception e) {
	            System.out.println("Error In manager retrival" + e.getMessage());
	            return (null);
	        }
		
	}
}
