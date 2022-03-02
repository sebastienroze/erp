package erp;

import rad.ListenerRAD;
import rad.RadButton;
import rad.RadContainer;
import rad.RadSQLGrid;

public class LogiqueListe {
	private RadButton btFiche;
	private ListenerRAD onCreateFiche;
	private RadSQLGrid grid;
	public LogiqueListe(ErpFiche erpFiche, RadSQLGrid grid,ListenerRAD onCreateFiche) {
		this.onCreateFiche = onCreateFiche;
		this.grid = grid;
		grid.filter();		
		grid.addRowSelectListener(key -> {
			LogiqueListe.this.btFiche.setReadOnly(false);
		}).addDoubleClicListener(key -> {
			LogiqueListe.this.btFiche.setReadOnly(false);
			affiche();
		}).getGridSearch().addSearchListener(e-> {
			LogiqueListe.this.btFiche.setReadOnly(true);
		});
		btFiche = erpFiche.componentFactory.createButton("Fiche", "Voir fiche").addActionListener(e -> {
			affiche();
		});
		btFiche.setReadOnly(true);
		RadButton btNouveau = erpFiche.componentFactory.createButton("Nouveau", "Nouveau").addActionListener(e -> {
			grid.selectKey(-1);
			this.onCreateFiche.actionPerformed(null);
		});
		RadButton btFermer = erpFiche.componentFactory.createButton("Retour", "Retour").addActionListener(e -> {
			erpFiche.close();
		});
		RadContainer logicButtonContainer = erpFiche.componentFactory.createContainer("bouttons");
		logicButtonContainer.addComponent(btNouveau);		
		logicButtonContainer.addComponent(btFiche);		
		logicButtonContainer.addComponent(btFermer);				
		erpFiche.ajoutComposant(logicButtonContainer);
		erpFiche.ajoutGrille(grid);		
	}

	private void affiche() {
		this.onCreateFiche.actionPerformed(null);	
	}

	public void rafraichit(long key) {
		grid.filter();
		grid.selectKey(key);
	}

}
