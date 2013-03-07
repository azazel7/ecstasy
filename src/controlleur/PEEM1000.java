package controlleur;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import modele.Codeur;
import modele.FileDeCommande;
import modele.Magasin;
import modele.RailMedicament;
import modele.TapisRoulant;
import modele.Tas;

public class PEEM1000
{
	private ThreadTapisRoulant thread;
	private TapisRoulant tapis;
	private static PEEM1000 controle;
	public static PEEM1000 recupererInstance()
	{
		if(controle == null)
		{
			controle = new PEEM1000();
		}
		return controle;
	}
	public PEEM1000()
	{
		this.tapis = new TapisRoulant(new FileDeCommande());
	}
	
	public RailMedicament[] getStock()
	{
		Magasin magasin = Magasin.recupererInstance();
		return magasin.getStock();
	}
	
	public List<Map<String, Integer>> getListeCommande()
	{
		return this.tapis.getFileDeCommande().getFile();
	}
	
	public List<Tas> getListeCommandeSurTapis()
	{
		return this.tapis.getTas();
	}
	
	/**
	 * Modifie la vitesse des impulsion du moteur
	 * @param nouveauTemps
	 */
	public void modifierVitesse(int nouveauTemps)
	{
		Codeur codeur = tapis.getCodeur();
		codeur.setValeurTick(nouveauTemps);
	}
	
	/**
	 * Ajoute une commande à la file
	 * @param commande
	 * @return
	 */
	public boolean ajouterCommande(String commande)
	{
		Map<String, Integer> commandeModele = AnalyseFichier.creerCommande(commande);
		if(commandeModele != null && commandeModele.size() > 0)
		{
			this.tapis.getFileDeCommande().ajouterCommande(commandeModele);
			return true;
		}
		return false;
	}
	
	/**
	 * Permet d'ajouter le contenu d'un fichier formatté à l a liste des commandes
	 * @param cheminFichier
	 * @return
	 */
	public boolean ajouterCommandeFichier(String cheminFichier)
	{
		List<Map<String, Integer>> listeCommande = AnalyseFichier.lireFichierDeCommande(cheminFichier);
		Iterator<Map<String, Integer>> iterator;
		FileDeCommande fileCommande;
		if(listeCommande != null && listeCommande.size() > 0)
		{
			iterator = listeCommande.iterator();
			fileCommande = this.tapis.getFileDeCommande();
			while(iterator.hasNext())
			{
				fileCommande.ajouterCommande(iterator.next());
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Demarre la fonction principale de l'automate dans un thread
	 */
	public void demarrer()
	{
		if(this.tapis != null)
		{
			if( ! this.tapis.isEnFonctionnement())
			{
				this.thread = new ThreadTapisRoulant(this.tapis);
				this.thread.start();
			}
		}
	}
	
	/**
	 * stoppe l'automate
	 */
	public void stopper()
	{
		this.tapis.setEnFonctionnement(false);
	}
	
	public void mettreEnPause(int temps)
	{
		try
		{
			this.thread.sleep(temps);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
