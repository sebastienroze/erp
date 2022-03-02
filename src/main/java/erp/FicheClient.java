package erp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import rad.DataRAD;
import rad.KeySelectListener;

public class FicheClient {
	private ErpFiche erpFiche;
	private DataRAD dataRAD;
	private LogiqueSaisie logiqueSaisie;

	public FicheClient(ErpFiche erpFiche, int selectedLine, ArrayList<Long> keyValues, KeySelectListener onClose) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase()) {
			dataRAD = erpFiche.createDataRad("CLIENT", "ID");
			logiqueSaisie = new LogiqueSaisie(erpFiche, dataRAD, selectedLine, keyValues, onClose);
			createContent();
			logiqueSaisie.init();
		}
	}

	private void createContent() {
		erpFiche.ajoutTitre("Client");
		erpFiche.ajoutComposant(logiqueSaisie.getBouttonsContainer());
		erpFiche.ajoutSaisie("Civilité :", dataRAD.createTextField("CIVILITE"));
		erpFiche.ajoutSaisie("Nom :", dataRAD.createTextField("NOM"));
		erpFiche.ajoutSaisie("Prénom :", dataRAD.createTextField("PRENOM"));
		erpFiche.ajoutSaisie("Adresse1 :", dataRAD.createTextField("ADRESSE1"));
		erpFiche.ajoutSaisie("Adresse2 :", dataRAD.createTextField("ADRESSE2"));
		erpFiche.ajoutSaisie("Code postal :", dataRAD.createTextField("CODE_POSTAL"));
		erpFiche.ajoutSaisie("Ville :", dataRAD.createTextField("VILLE"));
		erpFiche.ajoutSaisie("Tél :", dataRAD.createTextField("TEL"));
		erpFiche.ajoutSaisie("e-mail :", dataRAD.createTextField("EMAIL"));
		erpFiche.ajoutSaisie("T.V.A. :", dataRAD.createSQLComboBox("ID_TVA", "SELECT ID,TAUX FROM TVA"));
	}

	public static String getCiviliteNomPrenom(ResultSet rs) throws SQLException {
		return ErpFiche.concateneStringAvecEspace(ErpFiche.concateneStringAvecEspace(
				ErpFiche.concateneStringAvecEspace("", rs.getString("CLIENT.CIVILITE")), rs.getString("CLIENT.NOM")),
				rs.getString("CLIENT.PRENOM"));
	}
}
