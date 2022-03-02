package erp;

import rad.RadTextField;

public class FicheUtilitaire {
	private ErpFiche erpFiche;
	
	public FicheUtilitaire(ErpFiche erpFiche) {
		this.erpFiche = erpFiche;
		if (erpFiche.accesDatabase())
			createContent();
	}

	private void createContent() {
		erpFiche.ajoutTitre("Utilitaire");
		RadTextField nbGeneration = erpFiche.componentFactory.createTextField("nbgeneration");
		erpFiche.ajoutSaisie("Nombre à générer :", nbGeneration);
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("GenereClient","Générer clients aléatoires").addActionListener(e -> {
			new GenereClient().genereAleatoire(ErpFiche.conn,Integer.parseInt(nbGeneration.getValue()));
			erpFiche.showMessage("Terminé");
		}));				
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("GenereArticles","Générer articles aléatoires").addActionListener(e -> {
			new GenereArticle().genereAleatoire(ErpFiche.conn,Integer.parseInt(nbGeneration.getValue()));
			erpFiche.showMessage("Terminé");			
		}));
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("GenereFactures","Générer factures aléatoires").addActionListener(e -> {
			new GenereFacture().genereAleatoire(ErpFiche.conn,Integer.parseInt(nbGeneration.getValue()));
			erpFiche.showMessage("Terminé");			
		}));		
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("Fermer","Fermer").addActionListener(e -> {
			erpFiche.close();
		}));				
	}
}
