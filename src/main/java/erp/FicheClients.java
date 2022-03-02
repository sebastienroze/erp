package erp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import rad.SQLGridSearch;
import rad.RadContainer;
import rad.RadSQLGrid;
import rad.RadTextField;
import rad.SQLGridCalculatedField;

public class FicheClients {
	private ErpFiche erpFiche;
	private LogiqueListe logiqueListe;

	public FicheClients(ErpFiche erpFiche) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase())
			createContent();
	}

	private void createContent() {
		erpFiche.ajoutTitre("Liste des clients");
		RadSQLGrid grille = createGrille(erpFiche);
		logiqueListe = new LogiqueListe(erpFiche,grille,createfiche->{
			ErpFiche fiche = FicheClients.this.erpFiche.createFiche("Client");
			new FicheClient(fiche, grille.getSelectedLine() ,grille.getKeyValues(),oncloseKey->{
				FicheClients.this.logiqueListe.rafraichit(oncloseKey);
			});
		});					
	}

	public static RadSQLGrid createGrille(ErpFiche erpFiche) {
		SQLGridCalculatedField denomination = new SQLGridCalculatedField(0,"Dénomination") {
			@Override
			public String Calculate(ResultSet rs) throws SQLException {
				return FicheClient.getCiviliteNomPrenom(rs);
			}
		};
		RadSQLGrid grille = erpFiche.componentFactory.createRadSQLGrid("GRClients", ErpFiche.conn, "ID",
				"SELECT ID,CIVILITE, NOM ,PRENOM, CODE_POSTAL as 'Code postal', VILLE as Ville FROM CLIENT", 4,  new SQLGridCalculatedField[] {denomination});
		grille.setGridSearch(createSearch(erpFiche));
		return grille;
	}

	public static SQLGridSearch createSearch(ErpFiche erpFiche) {
		SQLGridSearch gs = new SQLGridSearch(erpFiche.componentFactory) {
			public RadTextField tfDenomination;
			public RadTextField tfCP;
			public RadTextField tfVille;

			@Override
			public void init() {
				tfDenomination = createTextField("DENOMINATION");
				tfCP = createTextField("CODE_POSTAL");
				tfVille = createTextField("Ville");
			}

			@Override
			public String getFilter(ArrayList<String> values) {
				StringBuilder sql = new StringBuilder();
				String denomination = tfDenomination.getValue();
				String cp = tfCP.getValue();
				String ville = tfVille.getValue();
				if (!"".equals(denomination)) {
					if (sql.length() > 0)
						sql.append(" AND ");
					sql.append("(NOM LIKE ?");
					sql.append("OR PRENOM LIKE ?)");
					values.add("%" + denomination + "%");
					values.add("%" + denomination + "%");					
				}
				if (!"".equals(ville)) {
					if (sql.length() > 0)
						sql.append(" AND ");
					sql.append("(VILLE LIKE ?)");
					values.add("%" + ville + "%");
				}
				if (!"".equals(cp)) {
					if (sql.length() > 0)
						sql.append(" AND ");
					sql.append("(CODE_POSTAL LIKE ?)");
					values.add(cp + "%");
				}				
				return  sql.toString();
			}

			@Override
			public RadContainer getContainer() {
				RadContainer container = createContainer("Recherche");
				//container.addLabelComponent("Dénomination :", null);
				container.addLabelComponent("Recherche par dénomination :", tfDenomination);
				container.addLabelComponent("Code postal :", tfCP);
				container.addLabelComponent("Ville :", tfVille);
				return container;
			}
		};
		return gs;
	}
}
