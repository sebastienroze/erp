package erp;

public class FicheMenu {
	private ErpFiche erpFiche;

	public FicheMenu(ErpFiche erpFiche) {
		this.erpFiche = erpFiche;
		createContent();
	}

	private void createContent() {
		erpFiche.ajoutTitre("Menu principal");
		
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("clients", "Clients").addActionListener(e -> {
			ErpFiche fiche = erpFiche.createFiche("Clients");
			new FicheClients(fiche);			
		}));	
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("TVA", "TVA").addActionListener(e -> {
			ErpFiche fiche = erpFiche.createFiche("TVA");
			new FicheTVAs(fiche);			
		}));
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("articles", "Articles").addActionListener(e -> {
			ErpFiche fiche = erpFiche.createFiche("Articles");
			new FicheArticles(fiche);			
		}));
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("factures", "Factures").addActionListener(e -> {
			ErpFiche fiche = erpFiche.createFiche("Factures");
			new FicheFactures(fiche);			
		}));		
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("parametres", "Parametres").addActionListener(e -> {
			ErpFiche fiche = erpFiche.createFiche("Parametres");
			new FicheParametres(fiche);			
		}));		
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("utils", "Utilitaire").addActionListener(e -> {
			ErpFiche fiche = erpFiche.createFiche("Utilitaire");
			new FicheUtilitaire(fiche);			
		}));		
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("Fermer","Se déconnecter").addActionListener(e -> {
			erpFiche.close();
		}));		
	}
}
