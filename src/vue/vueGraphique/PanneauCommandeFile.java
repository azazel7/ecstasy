package vue.vueGraphique;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controleur.AnalyseFichier;
import controleur.PEEM1000;
import observation.FileCommandeObserver;

public class PanneauCommandeFile extends JPanel implements FileCommandeObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PEEM1000 peem1000;
	
	public PanneauCommandeFile()
	{
		super();
		peem1000 = PEEM1000.recupererInstance();
		//Les données du tableau
	    
	}
	@Override
	public void onAddCommande()
	{
		this.mettreAJour();
	}

	@Override
	public void onDeleteCommande()
	{
		this.mettreAJour();
		
	}

	public void mettreAJour()
	{
		//On récupére la liste des commandes
		List<Map<String, Integer>> fileDeCommande = this.peem1000.getListeCommande();
		Object[][] data;
		//On créer le tableau de donnée
		data = remplirData(fileDeCommande);
	    //Les titres des colonnes
	    String  title[] = {"Supprimer", "Commande"};
	    //On créer la table
	    JTable jtable = new JTable(data, title);
	    //On un systéme qui transforme la premiére colonne en bouton qui effectue l'action de supprimer commande --> Je ne sais pas comment il fonctionne
	    ButtonColumn buttonColumn = new ButtonColumn(jtable, new SupprimerCommande(), 0);
	    //On met la JTable dans une scrollpann
	    JScrollPane scrollpane = new JScrollPane(jtable);
	    //On supprime tous les éléments
	    this.removeAll();
	    //On ajoute la scrollpane
	    this.add(scrollpane);
	    //On met à jour
	    this.updateUI();
	}
	public Object[][] remplirData(List<Map<String, Integer>> fileDeCommande)
	{
		//On créer un tableau
		Object[][] retour = new Object[fileDeCommande.size()][2];
		Iterator<Map<String, Integer>> iteratorListe = fileDeCommande.iterator();
		Map<String, Integer> courant;
		int i = 0;
		//On parcour la liste
		while(iteratorListe.hasNext())
		{
			courant = iteratorListe.next();
			//On écrit la commande en chaine
			retour[i][1] = AnalyseFichier.ecrireCommande(courant);
			retour[i][0] = "Supprimer";
			i++;
		}
		return retour;
	}
	
	class SupprimerCommande extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public void actionPerformed(ActionEvent e)
        {
			//Recupére la table puis la ligne séléctionnée
	        int modelRow = Integer.valueOf( e.getActionCommand());
	        //On supprime la commande
	        peem1000.retirerCommande(modelRow);
        }
	}

}
