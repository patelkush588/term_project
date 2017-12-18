package DAO;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import DAO.RegistrationDAO;

@ManagedBean
@SessionScoped
public class login_bean {

	public String msg = "Register";
	private String firstName = null;
	private String lastname = null;
	private String address = null;
	private String phonenumber = null;
	private String emailid = null;
	private String username = null;
	private String password = null;
	private int status = 2;
	private String type;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String register() {
		{
			this.setMsg(null);
			try {
				if (this.getType().equals("manager"))
					this.status = 1;
				if (!this.getType().equals("manager") && !this.getType().equals("user")) {
					this.setMsg("Invalid User Type:" + this.getType());
					return "registration";
				}
				RegistrationDAO register = new RegistrationDAO();
				if (register.registerUser(this) > 0) {
					this.setMsg("Registration Successful");
					FacesContext facesContext = FacesContext.getCurrentInstance();
					facesContext.getExternalContext().getSessionMap().put("msg", "Registration Successful");
					return "login";
				} else {
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("msg",
							"Error in registering the user.");
					this.setMsg("Error in registering the user.");
					return "registration";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				this.setMsg("Some Error Found \t" + e.toString());
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
						.getSession(false);
				session.invalidate();
				return "registration";
			}

		}
	}

	public String LoginUser() {
		Boolean result = loginDAO.validateUser(username, password, type);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (result) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			facesContext.getExternalContext().getSessionMap().put("message", "Success");
			// session.setAttribute("Message", this.getMsg());
			session.setAttribute("username", this.getUsername());
			session.setAttribute("address", this.getAddress());
			session.setAttribute("phonenumber", this.getPhonenumber());
			session.setAttribute("password", this.getPassword());
			session.setAttribute("emailid", this.getEmailid());
			session.setAttribute("type", this.getType());
			
			if (session.getAttribute("type").equals("user")) {
				return "user/user";
			}else if (session.getAttribute("type").equals("manager")) {
				return "manager/manager";
		} else {
			this.setIsValid(true);
			this.setMsg("Invalid Credentials");
			facesContext.getExternalContext().getSessionMap().put("message", "Invalid Credential");
			return "failure";
		}
		}
		return "login.xhtml";
	}
	
	public String LoginAdmin() {
		Boolean result = loginDAO.validateUser(username, password, "admin");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (result) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			facesContext.getExternalContext().getSessionMap().put("message", "Success");
			// session.setAttribute("Message", this.getMsg());
			session.setAttribute("username", this.getUsername());
			session.setAttribute("emailId", this.getEmailid());
			session.setAttribute("type", "admin");
			return "admin/index.xhtml";
		} else {
			this.setIsValid(true);
			this.setMsg("Invalid Credentials");
			facesContext.getExternalContext().getSessionMap().put("message", "Invalid Credential");
			return "failure";
		}
	}

	private void setIsValid(boolean b) {
		// TODO Auto-generated method stub

	}

	public void logOut() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("LOG OUT CALLED");
	}
}
