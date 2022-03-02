package erp;

import java.util.ArrayList;

import rad.SQLGridSearch;
import rad.RadContainer;
import rad.RadSQLGrid;
import rad.RadTextField;

public class FicheArticles {
	private ErpFiche erpFiche;
	private LogiqueListe logiqueListe;

	public FicheArticles(ErpFiche erpFiche) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase())
			createContent();
	}

	private void createContent() {
		erpFiche.ajoutTitre("Liste des articles");
		RadSQLGrid grille = createGrille(erpFiche);
		logiqueListe = new LogiqueListe(erpFiche,grille,createfiche->{
			ErpFiche fiche = FicheArticles.this.erpFiche.createFiche("ARTICLE");
			new FicheArticle(fiche, grille.getSelectedLine() ,grille.getKeyValues(),oncloseKey->{
				FicheArticles.this.logiqueListe.rafraichit(oncloseKey);
			});
		});					
	}

	public static RadSQLGrid createGrille(ErpFiche erpFiche) {
		RadSQLGrid grille = erpFiche.componentFactory.createRadSQLGrid("GRARTICLE", ErpFiche.conn, "ID",
				"SELECT ID,DESIGNATION as Désignation ,PRIX_UNITAIRE as Prix FROM ARTICLE", 1, null);
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
					sql.append("(DESIGNATION LIKE ?)");
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
