package erp;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PdfFacture {
	private String fichierFondDePage;
	private String fichierDestination;
	private Long idFacture;
	private ResultSet rsFacture;
	private ResultSet rsLigneFacture;
	private String fichierProduit;

	public PdfFacture(Long idFacture) {
		this.idFacture = idFacture;
		this.fichierFondDePage = Parametres.get("FondDePageFacture", "ModeleFacture.pdf");
		this.fichierDestination = Parametres.get("CheminFichierFacture", "");
		this.fichierDestination = fichierDestination+File.separator+Parametres.get("PrefixeFichierFacture", "Facture");
		if (OuvreRequettes()) {
			RempliPdf();
		}
	}

	private boolean OuvreRequettes() {
		try {
			PreparedStatement statement = ErpFiche.conn.prepareStatement(
					"SELECT * FROM FACTURE LEFT JOIN CLIENT ON CLIENT.ID = FACTURE.ID_CLIENT LEFT JOIN TVA ON TVA.ID = FACTURE.ID_TVA WHERE Facture.ID=?");
			statement.setLong(1, this.idFacture.longValue());
			this.rsFacture = statement.executeQuery();
			if (!this.rsFacture.next()) {
				return false;
			}
			statement = ErpFiche.conn.prepareStatement("SELECT * FROM LIGNE_FACTURE WHERE ID_FACTURE=? ORDER BY LIGNE");
			statement.setLong(1, this.idFacture.longValue());
			this.rsLigneFacture = statement.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
	}

	private void RempliPdf() {
		try {
			fichierProduit = String.valueOf(this.fichierDestination) + this.rsFacture.getString("Facture.NUMERO")
					+ ".pdf";
			PdfRemplissable pdf = new PdfRemplissable(this.fichierFondDePage, fichierProduit);
			pdf.setMiseEnEvidence(!"N".equals(Parametres.get("PdfMisEnEvidence", "N")));
			pdf.nouvellePage();
			pdf.setFontSize(11.0F);
			String date_Facture = getDateFacture();
			pdf.placeParagraph(pdf.paragraphFromString(date_Facture), 430.0F, 770.0F, 130.0F, 18.0F);
			int positionYClient = 730;
			int positionXClient = 327;
			int positionXClientLabel = 360;
			int positionXClientVille = 380;
			int tailleLigneClient = -14;

			pdf.placeParagraph(pdf.paragraphFromString(FicheClient.getCiviliteNomPrenom(this.rsFacture)),
					positionXClient, positionYClient, 190.0F, 18.0F);
			pdf.placeParagraph(pdf.paragraphFromString(this.rsFacture.getString("CLIENT.ADRESSE1")), positionXClient,
					positionYClient + tailleLigneClient, 155.0F, 18.0F);
			pdf.placeParagraph(pdf.paragraphFromString(this.rsFacture.getString("CLIENT.ADRESSE2")), positionXClient,
					2 * tailleLigneClient + positionYClient, 155.0F, 18.0F);
			pdf.placeParagraph(pdf.paragraphFromString(this.rsFacture.getString("CLIENT.CODE_POSTAL")), positionXClient,
					3 * tailleLigneClient + positionYClient, 50.0F, 18.0F);
			pdf.placeParagraph(pdf.paragraphFromString(this.rsFacture.getString("CLIENT.VILLE")), positionXClientVille,
					3 * tailleLigneClient + positionYClient, 110.0F, 18.0F);
			pdf.placeParagraph(pdf.paragraphFromString(this.rsFacture.getString("CLIENT.TEL")), positionXClientLabel,
					4 * tailleLigneClient + positionYClient, 210.0F, 18.0F);
			pdf.placeParagraph(pdf.paragraphFromString(this.rsFacture.getString("CLIENT.EMAIL")), positionXClientLabel,
					5 * tailleLigneClient + positionYClient, 210.0F, 17.0F);
			pdf.setFontSize(15.0F);
			pdf.placeParagraph(pdf.paragraphFromString(this.rsFacture.getString("FACTURE.NUMERO")), 120.0F, 667.0F,
					150.0F, 18.0F);
			pdf.setFontSize(11.0F);
			int hauteurLigne = 20;
			int yTableau = 600;
			int positionXTotal = 545;
			int positionLargeurTotal = 105;
			int i = 0;
			double totalHT = 0.0D;
			while (this.rsLigneFacture.next()) {
				pdf.setTextAlignment(TextAlignment.LEFT);
				pdf.placeParagraph(pdf.paragraphFromString(this.rsLigneFacture.getString("DESIGNATION")), 45.0F,
						(yTableau - i * hauteurLigne), 280.0F, 18.0F);
				pdf.setTextAlignment(TextAlignment.CENTER);
				pdf.placeParagraph(pdf.paragraphFromString(formatFloat(this.rsLigneFacture.getString("QUANTITE"))),
						352.0F, (yTableau - i * hauteurLigne), 52.0F, 18.0F);
				pdf.placeParagraph(
						pdf.paragraphFromString(
								ajouteEuro(formatFloat(this.rsLigneFacture.getString("PRIX_UNITAIRE")))),
						410.0F, (yTableau - i * hauteurLigne), 52.0F, 18.0F);
				pdf.setTextAlignment(TextAlignment.RIGHT);
				pdf.placeParagraph(pdf.paragraphFromString(ajouteEuro(this.rsLigneFacture.getString("MONTANT"))),
						positionXTotal, (yTableau - i * hauteurLigne), positionLargeurTotal, 18.0F);
				totalHT += this.rsLigneFacture.getDouble("MONTANT");
				i++;
				if (i == 24) {
					i = 0;
					pdf.nouvellePage();
				}
			}
			pdf.setTextAlignment(TextAlignment.LEFT);
			pdf.setTextColor((Color) new DeviceRgb(255, 255, 255));
			double remise = this.rsFacture.getDouble("FACTURE.REMISE");
			float positionYTotal = 108;
			int positionXLabels = 330;
			float tailleLigneTotal = 14.2F;

			pdf.setTextAlignment(TextAlignment.LEFT);
			pdf.placeParagraph(pdf.paragraphFromString("Prix total HT"), positionXLabels, positionYTotal, 65.0F, 18.0F);
			pdf.setTextAlignment(TextAlignment.RIGHT);
			pdf.placeParagraph(
					pdf.paragraphFromString(
							String.valueOf(String.format("%.02f", new Object[] { Double.valueOf(totalHT) })) + " €"),
					positionXTotal, positionYTotal, positionLargeurTotal, 18.0F);
			positionYTotal -= tailleLigneTotal;
			if (remise >= 0.01D) {
				pdf.setTextAlignment(TextAlignment.LEFT);
				pdf.placeParagraph(pdf.paragraphFromString("Remise"), positionXLabels, positionYTotal, 65.0F, 18.0F);
				pdf.placeParagraph(pdf.paragraphFromString("Total HT"), positionXLabels,
						(positionYTotal - tailleLigneTotal), 65.0F, 18.0F);
				pdf.setTextAlignment(TextAlignment.RIGHT);
				pdf.placeParagraph(pdf.paragraphFromString(
						String.valueOf(String.format("-%.02f", new Object[] { Double.valueOf(remise) })) + " €"),
						positionXTotal, positionYTotal, positionLargeurTotal, 18.0F);
				positionYTotal -= tailleLigneTotal;
				totalHT -= remise;
				pdf.placeParagraph(pdf.paragraphFromString(
						String.valueOf(String.format("%.02f", new Object[] { Double.valueOf(totalHT) })) + " €"),
						positionXTotal, positionYTotal, positionLargeurTotal, 18.0F);
				positionYTotal -= tailleLigneTotal;
			}
			double tauxTva = this.rsFacture.getDouble("TVA.TAUX");
			double totalTTC = totalHT;
			if (tauxTva >= 0.01D) {
				double montantTva = totalHT * tauxTva / 100.0D;
				pdf.setTextAlignment(TextAlignment.LEFT);
				pdf.placeParagraph(pdf.paragraphFromString(this.rsFacture.getString("TVA.LIBELLE")), positionXLabels,
						positionYTotal, 80.0F, 18.0F);
				pdf.setTextAlignment(TextAlignment.RIGHT);
				pdf.placeParagraph(pdf.paragraphFromString(
						String.valueOf(String.format("%.02f", new Object[] { Double.valueOf(montantTva) })) + " €"),
						positionXTotal, positionYTotal, positionLargeurTotal, 18.0F);
				positionYTotal -= tailleLigneTotal;
				totalTTC += montantTva;
			}
			pdf.setIsbold(true);
			pdf.setTextAlignment(TextAlignment.LEFT);
			pdf.placeParagraph(pdf.paragraphFromString("TOTAL TTC"), positionXLabels, positionYTotal, 65.0F, 18.0F);
			pdf.setTextAlignment(TextAlignment.RIGHT);
			pdf.placeParagraph(
					pdf.paragraphFromString(
							String.valueOf(String.format("%.02f", new Object[] { Double.valueOf(totalTTC) })) + " €"),
					positionXTotal, positionYTotal, positionLargeurTotal, 18.0F);
			pdf.sauvegarde();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String ajouteEuro(String string) {
		if (string == null || "".equals(string))
			return string;
		return String.valueOf(string) + " €";
	}

	private String formatFloat(String string) {
		if (string == null)
			return "";
		StringBuilder stringFormatted = new StringBuilder();
		boolean chiffreTrouve = false;
		boolean pointTrouve = false;
		for (int i = string.length() - 1; i >= 0; i--) {
			char ch = string.charAt(i);
			if (ch == '.') {
				pointTrouve = true;
				if (chiffreTrouve)
					stringFormatted.insert(0, ch);
				chiffreTrouve = true;
			} else if (ch != '0' || chiffreTrouve) {
				stringFormatted.insert(0, ch);
				chiffreTrouve = true;
			}
		}
		if (pointTrouve)
			return stringFormatted.toString();
		return string;
	}

	private String getDateFacture() throws SQLException {
		String date_Facture = this.rsFacture.getString("FACTURE.DATE_Facture");
		if (date_Facture == null || "".equals(date_Facture)) {
			date_Facture = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
			String sqlUpdate = "UPDATE FACTURE SET DATE_FACTURE=? WHERE ID = ?";
			PreparedStatement statement = ErpFiche.conn.prepareStatement(sqlUpdate);
			statement.setString(1, date_Facture);
			statement.setLong(2, this.idFacture.longValue());
			statement.executeUpdate();
		}
		try {
			return DateFormat.getDateInstance(1, new Locale("FR", "fr"))
					.format((new SimpleDateFormat("yyyy-MM-dd")).parse(date_Facture));
		} catch (ParseException e) {
			e.printStackTrace();
			return date_Facture;
		}
	}

	public String getFichierProduit() {
		return fichierProduit;
	}
}
