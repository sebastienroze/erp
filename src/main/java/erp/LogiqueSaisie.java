package erp;

import java.sql.SQLException;
import java.util.ArrayList;

import rad.DataRAD;
import rad.KeySelectListener;
import rad.ListenerRAD;
import rad.RadButton;
import rad.RadComponentFactory;
import rad.RadContainer;

public class LogiqueSaisie {
	protected static final int modeConsultation = 0;
	protected static final int modeCreation = 1;
	protected static final int modeModification = 2;
	private RadButton btCreate;
	private RadButton btCopy;
	private RadButton btUpdate;
	private RadButton btDelete;
	private RadButton btOk;
	private RadButton btCancel;
	private RadButton btPrecedent;
	private RadButton btSuivant;
	private RadButton btFermer;
	private int modeFiche;
	private int selectedLine;
	private ArrayList<Long> keyValues;
	private DataRAD dataRad;
	private ErpFiche erpFiche;
	private RadContainer buttonContainer;
	private KeySelectListener onClose;
	private ListenerRAD creeDonneeListener;

	public LogiqueSaisie(ErpFiche erpFiche, DataRAD dataRad, int selectedLine, ArrayList<Long> keyValues,
			KeySelectListener onClose) {
		this.dataRad = dataRad;
		this.erpFiche = erpFiche;
		this.selectedLine = selectedLine;
		this.keyValues = keyValues;
		this.onClose = onClose;
		buttonContainer = erpFiche.componentFactory.createContainer("boutons");
		CreeBouttons(erpFiche.componentFactory);
		buttonContainer.addComponent(btPrecedent);
		buttonContainer.addComponent(btSuivant);
		buttonContainer.addComponent(btCreate);
		buttonContainer.addComponent(btCopy);
		buttonContainer.addComponent(btUpdate);
		buttonContainer.addComponent(btDelete);
		buttonContainer.addComponent(btOk);
		buttonContainer.addComponent(btCancel);
		buttonContainer.addComponent(btFermer);
		setNavivationButtonState();
	}

	public RadContainer getBouttonsContainer() {
		return buttonContainer;
	}

	public void CreeBouttons(RadComponentFactory componentFactory) {
		btCreate = componentFactory.createButton("Nouveau", "Nouveau").addActionListener(e -> {
			dataRad.clear();
			dataRad.setReadOnly(false);
			LogiqueSaisie.this.setModeFiche(modeCreation);
		});
		btCopy = componentFactory.createButton("Dupliquer", "Dupliquer").addActionListener(e -> {
			dataRad.setReadOnly(false);
			LogiqueSaisie.this.setModeFiche(modeCreation);
		});
		btUpdate = componentFactory.createButton("Modifier", "Modifier").addActionListener(e -> {
			dataRad.setReadOnly(false);
			LogiqueSaisie.this.setModeFiche(modeModification);
		});
		btDelete = componentFactory.createButton("Supprimer", "Supprimer").addActionListener(e -> {
			try {
				dataRad.delete();
				dataRad.clear();
				btCopy.setReadOnly(true);
				btDelete.setReadOnly(true);
				btUpdate.setReadOnly(true);

			} catch (SQLException eSql) {				
				erpFiche.showMessage("Erreur de suppression !\n"
				  +eSql.getLocalizedMessage() );				 
			}
		});
		btFermer = componentFactory.createButton("Retour", "Retour").addActionListener(e -> {
			erpFiche.close();
			if (onClose != null)
				onClose.KeySelected(getKey());
		});

		btOk = componentFactory.createButton("Ok", "OK").addActionListener(e -> {
			try {
				boolean erreur = false;
				dataRad.setReadOnly(false);
				if (modeFiche == modeCreation) {
					dataRad.create();
					keyValues.add(dataRad.getKeyValue());
					selectedLine = keyValues.size() - 1;
					setNavivationButtonState();
					btCopy.setReadOnly(false);
					btDelete.setReadOnly(false);
					btUpdate.setReadOnly(false);

				}
				if (modeFiche == modeModification) {
					if (!dataRad.update()) {
						erreur = true;
						erpFiche.showMessage("L'enregistement n'existe plus !");
					}
				}
				if (!erreur) {
					dataRad.setReadOnly(true);
					LogiqueSaisie.this.setModeFiche(modeConsultation);
				}
			} catch (SQLException eSql) {
				eSql.printStackTrace();

				erpFiche.showMessage("Erreur dans les données !" + eSql.getLocalizedMessage());
			}
		});

		btCancel = componentFactory.createButton("Cancel", "Annuler").addActionListener(e -> {
			dataRad.setReadOnly(true);
			LogiqueSaisie.this.setModeFiche(modeConsultation);
			litDonnees();
		});
		btPrecedent = componentFactory.createButton("Precedent", "Precedent").addActionListener(e -> {
			if (selectedLine > 0) {
				selectedLine--;
				litDonnees();
			}
			setNavivationButtonState();
		});
		btSuivant = componentFactory.createButton("Suivant", "Suivant").addActionListener(e -> {
			if (LogiqueSaisie.this.keyValues.size() <= ++selectedLine) {
				selectedLine--;
			} else {
				litDonnees();
			}
			setNavivationButtonState();
		});
		btDelete.setConfirmation("Confirmer la suppression ?");
	}

	public void litDonnees() {
		boolean success = (selectedLine >= 0);
		success = success && dataRad.read(keyValues.get(selectedLine));
		if (!success)
			dataRad.clear();
		btCopy.setReadOnly(!success);
		btDelete.setReadOnly(!success);
		btUpdate.setReadOnly(!success);
	}

	private long getKey() {
		if ((selectedLine < 0) || (selectedLine >= keyValues.size()))
			return -1;
		return keyValues.get(selectedLine);
	}

	public void setNavivationButtonState() {
		btSuivant.setReadOnly(selectedLine >= (keyValues.size() - 1));
		btPrecedent.setReadOnly(selectedLine <= 0);
	}

	public void setModeFiche(int modeFiche) {
		if (modeFiche != modeConsultation) {
			ErpFiche.setCanClose("Quitter sans sauver ?");
			if (modeFiche == modeCreation) {
				doCreeDonneeListener();
			}
		} else
			ErpFiche.setCanClose(null);
		this.modeFiche = modeFiche;
		btPrecedent.setVisible(modeFiche == modeConsultation);
		btSuivant.setVisible(modeFiche == modeConsultation);
		btCreate.setVisible(modeFiche == modeConsultation);
		btCopy.setVisible(modeFiche == modeConsultation);
		btUpdate.setVisible(modeFiche == modeConsultation);
		btDelete.setVisible(modeFiche == modeConsultation);
		btOk.setVisible(modeFiche != modeConsultation);
		btCancel.setVisible(modeFiche != modeConsultation);
		btFermer.setVisible(modeFiche == modeConsultation);
	}

	public void init() {
		if (selectedLine >= keyValues.size())
			selectedLine = keyValues.size() - 1;
		setNavivationButtonState();
		if (selectedLine < 0) {
			btCreate.performAction();
		} else {
			setModeFiche(modeConsultation);
			litDonnees();
			dataRad.setReadOnly(true);
		}
	}

	public void addCreeDonneeListener(ListenerRAD creeDonneeListener) {
		this.creeDonneeListener = creeDonneeListener;
	}

	private void doCreeDonneeListener() {
		if (creeDonneeListener != null)
			creeDonneeListener.actionPerformed(null);
	}
}
