package DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

public class loginDAO {

	public static Boolean validateUser(String uName, String pass,String type) {
		// TODO Auto-generated method stub
		try {
			DatabaseConnection db = new DatabaseConnection();
			Connection con = db.createConnection();
			Statement statement = con.createStatement();
			ResultSet rest = statement
					.executeQuery("select * from kp where (username ='" + uName + "'&& pass='" + pass + "' && type='"+type+"')");
			if (rest.next())
				{
				if(type=="manager"){
					if(rest.getInt("type")!=2)
						{
						FacesContext facesContext = FacesContext.getCurrentInstance();
						facesContext.getExternalContext().getSessionMap().put("message", "Manager Account not approved.");
						return false;
						}
				}
				return true;
				}
			else
				return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

}