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
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controlleur.PEEM1000;



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
	 
	 private JMenuBar barMenu = new JMenuBar();
	 private JMenu fichierMenu = new JMenu("Fichier");
	 private JMenu aide = new JMenu("Aide");
			
	private JMenuItem demarrer = new JMenuItem("Demarrer");
	private JMenuItem stopper = new JMenuItem("Stopper");
	
	private JTextArea resultat = new JTextArea();
	
	private JPanel principal = new JPanel(null);
	
	private PEEM1000 peem1000;
	/**
	 * Constructeur mettant en place la disposition des composant
	 */	
     public Fenetre()
     {
    	 JPanel panneauVitesse = new JPanel(), panneauAjoutCommande = new JPanel();
    	 JSlider slideVitesse = new JSlider();
    	 
		this.setTitle("AGL");
		this.setSize(700, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.peem1000 = PEEM1000.recupererInstance();
		
		slideVitesse.setMaximum(10000);
		slideVitesse.setMinimum(0);
		panneauVitesse.add(slideVitesse);
		panneauVitesse.setBorder(BorderFactory.createTitledBorder("Modifier vitesse"));
		
		panneauAjoutCommande.setBorder(BorderFactory.createTitledBorder("Ajouter une commande"));
		
		//this.principal.setLayout(new BorderLayout());
		this.fichierMenu.add(demarrer);
		this.fichierMenu.add(stopper);
		
		this.demarrer.addMouseListener(new DemarrerPEEM1000());
		this.stopper.addMouseListener(new StopperPEEM1000());
		
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
	
	
	class DemarrerPEEM1000 extends MouseAdapter
	{
		public void mouseReleased(MouseEvent event)
      	{
			peem1000.demarrer();
      	}
	}
	
	class StopperPEEM1000 extends MouseAdapter
	{
		public void mouseReleased(MouseEvent event)
      	{
			peem1000.stopper();
      	}
	}
	public static void main( String arg[] )
     {
    	new Fenetre();
     }
}