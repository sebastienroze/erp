package erp;

import java.util.ArrayList;

import rad.DataRAD;
import rad.KeySelectListener;

public class FicheArticle {
	private ErpFiche erpFiche;
	private DataRAD dataRAD;
	private LogiqueSaisie logiqueSaisie;


	public FicheArticle(ErpFiche erpFiche, int selectedLine,ArrayList<Long> keyValues,KeySelectListener onClose) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase()) {
			dataRAD = erpFiche.createDataRad("ARTICLE", "ID");
			logiqueSaisie = new LogiqueSaisie(erpFiche,dataRAD,selectedLine,keyValues,onClose);
			createContent();
			logiqueSaisie.init();
		} 
	}

	private void createContent() {
		erpFiche.ajoutTitre("Article");
		erpFiche.ajoutComposant(logiqueSaisie.getBouttonsContainer());		
		erpFiche.ajoutSaisie("Désignation :", dataRAD.createTextField("DESIGNATION"));
		erpFiche.ajoutSaisie("Prix unitaire :", dataRAD.createTextField("PRIX_UNITAIRE"));
	}
}