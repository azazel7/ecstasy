package controleur;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import observation.FileCommandeObserver;
import observation.MagasinObserver;
import observation.TapisRoulantObserver;

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
	private PEEM1000()
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
		Codeur codeur = this.tapis.getCodeur();
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
				this.tapis.setNombreRestantDeTick(TapisRoulant.BOUCLE_INDETERMINEE);
				this.thread = new ThreadTapisRoulant(this.tapis);
				this.thread.start();
			}
		}
	}
	public void demarrer(int nombreTickMax)
	{
		if(this.tapis != null)
		{
			if( ! this.tapis.isEnFonctionnement())
			{
				this.tapis.setNombreRestantDeTick(nombreTickMax);
				this.thread = new ThreadTapisRoulant(this.tapis);
				this.thread.start();
			}
		}
	}
	
	public boolean enFonctionnement()
	{
		if(this.tapis != null)
		{
			return this.tapis.isEnFonctionnement();
		}
		return false;
	}
	
	public void viderCommande()
	{
		this.tapis.getFileDeCommande().getFile().clear();
	}
	public void viderTapis()
	{
		this.tapis.getTas().clear();
	}
	/**
	 * stoppe l'automate
	 */
	public void stopper()
	{
		this.tapis.setEnFonctionnement(false);
	}
	
	public void modifierNombreRestantTick(int nombre)
	{
		this.tapis.setNombreRestantDeTick(nombre);
	}
	public void retirerCommande(int index)
	{
		FileDeCommande file = this.tapis.getFileDeCommande();
		file.retirerCommande(index);
	}
	public void ajouterObserver(TapisRoulantObserver obs)
	{
		this.tapis.addObserver(obs);
	}
	
	public void retirerObservateur(TapisRoulantObserver obs)
	{
		this.tapis.removeObserver(obs);
	}
	
	public void ajouterObserverMagasin(MagasinObserver obs)
	{
		Magasin.recupererInstance().addObserver(obs);
	}
	
	public void retirerObservateurMagasin(MagasinObserver obs)
	{
		Magasin.recupererInstance().removeObserver(obs);
	}
	
	public void ajouterObserverFile(FileCommandeObserver obs)
	{
		this.tapis.getFileDeCommande().addObserver(obs);
	}
	public void retirerObserverFile(FileCommandeObserver obs)
	{
		this.tapis.getFileDeCommande().addObserver(obs);
	}
	
	public int recupererVitesse()
	{
		return this.tapis.getCodeur().getValeurTick();
	}
}
