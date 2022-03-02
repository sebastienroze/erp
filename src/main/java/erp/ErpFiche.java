package erp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import rad.DataFieldRAD;
import rad.DataRAD;
import rad.KeySelectListener;
import rad.ListenerRAD;
import rad.RadComponent;
import rad.RadComponentFactory;
import rad.RadSQLGrid;

public abstract class ErpFiche {
	public static Connection conn = null;
	protected ErpFiche ficheSuivante;
	protected ErpFiche fichePrecedente;
	protected RadComponentFactory componentFactory;
	public static String canClose = null;

	protected abstract void ajoutTitre(String titre);

	protected abstract ErpFiche createFiche(String nom);

	protected abstract void ajoutComposant(RadComponent comp);

	protected abstract void ajoutGrille(RadSQLGrid grille);

	protected abstract void ajoutSaisie(String label, RadComponent component);

	protected abstract void showMessage(String message);
	protected abstract void afficheFichier(String nomFichier);

	protected abstract void close();

	protected abstract DataRAD createDataRad(String tableName, String keyField);

	public ErpFiche getFicheSuivante() {
		return ficheSuivante;
	}

	public void setFicheSuivante(ErpFiche ficheSuivante) {
		this.ficheSuivante = ficheSuivante;
	}

	public static boolean establish() {
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/erp?allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris",
						"erpuser", "erppass");
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("manque com.mysql.jdbc.Driver");
				System.out.println("***** Desktop ****");
				System.out.println("Dans Project Explorer, Bouton droit Properties");
				System.out.println("Java build path / Libraries");
				System.out.println("Selection Modulepath bouton Add External JARs");
				System.out.println("Chercher dans C:\\Program Files (x86)\\MySQL\\Connector J 8.0");
				System.out.println("***** Tomcat ****");
				System.out.println("Dans Servers, double clic sur Tomcat");
				System.out.println("Open launch configuration");
				System.out.println("Classpath : Ajouter le Jar dans User entries");
				return false;
			}
		}
		return true;
	}

	public boolean accesDatabase() {
		if (establish())
			return true;
		ajoutTitre("Pas de connexion à la base de donnée !");
		ajoutComposant(componentFactory.createButton("Retour", "Retour").addActionListener(e -> {
			close();
		}));
		return false;
	}

	public static String getCanClose() {
		return canClose;
	}

	public static void setCanClose(String canClose) {
		ErpFiche.canClose = canClose;
	}

	protected void createFicheSelectionGrille(ErpFiche ficheSelectionGrille, String titre, DataFieldRAD dataField,
			RadSQLGrid grille, KeySelectListener onSelection) {
		String value = dataField.getValue();
		grille.filter();		
		if (value != null && !"".equals(value))
			grille.selectKey(Long.parseLong(value));
		ficheSelectionGrille.ajoutTitre(titre);
		ficheSelectionGrille.ajoutGrille(grille);
		ListenerRAD selectListener = e -> {
			long selectedKey = grille.getSelectedKey();
			if (selectedKey == -1)
				dataField.setValue(null);
			else
				dataField.setValue(grille.getSelectedKey() + "");
			onSelection.KeySelected(grille.getSelectedKey());
			ficheSelectionGrille.close();

		};
		grille.addDoubleClicListener(selectListener);
		ficheSelectionGrille.ajoutComposant(ficheSelectionGrille.componentFactory.createButton("ok", "Sélectionner")
				.addActionListener(selectListener));
		ficheSelectionGrille.ajoutComposant(
				ficheSelectionGrille.componentFactory.createButton("clear", "Aucun").addActionListener(e -> {
					grille.selectKey(-1);
				}));
		ficheSelectionGrille.ajoutComposant(
				ficheSelectionGrille.componentFactory.createButton("cancel", "Anuler").addActionListener(e -> {
					ficheSelectionGrille.close();
				}));
	}

	public static String concateneStringAvecEspace(String s1, String s2) {
		if (s2 == null || "".equals(s2)) {
			return s1;
		}
		if ("".equals(s1)) {
			return s2;
		}
		return String.valueOf(s1) + " " + s2;
	}
}
