package erp;

import rad.RadButton;
import rad.RadTextField;

public class FicheLogin {
	
	private ErpFiche fiche;
	private String validLogin;
	public FicheLogin(ErpFiche fiche) {
		this.fiche = fiche;
		createContent();
	} 
	
	private void createContent() {
		RadTextField saisieUtilisateur = fiche.componentFactory.createTextField("txtLogin",10);
		RadTextField saisieMotDePasse = fiche.componentFactory.createTextField("txtMdp",20);
		RadButton btLogin = fiche.componentFactory.createButton("Login","Login");
		fiche.ajoutTitre("Login");
		fiche.ajoutSaisie("Utilisateur", saisieUtilisateur);
		fiche.ajoutSaisie("Mot de passe", saisieMotDePasse);
		btLogin.addActionListener(e -> { 
			FicheLogin.this.validLogin =saisieUtilisateur.getValue();
			ErpFiche ficheMenu = fiche.createFiche("Menu");
			new FicheMenu(ficheMenu);			
		});
		fiche.ajoutComposant(btLogin);
		fiche.setFicheSuivante(fiche);
	}
	
	public String getLoginName() {
		return validLogin;
	}		

}
