package controlleur;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import observation.IObserver;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

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
		int taille = magasin.getStock().length;
		RailMedicament[] original = magasin.getStock();
		RailMedicament[] retour = Arrays.copyOf(original, taille);
		return retour;
	}
	
	public List<Map<String, Integer>> getListeCommande()
	{
		List<Map<String, Integer>> tmp = this.tapis.getFileDeCommande().getFile();
		List<Map<String, Integer>> retour = new LinkedList<Map<String,Integer>>(tmp);
		return retour;
	}
	
	public List<Tas> getListeCommandeSurTapis()
	{
		List<Tas> tmp = this.tapis.getTas();
		List<Tas> retour = new LinkedList<Tas>(tmp);
		return retour;
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
	
	public void ajouterObserver(IObserver obs)
	{
		this.tapis.addObserver(obs);
	}
	
	public void retirerObservateur(IObserver obs)
	{
		this.tapis.removeObserver(obs);
	}
}
