package erp;

import java.util.ArrayList;

import rad.DataRAD;
import rad.KeySelectListener;

public class FicheParametre {
	private ErpFiche erpFiche;
	private DataRAD dataRAD;
	private LogiqueSaisie logiqueSaisie;


	public FicheParametre(ErpFiche erpFiche, int selectedLine,ArrayList<Long> keyValues,KeySelectListener onClose) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase()) {
			dataRAD = erpFiche.createDataRad("PARAMETRES", "ID");
			logiqueSaisie = new LogiqueSaisie(erpFiche,dataRAD,selectedLine,keyValues,onClose);
			createContent();
			logiqueSaisie.init();
		} 
	}

	private void createContent() {
		erpFiche.ajoutTitre("Paramètres de l'application");
		erpFiche.ajoutComposant(logiqueSaisie.getBouttonsContainer());		
		erpFiche.ajoutSaisie("Paramètre :", dataRAD.createTextField("PARAMETRE"));
		erpFiche.ajoutSaisie("Valeur :", dataRAD.createTextField("VALEUR"));
	}
}