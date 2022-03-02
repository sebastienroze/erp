package erp;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import java.io.IOException;

public class PdfRemplissable {
	private int nbPages;
	private PdfDocument pdfImport;
	private PdfDocument pdfDocument;
	private Canvas canvas = null;
	private Color evidenceColor;
	private Color blackColor;
	private Color textColor;
	private PdfFont textFont;
	private float fontSize;
	private TextAlignment textAlignment;
	private boolean miseEnEvidence = false;
	private boolean isbold = false;

	public PdfRemplissable(String fichierFondDePage, String fichierDestination) throws IOException {
		PdfReader pdfReader = new PdfReader(fichierFondDePage);
		this.pdfImport = new PdfDocument(pdfReader);

		PdfWriter pdfWriter = new PdfWriter(fichierDestination);
		this.pdfDocument = new PdfDocument(pdfWriter);
		this.nbPages = 0;
//		this.yellowColor = (Color) new DeviceRgb(255, 255, 0);
		this.evidenceColor = (Color) new DeviceRgb(255, 128, 64);
		this.blackColor = (Color) new DeviceRgb(0, 0, 0);
		this.textColor = this.blackColor;
		this.fontSize = 11.0F;
		this.textAlignment = TextAlignment.LEFT;
	}

	public PdfFont findFontInForm(PdfDocument pdfDoc, PdfName fontName) {
		PdfDictionary acroformDict = ((PdfDictionary) pdfDoc.getCatalog().getPdfObject())
				.getAsDictionary(PdfName.AcroForm);
		if (acroformDict == null) {
			return null;
		}

		PdfDictionary dr = acroformDict.getAsDictionary(PdfName.DR);
		if (dr == null) {
			return null;
		}

		PdfDictionary font = dr.getAsDictionary(PdfName.Font);
		if (font == null) {
			return null;
		}

		for (PdfName key : font.keySet()) {
			if (key.equals(fontName)) {
				return PdfFontFactory.createFont(font.getAsDictionary(key));
			}
		}

		return null;
	}

	public void sauvegarde() {
		pdfImport.close();
		this.canvas.close();
		this.pdfDocument.close();
	}

	public void nouvellePage() {
		if (this.canvas != null)
			this.canvas.close();
		this.nbPages++;
		this.pdfImport.copyPagesTo(1, 1, this.pdfDocument);
		PdfPage pdfPage = this.pdfDocument.getPage(this.nbPages);
		this.canvas = new Canvas(new PdfCanvas(pdfPage), pdfPage.getMediaBox());
	}


	public Paragraph paragraphFromString(String text) {
		if (text == null)
			text = "";
		Paragraph pg =  new Paragraph(text)	;
		if (this.isbold) pg.setBold();		
		pg.setFontSize(this.fontSize).setFontColor(this.textColor);
		if (this.miseEnEvidence) pg.setBackgroundColor(this.evidenceColor);
		return pg;
	}
	
	public void placeParagraph(Paragraph paragraph, float xCoord, float yCoord, float width, float height) {
		paragraph.setWidth(width);
		paragraph.setHeight(height);
		this.canvas.showTextAligned(paragraph, xCoord, yCoord, this.textAlignment);
	}

	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}

	public Color getTextColor() {
		return this.textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public PdfFont getTextFont() {
		return this.textFont;
	}

	public void setTextFont(PdfFont textFont) {
		this.textFont = textFont;
	}

	public float getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public TextAlignment getTextAlignment() {
		return this.textAlignment;
	}

	public void setTextAlignment(TextAlignment textAlignment) {
		this.textAlignment = textAlignment;
	}

	public void setMiseEnEvidence(boolean miseEnEvidence) {
		this.miseEnEvidence = miseEnEvidence;
	}

	public void setIsbold(boolean isbold) {
		this.isbold = isbold;
	}
}
