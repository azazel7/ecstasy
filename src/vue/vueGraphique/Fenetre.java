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
import java.io.IOException;
import java.lang.Thread.State;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import modele.Codeur;


import controleur.PEEM1000;



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
	private JMenuItem ajouterFichierCommande = new JMenuItem("Ajouter un fichier de commande");
	
	private JPanel principal = new JPanel(null);
	private JTextField texteNouvelleCommande = new JTextField();
	private PEEM1000 peem1000;
	/**
	 * Constructeur mettant en place la disposition des composant
	 */	
     public Fenetre()
     {
    	 JPanel panneauVitesse = new JPanel(), panneauAjoutCommande = new JPanel();
    	 JSlider slideVitesse = new JSlider();
    	 JButton boutonAjouterCommande = new JButton("Ajouter");
    	 
		this.setTitle("AGL");
		this.setSize(700, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.peem1000 = PEEM1000.recupererInstance();
		
		slideVitesse.setMaximum(10000);
		slideVitesse.setMinimum(0);
		slideVitesse.setValue(Codeur.VITESSE_CODEUR_DEFAUT);
		slideVitesse.addChangeListener(new AjusterVitesse());
		panneauVitesse.add(slideVitesse);
		panneauVitesse.setBorder(BorderFactory.createTitledBorder("Modifier vitesse"));
		
		boutonAjouterCommande.addMouseListener(new AjouterCommande());
		this.texteNouvelleCommande.addKeyListener(new AjouterCommande());
		panneauAjoutCommande.add(this.texteNouvelleCommande);
		panneauAjoutCommande.add(boutonAjouterCommande);
		panneauAjoutCommande.setLayout(new GridLayout(1, 2));
		panneauAjoutCommande.setBorder(BorderFactory.createTitledBorder("Ajouter une commande"));
		
		this.principal.setLayout(new GridLayout(3, 2));
		this.fichierMenu.add(demarrer);
		this.fichierMenu.add(stopper);
		this.fichierMenu.add(ajouterFichierCommande);
		
		this.demarrer.addMouseListener(new DemarrerPEEM1000());
		this.stopper.addMouseListener(new StopperPEEM1000());
		this.ajouterFichierCommande.addMouseListener(new AjouterFichierCommande());
		
		this.barMenu.add(fichierMenu);
		this.barMenu.add(aide);

		this.principal.add(panneauVitesse);
		this.principal.add(panneauAjoutCommande);
		
		this.setJMenuBar(barMenu);
		this.setContentPane(principal);
		this.setVisible(true);
             
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
	
	class AjouterFichierCommande extends MouseAdapter
	{
		private String cheminPrecedant;
		public void mouseReleased(MouseEvent event)
      	{
			JFileChooser fileChooser = new JFileChooser(cheminPrecedant);
			fileChooser.showOpenDialog(null);
			if (fileChooser.getSelectedFile() != null)
			{
				try
				{
					cheminPrecedant = fileChooser.getSelectedFile().getCanonicalPath();
					peem1000.ajouterCommandeFichier(cheminPrecedant);
					
				}
				catch (IOException e1)
				{
					popupErreur(e1.getMessage());
				}
			}
      	}
	}
	
	class AjouterCommande extends MouseAdapter implements KeyListener
	{
		public void ajouterCommande()
		{
			String commande = texteNouvelleCommande.getText();
			if(peem1000.ajouterCommande(commande) == true)
			{
				texteNouvelleCommande.setText("");
			}
			else
			{
				popupErreur("Commande non conforme");
			}
		}
		public void mouseReleased(MouseEvent event)
      	{
			ajouterCommande();			
      	}

		
		@Override
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyChar() == '\n')
			{
				ajouterCommande();
			}
		}

		@Override
		public void keyPressed(KeyEvent arg0){}

		@Override
		public void keyTyped(KeyEvent arg0) {}
	}
	
	class AjusterVitesse implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e)
		{
			JSlider source = (JSlider)e.getSource();
			if(source != null)
			{
				int nouvelleValeur = source.getValue();
				peem1000.modifierVitesse(nouvelleValeur);
			}
		}
	}
	public static void main( String arg[] )
     {
    	new Fenetre();
     }
}