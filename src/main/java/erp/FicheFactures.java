package erp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import rad.SQLGridSearch;
import rad.RadContainer;
import rad.RadSQLGrid;
import rad.RadTextField;
import rad.SQLGridCalculatedField;

public class FicheFactures {
	private ErpFiche erpFiche;
	private LogiqueListe logiqueListe;

	public FicheFactures(ErpFiche erpFiche) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase())
			createContent();
	}

	private void createContent() {
		erpFiche.ajoutTitre("Liste des factures");
		RadSQLGrid grille = createGrille(erpFiche);
		logiqueListe = new LogiqueListe(erpFiche, grille, createfiche -> {
			ErpFiche fiche = FicheFactures.this.erpFiche.createFiche("FACTURE");
			new FicheFacture(fiche, grille.getSelectedLine(), grille.getKeyValues(), oncloseKey -> {
				FicheFactures.this.logiqueListe.rafraichit(oncloseKey);
			});
		});
	}

	public static RadSQLGrid createGrille(ErpFiche erpFiche) {
		SQLGridCalculatedField cfClient = new SQLGridCalculatedField(1, "Client") {
			public String Calculate(ResultSet rs) throws SQLException {
				return FicheClient.getCiviliteNomPrenom(rs);
			}
		};

		SQLGridCalculatedField cfCPVille = new SQLGridCalculatedField(2, "C.P. Ville") {
			public String Calculate(ResultSet rs) throws SQLException {
				return ErpFiche.concateneStringAvecEspace(rs.getString("CLIENT.CODE_POSTAL"),
						rs.getString("CLIENT.VILLE"));
			}
		};
		SQLGridCalculatedField cfMontant = new SQLGridCalculatedField(3, "Montant") {
			public String Calculate(ResultSet rs) throws SQLException {
				double montant = rs.getDouble("SUM(LIGNE_FACTURE.MONTANT)") - rs.getDouble("FACTURE.REMISE");
				montant += montant * rs.getDouble("TVA.TAUX") / 100.0D;
				return String.format("%.02f", new Object[] { Double.valueOf(montant) });
			}
		};

		RadSQLGrid grille = erpFiche.componentFactory.createRadSQLGrid("GRFACTURE", ErpFiche.conn, "FACTURE.ID",
				"SELECT FACTURE.ID,CLIENT.CIVILITE,CLIENT.NOM,CLIENT.PRENOM,"
						+ "CLIENT.CODE_POSTAL,CLIENT.VILLE,FACTURE.REMISE,TVA.TAUX,"
						+ "SUM(LIGNE_FACTURE.MONTANT),FACTURE.NUMERO as Numéro FROM FACTURE "
						+ "LEFT JOIN CLIENT ON CLIENT.ID = FACTURE.ID_CLIENT "
						+ "LEFT JOIN TVA ON TVA.ID = FACTURE.ID_TVA "
						+ "LEFT JOIN LIGNE_FACTURE ON LIGNE_FACTURE.ID_FACTURE=FACTURE.ID",
				9, new SQLGridCalculatedField[] { cfClient, cfCPVille, cfMontant });
		grille.setGroupBy("FACTURE.ID");
		grille.setGridSearch(createSearch(erpFiche));
		return grille;
	}

	public static SQLGridSearch createSearch(ErpFiche erpFiche) {
		SQLGridSearch gs = new SQLGridSearch(erpFiche.componentFactory) {
			public RadTextField tfNumero;
			public RadTextField tfCP;
			public RadTextField tfVille;

			@Override
			public void init() {
				tfNumero = createTextField("Recherche par numéro");
				tfCP = createTextField("CODE_POSTAL");
				tfVille = createTextField("Ville");
			}

			@Override
			public String getFilter(ArrayList<String> values) {
				StringBuilder sql = new StringBuilder();
				String numero = tfNumero.getValue();
				if (!"".equals(numero)) {
					if (sql.length() > 0)
						sql.append(" AND ");
					sql.append("(NUMERO LIKE ?)");
					values.add("%" + numero + "%");
				}
				String ville = tfVille.getValue();
				if (!"".equals(ville)) {
					if (sql.length() > 0)
						sql.append(" AND ");
					sql.append("(CLIENT.VILLE LIKE ?)");
					values.add("%" + ville + "%");
				}
				String cp = tfCP.getValue();
				if (!"".equals(cp)) {
					if (sql.length() > 0)
						sql.append(" AND ");
					sql.append("(CLIENT.CODE_POSTAL LIKE ?)");
					values.add(cp + "%");
				}
				return sql.toString();
			}

			@Override
			public RadContainer getContainer() {
				RadContainer container = createContainer("Recherche");
				container.addLabelComponent("Recherche par numéro :", tfNumero);
				container.addLabelComponent("Code postal :", tfCP);
				container.addLabelComponent("Ville :", tfVille);
				return container;
			}
		};
		return gs;
	}
}
