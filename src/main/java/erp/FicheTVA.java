package erp;

import java.util.ArrayList;

import rad.DataRAD;
import rad.KeySelectListener;

public class FicheTVA {
	private ErpFiche erpFiche;
	private DataRAD dataRAD;
	private LogiqueSaisie logiqueSaisie;


	public FicheTVA(ErpFiche erpFiche, int selectedLine,ArrayList<Long> keyValues,KeySelectListener onClose) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase()) {
			dataRAD = erpFiche.createDataRad("TVA", "ID");
			logiqueSaisie = new LogiqueSaisie(erpFiche,dataRAD,selectedLine,keyValues,onClose);
			createContent();
			logiqueSaisie.init();
		} 
	}

	private void createContent() {
		erpFiche.ajoutTitre("Taux de TVA");
		erpFiche.ajoutComposant(logiqueSaisie.getBouttonsContainer());		
		erpFiche.ajoutSaisie("Libellé :", dataRAD.createTextField("LIBELLE"));
		erpFiche.ajoutSaisie("Taux :", dataRAD.createTextField("TAUX"));
	}
}