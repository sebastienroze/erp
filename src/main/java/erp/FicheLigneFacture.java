package erp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import rad.DataRAD;
import rad.KeySelectListener;

public class FicheLigneFacture {
	private ErpFiche erpFiche;
	private DataRAD dataRAD;
	private LogiqueSaisie logiqueSaisie;
	Long id_Facture;

	public FicheLigneFacture(ErpFiche erpFiche, Long id_Facture, int selectedLine, ArrayList<Long> keyValues,
			KeySelectListener onClose) {
		this.erpFiche = erpFiche;
		if (id_Facture == null) {
			erpFiche.close();
			return;
		}
		this.id_Facture = id_Facture;
		if (erpFiche.accesDatabase()) {
			dataRAD = erpFiche.createDataRad("LIGNE_FACTURE", "ID");
			logiqueSaisie = new LogiqueSaisie(erpFiche, dataRAD, selectedLine, keyValues, onClose);
			createContent();
			logiqueSaisie.init();
		}
	}

	private void createContent() {
		erpFiche.ajoutTitre("Ligne de facture");
		erpFiche.ajoutComposant(logiqueSaisie.getBouttonsContainer());
		erpFiche.ajoutSaisie("Ligne :", dataRAD.createTextField("LIGNE"));
		erpFiche.ajoutSaisie("Désignation :", dataRAD.createTextField("DESIGNATION"));
		erpFiche.ajoutSaisie("Prix unitaire :", dataRAD.createTextField("PRIX_UNITAIRE"));
		erpFiche.ajoutSaisie("Quantité :", dataRAD.createTextField("QUANTITE"));
		erpFiche.ajoutSaisie("Montant :", dataRAD.createTextField("MONTANT"));
		dataRAD.createField("ID_FACTURE");
		dataRAD.addValidateSaveListener(data -> {
			dataRAD.setFieldValue("ID_FACTURE", id_Facture + "");
			if ("".equals(dataRAD.getFieldValue("LIGNE"))) {
				dataRAD.setFieldValue("LIGNE", nouveauNumeroLigne() + "");
			}
			String valueQuantite = dataRAD.getFieldValue("QUANTITE");
			String valuePrixUnitaire = dataRAD.getFieldValue("PRIX_UNITAIRE");
			String valueMontant = "";
			if (valueQuantite != null && valuePrixUnitaire != null)
				try {
					Double montant = Double
							.valueOf(Double.parseDouble(valueQuantite) * Double.parseDouble(valuePrixUnitaire));
					valueMontant = String.format(Locale.US, "%.2f", new Object[] { montant });
					this.dataRAD.setFieldValue("MONTANT", valueMontant);
				} catch (NumberFormatException numberFormatException) {
				}
			return true;
		});
		logiqueSaisie.addCreeDonneeListener(e -> {
			dataRAD.setFieldValue("LIGNE", nouveauNumeroLigne() + "");
		});
	}

	public int nouveauNumeroLigne() {
		int numLigne = 0;
		try {
			PreparedStatement statement = ErpFiche.conn
					.prepareStatement("SELECT MAX(LIGNE) FROM LIGNE_FACTURE WHERE ID_FACTURE=?");
			statement.setLong(1, this.id_Facture);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				numLigne = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numLigne + 1;
	}

}
