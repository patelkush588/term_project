package DAO;

import java.sql.Connection;
import java.sql.Statement;

import com.mysql.jdbc.ResultSet;




public class RegistrationDAO {
	DatabaseConnection db = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet result = null;

	public RegistrationDAO() {
		// TODO Auto-generated constructor stub
		try {
			db = new DatabaseConnection();
			connection = db.createConnection();
			statement = connection.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public int registerUser(login_bean login_bean) {
		
		//System.out.println("register");
		try {
				String sql = "INSERT INTO `kp`(`firstname`,`lastname`,`address`,`phonenumber`,`emailid`,`username`,`pass`,`status`,`type`) VALUES ('" + login_bean.getFirstName() 
						+ "','" + login_bean.getLastname() + "','" + login_bean.getAddress() + "','" + login_bean.getPhonenumber() + "','" + login_bean.getEmailid() + "','" + login_bean.getUsername() + "','" + login_bean.getPassword() +"','" + login_bean.getStatus() + "','" + login_bean.getType() + "')";    
				System.out.println(sql);
				return statement.executeUpdate(sql);
				


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}


}
