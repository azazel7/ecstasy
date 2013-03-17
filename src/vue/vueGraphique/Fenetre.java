package vue.vueGraphique;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
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
	 
	private JPanel principal = new JPanel(null);
	private JTextField texteNouvelleCommande = new JTextField();
	private PanneauCommandeFile panneauCommandeFile;
	private PanneauTapis panneauTapis;
	private PanneauCommandeTapis panneauCommandeTapis;
	private PEEM1000 peem1000;
	private Journal journal;
	private String cheminPrecedant;
	
	/**
	 * Constructeur mettant en place la disposition des composant
	 */	
     public Fenetre()
     {
    	 this.peem1000 = PEEM1000.recupererInstance();
    	 this.journal = new Journal();
    	 this.initialiserComposant();
    	 this.peem1000.ajouterObserverFile(this.panneauCommandeFile);
    	 this.peem1000.ajouterObserver(this.panneauTapis);
    	 this.peem1000.ajouterObserver(this.panneauCommandeTapis);
    	 this.peem1000.ajouterObserver(this.journal);
    	 this.peem1000.ajouterObserverMagasin(this.journal);
     }
     

	public void initialiserComposant()
	{
		JPanel panneauVitesse = new JPanel(), panneauAjoutCommande = new JPanel();
   	 	JSlider slideVitesse = new JSlider();
   	 	JButton boutonAjouterCommande = new JButton("Ajouter");
   		JMenuItem demarrer = new JMenuItem("Demarrer");
   		JMenuItem stopper = new JMenuItem("Stopper");
   		JMenuItem ajouterFichierCommande = new JMenuItem("Ajouter un fichier de commande");
   		JMenuItem sauvegarderLog = new JMenuItem("Sauvegarder les logs");
   		JMenuItem quitter = new JMenuItem("Quitter");
   		JMenuBar barMenu = new JMenuBar();
   		JMenu fichierMenu = new JMenu("Fichier");
   	 
		this.setTitle("AGL");
		this.setSize(700, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		slideVitesse.setMaximum(4000);
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
		
		//On remplis la barre de menus
		fichierMenu.add(demarrer);
		fichierMenu.add(stopper);
		fichierMenu.add(ajouterFichierCommande);
		fichierMenu.add(sauvegarderLog);
		fichierMenu.add(quitter);
		
		//Ajout de listener
		demarrer.addMouseListener(new DemarrerPEEM1000());
		demarrer.setAccelerator(KeyStroke.getKeyStroke('D', KeyEvent.CTRL_DOWN_MASK));
		stopper.addMouseListener(new StopperPEEM1000());
		ajouterFichierCommande.addMouseListener(new AjouterFichierCommande());
		quitter.addMouseListener(new QuitterApplication());
		sauvegarderLog.addMouseListener(new SauvegarderLogs());
		
		this.panneauCommandeFile = new PanneauCommandeFile();
		this.panneauCommandeTapis = new PanneauCommandeTapis();
		this.panneauTapis = new PanneauTapis();
		
		this.principal.add(panneauVitesse);
		this.principal.add(panneauAjoutCommande);
		this.principal.add(this.panneauCommandeTapis);
		this.principal.add(this.panneauCommandeFile);
		this.principal.add(this.panneauTapis);
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
	
	public void quitter()
	{
		this.peem1000.stopper();
		this.dispose();
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
	
	class QuitterApplication extends MouseAdapter
	{
		public void mouseReleased(MouseEvent event)
      	{
			quitter();//Voir classe Fenetre			
      	}
	}
	
	class SauvegarderLogs extends MouseAdapter
	{
		public void mouseReleased(MouseEvent event)
      	{
			
			JFileChooser fileChooser = new JFileChooser(cheminPrecedant);
			fileChooser.showOpenDialog(null);
			if (fileChooser.getSelectedFile() != null)
			{
				try
				{
					cheminPrecedant = fileChooser.getSelectedFile().getCanonicalPath();
					journal.enregistrerLogs(cheminPrecedant);
					
				}
				catch (IOException e1)
				{
					popupErreur(e1.getMessage());
				}
			}
      	}
	}
	public static void main( String arg[] )
     {
    	new Fenetre();
     }
}