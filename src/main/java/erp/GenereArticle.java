package erp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class GenereArticle {
	private Random random;

	public static void main(String[] args) {
		System.out.println("Début génération");
		if (ErpFiche.establish()) {
			new GenereArticle().genereAleatoire(ErpFiche.conn, 1);
			System.out.println("Génération terminée");
		}
		System.out.println("Fin");
	}

	public void genereAleatoire(Connection conn, int nbGenere) {
		random = new Random();
		String champs = "DESIGNATION,PRIX_UNITAIRE";
		String values = "(?,?)";
		StringBuffer mySql = new StringBuffer("insert into ARTICLE(" + champs + ") values " + values);
		try {
			PreparedStatement statement = conn.prepareStatement(mySql.toString());
			for (int i = 0; i < nbGenere; i++) {
				statement.setString(1, getRandom(articles));
				statement.setString(2, getPrix());
				;
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPrix() {
		StringBuilder prix = new StringBuilder();
		int decimales = 0;
		switch (random.nextInt(3)) {
		case 0: {
			prix.append(random.nextInt(2) + ".");
			decimales = 5;
			break;
		}
		case 1: {
			prix.append(random.nextInt(100) + ".");
			decimales = 2;
			break;
		}
		case 2: {
			prix.append(random.nextInt(100000));
			decimales = 0;
			break;
		}
		}
		for (; decimales > 0; decimales--) {
			prix.append(random.nextInt(10));
		}
		return prix.toString();
	}

	public String getRandom(String[] tableau) {
		String valeur = tableau[random.nextInt(tableau.length)];
		if (valeur.length() <= 2)
			return valeur;
		return valeur.substring(0, 1).toUpperCase() + valeur.substring(1).toLowerCase();
	}

	public static final String[] articles = { "Abaca", "Abélie à grandes fleurs", "Abricotier",
			"Abricotier des Antilles", "Abricotier du Japon", "Abricotier de Saint-Domingue", "Abricotier d'Afrique",
			"Absinthe", "Absinthe maritime", "Abutilon d'Avicenne", "Acacia", "Acajou", "Acajou amer", "Acanthe",
			"Acanthe à feuilles molles", "Ache", "Ache inondée", "Ache des montagnes", "Achillée",
			"Achillée millefeuille", "Achillée musquée", "Achillée sternutatoire", "Achillée à feuilles d'agératum",
			"Aconit napel", "Aconit tue-loup", "Acore", "Acore odorant", "Actée en épi", "Actée rouge", "Actinidia",
			"Adonide", "Adonide goutte-de-sang", "Adonis", "Adonis d'été", "Agapanthe", "Agapanthe en ombelles",
			"Agastache", "Agave", "Agave américain", "Agérate", "Agneau chaste", "Agrostide", "Agrume", "Aigremoine",
			"Aigremoine eupatoire", "Ail", "Ail blanc", "Ail caréné", "Ail cultivé", "Ail des jardins", "Ail des ours",
			"Ail doré", "Ail jaune", "Ail rocambole", "Ail rose", "Ail à fleurs de narcisse", "Ail à tête ronde",
			"Ailante", "Airelle", "Ajonc", "Ajonc d'Europe", "Ajonc de Provence", "Ajuga", "Albizia", "Alchémille",
			"Alchémille des Alpes", "Alchémille vulgaire", "Alfa", "Algue", "Aliboufier", "Alisier", "Alisier blanc",
			"Alisier torminal", "Alkékenge", "Alliaire officinale", "Allier", "Aloès", "Alouchier", "Alpinia",
			"Alpiste", "Alstrœmère", "Alvier", "Alysse", "Amande de terre", "Amandier", "Amarante", "Amarante blanche",
			"Amarante crête de coq", "Amarante hybride", "Amarante queue de renard", "Amarante tricolore", "Amaryllis",
			"Amélanchier", "Amélanchier à feuilles ovales", "Ammi", "Ammi visnage", "Ammi élevé", "Amour en cage",
			"Anacardier", "Anacycle tomenteux", "Ananas", "Ancolie", "Ancolie commune", "Ancolie des Alpes",
			"Ancolie noirâtre", "Andromède", "Androsace", "Andryale à feuilles entières", "Anémone", "Anémone de Grèce",
			"Anémone fausse renoncule", "Anémone hépatique", "Anémone sylvie", "Anémone des fleuristes",
			"Anémone des jardins", "Anémone du Japon", "Aneth doux", "Aneth odorant", "Angélique", "Angélique des bois",
			"Angélique de Chine", "Angélique japonaise", "Angélique officinale", "Anis", "Anis vert", "Anis étoilé",
			"Antennaire dioïque", "Anthémis", "Anthémis des teinturiers", "Anthrisque", "Anthrisque sauvage",
			"Arabette", "Arabette des dames", "Arachide", "Aralia", "Aralie du Japon", "Araucaria", "Arbousier",
			"Arbre", "Arbre à gutta-percha", "Arbre-à-surelle", "Arbre de Judée", "Arbre de soie", "Arbre à beurre",
			"Arbre bouteille", "Arbre à caoutchouc", "Arbre aux clochettes d'argent", "Arbre aux quarante écus",
			"Arbre aux saucisses", "Arbre du voyageur", "Arbre à pain", "Arbre à suif", "Aréquier", "Arganier",
			"Argémone", "Argousier", "Aristoloche", "Aristoloche clématite", "Aristoloche sipho", "Armoise blanche",
			"Armoise commune", "Armérie", "Armérie des Alpes", "Armérie maritime", "Arnica", "Arnica des montagnes",
			"Arolle", "Aronia", "Arroche", "Arrow-root des Antilles", "Artichaut", "Arum", "Arum d'Éthiopie", "Asaret",
			"Asclépiade", "Asiminier trilobé", "Asparagus", "Asperge", "Aspérule", "Asphodèle", "Asphodèle blanc",
			"Asplenium", "Aster", "Aster à feuilles de linaire", "Aster à feuilles lancéolées", "Aster amelle",
			"Aster maritime", "Astilbé", "Astragale", "Astragale à feuilles de réglisse", "Astragale de Montpellier",
			"Astragale pourpre", "Astrance", "Astrophytum", "Aubergine", "Aubépine", "Aubépine officinale", "Aubour",
			"Aubriétie", "Aucuba", "Aulne", "Aulne blanc", "Aulne glutineux", "Aunée officinale", "Aurone",
			"Averse rose", "Avocatier", "Avoine", "Avoine à chapelets", "Avoine cultivée", "Avoine élevée", "Ayote",
			"Azalée", "Azérolier", "Azurite (flore)", "Badiane chinoise", "BaguenaudierBalata", "Balisier",
			"Balisier bihaï", "Ballote", "Balsa", "Balsamine", "Balsamine de Balfour", "Balsamine de l'Himalaya",
			"Balsamine des bois", "Balsamine des jardins", "Bambou", "Bananier", "Bananier nain", "Baobab africain",
			"Barbadine", "Barbe de Jupiter", "Bardane", "Basilic", "Baumier", "Bayal", "Beaucarnea", "Bégonia",
			"Bélimbe", "Belladone", "Belle de nuit", "Belombra", "Bénincasa", "Benoîte", "Berbéris",
			"Berbéris à feuilles de buis", "Berce", "Berce du Caucase", "Berce sphondyle", "Bergamote", "Bétel",
			"Bétoine", "Bette", "Betterave", "Bibacier", "Bifora rayonnant", "Bigaradier", "Bigarreautier", "Bignone",
			"Bilbergie", "Bistorte", "Blackstonie perfoliée", "Blé", "Blé dur", "Blé noir", "Blechnum en épi", "Blette",
			"Bleuet des champs", "Bleuet des montagnes", "Bois de sainte Lucie", "Bois d'ortie", "Bois joli",
			"Bonnet d'évêque", "Bougainvillée", "Bouleau", "Bouleau pubescent", "Bouleau verruqueux", "Bourdaine",
			"Bourrache officinale", "Bourreau des arbres", "Bourse à pasteur", "Brahéa doux", "Brède mafane", "Brize",
			"Brocoli", "Brome", "Brome faux-seigle", "Brome mou", "Brome stérile", "Brome des toits", "Bromelia",
			"Brugmansia", "Brunelle", "Brunelle commune", "Brunelle laciniée", "Brunelle à grandes fleurs", "Bruyère",
			"Bruyère arborescente", "Bruyère cendrée", "Bryone dioïque", "Buddleia de David", "Bugle", "Buglosse",
			"Bugrane", "Buis", "Buis de Mahon", "Buisson ardent", "Bulbocode", "Buplèvre", "Buplèvre en faux",
			"Buplèvre à feuilles rondes", "Busserole", "Butome", "Cacaoyer", "Cachiman épineux", "Cactée", "Cactus",
			"Caesalpinia", "Caféier", "Caille-lait jaune", "Caimitier", "Cakile", "Caladium", "Calament", "Calcéolaire",
			"Calebasse (Gourde ou fruit du Calebassier)", "Calla des marais", "Callicarpe", "Callistemon", "Callune",
			"Calypso bulbosa", "Camélée", "Camélia", "Camélia du Japon", "Camomille", "Camomille romaine", "Campanule",
			"Campanule agglomérée", "Campanule gantelée", "Campanule raiponce", "Campanule à feuilles de Cochléaire",
			"Campanule à feuilles de pêcher", "Camphrier", "Canéficier", "Canna", "Cannabis", "Canche",
			"Canne de Provence", "Canne à sucre", "Cannelier", "Canola", "Capillaire de Montpellier", "Câprier",
			"Capucine", "Carambole", "Cardamine", "Cardon", "Cardère laciniée", "Cardère sauvage", "Carline",
			"Carline acaule", "Carline en corymbe", "Carline à feuilles d'acanthe", "Cardon", "Carex", "Carotte",
			"Caroubier", "Carthame", "Carthame des teinturiers", "Carvi", "Cassier", "Casse", "Cassia", "Cassissier",
			"Casuarina", "Cataire", "Catalpa", "Catananche", "Cattleya", "Caulerpe", "Céanothe", "Cédratier", "cédrèle",
			"Cèdre", "Cèdre à encens", "Céleri", "Céleri-rave", "Célosie", "Célosie crête-de-coq", "Centaurée",
			"Centaurée du solstice", "Centaurée noirâtre", "Centaurée rude", "Centaurée scabieuse",
			"Centaurée à feuilles simples", "Centaurée à grosse tête", "Centaurea jacea", "Centranthe",
			"Centranthe rouge", "Céphalanthère", "Céphalanthère de Damas", "Céphalanthère rouge",
			"Céphalanthère à feuilles étroites", "Céraiste", "Cerfeuil", "Cerfeuil commun", "Cerfeuil hirsute",
			"Cerfeuil musqué", "Cerfeuil penché", "Cerfeuil tubéreux", "Cerfeuil de Villars", "Cerisier",
			"Cerisier de sainte Lucie", "Cerisier des Antilles", "Cerisier de Tahiti", "Cétérach", "Chadèque",
			"Chamédorée", "Chanvre", "Chanvre de Manille", "Chardon", "Chardon à foulons", "Chardon aux ânes",
			"Chardon bénit", "Chardon bleu des Alpes", "Chardon d'Écosse", "Chardon d'Espagne", "Chardon-Marie",
			"Chardon roulant", "Chardon penché", "Charme", "Charme-houblon", "Châtaigne d'eau", "Châtaignier",
			"Chataire", "Chayote", "Chélidoine", "Chêne", "Chêne blanc d'Amérique", "Chêne chevelu",
			"Chêne de Bannister", "Chêne des marais", "Chêne écarlate", "Chêne kermès", "Chêne-liège",
			"Chêne pédonculé", "Chêne pubescent", "Chêne rouge", "Chêne rouvre", "Chêne saule", "Chêne tauzin",
			"Chêne vert", "Chénopode", "Chérimolier", "Chervis", "Chèvrefeuille", "Chèvrefeuille des bois",
			"Chèvrefeuille des haies", "Chèvrefeuille des jardins", "Chèvrefeuille d'ItalieChèvrefeuille du Japon",
			"Chicon", "Chicorée", "Chicorée endive", "Chicorée sauvage", "Chicot du Canada", "Chiendent",
			"Chlorophytum", "Chou", "Chou de Bruxelles", "Chou-fleur", "Chou marin", "Chou palmiste", "Chou romanesco",
			"Chou-rave", "Christophine", "Chou-chou", "Chrysanthème", "Ciboule", "Ciboule de Chine", "Ciboulette",
			"Cierge", "Ciguë", "Cinéraire", "Cirse", "Cirse acaule", "Cirse commun", "Cirse de Montpellier",
			"Cirse des champs", "Cirse des marais", "Cirse laineuxCiste", "Citronnelle", "Citronnier", "Citrange",
			"Citrouille", "Claytone de Cuba", "Clématite", "Clématite des haies", "Clématite flammette",
			"Clématite des Alpes", "Clématite à Vrille", "Clématite Dressée", "Clématite d'Armand",
			"Clématites des montagnes", "Clémentinier", "Cleome", "Clérodendron", "Clivia", "Cobée", "Cobée grimpante",
			"Coca", "Coco-fesse", "Cocotier", "Cocotier du Chili", "Coccothrinax argenté", "Cœur-de-Marie",
			"Cognassier", "Cognassier du Japon", "Coquelicot", "Colchique", "Colchique d'automne",
			"Coloquinte officinale", "Colza", "Combava", "Concombre", "Concombre d'âne", "Concombre des Antilles",
			"Conifère", "Consoude", "Consoude officinale", "Copalme", "Coquelicot", "Coquelicot bleu de l'Himalaya",
			"Coquelourde des jardins", "Coqueret du Pérou", "Corbeille d'argent", "Corbeille d'or", "Cordyline",
			"Coréopsis", "Coriandre", "Cormier", "Corne de cerf", "Corne-de-cerf didyme", "Corne d'élan", "Cornichon",
			"Cornouiller", "Cornouiller femelle", "Cornouiller mâle", "Cornouiller sanguin", "Coronille",
			"Coronille bigarrée", "Corosse", "Corossol", "Corydale", "Corydale jaune", "Cosmos", "Coste", "Cotonéaster",
			"Cotonnier", "Coucou", "Coudrier", "Courbaril", "Couronne d'épines", "Couronne impériale", "Coyor",
			"Crambé", "Crépide", "Cresson", "Cresson alénois", "Cresson de fontaine", "Cresson de Para",
			"Cresson de terre", "Crocus", "Crocus printanier", "Croix de Malte", "Crosne du Japon", "Croton", "Cumin",
			"Cumin des prés", "Cupidone", "Curcuma", "Cuscute", "Cuscute d'Europe", "Cuscute du thym", "Cycas",
			"Cyclamen", "Cymbalaire des murs", "Cymbidium", "Cynodon", "Cynoglosse",
			"Cynoglosse à feuilles de giroflée", "Courge", "Courgette", "Courge cireuse", "Courge musquée",
			"Courge de Siam", "Courge spaghetti", "Courge torchon", "Cyprès", "Cyprès chauve", "Cyprès de Lawson",
			"Cyprès de Nootka", "Cyprès de Provence", "Cypripède acaule", "Cytinelle", "Cytise",
			"Cytise à feuilles sessiles", "Dactyle", "Dactyle pelotonné", "Dactylorhize de mai", "Dahlia", "Daikon",
			"Dame d'onze heures", "Daphné garou", "Dattier", "Datura", "Deinanthe", "Dent de chien", "Dentelaire",
			"Dentelaire du Cap", "Digitale", "Digitale jaune", "Dionée attrape-mouche", "Diplotaxis",
			"Diplotaxis fausse roquette", "Dolique", "Dompte-venin funèbre", "Dompte-venin officinal", "Doronic",
			"Doronic plantain", "Douce-amère", "Douglas", "Dragonnier fragrant", "Drave", "Droséra", "Dryade",
			"Dryade à huit pétales", "Durion", "Échalote", "Echinops", "Edelweiss", "Églantier", "Égopode podagraire",
			"Éleusine", "Élyme des marais", "Encyclia", "Endive", "Engrain", "Épervière", "Épervière laineuse",
			"Épervière mixte", "Épervière orangée", "Épervière velue", "Éphèdre", "Épiaire", "Épiaire des bois",
			"Épiaire officinale", "Épicéa", "Épicéa bleu", "Épicéa commun", "Épilobe", "Épilobe en épi",
			"Épilobe à grandes fleurs", "Épinard", "Épine du Christ", "Épine-vinette", "Épinette blanche",
			"Épipactis des marais", "Épipactis pourpre noirâtre", "Épipactis à larges feuilles", "Éponge végétale",
			"Épurge", "Érable", "Érable argenté", "Érable champêtre", "Érable de Montpellier", "Érable plane",
			"Érable sycomore", "Érigéron", "Érine des Alpes", "Estragon", "Eucalyptus", "Eupatoire",
			"Eupatoire à feuilles de chanvre", "Euphorbe", "Euphorbe characias", "Euphorbe hétérophylle",
			"Euphorbe maculée", "Euphorbe maritime", "Euphraise de Rostkov", "Euphorbia pulcherrima", "Fabiana",
			"Fausse badiane", "Faux acacia", "Faux de Verzy", "Feijoa", "Fenouil", "Fenouil des Alpes", "Fenugrec",
			"Fer à cheval", "Férule", "Festulolium", "Fétuque", "Fétuque bleue", "Fétuque élevée",
			"Fétuque ovine (Fétuque des moutons)", "Fétuque des prés", "Fétuque rouge", "Fève", "Fèverole",
			"Févier d'Amérique", "Ficaire", "Ficoïde", "Ficoïde orange", "Ficoïde à feuilles en cœur", "Ficus",
			"Ficus retusa", "Figuier", "Figuier de Barbarie", "Figuier des Banyans", "Figuier des pagodes",
			"Figuier pleureur", "Filao", "Filaria", "Filipendule", "Flamboyant", "Fleur de la passion", "Fléole",
			"Flouve odorante", "Fonio", "Forsythia", "Fougère", "Fougère arborescente", "Fougère de Boston",
			"Fougère mâle", "Fragon faux houx", "Fraisier", "Fraisier des bois", "Fraisier des Indes", "Framboisier",
			"Frangipanier", "Frankenia", "Fraxinelle", "Freesia", "Fritillaire", "Fritillaire impériale",
			"Fritillaire pintade", "Fritillaire des Pyrénées", "Fruit à pain mexicain", "Frêne",
			"Frêne à feuilles étroites", "Frêne à fleurs", "Frisée", "Fromager", "Froment", "Fromental", "Fumeterre",
			"Fusain d'Europe", "Fuchsia", "Fustet", "Fève de cacao", "Févier d'Amérique", "Gagée", "Gaillarde",
			"Gaillet blanc", "Gaillet odorant", "Galéga officinal", "Garance des teinturiers", "Garance voyageuse",
			"Gardénia", "Gaude", "Gazania", "Génépi.", "Génépi blanc", "Genévrier", "Genévrier commun",
			"Genévrier cade", "Genévrier rampant", "Genévrier sabine", "Géranium", "Géranium sanguin", "Gerbera",
			"Germandrée", "Germandrée scorodoine", "Germandrée petit chêne", "Genêt", "Genêt à balais", "Genêt ailé",
			"Genêt cendré", "Genêt d'Angleterre", "Genêt d'Espagne", "Genêt des teinturiers", "Genêt épineux",
			"Genêt poilu", "Gentiane", "Gentiane Asclépiade", "Gentiane amère", "Gentiane des champs",
			"Gentiane ciliée", "Gentiane croisette", "Gentiane de Clusius", "Gentiane de Koch", "Gentiane de printemps",
			"Gentiane des marais", "Gentiane des neiges", "Gentiane jaune", "Gesse", "Gesse aphaca", "Gesse des bois",
			"Gesse printanière", "Gesse tubéreuse", "Gingembre", "Gingembre sauvage", "Ginkgo", "Ginseng", "Giroflée",
			"Giroflée des dunes", "Giroflier", "Giroselle", "Glaïeul", "Glaïeul d'Abyssinie", "Glaïeul des moissons",
			"Gléchome", "Gléichénia", "Globulaire", "Globulaire buissonnante", "Glycérie", "« Glycine »",
			"Goodyère rampante", "Gouet", "Gouet tacheté", "Gourbet", "Gourde", "Goyavier", "Goyavier du Chili",
			"Grand Rhinanthe", "Grande astrance", "Grande aunée", "Grande bardane", "Grande capucine", "Grande ciguë",
			"Grande mauve", "Grande ortie", "Grassette", "Grenadier", "Grémil", "Grémil pourpre bleu", "Griffe-de-Chat",
			"Grindélia", "Groseillier", "Guarana", "Gueule de loup", "Gui", "Guimauve officinale",
			"Gymnocarpe de Robert", "Gypsophile", "Guzmania", "Hamatocactus", "Haricot", "Haricot commun",
			"Haricot d'Espagne", "Haricot de Lima", "Hélianthème", "Hélianthème à gouttes", "Hélianthème des Apennins",
			"Héliotrope", "Hellébore", "Hémérocalle", "Hémérocalle fauve", "Hémérocalle jaune", "Henné", "Hépatiques",
			"Herbe aux chats", "Herbe aux écus", "Herbe aux massues", "Herbe aux mouches", "Herbe de la pampa", "Hêtre",
			"Hêtre à grandes feuilles", "Hêtre commun", "Hêtre d'Orient", "Heuchère", "Hévéa", "Hibiscus", "Hièble",
			"Homme pendu", "Hortensia", "Hosta", "Houblon", "Houlque", "Houx", "Hoya", "Hyoséride rayonnante", "Hysope",
			"Ibéride", "Ibéris", "Ibéris toujours vert", "Icaquier", "If", "If commun", "If du Japon", "Igname",
			"Ilima rouge", "Immortelle", "Immortelle commune", "Impatiente", "Incarvillée", "Indigotier", "Inule",
			"Inule visqueuse", "Ipomée", "Ipécacuanha", "Iris", "Iris des Pyrénées", "Iris des marais", "Iris du Japon",
			"Iris versicolore", "Isoète", "Ive", "Ivraie", "Jacaranda", "Jacée", "Jacinthe", "Jacinthe des bois",
			"Jacinthe d'eau", "Jacinthe du Cap", "Jacobée", "Jalap", "Jaquier", "Jasione", "Jasione vivace", "Jasmin",
			"Jasmin d'hiver", "Jasmin ligneux", "Jasmin du Cap", "Jatropha", "Jojoba", "Jonc", "Jonc fleuri",
			"Jonquille", "Joubarbe", "Joubarbe des montagnes", "Joubarbe des toits", "Joubarbe du calcaire",
			"Joubarbe à toile d'araignée", "Jujubier", "Julienne", "Julienne de Mahon", "Jusquiame", "Jussiée", "Jute",
			"Kaki", "Kalanchoé", "Kapokier", "Karité", "Kentia", "Khat", "Kermès", "Kerria", "Ketmie", "Keulérie",
			"Kiwai", "Kiwi", "Kiwi de Sibérie", "Knautie", "Kumquat", "Lagure", "Laiteron", "Laîche",
			"Laîche à épis pendants", "Laitue", "Laitue cultivée", "Laitue des vignes", "Laitue scariole",
			"Laitue vivace", "Lamier", "Lamier jaune", "Lamier pourpre", "Lampourde", "Lampsane", "Lampsane commune",
			"Langue de belle-mère", "Larmes de Job", "Latanier bleu", "Lauréole", "Lavande", "Lavande aspic",
			"Lavande de mer", "Lavande à toupet", "Lavande du désert", "Lavandin", "Lavatère", "Lentille cultivée",
			"Lentille d'eau", "Lentisque", "Léontodon", "Leuzée", "Liane-de-feu", "Liane de jade", "Lichen", "Liciet",
			"Lierre", "Lierre grimpant", "Lierre terrestre", "Ligulaire", "Lilas", "Lilas commun", "Lime", "Limettier",
			"Limodore à feuilles avortées", "limon", "Laurier", "Laurier rose", "Laurier sauce", "Laurier-cerise",
			"Laurier de Saint-Antoine", "Lin", "Lin bisannuel", "Lin campanulé", "Lin cultivé", "Lin dressé",
			"Lin à feuilles étroites", "Lin jaune", "Lin de Narbonne", "Linaigrette", "Linaigrette à feuilles larges",
			"Linaigrette à feuilles étroites", "Linaire", "Linaire commune", "Linaire élatine", "Linnée",
			"Liparis de Loesel", "Liquidambar", "Livèche", "Liseron", "Liseron des champs", "Liseron des dunes",
			"Liseron des haies", "Liseron tricolore", "Listère à feuilles ovales", "Listère cordée", "Litchi",
			"Livèche", "Lobélie", "Lobélie brûlante", "Lobélie érine", "Lotier", "Lotier corniculé", "Lotier des Alpes",
			"Lotier des rocailles", "Lotier-pois", "Lotus", "Lotus sacré", "Luffa", "Lunaire",
			"Lunetière à oreillettes", "Lupin", "Luzerne", "Luzerne arborescente", "Luzerne cultivée",
			"Luzerne d'Arabie", "Luzerne lupuline", "Luzerne marine", "Luzule", "Lychnide", "Lyciet", "Lycopode",
			"Lysichite blanc", "Lysimaque commune", "Lysimaque nummulaire", "Lys (ou Lis)", "Lis de mer",
			"Lys des Incas", "Lis des Pyrénées", "Lis d'un jour", "Lis maritime", "Lis Martagon", "Lys rouge",
			"Lis tigré", "Macadamier", "Maceron", "Mâche", "Mâcre nageante", "Mâcre bicorne", "Magnolia",
			"Magnolia à grandes fleurs", "Magnolia étoilé", "Mahonia", "Mahonia du Japon", "Maïanthème",
			"Maïanthème à deux feuilles", "Maïs", "Mamillaire", "Mancenillier", "Mandarine", "Mandragore",
			"Mangoustanier", "Mangue", "Manguier", "Manioc", "Marguerite", "Marguerite commune", "Marjolaine",
			"Marronnier", "Marrube", "Marsilée", "Masdevallia", "Massette", "Maté", "Mathiole ou Matthiole",
			"Matricaire", "Mauve", "Mauve alcée", "Mauve sylvestre", "Megaskepasma", "Mélampyre",
			"Mélampyre des champs", "Mélampyre des prés", "Mélèze", "Mélèze d'Europe", "Mélèze du Japon",
			"Mélèze laricin", "Mélilot", "Mélilot officinal", "Mélilot élevé", "Mélique", "Mélisse officinale",
			"Mélitte à feuilles de mélisse", "Melon", "Menthe", "Menthe pouliot", "Menthe-coq", "Ményanthe", "Méon",
			"Mercuriale", "Mercuriale annuelle", "Mercuriale vivace", "Merisier", "Métaséquoïa", "Micocoulier",
			"Micocoulier de Provence", "Millepertuis", "Millepertuis perforé", "Millepertuis à grandes fleurs",
			"Millepertuis élégant", "Millet", "Millet ", "commun", "Millet des oiseaux", "Millet japonais", "Mimosa",
			"Mimosa des quatre saisons", "Mimosa odorant", "Mimosa à bois noir", "Mimosa de Paris", "Mimule",
			"Mimule cardinale", "Minette", "Miroir de Vénus", "Molène", "Molène blattaire", "Molène de Phénicie",
			"Molinie", "Momordique", "Monarde", "Mongette", "Monnaie du pape", "MontbrétiaMorelle", "Morelle de Linné",
			"Morelle faux jasmin", "Morelle noire", "Morène", "Mouron", "Moutarde blanche", "Moutarde brune",
			"Moutarde des champs", "Moutarde noire", "Muflier", "Muguet", "Muguet de mai", "Muscari",
			"Muscari à toupet", "Mûrier", "Mûrier blanc", "Mûrier noir", "Mûrier à papier", "Mûre de Logan", "Muscari",
			"Myosotis", "Myriophille", "Myrsine africana", "Myrte", "Myrtille", "Narcisse", "Narcisse d'Asso",
			"Narcisse des poètes", "Narcisse douteux", "Narcisse jaune", "Nashi", "Navet", "Navet du diable", "Navette",
			"Néflier", "Néflier du Japon", "Nénuphar", "Nénuphar blanc", "Nénuphar jaune", "Néorégélia",
			"Néottie nid d'oiseau", "Nérine", "Nerprun", "Nerprun alaterne", "Nielle des blés", "Nigelle", "Nivéole",
			"Noix de muscade", "Noyer", "Nopal", "Noyer cendré", "Noyer commun", "Noyer du Brésil",
			"Noyer du Queensland", "Noyer noir", "Néflier", "Nymphéa", "Nyssa", "Oca du Pérou", "Œillet",
			"Œillet arméria", "Œillet de France", "Œillet des Chartreux", "Œillet commun", "Œillet des glaciers",
			"Œillet mignardise", "obione", "Œnanthe", "Œnanthe aquatique", "Œnanthe faux boucage",
			"Œnanthe à feuilles de silaüs", "Oignon", "Oiseau de paradis", "Olivier", "Olivier de Bohême", "Onagre",
			"Onopordon", "Ophioglosse", "Orcanette", "Ophrys", "Ophrys abeille", "Ophrys araignée", "Ophrys bourdon",
			"Ophrys brun", "Ophrys bécasse", "Ophrys de Gascogne", "Ophrys de l'Aveyron", "Ophrys jaune",
			"Ophrys miroir", "Ophrys mouche", "Ophrys noir", "Ophrys petite araignée", "Opopanax", "Oranger",
			"Oranger des Osages", "Oranger du Mexique", "Orcanette", "Orchidée", "Orchidée jacinthe", "Orchis",
			"Orchis bouc", "Orchis bouffon", "Orchis brûlé", "Orchis à un bulbe", "Orchis conique", "Orchis de Fuchs",
			"Orchis géant", "Orchis guerrier", "Orchis globuleux", "Orchis intact", "Orchis mâle", "Orchis moucheron",
			"Orchis négligé", "Orchis papillon", "Orchis pourpre", "Orchis pyramidal", "Orchis singe", "Orchis sureau",
			"Orchis tacheté", "Oreille-d'ours", "Orge commune", "Orge des rats", "Origan", "Orme", "Orne",
			"Ornithogale", "Ornithogale des Pyrénées", "Ornithogale à ombelle", "Orobanche", "Orpin", "Orpin blanc",
			"Orpin âcre", "Ortie", "Ortie à pilules", "Oseille", "Osier", "Osmonde", "Osmonde royale",
			"Oursin à têtes rondes", "Oxalide", "Oxalis", "Oxalis corniculée", "Oxalis petite oseille", "Oyat",
			"Pacane", "Pacanier", "Pachystachys jaune", "Palétuvier", "Palétuvier rouge", "Pamplemousse", "Panais",
			"Pancrace d'Illyrie", "Pandanus", "Palmier", "Palmier de Chine", "Palmier à bétel", "Palmier à huile",
			"Palmier-dattier", "Palmier à huile africain", "Palmier Boucanier", "Palmier de Bismark", "Palmier nain",
			"Palmier de Noël", "Palmier chaume de Floride", "Palmier des Everglades", "Palmier pêche",
			"Palmier royal de Cuba", "Panicaut", "Panicaut champêtre", "Panicaut de Bourgat", "Panicaut des Alpes",
			"Panicaut à feuilles de yucca", "Panicaut maritime", "Papayer", "Cyperus papyrus", "Pâquerette",
			"Parisette à quatre feuilles", "Passerage", "Passiflore", "Pastel", "Pastèque", "Patate douce", "Patchouli",
			"Pâtisson", "Pâturin", "Pâturin annuel", "Pâturin commun", "Pâturin des prés", "Pavot",
			"Pavot de Californie", "Pavot cornu", "Pêcher", "Pédiculaire", "Peigne de Vénus", "Pensée de Rouen",
			"Pensée des champs", "Pensée à deux fleurs", "Penstemon", "Perce-neige", "Pernambouc", "Persicaire",
			"Persil", "Pervenche", "Pervenche de Madagascar", "Pétasite", "Petite centaurée", "Petite ciguë",
			"Petit genêt d'Espagne", "Petit houx", "Petite pervenche", "Pétunia", "Peuplier", "Peuplier baumier",
			"Peuplier blanc", "Peuplier noir d'Amérique", "Peyotl", "Phacélie", "Phacélie à feuilles de tanaisie",
			"Philodendron", "Phlox", "Phytolaque", "Pied d'alouette", "Pied-de-veau", "Pigamon", "Piment",
			"Piment de la Jamaïque", "Pimprenelle", "Pin", "Pin Napoléon", "Pin cembro", "Pin d'Alep",
			"Pin de l'Himalaya", "Pin du Chili", "Pin maritime", "Pin de l'Orégon", "Pin parasol", "Pin sylvestre",
			"Pissenlit", "Pistachier", "Pistachier vrai", "Pivoine", "Pivoine officinale", "Plane", "Plantain",
			"Plantain intermédiaire", "Plantain Pied-de-lièvre", "Plante annuelle", "Plante bisannuelle",
			"Plante-caillou", "Plante caméléon", "Plante cobra", "Plante-crevette", "Plante vivace", "Plaquebière",
			"Plaqueminier", "Plaine blanche", "Plaine rouge", "Platane", "Platane d'Occident", "Platane d'Orient",
			"Platanthère à deux feuilles", "Platanthère à fleurs vertes", "Pohutukawa", "Poireau", "Poireau d'été",
			"Poirier", "Poirée", "Pois potager ou fourrager", "Pois chiche", "Pois de senteur", "Pois doux",
			"Pois vivace", "Poivre", "Poivre du Sichuan", "Poivrier", "Poivron", "Polémoine", "Polémoine bleue",
			"Polygale", "Polypode vulgaire", "Pomelo", "Pomme de terre", "Pommier", "Pommier-canellier",
			"Pommier-malacca", "Pommier de Sodome", "Pommier domestique", "Pommier sauvage", "Poncirus",
			"Pontédérie à feuilles en cœur", "Populage des marais", "Porcelle", "Posidonie", "Potamot", "Potentille",
			"Potentille ansérine", "Potentille fausse alchémille", "Potiron", "pourpier", "Prêle", "Prénanthe pourpre",
			"Prestoa", "Primevère", "Primevère de Chine", "Primevère commune", "Primevère du Japon",
			"Primevère officinale", "Primevère élevée", "Protée", "Prunellier", "Prunier", "Prunier de Cythère",
			"Prunier d'Espagne", "Prunier mombin", "Psoralée bitumineuse", "Pulicaire", "Pulmonaire",
			"Pulmonaire officinale", "Pulsatille", "Pulsatille rouge", "Pyracantha", "Pyrèthre", "Quatre-épices",
			"Quenettier", "Quercitron", "Quetsche", "Queue de chat", "Queue-de-lièvre", "Quinoa", "Quinquina", "Radis",
			"Raifort", "Raiponce", "Raiponce en épi", "Raiponce orbiculaire", "Raisin", "Raisin d'Amérique",
			"Raisin d'ours", "Raisin de mer", "Ramondie", "Raphia", "Ravenala", "Ravenelle", "Ray-Grass", "Réglisse",
			"Reine-marguerite", "Reine de la nuit", "Reine-des-prés", "Renoncule", "Renoncule aquatique",
			"Renoncule des rivières", "Renoncule tête d'or", "Renouée", "Renouée du Japon", "Renouée bistorte",
			"Renouée d'Aubert", "Renouée des haies", "Renouée liseron", "Renouée persicaire", "Réséda",
			"Réséda raiponce", "Rhododendron", "Rhododendron cilié", "Rhododendron ferrugineux", "Rhubarbe", "Ricin",
			"Riz", "Riz sauvage", "Robinier", "Romarin", "Ronce", "Ronce commune", "Ronce petit-mûrier", "Roquette",
			"Roquette de mer", "Rose", "Rose de Chine", "Rose de Noël", "Rose de Sharon", "Rose trémière", "Rosier",
			"Rose Myrtle", "Roseau", "Roseau commun", "Rudbeckia", "Rue officinale", "Rutabaga", "Sabline",
			"Sabline d'Espagne", "Sabot de Vénus", "Safran", "Sagittaire", "Sainfoin", "Sainfoin cultivé",
			"Saintpaulia", "Salicaire", "Salicaire commune", "Salicorne", "Salicorne d'Europe", "Salsepareille",
			"Salsifis", "Salsifis cultivé", "Salsifis des prés", "Sanchezia", "Sandragon", "Sanguinaire", "Sanguisorbe",
			"Sanicle d'Europe", "Santoline", "Santoline à feuilles de romarin", "Sapin", "Sapin blanc",
			"Sapin d'Andalousie", "Sapin de Céphalonie", "Sapin du Colorado", "Sapin de Nordmann", "Sapin de Vancouver",
			"Saponaire", "Saponaire officinale", "Sapotillier", "Sarracénie", "Sarracénie pourpre", "Sarrasin",
			"Sarriette", "Sauge", "Sauge des prés", "Sauge officinale", "Sauge sclarée", "Saule", "Saule blanc",
			"Saule pleureur", "Savonnier", "Saxifrage", "Saxifrage sarmenteux", "Scabieuse", "Scabieuse colombaire",
			"Scabieuse à feuilles de graminée", "Scabieuse à trois étamines", "Scarole", "Sceau de Salomon",
			"Sceau de Salomon multiflore", "Sceau de Salomon odorant", "Scille", "Scille d'automne",
			"Scille de Sibérie", "Scille du Pérou", "Scirpe", "Scolopendre", "Scolyme", "Scrophulaire",
			"Scrophulaire des chiens", "Scrophulaire noueuse", "Scrophulaire à oreillettes", "Scutellaire",
			"Scutellaire à casque", "Sedum", "Sédum remarquable", "Sélaginelle", "Séné", "Séneçon", "Séneçon de Jacob",
			"Séneçon du Cap", "Séneçon géant du Kilimandjaro", "Sensitive", "Séquoia", "Séquoia géant",
			"Séquoia à feuilles d'if", "Sérapias en cœur", "Sérapias à labelle allongé", "Seringat", "Serpolet",
			"Sésame", "Séséli", "Seslérie", "Sharon", "Silène", "Silène acaule", "Silène conique", "Silène enflé",
			"Sisal", "Sisymbre", "Sobralia", "Soja", "Soldanelle", "Soldanelle des Alpes", "Soleil", "Solidage",
			"Sorbier", "Sorbier des oiseleurs", "Sorgho", "Souchet", "Souchet comestible", "Souci", "Souci officinal",
			"Spartier à tiges de jonc", "Spiranthe d'automne", "Spirée", "Spirée ulmaire", "Statice",
			"Statice de l'ouest", "Stellaire", "Sureau", "Sureau hièble", "Sureaux à grappes", "Surettier", "Sycomore",
			"Symphorine", "Tabac", "Tabac d'ornement", "Tabouret perfolié", "Tagète", "Tamarinier", "Tamaris", "Tamier",
			"Tanaisie", "Tanaisie commune", "Tangerine", "Taro", "Teff", "Tétragone", "Théier", "Thlaspi", "Thuya",
			"Thuya du Canada", "Thym", "Tillandsia", "Tilleul", "Tilleul à petites feuilles", "Tomate", "Topinambour",
			"Tormentille", "Tournesol", "Toute-bonne", "Tradescantia", "Trèfle", "Trèfle à feuilles étroites",
			"Trèfle alpestre", "Trèfle alpin", "Trèfle bai", "Trèfle blanc", "Trèfle couché", "Trèfle d'Alexandrie",
			"Trèfle d'eau", "Trèfle de Perse (ou Trèfle renversé)", "Trèfle de Micheli", "Trèfle de Thal",
			"Trèfle de montagne", "Trèfle des prés (ou Trèfle violet)", "Trèfle doré", "Trèfle douteux",
			"Trèfle étoilé", "Trèfle hybride", "Trèfle incarnat", "Trèfle jaunâtre", "Trèfle Pied-de-lièvre",
			"Trèfle rougeâtre", "Trèfle souterrain", "Tremble", "Tribule terrestre", "Tricyrtis à feuilles larges",
			"Trigonelle", "Trille blanc", "Trille rouge", "Troène", "Troène commun", "Trolle", "Trolle d'Europe",
			"Trompette de Virginie", "Trompette de méduse", "Tsuga", "Tubéreuse", "Tubéreuse bleue", "Tulipe",
			"Tulipe méridionale", "Tulipier", "Tulipier de Chine", "Tulipier de Virginie", "Turraea", "Tussilage",
			"Urosperme", "Utriculaire", "Valériane", "Valériane dioïque", "Valériane officinale", "Valériane rouge",
			"Vallisnérie", "Vanda", "Vanille", "Vanillier", "Varaire", "Vase d'argent", "Vendangeuse", "Vérâtre",
			"Vergerette", "Verge d'or", "Verge d'or bicolore", "Verne", "Véronique", "Véronique d'Allioni",
			"Véronique des Alpes", "Véronique buissonnante", "Véronique des champs", "Véronique couchée",
			"Véronique en épi", "Véronique à feuilles de lierre", "Véronique à feuilles d'ortie",
			"Véronique à feuilles de serpolet", "Véronique filiforme", "Véronique germandrée",
			"Véronique à longues feuilles", "Véronique mouron d'eau", "Véronique officinale", "Véronique de Perse",
			"Véronique petit-chêne", "Véronique rampante", "Véronique des ruisseaux", "Véronique sans feuilles",
			"Véronique voyageuse", "Verveine", "Verveine citronnée", "Verveine officinale", "Vesce", "Vesce commune",
			"Vesce craque", "Vesce des haies", "Vesce hérissée", "Vétiver", "Vigne", "Vigne blanche", "Vigne vierge",
			"Violettes et pensées", "Violette blanche", "Violette des chiens", "Violette odorante",
			"Violette d'Usumbura", "Violette lactée", "Viorne", "Viorne lantane", "Viorne mancienne", "Viorne obier",
			"Viorne tin", "Vipérine", "Vipérine commune", "Volubilis", "Vulnéraire", "Vulpin", "Vulpin des champs",
			"Vulpin des prés", "Vriésia splendide", "Wasabi", "Witloof", "Xanthoceras", "Xanthosoma", "Xeranthemum",
			"Xerophyllum", "Yagua", "Yeuse", "Ylang-ylang", "Ypréau", "Yucca", "Yucca à feuille d'aloès", "Yogalophile",
			"Zamia", "Zamioculcas zamiifolia", "Zannichellie", "Zelkova", "Zinnia", "Zizanie", "Zostère", "Zygopetalum"

	};

}
