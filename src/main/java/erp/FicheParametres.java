package erp;

import java.util.ArrayList;

import rad.SQLGridSearch;
import rad.RadContainer;
import rad.RadSQLGrid;
import rad.RadTextField;

public class FicheParametres {
	private ErpFiche erpFiche;
	private LogiqueListe logiqueListe;

	public FicheParametres(ErpFiche erpFiche) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase())
			createContent();
	}

	private void createContent() {
		erpFiche.ajoutTitre("Paramètres de l'application");
		RadSQLGrid grille = createGrille(erpFiche);
		logiqueListe = new LogiqueListe(erpFiche,grille,createfiche->{
			ErpFiche fiche = FicheParametres.this.erpFiche.createFiche("PARAMETRE");
			new FicheParametre(fiche, grille.getSelectedLine() ,grille.getKeyValues(),oncloseKey->{
				FicheParametres.this.logiqueListe.rafraichit(oncloseKey);
			});
		});					
	}

	public static RadSQLGrid createGrille(ErpFiche erpFiche) {
		RadSQLGrid grille = erpFiche.componentFactory.createRadSQLGrid("GRPARAMETRE", ErpFiche.conn, "ID",
				"SELECT ID,PARAMETRE as Paramètre ,VALEUR as Valeur FROM PARAMETRES", 1, null);
		grille.setGridSearch(createSearch(erpFiche));
		return grille;
	}

	public static SQLGridSearch createSearch(ErpFiche erpFiche) {
		SQLGridSearch gs = new SQLGridSearch(erpFiche.componentFactory) {
			public RadTextField tfRecheche;

			@Override
			public void init() {
				tfRecheche = createTextField("Recherche");
			}

			@Override
			public String getFilter(ArrayList<String> values) {
				StringBuilder sql = new StringBuilder();
				String recherche = tfRecheche.getValue();
				if (!"".equals(recherche)) {
					if (sql.length() > 0)
						sql.append(" AND ");
					sql.append("(PARAMETRE LIKE ?)");
					sql.append("OR (VALEUR LIKE ?)");
					values.add("%" + recherche + "%");
					values.add("%" + recherche + "%");
				}
				return sql.toString();
			}

			@Override
			public RadContainer getContainer() {
				RadContainer container = createContainer("Recherche");
				container.addLabelComponent("Recherche :", tfRecheche);
				return container;
			}
		};
		return gs;
	}
}
