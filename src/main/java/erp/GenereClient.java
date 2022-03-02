package erp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class GenereClient {
	private Random random;
	private String villeDuCp;

	public static void main(String[] args) {
		System.out.println("Début génération");
		if (ErpFiche.establish()) {
			new GenereClient().genereAleatoire(ErpFiche.conn,17);			
			System.out.println("Génération terminée");
		}
		System.out.println("Fin");		
	}

	public void genereAleatoire(Connection conn,int nbGenere) {
		random = new Random();		
		String champs = "CIVILITE,NOM,PRENOM,ADRESSE1,ADRESSE2,CODE_POSTAL,VILLE,TEL,EMAIL,ID_TVA";
		String values = "(?,?,?,?,?,?,?,?,?,?)";
		StringBuffer mySql = new StringBuffer("insert into CLIENT(" + champs + ") values " + values);
		try {
			PreparedStatement statement = conn.prepareStatement(mySql.toString());
			for (int i = 0; i < nbGenere; i++) {
				statement.setString(1, getRandom(civilites));
				statement.setString(2, getRandom(noms));
				statement.setString(3, getRandom(prenoms));
				statement.setString(4, getRue());
				statement.setString(5, getRue2());
				statement.setString(6, getCp());
				statement.setString(7, villeDuCp);
				statement.setString(8, getTel());
				statement.setString(9, getEmail());				
				statement.setLong(10,random.nextInt(4)+1 );				
				statement.addBatch();				
			}
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getEmail() {
		return getRandom(pseudos) + random.nextInt(100) + "@" + getRandom(emails);
	}

	public String getRandom(String[] tableau) {
		String valeur = tableau[random.nextInt(tableau.length)];
		if (valeur.length()<=2) return valeur;
		return valeur.substring(0, 1).toUpperCase() + valeur.substring(1).toLowerCase();
	}

	public String getRue() {
		return random.nextInt(50) + " rue " + getRandom(rues);
	}

	public String getRue2() {
		if (random.nextInt(5) == 1) {
			return "Batiment " + ('A' + random.nextInt(5));
		}
		return "";
	}

	public String getTel() {
		String tel = "0" + (6 + random.nextInt((3))) + " ";
		for (int i = 0; i < 12; i++) {
			if (i % 3 == 2) {
				tel = tel + " ";
			} else {
				tel = tel + random.nextInt(10);
			}
		}
		return tel;
	}

	public String getCp() {
		int choix = (random.nextInt(cpvilles.length) / 2) * 2;
		villeDuCp = cpvilles[choix + 1];
		return cpvilles[choix];
	}

	public static final String[] prenoms = { "JADE", "LOUISE", "EMMA", "ALICE", "AMBRE", "LINA", "ROSE", "CHLOÉ", "MIA",
			"LÉA", "ANNA", "MILA", "JULIA", "ROMY", "LOU", "INÈS", "LÉNA", "AGATHE", "JULIETTE", "INAYA", "NINA", "ZOÉ",
			"LÉONIE", "JEANNE", "IRIS", "EVA", "CHARLIE", "LOLA", "ADÈLE", "VICTOIRE", "LÉO", "GABRIEL", "RAPHAËL",
			"ARTHUR", "LOUIS", "JULES", "ADAM", "MAËL", "LUCAS", "HUGO", "NOAH", "LIAM", "GABIN", "SACHA", "PAUL",
			"NATHAN", "AARON", "MOHAMED", "ETHAN", "TOM", "EDEN", "LÉON", "NOÉ", "TIAGO", "THÉO", "ISAAC", "MARIUS",
			"VICTOR", "AYDEN", "MARTIN" };

	public static final String[] noms = { "Martin", "Bernard", "Thomas", "Petit", "Robert", "Richard", "Durand",
			"Dubois", "Moreau", "Laurent", "Simon", "Michel", "Lefebvre", "Leroy", "Roux", "David", "Bertrand", "Morel",
			"Fournier", "Girard", "Bonnet", "Dupont", "Lambert", "Fontaine", "Rousseau", "Vincent", "Muller", "Lefevre",
			"Faure", "Andre", "Mercier", "Blanc", "Guerin", "Boyer", "Garnier", "Chevalier", "Francois", "Legrand",
			"Gauthier", "Garcia", "Perrin", "Robin", "Clement", "Morin", "Nicolas", "Henry", "Roussel", "Mathieu",
			"Gautier", "Masson", "Marchand", "Duval", "Denis", "Dumont", "Marie", "Lemaire", "Noel", "Meyer", "Dufour",
			"Meunier", "Brun", "Blanchard", "Giraud", "Joly", "Riviere", "Lucas", "Brunet", "Gaillard", "Barbier",
			"Arnaud", "Martinez", "Gerard", "Roche", "Renard", "Schmitt", "Roy", "Leroux", "Colin", "Vidal", "Caron",
			"Picard", "Roger", "Fabre", "Aubert", "Lemoine", "Renaud", "Dumas", "Lacroix", "Olivier", "Philippe",
			"Bourgeois", "Pierre", "Benoit", "Rey", "Leclerc", "Payet", "Rolland", "Leclercq", "Guillaume", "Lecomte",
			"Lopez", "Jean", "Dupuy", "Guillot", "Hubert", "Berger", "Carpentier", "Sanchez", "Dupuis", "Moulin",
			"Louis", "Déschamps", "Huet", "Vasseur", "Perez", "Boucher", "Fleury", "Royer", "Klein", "Jacquet", "Adam",
			"Paris", "Poirier", "Marty", "Aubry", "Guyot", "Carre", "Charles", "Renault", "Charpentier", "Menard",
			"Maillard", "Baron", "Bertin", "Bailly", "Herve", "Schneider", "Fernandez", "Le", "Collet", "Leger",
			"Bouvier", "Julien", "Prevost", "Millet", "Perrot", "Daniel", "Le", "Cousin", "Germain", "Breton", "Besson",
			"Langlois", "Remy", "Le", "Pelletier", "Leveque", "Perrier", "Leblanc", "Barre", "Lebrun", "Marchal",
			"Weber", "Mallet", "Hamon", "Boulanger", "Jacob", "Monnier", "Michaud", "Rodriguez", "Guichard", "Gillet",
			"Etienne", "Grondin", "Poulain", "Tessier", "Chevallier", "Collin", "Chauvin", "Da", "Bouchet", "Gay",
			"Lemaitre", "Benard", "Marechal", "Humbert", "Reynaud", "Antoine", "Hoarau", "Perret", "Barthelemy",
			"Cordier", "Pichon", "Lejeune", "Gilbert", "Lamy", "Delaunay", "Pasquier", "Carlier", "Laporte" };
	public static final String[] cpvilles = { "75056", "ParisNote 1", "13055", "Marseille", "31555", "Toulouse", "6088",
			"Nice", "44109", "Nantes", "34172", "Montpellier", "35238", "Rennes", "42218", "Saint-Étienne", "21231",
			"Dijon", "49007", "Angers", "30189", "Nîmes", "63113", "Clermont-Ferrand", "13001", "Aix-en-Provence",
			"29019", "Brest", "37261", "Tours", "80021", "Amiens", "87085", "Limoges", "92012", "Boulogne-Billancourt",
			"66136", "Perpignan", "25056", "Besançon", "45234", "Orléans", "93066", "Saint-Denis", "76540", "Rouen",
			"95018", "Argenteuil", "68224", "Mulhouse", "59512", "Roubaix", "59599", "Tourcoing", "98818", "Nouméa11",
			"94028", "Créteil", "84007", "Avignon", "92004", "Asnières-sur-Seine", "93005", "Aulnay-sous-Bois", "97416",
			"Saint-Pierre", "97422", "Le Tampon", "34032", "Béziers", "94017", "Champigny-sur-Marne", "64445", "Pau",
			"6029", "Cannes", "93029", "Drancy", "2A004", "Ajaccio", "92040", "Issy-les-Moulineaux", "93051",
			"Noisy-le-Grand", "91228", "Évry-CourcouronnesNote 6", "92044", "Levallois-Perret", "95127", "Cergy",
			"97302", "Cayenne", "26362", "Valence", "18033", "Bourges", "92002", "Antony", "10387", "Troyes", "92051",
			"Neuilly-sur-Seine", "95585", "Sarcelles", "93007", "Le Blanc-Mesnil", "56121", "Lorient", "77284", "Meaux",
			"11262", "Narbonne", "85191", "La Roche-sur-Yon", "93031", "Épinay-sur-Seine", "93010", "Bondy", "83061",
			"Fréjus", "49099", "Cholet", "2691", "Saint-Quentin", "97101", "Les Abymes", "92023", "Clamart", "69256",
			"Vaulx-en-Velin", "78586", "Sartrouville", "6027", "Cagnes-sur-Mer", "94033", "Fontenay-sous-Bois", "64102",
			"Bayonne", "91174", "Corbeil-Essonnes", "91377", "Massy", "92073", "Suresnes", "6069", "Grasse", "92036",
			"Gennevilliers", "92049", "Montrouge", "97311", "Saint-Laurent-du-Maroni", "44162", "Saint-Herblain",
			"69290", "Saint-Priest", "11069", "Carcassonne", "90010", "Belfort", "19031", "Brive-la-Gaillarde", "93064",
			"Rosny-sous-Bois", "41018", "Blois", "92048", "Meudon", "13103", "Salon-de-Provence", "92062", "Puteaux",
			"71076", "Chalon-sur-Saône", "94002", "Alfortville", "51108", "Châlons-en-Champagne", "22278",
			"Saint-Brieuc", "95268", "Garges-lès-Gonesse", "59606", "Valenciennes", "65440", "Tarbes", "69029", "Bron",
			"6030", "Le Cannet", "30007", "Alès", "62041", "Arras", "16015", "Angoulême", "1053", "Bourg-en-Bresse",
			"59650", "Wattrelos", "77288", "Melun", "5061", "Gap", "62160", "Boulogne-sur-Mer", "64024", "Anglet",
			"93032", "Gagny", "59178", "Douai", "78498", "Poissy", "28085", "Chartres", "37122", "Joué-lès-Tours",
			"38421", "Saint-Martin-d'Hères", "97412", "Saint-Joseph", "77373", "Pontault-Combault", "95252",
			"Franconville", "33550", "Villenave-d'Ornon", "93073", "Tremblay-en-France", "69264",
			"Villefranche-sur-Saône", "60175", "Creil", "83118", "Saint-Raphaël", "13028", "La Ciotat", "93050",
			"Neuilly-sur-Marne", "98805", "Dumbéa11", "91549", "Sainte-Geneviève-des-Bois", "74281", "Thonon-les-Bains",
			"91477", "Palaiseau", "69282", "Meyzieu", "83129", "Six-Fours-les-Plages", "94052", "Nogent-sur-Marne",
			"71270", "Mâcon", "67447", "Schiltigheim", "97307", "Matoury", "13117", "Vitrolles", "78440", "Les Mureaux",
			"97408", "La Possession", "47001", "Agen", "88160", "Épinal", "59122", "Cambrai", "94038",
			"L'Haÿ-les-Roses", "86066", "Châtellerault", "62498", "Lens", "95306", "Herblay-sur-Seine", "95280",
			"Goussainville", "91687", "Viry-Châtillon", "94073", "Thiais", "28134", "Dreux", "33039", "Bègles", "73008",
			"Aix-les-Bains", "78146", "Chatou" };

	public static final String[] rues = { "Charles de Gaulle", "Louis Pasteur", "Victor Hugo", "Jean Jaurès",
			"Jean Moulin", "Léon Gambetta", "Général Leclerc", "Jules Ferry", "Maréchal Foch", "Georges Clémenceau" };
	public static final String[] civilites = { "Monsieur", "Madame", "", "SARL", };
	public static final String[] pseudos = { "Grimdal", "Raptor", "PetiteFee", "Pewfan", "Seltade", "Kairrin",
			"Potaaato", "Neptendus", "RainbowMan", "Exominiate", "Meyriu", "Ectobiologiste", "ItsGodHere", "MaxMadMen",
			"TankerTanker", "Felipero", "TheFlyingBat", "JustEpic", "LeGrandBlond", "Azalee", "OarisKiller",
			"LeHamster", "Dialove", "Frexs", "Rofaly", "GeoMan", "Kirna", "Gruty", "Fridame", "Fluxy", "Drastics",
			"Grimace", "Viiper", "xXSerpentXx", "Cristobal", "Scubrina", "Xanoneq", "McTimley", "LittleDank",
			"Rocketman" };

	public static final String[] emails = { "yahoo.fr", "live.com", "gmail.com", "aol.com", "laposte.net" };

}
