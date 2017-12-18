package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name="getData")
@SessionScoped
public class GetData {
	DatabaseConnection db = new DatabaseConnection();
	Connection con = db.createConnection();;
	Statement statement = null;
	PreparedStatement ps = null;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	public String approveRejectManager(int id, int status){
		try {
			statement = con.createStatement();
			ps = con.prepareStatement("UPDATE kp SET status=? WHERE id=?");
			if (status != 0) {
		        ps.setInt(1, status);
		    } else {
		        ps.setInt(1,0);
		    }
			ps.setInt(2,id);
			int i= ps.executeUpdate();
			if(i>0){
				return "managerList";
			}
			else{
				facesContext.getExternalContext().getSessionMap().put("messageQ", "No Action performed");
				return "managerList";
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return "managerList";
	}
	
	
	
	
	
	public List<Manager> getManagerList()
	{
	
	List<Manager> list = new ArrayList<Manager>();
	try
	{
	statement = con.createStatement();
	ResultSet rs = statement.executeQuery("select * from kp where status = 1 AND type='manager'");
	while (rs.next())
	{
	Manager mgr = new Manager();
	mgr.setId(rs.getInt("id"));
	mgr.setStatus(rs.getInt("status"));
	mgr.setFirst_name(rs.getString("firstname"));
	mgr.setLast_name(rs.getString("lastname"));
	list.add(mgr);
	} 
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	
	return list;
	}
	
	public List<Manager> selectManager()
	{
	
	List<Manager> list = new ArrayList<Manager>();
	try
	{
	statement = con.createStatement();
	ResultSet rs = statement.executeQuery("select * from kp where status = 2 AND type='manager'");
	while (rs.next())
	{
	Manager mgr = new Manager();
	mgr.setId(rs.getInt("id"));
	mgr.setStatus(rs.getInt("status"));
	mgr.setFirst_name(rs.getString("firstname"));
	mgr.setLast_name(rs.getString("lastname"));
	list.add(mgr);
	} 
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	
	return list;
	}
}
