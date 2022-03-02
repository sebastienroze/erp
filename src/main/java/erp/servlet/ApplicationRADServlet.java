package erp.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import rad.DataRAD;
import erp.ErpFicheHtml;
import erp.FicheLogin;

public class ApplicationRADServlet extends HttpServlet {
	/**
	 * 
	 */	
	
	final static String apiURL = "./ApplicationRAD";

	private static final long serialVersionUID = 1L;

	
	@Override
	public void init() throws ServletException {
		DataRAD.maxFieldLength = 30;
	}	
	
	public boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("login");
		if (login == null) {			
			ErpFicheHtml erpFicheHtml = new ErpFicheHtml(apiURL, "Login") ;
			new FicheLogin(erpFicheHtml);			
			erpFicheHtml.setValuesAndWriteResponse(request.getParameterMap(),response);
			response.getWriter().print(erpFicheHtml.getContent());
			return false;
		}
		return true;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("get");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(getFiche(request).getContent());
//		System.out.println(getFiche(request).getContent());
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("post");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		ErpFicheHtml ficheHtml = getFiche(request);
		ficheHtml = ficheHtml.setValuesAndWriteResponse(request.getParameterMap(),response);
		if (ficheHtml!=null) {
//			System.out.println("Changement de fiche");
			HttpSession session = request.getSession();
			if (ficheHtml.storeSession(session)) {
			session.setAttribute("ficheHtml", ficheHtml);
			} else {
				session.invalidate();
			}
			response.getWriter().print(ficheHtml.getContent());			
		}
	}
	
	protected ErpFicheHtml getFiche(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ErpFicheHtml ficheHtml = (ErpFicheHtml) session.getAttribute("ficheHtml");		
		if (ficheHtml == null) {
//			System.out.println("Pas de session");
			ficheHtml = new ErpFicheHtml(apiURL, "Login");
			new FicheLogin(ficheHtml);			
		} 
		return ficheHtml;
	}
}
