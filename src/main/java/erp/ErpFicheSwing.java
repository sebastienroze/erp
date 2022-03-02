package erp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import rad.DataRAD;
import rad.RadComponent;
import rad.RadSQLGrid;
import rad.swing.DataSwing;
import rad.swing.SwingComponent;
import rad.swing.SwingComponentFactory;
import rad.swing.SwingSQLGrid;

public class ErpFicheSwing extends ErpFiche {
	private GridBagConstraints gbc;
	protected JComponent panelNorth;
	protected JComponent panelCenter;
	protected JComponent panelSaisie = null;

	public ErpFicheSwing() {
		super();
		componentFactory = new SwingComponentFactory();
		gbc = new GridBagConstraints();
		gbc.gridy = 0;
		panelNorth = new JPanel();
//		panelNorth.setBorder(new LineBorder(Color.WHITE));
		panelCenter = new JPanel();
//		panelCenter.setBorder(new LineBorder(Color.YELLOW));
		panelSaisie = panelNorth;
		panelSaisie.setLayout(new GridBagLayout());
//		panelSaisie.setBorder(new LineBorder(Color.BLUE));
	}

	@Override
	protected void ajoutTitre(String titre) {
		JLabel lTitre;
		lTitre = new JLabel(titre);
		Font labelFont = lTitre.getFont();
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
//		lTitre.setBorder(new LineBorder(Color.BLACK));
		lTitre.setFont(new Font(labelFont.getFontName(), Font.BOLD, (int) (labelFont.getSize() * 1.2)));
			gbc.gridx = 0;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.ipady = 5;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			panelSaisie.add(lTitre, gbc);
			gbc.gridy = gbc.gridy + 1;
			gbc.ipady = 0;
	}

	@Override
	protected ErpFicheSwing createFiche(String nom) {
		ErpFicheSwing erpFicheSwing = new ErpFicheSwing();
		ficheSuivante = erpFicheSwing;
		erpFicheSwing.fichePrecedente = this;
		Erp.mainPanel.remove(panelCenter);
		Erp.mainPanel.remove(panelNorth);
		Erp.mainPanel.add(erpFicheSwing.panelCenter, BorderLayout.CENTER);
		Erp.mainPanel.add(erpFicheSwing.panelNorth, BorderLayout.NORTH);
		Erp.mainPanel.revalidate();
		Erp.mainPanel.repaint();
		return erpFicheSwing;
	}

	@Override
	protected void close() {
		Erp.mainPanel.remove(panelCenter);
		Erp.mainPanel.remove(panelNorth);
		Erp.mainPanel.revalidate();
		Erp.mainPanel.add(((ErpFicheSwing) fichePrecedente).panelCenter, BorderLayout.CENTER);
		Erp.mainPanel.add(((ErpFicheSwing) fichePrecedente).panelNorth, BorderLayout.NORTH);
		fichePrecedente.ficheSuivante = null;
		Erp.mainPanel.repaint();
	}

	@Override
	protected void ajoutGrille(RadSQLGrid grille) {
			// panelFiche.add(((SwingGrid) grille).getSearchPane(), BorderLayout.CENTER);
			Erp.mainPanel.remove(panelCenter);
			panelCenter = ((SwingSQLGrid) grille).getScrollPane();
			Erp.mainPanel.add(panelCenter, BorderLayout.CENTER);
			gbc.weighty = 1;
			gbc.gridx = 0;
			gbc.gridwidth = 2;
			Component searchPane = ((SwingSQLGrid) grille).getSearchPane();
			if (searchPane!=null) panelSaisie.add(searchPane, gbc);
			gbc.gridy = gbc.gridy + 1;

		
	}

	@Override
	protected void ajoutComposant(RadComponent comp) {
//		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		panelSaisie.add(((SwingComponent) comp).getComponent(), gbc);
		gbc.gridy = gbc.gridy + 1;
	}

	@Override
	protected void ajoutSaisie(String label, RadComponent component) {
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelSaisie.add(new JLabel(label), gbc);
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.LINE_START;
		panelSaisie.add(((SwingComponent) component).getComponent(), gbc);
		gbc.gridy = gbc.gridy + 1;
	}

	@Override
	protected void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	protected DataRAD createDataRad(String tableName, String keyField) {
		DataSwing data = new DataSwing(conn, tableName, keyField);
		return data;
	}

	@Override
	protected void afficheFichier(String nomFichier) {
		if (Desktop.isDesktopSupported()) {
			try {
				File myFile = new File(nomFichier);
				Desktop.getDesktop().open(myFile);
			} catch (IOException iOException) {
			}
		}
	}
}
