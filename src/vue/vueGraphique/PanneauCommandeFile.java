package vue.vueGraphique;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import modele.FileDeCommande;

import controleur.AnalyseFichier;
import controleur.PEEM1000;
import observation.FileCommandeObserver;

public class PanneauCommandeFile extends JPanel implements FileCommandeObserver
{
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
		List<Map<String, Integer>> fileDeCommande = this.peem1000.getListeCommande();
		Object[][] data;

		data = remplirData(fileDeCommande);
	    //Les titres des colonnes
	    String  title[] = {"Supprimer", "Commande"};
	    JTable tableau = new JTable(data, title);
	    JScrollPane scrollpane = new JScrollPane(tableau);
	    this.removeAll();
	    this.add(scrollpane);
	    this.updateUI();
	}
	public Object[][] remplirData(List<Map<String, Integer>> fileDeCommande)
	{
		Object[][] retour = new Object[fileDeCommande.size()][2];
		Iterator<Map<String, Integer>> iteratorListe = fileDeCommande.iterator();
		Map<String, Integer> courant;
		int i = 0;
		while(iteratorListe.hasNext())
		{
			courant = iteratorListe.next();
			retour[i][1] = AnalyseFichier.ecrireCommande(courant);
			retour[i][0] = new JButton();
			i++;
		}
		return retour;
	}
	

}
