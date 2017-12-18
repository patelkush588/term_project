package session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter()
public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			System.out.println("DO FILTER CALLED");
			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();
			String reqCon = reqt.getContextPath();
			System.out.println("reqURI: "+reqURI);
			System.out.println("reqCon: "+reqCon);
			if (reqURI.indexOf("/login.xhtml") >= 0
					|| reqURI.indexOf("/login_admin.xhtml") >= 0
					|| (ses != null && ses.getAttribute("username") != null)
					|| reqURI.indexOf(reqCon+"/index.xhtml") >= 0
					|| reqURI.indexOf("/javax.faces.resource/") >=0
					|| reqURI.indexOf("/registration.xhtml") >= 0
					)
				{
					if(ses != null && ses.getAttribute("type") != null){
						if(ses.getAttribute("type").equals("user") && reqURI.indexOf(reqCon+"/user/") <0){
							//System.out.println("HERE1");
							resp.sendRedirect(reqt.getContextPath() + "/user/user.xhtml");
						}
						else if(ses.getAttribute("type").equals("manager") && reqURI.indexOf(reqCon+"/manager/") <0){
							//System.out.println("HERE2");
							resp.sendRedirect(reqt.getContextPath() + "/manager/manager.xhtml");
						}
						else if(ses.getAttribute("type").equals("admin") && reqURI.indexOf(reqCon+"/admin/") <0){
							System.out.println("HERE3");
							resp.sendRedirect(reqt.getContextPath() + "/admin/index.xhtml");
						}
						else{
							System.out.println("HERE3");
							chain.doFilter(request, response);
						}
					}
					else{
						chain.doFilter(request, response);
					}

				}
			else{
				System.out.println("INSIDE ELSE");
				resp.sendRedirect(reqt.getContextPath() + "/index.xhtml");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {

	}
}