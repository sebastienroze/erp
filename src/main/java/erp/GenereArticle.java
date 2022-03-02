package erp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class GenereArticle {
	private Random random;

	public static void main(String[] args) {
		System.out.println("D�but g�n�ration");
		if (ErpFiche.establish()) {
			new GenereArticle().genereAleatoire(ErpFiche.conn, 1);
			System.out.println("G�n�ration termin�e");
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

	public static final String[] articles = { "Abaca", "Ab�lie � grandes fleurs", "Abricotier",
			"Abricotier des Antilles", "Abricotier du Japon", "Abricotier de Saint-Domingue", "Abricotier d'Afrique",
			"Absinthe", "Absinthe maritime", "Abutilon d'Avicenne", "Acacia", "Acajou", "Acajou amer", "Acanthe",
			"Acanthe � feuilles molles", "Ache", "Ache inond�e", "Ache des montagnes", "Achill�e",
			"Achill�e millefeuille", "Achill�e musqu�e", "Achill�e sternutatoire", "Achill�e � feuilles d'ag�ratum",
			"Aconit napel", "Aconit tue-loup", "Acore", "Acore odorant", "Act�e en �pi", "Act�e rouge", "Actinidia",
			"Adonide", "Adonide goutte-de-sang", "Adonis", "Adonis d'�t�", "Agapanthe", "Agapanthe en ombelles",
			"Agastache", "Agave", "Agave am�ricain", "Ag�rate", "Agneau chaste", "Agrostide", "Agrume", "Aigremoine",
			"Aigremoine eupatoire", "Ail", "Ail blanc", "Ail car�n�", "Ail cultiv�", "Ail des jardins", "Ail des ours",
			"Ail dor�", "Ail jaune", "Ail rocambole", "Ail rose", "Ail � fleurs de narcisse", "Ail � t�te ronde",
			"Ailante", "Airelle", "Ajonc", "Ajonc d'Europe", "Ajonc de Provence", "Ajuga", "Albizia", "Alch�mille",
			"Alch�mille des Alpes", "Alch�mille vulgaire", "Alfa", "Algue", "Aliboufier", "Alisier", "Alisier blanc",
			"Alisier torminal", "Alk�kenge", "Alliaire officinale", "Allier", "Alo�s", "Alouchier", "Alpinia",
			"Alpiste", "Alstr�m�re", "Alvier", "Alysse", "Amande de terre", "Amandier", "Amarante", "Amarante blanche",
			"Amarante cr�te de coq", "Amarante hybride", "Amarante queue de renard", "Amarante tricolore", "Amaryllis",
			"Am�lanchier", "Am�lanchier � feuilles ovales", "Ammi", "Ammi visnage", "Ammi �lev�", "Amour en cage",
			"Anacardier", "Anacycle tomenteux", "Ananas", "Ancolie", "Ancolie commune", "Ancolie des Alpes",
			"Ancolie noir�tre", "Androm�de", "Androsace", "Andryale � feuilles enti�res", "An�mone", "An�mone de Gr�ce",
			"An�mone fausse renoncule", "An�mone h�patique", "An�mone sylvie", "An�mone des fleuristes",
			"An�mone des jardins", "An�mone du Japon", "Aneth doux", "Aneth odorant", "Ang�lique", "Ang�lique des bois",
			"Ang�lique de Chine", "Ang�lique japonaise", "Ang�lique officinale", "Anis", "Anis vert", "Anis �toil�",
			"Antennaire dio�que", "Anth�mis", "Anth�mis des teinturiers", "Anthrisque", "Anthrisque sauvage",
			"Arabette", "Arabette des dames", "Arachide", "Aralia", "Aralie du Japon", "Araucaria", "Arbousier",
			"Arbre", "Arbre � gutta-percha", "Arbre-�-surelle", "Arbre de Jud�e", "Arbre de soie", "Arbre � beurre",
			"Arbre bouteille", "Arbre � caoutchouc", "Arbre aux clochettes d'argent", "Arbre aux quarante �cus",
			"Arbre aux saucisses", "Arbre du voyageur", "Arbre � pain", "Arbre � suif", "Ar�quier", "Arganier",
			"Arg�mone", "Argousier", "Aristoloche", "Aristoloche cl�matite", "Aristoloche sipho", "Armoise blanche",
			"Armoise commune", "Arm�rie", "Arm�rie des Alpes", "Arm�rie maritime", "Arnica", "Arnica des montagnes",
			"Arolle", "Aronia", "Arroche", "Arrow-root des Antilles", "Artichaut", "Arum", "Arum d'�thiopie", "Asaret",
			"Ascl�piade", "Asiminier trilob�", "Asparagus", "Asperge", "Asp�rule", "Asphod�le", "Asphod�le blanc",
			"Asplenium", "Aster", "Aster � feuilles de linaire", "Aster � feuilles lanc�ol�es", "Aster amelle",
			"Aster maritime", "Astilb�", "Astragale", "Astragale � feuilles de r�glisse", "Astragale de Montpellier",
			"Astragale pourpre", "Astrance", "Astrophytum", "Aubergine", "Aub�pine", "Aub�pine officinale", "Aubour",
			"Aubri�tie", "Aucuba", "Aulne", "Aulne blanc", "Aulne glutineux", "Aun�e officinale", "Aurone",
			"Averse rose", "Avocatier", "Avoine", "Avoine � chapelets", "Avoine cultiv�e", "Avoine �lev�e", "Ayote",
			"Azal�e", "Az�rolier", "Azurite (flore)", "Badiane chinoise", "BaguenaudierBalata", "Balisier",
			"Balisier biha�", "Ballote", "Balsa", "Balsamine", "Balsamine de Balfour", "Balsamine de l'Himalaya",
			"Balsamine des bois", "Balsamine des jardins", "Bambou", "Bananier", "Bananier nain", "Baobab africain",
			"Barbadine", "Barbe de Jupiter", "Bardane", "Basilic", "Baumier", "Bayal", "Beaucarnea", "B�gonia",
			"B�limbe", "Belladone", "Belle de nuit", "Belombra", "B�nincasa", "Beno�te", "Berb�ris",
			"Berb�ris � feuilles de buis", "Berce", "Berce du Caucase", "Berce sphondyle", "Bergamote", "B�tel",
			"B�toine", "Bette", "Betterave", "Bibacier", "Bifora rayonnant", "Bigaradier", "Bigarreautier", "Bignone",
			"Bilbergie", "Bistorte", "Blackstonie perfoli�e", "Bl�", "Bl� dur", "Bl� noir", "Blechnum en �pi", "Blette",
			"Bleuet des champs", "Bleuet des montagnes", "Bois de sainte Lucie", "Bois d'ortie", "Bois joli",
			"Bonnet d'�v�que", "Bougainvill�e", "Bouleau", "Bouleau pubescent", "Bouleau verruqueux", "Bourdaine",
			"Bourrache officinale", "Bourreau des arbres", "Bourse � pasteur", "Brah�a doux", "Br�de mafane", "Brize",
			"Brocoli", "Brome", "Brome faux-seigle", "Brome mou", "Brome st�rile", "Brome des toits", "Bromelia",
			"Brugmansia", "Brunelle", "Brunelle commune", "Brunelle lacini�e", "Brunelle � grandes fleurs", "Bruy�re",
			"Bruy�re arborescente", "Bruy�re cendr�e", "Bryone dio�que", "Buddleia de David", "Bugle", "Buglosse",
			"Bugrane", "Buis", "Buis de Mahon", "Buisson ardent", "Bulbocode", "Bupl�vre", "Bupl�vre en faux",
			"Bupl�vre � feuilles rondes", "Busserole", "Butome", "Cacaoyer", "Cachiman �pineux", "Cact�e", "Cactus",
			"Caesalpinia", "Caf�ier", "Caille-lait jaune", "Caimitier", "Cakile", "Caladium", "Calament", "Calc�olaire",
			"Calebasse (Gourde ou fruit du Calebassier)", "Calla des marais", "Callicarpe", "Callistemon", "Callune",
			"Calypso bulbosa", "Cam�l�e", "Cam�lia", "Cam�lia du Japon", "Camomille", "Camomille romaine", "Campanule",
			"Campanule agglom�r�e", "Campanule gantel�e", "Campanule raiponce", "Campanule � feuilles de Cochl�aire",
			"Campanule � feuilles de p�cher", "Camphrier", "Can�ficier", "Canna", "Cannabis", "Canche",
			"Canne de Provence", "Canne � sucre", "Cannelier", "Canola", "Capillaire de Montpellier", "C�prier",
			"Capucine", "Carambole", "Cardamine", "Cardon", "Card�re lacini�e", "Card�re sauvage", "Carline",
			"Carline acaule", "Carline en corymbe", "Carline � feuilles d'acanthe", "Cardon", "Carex", "Carotte",
			"Caroubier", "Carthame", "Carthame des teinturiers", "Carvi", "Cassier", "Casse", "Cassia", "Cassissier",
			"Casuarina", "Cataire", "Catalpa", "Catananche", "Cattleya", "Caulerpe", "C�anothe", "C�dratier", "c�dr�le",
			"C�dre", "C�dre � encens", "C�leri", "C�leri-rave", "C�losie", "C�losie cr�te-de-coq", "Centaur�e",
			"Centaur�e du solstice", "Centaur�e noir�tre", "Centaur�e rude", "Centaur�e scabieuse",
			"Centaur�e � feuilles simples", "Centaur�e � grosse t�te", "Centaurea jacea", "Centranthe",
			"Centranthe rouge", "C�phalanth�re", "C�phalanth�re de Damas", "C�phalanth�re rouge",
			"C�phalanth�re � feuilles �troites", "C�raiste", "Cerfeuil", "Cerfeuil commun", "Cerfeuil hirsute",
			"Cerfeuil musqu�", "Cerfeuil pench�", "Cerfeuil tub�reux", "Cerfeuil de Villars", "Cerisier",
			"Cerisier de sainte Lucie", "Cerisier des Antilles", "Cerisier de Tahiti", "C�t�rach", "Chad�que",
			"Cham�dor�e", "Chanvre", "Chanvre de Manille", "Chardon", "Chardon � foulons", "Chardon aux �nes",
			"Chardon b�nit", "Chardon bleu des Alpes", "Chardon d'�cosse", "Chardon d'Espagne", "Chardon-Marie",
			"Chardon roulant", "Chardon pench�", "Charme", "Charme-houblon", "Ch�taigne d'eau", "Ch�taignier",
			"Chataire", "Chayote", "Ch�lidoine", "Ch�ne", "Ch�ne blanc d'Am�rique", "Ch�ne chevelu",
			"Ch�ne de Bannister", "Ch�ne des marais", "Ch�ne �carlate", "Ch�ne kerm�s", "Ch�ne-li�ge",
			"Ch�ne p�doncul�", "Ch�ne pubescent", "Ch�ne rouge", "Ch�ne rouvre", "Ch�ne saule", "Ch�ne tauzin",
			"Ch�ne vert", "Ch�nopode", "Ch�rimolier", "Chervis", "Ch�vrefeuille", "Ch�vrefeuille des bois",
			"Ch�vrefeuille des haies", "Ch�vrefeuille des jardins", "Ch�vrefeuille d'ItalieCh�vrefeuille du Japon",
			"Chicon", "Chicor�e", "Chicor�e endive", "Chicor�e sauvage", "Chicot du Canada", "Chiendent",
			"Chlorophytum", "Chou", "Chou de Bruxelles", "Chou-fleur", "Chou marin", "Chou palmiste", "Chou romanesco",
			"Chou-rave", "Christophine", "Chou-chou", "Chrysanth�me", "Ciboule", "Ciboule de Chine", "Ciboulette",
			"Cierge", "Cigu�", "Cin�raire", "Cirse", "Cirse acaule", "Cirse commun", "Cirse de Montpellier",
			"Cirse des champs", "Cirse des marais", "Cirse laineuxCiste", "Citronnelle", "Citronnier", "Citrange",
			"Citrouille", "Claytone de Cuba", "Cl�matite", "Cl�matite des haies", "Cl�matite flammette",
			"Cl�matite des Alpes", "Cl�matite � Vrille", "Cl�matite Dress�e", "Cl�matite d'Armand",
			"Cl�matites des montagnes", "Cl�mentinier", "Cleome", "Cl�rodendron", "Clivia", "Cob�e", "Cob�e grimpante",
			"Coca", "Coco-fesse", "Cocotier", "Cocotier du Chili", "Coccothrinax argent�", "C�ur-de-Marie",
			"Cognassier", "Cognassier du Japon", "Coquelicot", "Colchique", "Colchique d'automne",
			"Coloquinte officinale", "Colza", "Combava", "Concombre", "Concombre d'�ne", "Concombre des Antilles",
			"Conif�re", "Consoude", "Consoude officinale", "Copalme", "Coquelicot", "Coquelicot bleu de l'Himalaya",
			"Coquelourde des jardins", "Coqueret du P�rou", "Corbeille d'argent", "Corbeille d'or", "Cordyline",
			"Cor�opsis", "Coriandre", "Cormier", "Corne de cerf", "Corne-de-cerf didyme", "Corne d'�lan", "Cornichon",
			"Cornouiller", "Cornouiller femelle", "Cornouiller m�le", "Cornouiller sanguin", "Coronille",
			"Coronille bigarr�e", "Corosse", "Corossol", "Corydale", "Corydale jaune", "Cosmos", "Coste", "Coton�aster",
			"Cotonnier", "Coucou", "Coudrier", "Courbaril", "Couronne d'�pines", "Couronne imp�riale", "Coyor",
			"Cramb�", "Cr�pide", "Cresson", "Cresson al�nois", "Cresson de fontaine", "Cresson de Para",
			"Cresson de terre", "Crocus", "Crocus printanier", "Croix de Malte", "Crosne du Japon", "Croton", "Cumin",
			"Cumin des pr�s", "Cupidone", "Curcuma", "Cuscute", "Cuscute d'Europe", "Cuscute du thym", "Cycas",
			"Cyclamen", "Cymbalaire des murs", "Cymbidium", "Cynodon", "Cynoglosse",
			"Cynoglosse � feuilles de girofl�e", "Courge", "Courgette", "Courge cireuse", "Courge musqu�e",
			"Courge de Siam", "Courge spaghetti", "Courge torchon", "Cypr�s", "Cypr�s chauve", "Cypr�s de Lawson",
			"Cypr�s de Nootka", "Cypr�s de Provence", "Cyprip�de acaule", "Cytinelle", "Cytise",
			"Cytise � feuilles sessiles", "Dactyle", "Dactyle pelotonn�", "Dactylorhize de mai", "Dahlia", "Daikon",
			"Dame d'onze heures", "Daphn� garou", "Dattier", "Datura", "Deinanthe", "Dent de chien", "Dentelaire",
			"Dentelaire du Cap", "Digitale", "Digitale jaune", "Dion�e attrape-mouche", "Diplotaxis",
			"Diplotaxis fausse roquette", "Dolique", "Dompte-venin fun�bre", "Dompte-venin officinal", "Doronic",
			"Doronic plantain", "Douce-am�re", "Douglas", "Dragonnier fragrant", "Drave", "Dros�ra", "Dryade",
			"Dryade � huit p�tales", "Durion", "�chalote", "Echinops", "Edelweiss", "�glantier", "�gopode podagraire",
			"�leusine", "�lyme des marais", "Encyclia", "Endive", "Engrain", "�pervi�re", "�pervi�re laineuse",
			"�pervi�re mixte", "�pervi�re orang�e", "�pervi�re velue", "�ph�dre", "�piaire", "�piaire des bois",
			"�piaire officinale", "�pic�a", "�pic�a bleu", "�pic�a commun", "�pilobe", "�pilobe en �pi",
			"�pilobe � grandes fleurs", "�pinard", "�pine du Christ", "�pine-vinette", "�pinette blanche",
			"�pipactis des marais", "�pipactis pourpre noir�tre", "�pipactis � larges feuilles", "�ponge v�g�tale",
			"�purge", "�rable", "�rable argent�", "�rable champ�tre", "�rable de Montpellier", "�rable plane",
			"�rable sycomore", "�rig�ron", "�rine des Alpes", "Estragon", "Eucalyptus", "Eupatoire",
			"Eupatoire � feuilles de chanvre", "Euphorbe", "Euphorbe characias", "Euphorbe h�t�rophylle",
			"Euphorbe macul�e", "Euphorbe maritime", "Euphraise de Rostkov", "Euphorbia pulcherrima", "Fabiana",
			"Fausse badiane", "Faux acacia", "Faux de Verzy", "Feijoa", "Fenouil", "Fenouil des Alpes", "Fenugrec",
			"Fer � cheval", "F�rule", "Festulolium", "F�tuque", "F�tuque bleue", "F�tuque �lev�e",
			"F�tuque ovine (F�tuque des moutons)", "F�tuque des pr�s", "F�tuque rouge", "F�ve", "F�verole",
			"F�vier d'Am�rique", "Ficaire", "Fico�de", "Fico�de orange", "Fico�de � feuilles en c�ur", "Ficus",
			"Ficus retusa", "Figuier", "Figuier de Barbarie", "Figuier des Banyans", "Figuier des pagodes",
			"Figuier pleureur", "Filao", "Filaria", "Filipendule", "Flamboyant", "Fleur de la passion", "Fl�ole",
			"Flouve odorante", "Fonio", "Forsythia", "Foug�re", "Foug�re arborescente", "Foug�re de Boston",
			"Foug�re m�le", "Fragon faux houx", "Fraisier", "Fraisier des bois", "Fraisier des Indes", "Framboisier",
			"Frangipanier", "Frankenia", "Fraxinelle", "Freesia", "Fritillaire", "Fritillaire imp�riale",
			"Fritillaire pintade", "Fritillaire des Pyr�n�es", "Fruit � pain mexicain", "Fr�ne",
			"Fr�ne � feuilles �troites", "Fr�ne � fleurs", "Fris�e", "Fromager", "Froment", "Fromental", "Fumeterre",
			"Fusain d'Europe", "Fuchsia", "Fustet", "F�ve de cacao", "F�vier d'Am�rique", "Gag�e", "Gaillarde",
			"Gaillet blanc", "Gaillet odorant", "Gal�ga officinal", "Garance des teinturiers", "Garance voyageuse",
			"Gard�nia", "Gaude", "Gazania", "G�n�pi.", "G�n�pi blanc", "Gen�vrier", "Gen�vrier commun",
			"Gen�vrier cade", "Gen�vrier rampant", "Gen�vrier sabine", "G�ranium", "G�ranium sanguin", "Gerbera",
			"Germandr�e", "Germandr�e scorodoine", "Germandr�e petit ch�ne", "Gen�t", "Gen�t � balais", "Gen�t ail�",
			"Gen�t cendr�", "Gen�t d'Angleterre", "Gen�t d'Espagne", "Gen�t des teinturiers", "Gen�t �pineux",
			"Gen�t poilu", "Gentiane", "Gentiane Ascl�piade", "Gentiane am�re", "Gentiane des champs",
			"Gentiane cili�e", "Gentiane croisette", "Gentiane de Clusius", "Gentiane de Koch", "Gentiane de printemps",
			"Gentiane des marais", "Gentiane des neiges", "Gentiane jaune", "Gesse", "Gesse aphaca", "Gesse des bois",
			"Gesse printani�re", "Gesse tub�reuse", "Gingembre", "Gingembre sauvage", "Ginkgo", "Ginseng", "Girofl�e",
			"Girofl�e des dunes", "Giroflier", "Giroselle", "Gla�eul", "Gla�eul d'Abyssinie", "Gla�eul des moissons",
			"Gl�chome", "Gl�ich�nia", "Globulaire", "Globulaire buissonnante", "Glyc�rie", "� Glycine �",
			"Goody�re rampante", "Gouet", "Gouet tachet�", "Gourbet", "Gourde", "Goyavier", "Goyavier du Chili",
			"Grand Rhinanthe", "Grande astrance", "Grande aun�e", "Grande bardane", "Grande capucine", "Grande cigu�",
			"Grande mauve", "Grande ortie", "Grassette", "Grenadier", "Gr�mil", "Gr�mil pourpre bleu", "Griffe-de-Chat",
			"Grind�lia", "Groseillier", "Guarana", "Gueule de loup", "Gui", "Guimauve officinale",
			"Gymnocarpe de Robert", "Gypsophile", "Guzmania", "Hamatocactus", "Haricot", "Haricot commun",
			"Haricot d'Espagne", "Haricot de Lima", "H�lianth�me", "H�lianth�me � gouttes", "H�lianth�me des Apennins",
			"H�liotrope", "Hell�bore", "H�m�rocalle", "H�m�rocalle fauve", "H�m�rocalle jaune", "Henn�", "H�patiques",
			"Herbe aux chats", "Herbe aux �cus", "Herbe aux massues", "Herbe aux mouches", "Herbe de la pampa", "H�tre",
			"H�tre � grandes feuilles", "H�tre commun", "H�tre d'Orient", "Heuch�re", "H�v�a", "Hibiscus", "Hi�ble",
			"Homme pendu", "Hortensia", "Hosta", "Houblon", "Houlque", "Houx", "Hoya", "Hyos�ride rayonnante", "Hysope",
			"Ib�ride", "Ib�ris", "Ib�ris toujours vert", "Icaquier", "If", "If commun", "If du Japon", "Igname",
			"Ilima rouge", "Immortelle", "Immortelle commune", "Impatiente", "Incarvill�e", "Indigotier", "Inule",
			"Inule visqueuse", "Ipom�e", "Ip�cacuanha", "Iris", "Iris des Pyr�n�es", "Iris des marais", "Iris du Japon",
			"Iris versicolore", "Iso�te", "Ive", "Ivraie", "Jacaranda", "Jac�e", "Jacinthe", "Jacinthe des bois",
			"Jacinthe d'eau", "Jacinthe du Cap", "Jacob�e", "Jalap", "Jaquier", "Jasione", "Jasione vivace", "Jasmin",
			"Jasmin d'hiver", "Jasmin ligneux", "Jasmin du Cap", "Jatropha", "Jojoba", "Jonc", "Jonc fleuri",
			"Jonquille", "Joubarbe", "Joubarbe des montagnes", "Joubarbe des toits", "Joubarbe du calcaire",
			"Joubarbe � toile d'araign�e", "Jujubier", "Julienne", "Julienne de Mahon", "Jusquiame", "Jussi�e", "Jute",
			"Kaki", "Kalancho�", "Kapokier", "Karit�", "Kentia", "Khat", "Kerm�s", "Kerria", "Ketmie", "Keul�rie",
			"Kiwai", "Kiwi", "Kiwi de Sib�rie", "Knautie", "Kumquat", "Lagure", "Laiteron", "La�che",
			"La�che � �pis pendants", "Laitue", "Laitue cultiv�e", "Laitue des vignes", "Laitue scariole",
			"Laitue vivace", "Lamier", "Lamier jaune", "Lamier pourpre", "Lampourde", "Lampsane", "Lampsane commune",
			"Langue de belle-m�re", "Larmes de Job", "Latanier bleu", "Laur�ole", "Lavande", "Lavande aspic",
			"Lavande de mer", "Lavande � toupet", "Lavande du d�sert", "Lavandin", "Lavat�re", "Lentille cultiv�e",
			"Lentille d'eau", "Lentisque", "L�ontodon", "Leuz�e", "Liane-de-feu", "Liane de jade", "Lichen", "Liciet",
			"Lierre", "Lierre grimpant", "Lierre terrestre", "Ligulaire", "Lilas", "Lilas commun", "Lime", "Limettier",
			"Limodore � feuilles avort�es", "limon", "Laurier", "Laurier rose", "Laurier sauce", "Laurier-cerise",
			"Laurier de Saint-Antoine", "Lin", "Lin bisannuel", "Lin campanul�", "Lin cultiv�", "Lin dress�",
			"Lin � feuilles �troites", "Lin jaune", "Lin de Narbonne", "Linaigrette", "Linaigrette � feuilles larges",
			"Linaigrette � feuilles �troites", "Linaire", "Linaire commune", "Linaire �latine", "Linn�e",
			"Liparis de Loesel", "Liquidambar", "Liv�che", "Liseron", "Liseron des champs", "Liseron des dunes",
			"Liseron des haies", "Liseron tricolore", "List�re � feuilles ovales", "List�re cord�e", "Litchi",
			"Liv�che", "Lob�lie", "Lob�lie br�lante", "Lob�lie �rine", "Lotier", "Lotier cornicul�", "Lotier des Alpes",
			"Lotier des rocailles", "Lotier-pois", "Lotus", "Lotus sacr�", "Luffa", "Lunaire",
			"Luneti�re � oreillettes", "Lupin", "Luzerne", "Luzerne arborescente", "Luzerne cultiv�e",
			"Luzerne d'Arabie", "Luzerne lupuline", "Luzerne marine", "Luzule", "Lychnide", "Lyciet", "Lycopode",
			"Lysichite blanc", "Lysimaque commune", "Lysimaque nummulaire", "Lys (ou Lis)", "Lis de mer",
			"Lys des Incas", "Lis des Pyr�n�es", "Lis d'un jour", "Lis maritime", "Lis Martagon", "Lys rouge",
			"Lis tigr�", "Macadamier", "Maceron", "M�che", "M�cre nageante", "M�cre bicorne", "Magnolia",
			"Magnolia � grandes fleurs", "Magnolia �toil�", "Mahonia", "Mahonia du Japon", "Ma�anth�me",
			"Ma�anth�me � deux feuilles", "Ma�s", "Mamillaire", "Mancenillier", "Mandarine", "Mandragore",
			"Mangoustanier", "Mangue", "Manguier", "Manioc", "Marguerite", "Marguerite commune", "Marjolaine",
			"Marronnier", "Marrube", "Marsil�e", "Masdevallia", "Massette", "Mat�", "Mathiole ou Matthiole",
			"Matricaire", "Mauve", "Mauve alc�e", "Mauve sylvestre", "Megaskepasma", "M�lampyre",
			"M�lampyre des champs", "M�lampyre des pr�s", "M�l�ze", "M�l�ze d'Europe", "M�l�ze du Japon",
			"M�l�ze laricin", "M�lilot", "M�lilot officinal", "M�lilot �lev�", "M�lique", "M�lisse officinale",
			"M�litte � feuilles de m�lisse", "Melon", "Menthe", "Menthe pouliot", "Menthe-coq", "M�nyanthe", "M�on",
			"Mercuriale", "Mercuriale annuelle", "Mercuriale vivace", "Merisier", "M�tas�quo�a", "Micocoulier",
			"Micocoulier de Provence", "Millepertuis", "Millepertuis perfor�", "Millepertuis � grandes fleurs",
			"Millepertuis �l�gant", "Millet", "Millet ", "commun", "Millet des oiseaux", "Millet japonais", "Mimosa",
			"Mimosa des quatre saisons", "Mimosa odorant", "Mimosa � bois noir", "Mimosa de Paris", "Mimule",
			"Mimule cardinale", "Minette", "Miroir de V�nus", "Mol�ne", "Mol�ne blattaire", "Mol�ne de Ph�nicie",
			"Molinie", "Momordique", "Monarde", "Mongette", "Monnaie du pape", "Montbr�tiaMorelle", "Morelle de Linn�",
			"Morelle faux jasmin", "Morelle noire", "Mor�ne", "Mouron", "Moutarde blanche", "Moutarde brune",
			"Moutarde des champs", "Moutarde noire", "Muflier", "Muguet", "Muguet de mai", "Muscari",
			"Muscari � toupet", "M�rier", "M�rier blanc", "M�rier noir", "M�rier � papier", "M�re de Logan", "Muscari",
			"Myosotis", "Myriophille", "Myrsine africana", "Myrte", "Myrtille", "Narcisse", "Narcisse d'Asso",
			"Narcisse des po�tes", "Narcisse douteux", "Narcisse jaune", "Nashi", "Navet", "Navet du diable", "Navette",
			"N�flier", "N�flier du Japon", "N�nuphar", "N�nuphar blanc", "N�nuphar jaune", "N�or�g�lia",
			"N�ottie nid d'oiseau", "N�rine", "Nerprun", "Nerprun alaterne", "Nielle des bl�s", "Nigelle", "Niv�ole",
			"Noix de muscade", "Noyer", "Nopal", "Noyer cendr�", "Noyer commun", "Noyer du Br�sil",
			"Noyer du Queensland", "Noyer noir", "N�flier", "Nymph�a", "Nyssa", "Oca du P�rou", "�illet",
			"�illet arm�ria", "�illet de France", "�illet des Chartreux", "�illet commun", "�illet des glaciers",
			"�illet mignardise", "obione", "�nanthe", "�nanthe aquatique", "�nanthe faux boucage",
			"�nanthe � feuilles de sila�s", "Oignon", "Oiseau de paradis", "Olivier", "Olivier de Boh�me", "Onagre",
			"Onopordon", "Ophioglosse", "Orcanette", "Ophrys", "Ophrys abeille", "Ophrys araign�e", "Ophrys bourdon",
			"Ophrys brun", "Ophrys b�casse", "Ophrys de Gascogne", "Ophrys de l'Aveyron", "Ophrys jaune",
			"Ophrys miroir", "Ophrys mouche", "Ophrys noir", "Ophrys petite araign�e", "Opopanax", "Oranger",
			"Oranger des Osages", "Oranger du Mexique", "Orcanette", "Orchid�e", "Orchid�e jacinthe", "Orchis",
			"Orchis bouc", "Orchis bouffon", "Orchis br�l�", "Orchis � un bulbe", "Orchis conique", "Orchis de Fuchs",
			"Orchis g�ant", "Orchis guerrier", "Orchis globuleux", "Orchis intact", "Orchis m�le", "Orchis moucheron",
			"Orchis n�glig�", "Orchis papillon", "Orchis pourpre", "Orchis pyramidal", "Orchis singe", "Orchis sureau",
			"Orchis tachet�", "Oreille-d'ours", "Orge commune", "Orge des rats", "Origan", "Orme", "Orne",
			"Ornithogale", "Ornithogale des Pyr�n�es", "Ornithogale � ombelle", "Orobanche", "Orpin", "Orpin blanc",
			"Orpin �cre", "Ortie", "Ortie � pilules", "Oseille", "Osier", "Osmonde", "Osmonde royale",
			"Oursin � t�tes rondes", "Oxalide", "Oxalis", "Oxalis cornicul�e", "Oxalis petite oseille", "Oyat",
			"Pacane", "Pacanier", "Pachystachys jaune", "Pal�tuvier", "Pal�tuvier rouge", "Pamplemousse", "Panais",
			"Pancrace d'Illyrie", "Pandanus", "Palmier", "Palmier de Chine", "Palmier � b�tel", "Palmier � huile",
			"Palmier-dattier", "Palmier � huile africain", "Palmier Boucanier", "Palmier de Bismark", "Palmier nain",
			"Palmier de No�l", "Palmier chaume de Floride", "Palmier des Everglades", "Palmier p�che",
			"Palmier royal de Cuba", "Panicaut", "Panicaut champ�tre", "Panicaut de Bourgat", "Panicaut des Alpes",
			"Panicaut � feuilles de yucca", "Panicaut maritime", "Papayer", "Cyperus papyrus", "P�querette",
			"Parisette � quatre feuilles", "Passerage", "Passiflore", "Pastel", "Past�que", "Patate douce", "Patchouli",
			"P�tisson", "P�turin", "P�turin annuel", "P�turin commun", "P�turin des pr�s", "Pavot",
			"Pavot de Californie", "Pavot cornu", "P�cher", "P�diculaire", "Peigne de V�nus", "Pens�e de Rouen",
			"Pens�e des champs", "Pens�e � deux fleurs", "Penstemon", "Perce-neige", "Pernambouc", "Persicaire",
			"Persil", "Pervenche", "Pervenche de Madagascar", "P�tasite", "Petite centaur�e", "Petite cigu�",
			"Petit gen�t d'Espagne", "Petit houx", "Petite pervenche", "P�tunia", "Peuplier", "Peuplier baumier",
			"Peuplier blanc", "Peuplier noir d'Am�rique", "Peyotl", "Phac�lie", "Phac�lie � feuilles de tanaisie",
			"Philodendron", "Phlox", "Phytolaque", "Pied d'alouette", "Pied-de-veau", "Pigamon", "Piment",
			"Piment de la Jama�que", "Pimprenelle", "Pin", "Pin Napol�on", "Pin cembro", "Pin d'Alep",
			"Pin de l'Himalaya", "Pin du Chili", "Pin maritime", "Pin de l'Or�gon", "Pin parasol", "Pin sylvestre",
			"Pissenlit", "Pistachier", "Pistachier vrai", "Pivoine", "Pivoine officinale", "Plane", "Plantain",
			"Plantain interm�diaire", "Plantain Pied-de-li�vre", "Plante annuelle", "Plante bisannuelle",
			"Plante-caillou", "Plante cam�l�on", "Plante cobra", "Plante-crevette", "Plante vivace", "Plaquebi�re",
			"Plaqueminier", "Plaine blanche", "Plaine rouge", "Platane", "Platane d'Occident", "Platane d'Orient",
			"Platanth�re � deux feuilles", "Platanth�re � fleurs vertes", "Pohutukawa", "Poireau", "Poireau d'�t�",
			"Poirier", "Poir�e", "Pois potager ou fourrager", "Pois chiche", "Pois de senteur", "Pois doux",
			"Pois vivace", "Poivre", "Poivre du Sichuan", "Poivrier", "Poivron", "Pol�moine", "Pol�moine bleue",
			"Polygale", "Polypode vulgaire", "Pomelo", "Pomme de terre", "Pommier", "Pommier-canellier",
			"Pommier-malacca", "Pommier de Sodome", "Pommier domestique", "Pommier sauvage", "Poncirus",
			"Pont�d�rie � feuilles en c�ur", "Populage des marais", "Porcelle", "Posidonie", "Potamot", "Potentille",
			"Potentille ans�rine", "Potentille fausse alch�mille", "Potiron", "pourpier", "Pr�le", "Pr�nanthe pourpre",
			"Prestoa", "Primev�re", "Primev�re de Chine", "Primev�re commune", "Primev�re du Japon",
			"Primev�re officinale", "Primev�re �lev�e", "Prot�e", "Prunellier", "Prunier", "Prunier de Cyth�re",
			"Prunier d'Espagne", "Prunier mombin", "Psoral�e bitumineuse", "Pulicaire", "Pulmonaire",
			"Pulmonaire officinale", "Pulsatille", "Pulsatille rouge", "Pyracantha", "Pyr�thre", "Quatre-�pices",
			"Quenettier", "Quercitron", "Quetsche", "Queue de chat", "Queue-de-li�vre", "Quinoa", "Quinquina", "Radis",
			"Raifort", "Raiponce", "Raiponce en �pi", "Raiponce orbiculaire", "Raisin", "Raisin d'Am�rique",
			"Raisin d'ours", "Raisin de mer", "Ramondie", "Raphia", "Ravenala", "Ravenelle", "Ray-Grass", "R�glisse",
			"Reine-marguerite", "Reine de la nuit", "Reine-des-pr�s", "Renoncule", "Renoncule aquatique",
			"Renoncule des rivi�res", "Renoncule t�te d'or", "Renou�e", "Renou�e du Japon", "Renou�e bistorte",
			"Renou�e d'Aubert", "Renou�e des haies", "Renou�e liseron", "Renou�e persicaire", "R�s�da",
			"R�s�da raiponce", "Rhododendron", "Rhododendron cili�", "Rhododendron ferrugineux", "Rhubarbe", "Ricin",
			"Riz", "Riz sauvage", "Robinier", "Romarin", "Ronce", "Ronce commune", "Ronce petit-m�rier", "Roquette",
			"Roquette de mer", "Rose", "Rose de Chine", "Rose de No�l", "Rose de Sharon", "Rose tr�mi�re", "Rosier",
			"Rose Myrtle", "Roseau", "Roseau commun", "Rudbeckia", "Rue officinale", "Rutabaga", "Sabline",
			"Sabline d'Espagne", "Sabot de V�nus", "Safran", "Sagittaire", "Sainfoin", "Sainfoin cultiv�",
			"Saintpaulia", "Salicaire", "Salicaire commune", "Salicorne", "Salicorne d'Europe", "Salsepareille",
			"Salsifis", "Salsifis cultiv�", "Salsifis des pr�s", "Sanchezia", "Sandragon", "Sanguinaire", "Sanguisorbe",
			"Sanicle d'Europe", "Santoline", "Santoline � feuilles de romarin", "Sapin", "Sapin blanc",
			"Sapin d'Andalousie", "Sapin de C�phalonie", "Sapin du Colorado", "Sapin de Nordmann", "Sapin de Vancouver",
			"Saponaire", "Saponaire officinale", "Sapotillier", "Sarrac�nie", "Sarrac�nie pourpre", "Sarrasin",
			"Sarriette", "Sauge", "Sauge des pr�s", "Sauge officinale", "Sauge sclar�e", "Saule", "Saule blanc",
			"Saule pleureur", "Savonnier", "Saxifrage", "Saxifrage sarmenteux", "Scabieuse", "Scabieuse colombaire",
			"Scabieuse � feuilles de gramin�e", "Scabieuse � trois �tamines", "Scarole", "Sceau de Salomon",
			"Sceau de Salomon multiflore", "Sceau de Salomon odorant", "Scille", "Scille d'automne",
			"Scille de Sib�rie", "Scille du P�rou", "Scirpe", "Scolopendre", "Scolyme", "Scrophulaire",
			"Scrophulaire des chiens", "Scrophulaire noueuse", "Scrophulaire � oreillettes", "Scutellaire",
			"Scutellaire � casque", "Sedum", "S�dum remarquable", "S�laginelle", "S�n�", "S�ne�on", "S�ne�on de Jacob",
			"S�ne�on du Cap", "S�ne�on g�ant du Kilimandjaro", "Sensitive", "S�quoia", "S�quoia g�ant",
			"S�quoia � feuilles d'if", "S�rapias en c�ur", "S�rapias � labelle allong�", "Seringat", "Serpolet",
			"S�same", "S�s�li", "Sesl�rie", "Sharon", "Sil�ne", "Sil�ne acaule", "Sil�ne conique", "Sil�ne enfl�",
			"Sisal", "Sisymbre", "Sobralia", "Soja", "Soldanelle", "Soldanelle des Alpes", "Soleil", "Solidage",
			"Sorbier", "Sorbier des oiseleurs", "Sorgho", "Souchet", "Souchet comestible", "Souci", "Souci officinal",
			"Spartier � tiges de jonc", "Spiranthe d'automne", "Spir�e", "Spir�e ulmaire", "Statice",
			"Statice de l'ouest", "Stellaire", "Sureau", "Sureau hi�ble", "Sureaux � grappes", "Surettier", "Sycomore",
			"Symphorine", "Tabac", "Tabac d'ornement", "Tabouret perfoli�", "Tag�te", "Tamarinier", "Tamaris", "Tamier",
			"Tanaisie", "Tanaisie commune", "Tangerine", "Taro", "Teff", "T�tragone", "Th�ier", "Thlaspi", "Thuya",
			"Thuya du Canada", "Thym", "Tillandsia", "Tilleul", "Tilleul � petites feuilles", "Tomate", "Topinambour",
			"Tormentille", "Tournesol", "Toute-bonne", "Tradescantia", "Tr�fle", "Tr�fle � feuilles �troites",
			"Tr�fle alpestre", "Tr�fle alpin", "Tr�fle bai", "Tr�fle blanc", "Tr�fle couch�", "Tr�fle d'Alexandrie",
			"Tr�fle d'eau", "Tr�fle de Perse (ou Tr�fle renvers�)", "Tr�fle de Micheli", "Tr�fle de Thal",
			"Tr�fle de montagne", "Tr�fle des pr�s (ou Tr�fle violet)", "Tr�fle dor�", "Tr�fle douteux",
			"Tr�fle �toil�", "Tr�fle hybride", "Tr�fle incarnat", "Tr�fle jaun�tre", "Tr�fle Pied-de-li�vre",
			"Tr�fle rouge�tre", "Tr�fle souterrain", "Tremble", "Tribule terrestre", "Tricyrtis � feuilles larges",
			"Trigonelle", "Trille blanc", "Trille rouge", "Tro�ne", "Tro�ne commun", "Trolle", "Trolle d'Europe",
			"Trompette de Virginie", "Trompette de m�duse", "Tsuga", "Tub�reuse", "Tub�reuse bleue", "Tulipe",
			"Tulipe m�ridionale", "Tulipier", "Tulipier de Chine", "Tulipier de Virginie", "Turraea", "Tussilage",
			"Urosperme", "Utriculaire", "Val�riane", "Val�riane dio�que", "Val�riane officinale", "Val�riane rouge",
			"Vallisn�rie", "Vanda", "Vanille", "Vanillier", "Varaire", "Vase d'argent", "Vendangeuse", "V�r�tre",
			"Vergerette", "Verge d'or", "Verge d'or bicolore", "Verne", "V�ronique", "V�ronique d'Allioni",
			"V�ronique des Alpes", "V�ronique buissonnante", "V�ronique des champs", "V�ronique couch�e",
			"V�ronique en �pi", "V�ronique � feuilles de lierre", "V�ronique � feuilles d'ortie",
			"V�ronique � feuilles de serpolet", "V�ronique filiforme", "V�ronique germandr�e",
			"V�ronique � longues feuilles", "V�ronique mouron d'eau", "V�ronique officinale", "V�ronique de Perse",
			"V�ronique petit-ch�ne", "V�ronique rampante", "V�ronique des ruisseaux", "V�ronique sans feuilles",
			"V�ronique voyageuse", "Verveine", "Verveine citronn�e", "Verveine officinale", "Vesce", "Vesce commune",
			"Vesce craque", "Vesce des haies", "Vesce h�riss�e", "V�tiver", "Vigne", "Vigne blanche", "Vigne vierge",
			"Violettes et pens�es", "Violette blanche", "Violette des chiens", "Violette odorante",
			"Violette d'Usumbura", "Violette lact�e", "Viorne", "Viorne lantane", "Viorne mancienne", "Viorne obier",
			"Viorne tin", "Vip�rine", "Vip�rine commune", "Volubilis", "Vuln�raire", "Vulpin", "Vulpin des champs",
			"Vulpin des pr�s", "Vri�sia splendide", "Wasabi", "Witloof", "Xanthoceras", "Xanthosoma", "Xeranthemum",
			"Xerophyllum", "Yagua", "Yeuse", "Ylang-ylang", "Ypr�au", "Yucca", "Yucca � feuille d'alo�s", "Yogalophile",
			"Zamia", "Zamioculcas zamiifolia", "Zannichellie", "Zelkova", "Zinnia", "Zizanie", "Zost�re", "Zygopetalum"

	};

}
