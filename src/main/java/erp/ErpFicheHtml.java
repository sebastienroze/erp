package erp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import rad.DataRAD;
import rad.RadComponent;
import rad.RadSQLGrid;
import rad.html.DataHtml;
import rad.html.HtmlComponent;
import rad.html.HtmlComponentFactory;
import rad.html.WebService;

public class ErpFicheHtml extends ErpFiche {
	private LinkedList<Object> content;
	private WebService webService;
	private HttpSession session;
	public ErpFicheHtml(String apiURL, String name) {
		webService = new WebService(apiURL, name);
		componentFactory = new  HtmlComponentFactory(webService); 
		content = new LinkedList<Object>();
	}

	public boolean storeSession(HttpSession session) {
		this.session = session;
		return (fichePrecedente != null);
	}

	
	@Override
	protected void ajoutTitre(String titre) {
		content.add("<h1>" + titre + "</h1>");				
	}

	@Override
	protected ErpFiche createFiche(String nom) {
		ficheSuivante = new ErpFicheHtml(webService.getApiUrl(),nom);
		ficheSuivante.fichePrecedente = this;
		return ficheSuivante;
	}



	@Override
	protected void ajoutComposant(RadComponent comp) {
		content.add(comp);
	}

	@Override
	protected void ajoutSaisie(String label, RadComponent component) {
		content.add("<label for =\"id" + ((HtmlComponent)component).getName() + "\">" + label + "</label>");				
		content.add(component);		
	}

	@Override
	protected void close() {
		ficheSuivante = fichePrecedente;
		ficheSuivante.ficheSuivante = null;
	}
	
	public String getContent() {
		StringBuilder fiche = new StringBuilder();
		for (Object object : content) {
			if (object instanceof String) {
				fiche.append((String)object);				
			}	
			if (object instanceof HtmlComponent) {
				fiche.append(((HtmlComponent)object).getHtml());
				fiche.append("<br>");				
			}
		}		
		return fiche.toString();		
	}

	public ErpFicheHtml setValuesAndWriteResponse(Map<String, String[]> values,HttpServletResponse response) throws IOException {
		webService.setValues(values);		
		if (ficheSuivante ==null) {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print("{");
			webService.writeResponses(out);
			out.print(",");
			out.print("\"canclose\":\"");
			if (canClose!=null) out.print(canClose);
			out.print("\"");			
			out.println("}");
			return null;
		}
		return (ErpFicheHtml)ficheSuivante;
	}

	@Override
	protected void showMessage(String message) {
		webService.addAlert(message);	
	}	
	
	@Override
	protected DataRAD createDataRad(String tableName, String keyField) {
		DataHtml data = new DataHtml(webService,conn, tableName, keyField);
		return data; 		
	}

	@Override
	protected void ajoutGrille(RadSQLGrid grille) {
		ajoutComposant(grille);
	}

	@Override
	protected void afficheFichier(String nomFichier) {
		ErpFicheHtml ficheVisualisation = (ErpFicheHtml) createFiche("visualisation");
		ficheVisualisation.ajoutTitre("Visualisation");
		ficheVisualisation.ajoutComposant(ficheVisualisation.componentFactory.createButton("Fermer","Fermer").addActionListener(e -> {
			ficheVisualisation.close();
		}));
		session.setAttribute("downloadfile",nomFichier);
		String urlFichier = "http://localhost:8080/ERP/DownloadFile";		
		ficheVisualisation.content.add("<object style=\"width:100%;height:calc(100vh - 120px)\" data=\""+urlFichier+"\"></object>");
	}
}
