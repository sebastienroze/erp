package erp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import rad.DataFieldRAD;
import rad.DataRAD;
import rad.SQLGridSearch;
import rad.KeySelectListener;
import rad.RadButton;
import rad.RadButtonField;
import rad.RadComboBox;
import rad.RadContainer;
import rad.RadSQLGrid;
import rad.RadStaticText;

public class FicheFacture {
	private ErpFiche erpFiche;
	private DataRAD dataRAD;
	private LogiqueSaisie logiqueSaisie;

	public FicheFacture(ErpFiche erpFiche, int selectedLine, ArrayList<Long> keyValues, KeySelectListener onClose) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase()) {
			dataRAD = erpFiche.createDataRad("FACTURE", "ID");
			logiqueSaisie = new LogiqueSaisie(erpFiche, dataRAD, selectedLine, keyValues, onClose);
			createContent();
			logiqueSaisie.init();
		}
	}

	private void createContent() {
		DataRAD dataClient = dataRAD.createLinkData("ID_CLIENT", "CLIENT", "ID");
		DataFieldRAD nomClient = dataClient.createField("NOM");
		DataFieldRAD nomPrenom = dataClient.createField("PRENOM");
		RadComboBox comboTva;
		erpFiche.ajoutTitre("Facture");
		erpFiche.ajoutComposant(logiqueSaisie.getBouttonsContainer());
		erpFiche.ajoutSaisie("Numéro :", dataRAD.createTextField("NUMERO"));
		erpFiche.ajoutSaisie("Date :", dataRAD.createDateField("DATE_FACTURE"));
		erpFiche.ajoutSaisie("Etat :", dataRAD.createRadioGroup("ETAT", new String[] { "S", "P", "F" },
				new String[] { "Saisie en cours", "Proformat", "Facture" }));
		comboTva = 	dataRAD.createSQLComboBox("ID_TVA", "SELECT ID,TAUX FROM TVA");	
		erpFiche.ajoutSaisie("T.V.A. :",comboTva );
		erpFiche.ajoutSaisie("Remise :", dataRAD.createTextField("REMISE"));
		RadStaticText totalbut = erpFiche.componentFactory.createStaticText("totalbrut");
		erpFiche.ajoutSaisie("Total des lignes :", totalbut);
		RadStaticText totalTTC = erpFiche.componentFactory.createStaticText("totalbrut");
		erpFiche.ajoutSaisie("Total TTC :", totalTTC);
		RadStaticText infoClient = erpFiche.componentFactory.createStaticText("infoclient");
		erpFiche.ajoutSaisie("Client :", infoClient);

		erpFiche.ajoutComposant(
				dataRAD.createButtonField("ID_CLIENT", "Changer client").addActionListener(button -> {
					ErpFiche ficheSelectionGrille = erpFiche.createFiche("CLIENT");
					RadSQLGrid grSelection = FicheClients.createGrille(ficheSelectionGrille);
					erpFiche.createFicheSelectionGrille(ficheSelectionGrille, "Choisir un client",
							(RadButtonField) button, grSelection, key -> {
								dataRAD.readLink("ID_CLIENT");
								infoClient.setValue(nomClient.getValue() + " " + nomPrenom.getValue());								
							});
				}));

		RadSQLGrid grilleLigne = erpFiche.componentFactory.createRadSQLGrid("GRlignes", ErpFiche.conn, "ID",
				"SELECT ID,ID_FACTURE,LIGNE as Ligne,DESIGNATION as Désignation,PRIX_UNITAIRE as 'Prix Unitaire', QUANTITE as Quantité,MONTANT as Montant FROM LIGNE_FACTURE",
				2, null);
		grilleLigne.setOrderBy("LIGNE");
		dataRAD.addRefresh(value -> {
			infoClient.setValue(nomClient.getValue() + " " + nomPrenom.getValue());
			grilleLigne.filter();
			Double totalHT = FicheFacture.calculMontantBrut(dataRAD.getKeyValue());
			Double remise = dataRAD.getFieldValueDouble("REMISE");
			String strTva = comboTva.getDisplayValue();
			Double tauxTva = 0.0;
			if (strTva != null) tauxTva = Double.parseDouble(strTva);
			totalbut.setValue(totalHT+"");
			Double valeurtotalTTC = (totalHT-remise)*(100+tauxTva)/100;
			totalTTC.setValue(valeurtotalTTC +"");			
		});

		grilleLigne.setGridSearch(new SQLGridSearch(erpFiche.componentFactory) {
			@Override
			public void init() {
			}

			@Override
			public String getFilter(ArrayList<String> values) {
				values.add(dataRAD.getKeyValue() + "");
				return "ID_FACTURE=?";
			}

			@Override
			public RadContainer getContainer() {
				return null;
			}
		});
		RadButton btAjoutLigne = erpFiche.componentFactory.createButton("ajoutligne", "Ajouter ligne")
				.addActionListener(e -> {
					ErpFiche fiche = FicheFacture.this.erpFiche.createFiche("lignesfacture");
					new FicheLigneFacture(fiche, dataRAD.getKeyValue(), -1,
							grilleLigne.getKeyValues(), oncloseKey -> {
								grilleLigne.filter();
								dataRAD.doRefresh();
							});
				});
		RadButton btVoirLigne = erpFiche.componentFactory.createButton("voirligne", "Détail ligne")
				.addActionListener(e -> {
					ErpFiche fiche = FicheFacture.this.erpFiche.createFiche("lignesfacture");
					new FicheLigneFacture(fiche, dataRAD.getKeyValue(), grilleLigne.getSelectedLine(),
							grilleLigne.getKeyValues(), oncloseKey -> {
								grilleLigne.filter();
								dataRAD.doRefresh();								
							});
				});
		btVoirLigne.setReadOnly(true);
		grilleLigne.addRowSelectListener(e->btVoirLigne.setReadOnly(false));
		grilleLigne.addDoubleClicListener(e->btVoirLigne.performAction());
		erpFiche.ajoutComposant(btAjoutLigne);
		erpFiche.ajoutComposant(btVoirLigne);
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("imprimer", "Imprimer")
				.addActionListener(e -> {
					erpFiche.afficheFichier(new PdfFacture(dataRAD.getKeyValue()).getFichierProduit());
				}));			
		logiqueSaisie.addCreeDonneeListener(e->{grilleLigne.filter();});
		erpFiche.ajoutGrille(grilleLigne);
	}
	public static double calculMontantBrut(Long idFacture) {
		double montant = 0;
		if (idFacture==null) return montant;
		try {
			PreparedStatement statement = ErpFiche.conn
					.prepareStatement("SELECT SUM(MONTANT) FROM LIGNE_FACTURE WHERE ID_FACTURE=?");
			statement.setLong(1, idFacture);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				montant = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return montant;
	}
}