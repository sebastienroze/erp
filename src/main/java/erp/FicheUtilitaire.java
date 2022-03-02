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
		erpFiche.ajoutSaisie("Nombre � g�n�rer :", nbGeneration);
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("GenereClient","G�n�rer clients al�atoires").addActionListener(e -> {
			new GenereClient().genereAleatoire(ErpFiche.conn,Integer.parseInt(nbGeneration.getValue()));
			erpFiche.showMessage("Termin�");
		}));				
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("GenereArticles","G�n�rer articles al�atoires").addActionListener(e -> {
			new GenereArticle().genereAleatoire(ErpFiche.conn,Integer.parseInt(nbGeneration.getValue()));
			erpFiche.showMessage("Termin�");			
		}));
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("GenereFactures","G�n�rer factures al�atoires").addActionListener(e -> {
			new GenereFacture().genereAleatoire(ErpFiche.conn,Integer.parseInt(nbGeneration.getValue()));
			erpFiche.showMessage("Termin�");			
		}));		
		erpFiche.ajoutComposant(erpFiche.componentFactory.createButton("Fermer","Fermer").addActionListener(e -> {
			erpFiche.close();
		}));				
	}
}
