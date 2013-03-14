package vue.vueGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.Thread.State;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/**
 * Cette classe s'occupe de la gestion de l'affichage et de l'interface graphique
 * @author Arkkandia
 *
 */
public class Fenetre extends JFrame{
 /**
  * Tout les attribu de la classe et lrd composant graphique
  */
	private static final long serialVersionUID = 1L;
	private JTextField saisieCoup = new JTextField();
	 private JButton envoyer = new JButton("Envoyer");
	 
	 private JMenuBar barMenu = new JMenuBar();
	 private JMenu fichierMenu = new JMenu("Fichier");
	 private JMenu aide = new JMenu("Aide");
			
	private JMenuItem choisirProjet = new JMenuItem("Choisir un projet");
	private JMenuItem seConnecter = new JMenuItem("Se connecter");
	private JMenuItem creerProjet = new JMenuItem("Créer projet");
	
	private JTextArea resultat = new JTextArea();
	
	private JPanel principal = new JPanel(null);
	private JPanel sud = new JPanel();
	
	/**
	 * Constructeur mettant en place la disposition des composant
	 */	
     public Fenetre()
     {
		this.setTitle("Gestion de projet");
		this.setSize(700, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		sud.setLayout(new GridLayout(1, 10));
		sud.add(saisieCoup);
		sud.add(envoyer);

		principal.setLayout(new BorderLayout());
		principal.add(sud, BorderLayout.SOUTH);
		principal.add(resultat);
		this.fichierMenu.add(creerProjet);
		this.fichierMenu.add(choisirProjet);
		this.fichierMenu.add(seConnecter);
		

		this.barMenu.add(fichierMenu);
		this.barMenu.add(aide);

		this.setJMenuBar(barMenu);
		this.setContentPane(principal);
		this.setVisible(true);
             
     }
     
     public JTextArea getResultat()
	{
		return resultat;
	}

	
	/**
	 * Affiche une popup qui signale une erreur
	 *
	 * @param message
	 *            Le message d'erreur à afficher
	 * @param title
	 *            Le titre de la popup
	 */
	public static void popupErreur(String message, String title)
	{
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Affiche une popup qui signale une erreur avec en titre Erreur
	 *
	 * @param message
	 *            Le message d'erreur à afficher
	 */
	public static void popupErreur(String message)
	{
		popupErreur(message, "Erreur");
	}

	/**
	 * Affiche une popup d'information
	 *
	 * @param message
	 *            L'information à afficher
	 * @param title
	 *            Le titre de la popup
	 */
	public static void popupInfo(String message, String title)
	{
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main( String arg[] )
     {
    	new Fenetre();
     }
}