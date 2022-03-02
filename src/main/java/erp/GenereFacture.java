package erp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

public class GenereFacture {
	private Random random;
	private String date_facture;
	private String montant;
	private ResultSet rs = null;
	private Connection conn;

	public static void main(String[] args) {
		System.out.println("Début génération");
		if (ErpFiche.establish()) {
			new GenereFacture().genereAleatoire(ErpFiche.conn, 1);
			System.out.println("Génération terminée");
		}
		System.out.println("Fin");
	}

	public void genereAleatoire(Connection conn, int nbGenere) {
		for (int i = 0; i < nbGenere; i++) {
			genereUnAleatoire(conn);
		}
	}

	public void genereUnAleatoire(Connection conn) {
		this.conn = conn;
		random = new Random();
		String champs = "NUMERO,ETAT,DATE_FACTURE,REMISE,ID_CLIENT,ID_TVA";
		String values = "(?,?,?,?,?,?)";
		StringBuffer mySql = new StringBuffer("insert into FACTURE(" + champs + ") values " + values);
		try {
			PreparedStatement statement = conn.prepareStatement(mySql.toString(), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, getNumero());
			statement.setString(2, getRandom(etats));
			statement.setString(3, getDate_Facture());
			statement.setString(4, getDate_Remise());
			statement.setLong(5, getRandomIDFromTable("CLIENT"));
			statement.setLong(6, rs.getLong("ID_TVA"));
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			rs.next();
			genereLignes(rs.getLong(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void genereLignes(Long idFacture) {
		String champs = "LIGNE,DESIGNATION,PRIX_UNITAIRE,QUANTITE,MONTANT,ID_ARTICLE,ID_FACTURE";
		String values = "(?,?,?,?,?,?,?)";
		StringBuffer mySql = new StringBuffer("insert into LIGNE_FACTURE(" + champs + ") values " + values);
		int nbLignes = random.nextInt(30) + 5;
		try {
			PreparedStatement statement = conn.prepareStatement(mySql.toString());
			for (int i = 0; i < nbLignes; i++) {
				statement.setInt(1, i + 1);
				statement.setLong(6, getRandomIDFromTable("ARTICLE"));
				statement.setString(2, rs.getString("DESIGNATION"));
				statement.setFloat(3, rs.getFloat("PRIX_UNITAIRE"));
				statement.setString(4, getQuantite());
				statement.setString(5, getMontant());
				statement.setLong(7, idFacture);
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getMontant() {
		return montant;
	}

	private String getQuantite() throws SQLException {
		Float prix = rs.getFloat("PRIX_UNITAIRE");
		if (prix < 2) {
			int quantite = 100 + random.nextInt(1000);
			montant = "" + (prix * (float) quantite);
			return quantite + "";
		} else if (prix > 2000) {
			float quantite = 0.1F + random.nextFloat();
			montant = "" + (prix * (float) quantite);
			return quantite + "";
		}
		int quantite = 1 + random.nextInt(10);
		montant = "" + (prix * (float) quantite);
		return quantite +"";
	}

	public Long getRandomIDFromTable(String tableName) throws SQLException {
		Statement statement = conn.createStatement();
		rs = statement.executeQuery("SELECT * FROM " + tableName + " ORDER BY RAND() LIMIT 1");
		rs.next();
		return rs.getLong("ID");
	}

	public String getDate_Remise() {
		return random.nextInt(1000) + "";
	}

	public String getNumero() {
		LocalDate startDate = LocalDate.of(2010, 1, 1); // start date
		long start = startDate.toEpochDay();
		LocalDate endDate = LocalDate.now(); // end date
		long end = endDate.toEpochDay();
		int difference = (int) (end - start);
		long randomEpochDay = start + (long) random.nextInt(difference);
		LocalDate randomDate = (LocalDate.ofEpochDay(randomEpochDay));
		date_facture = randomDate.toString();
		return (randomDate.getYear() - 2000) + fmt0(randomDate.getDayOfYear(), 3) + fmt0(random.nextInt(100), 2);
	}

	public String fmt0(int i, int taille) {
		return fmt0(i + "", taille);
	}

	public String fmt0(String str, int taille) {
		StringBuilder strfmt = new StringBuilder();
		for (int i = str.length(); i < taille; i++) {
			strfmt.append("0");
		}
		strfmt.append(str);
		return strfmt.toString();
	}

	public String getDate_Facture() {
		return date_facture;
	}

	public String getRandom(String[] tableau) {
		String valeur = tableau[random.nextInt(tableau.length)];
		if (valeur.length() <= 2)
			return valeur;
		return valeur.substring(0, 1).toUpperCase() + valeur.substring(1).toLowerCase();
	}

	public static final String[] etats = { "S", "P", "F" };

}
