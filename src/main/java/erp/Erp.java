package erp;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import rad.DataRAD;

public class Erp extends JFrame {
	private static final long serialVersionUID = 1L;
	static JPanel mainPanel;

	public Erp() throws HeadlessException {
		super();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				if (ErpFiche.canClose != null) {
					int dialogResult = JOptionPane.showConfirmDialog(null, ErpFiche.canClose, "Warning",
							JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						Erp.this.dispose();
					}
				} else
					Erp.this.dispose();
			}
		});
		this.setSize(1000, 800);
		this.setLocationRelativeTo(null);
	}

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		Erp erp = new Erp();
		erp.createApplication();
		erp.setVisible(true);
	}

	private void createApplication() {
		
		mainPanel = new JPanel();
//		mainPanel.setBorder(new LineBorder(Color.RED));
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);
		DataRAD.maxFieldLength = 30;

		ErpFicheSwing premiereFiche;
		premiereFiche = new ErpFicheSwing();
		new FicheLogin(premiereFiche);
		mainPanel.add(premiereFiche.panelCenter, BorderLayout.CENTER);
		mainPanel.add(premiereFiche.panelNorth, BorderLayout.NORTH);
	}
}
