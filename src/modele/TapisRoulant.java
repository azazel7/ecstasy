package modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import observation.TapisRoulantObservable;

public class TapisRoulant extends TapisRoulantObservable
{
	public static int BOUCLE_INDETERMINEE = -1;
	public static int BOUCLE_STOPPEE = -2;
	private Codeur codeur;
	private FileDeCommande fileDeCommande;
	private List<Tas> tas;
	private static final int NOMBRE_POSITION = 10;
	private int enFonctionnement;
	private int numeroTasSuivant = 0;
	
	public TapisRoulant(FileDeCommande fileDeCommande)
	{
		this.fileDeCommande = fileDeCommande;
		this.tas = new LinkedList<Tas>();
		this.codeur = new Codeur(Codeur.VITESSE_CODEUR_DEFAUT);
		this.enFonctionnement = BOUCLE_STOPPEE;
	}
	
	public void creerTas()
	{
		Map<String, Integer> commande = this.fileDeCommande.lireCommandeSuivante();
		if(commande != null)
		{
			Tas nouveauTas = new Tas(commande, 0, this.numeroTasSuivant);
			this.notifyCreateTas(nouveauTas);
			nouveauTas.majPosition();
			this.tas.add(nouveauTas);
			
			//4 étant le nombre maximun de tas qui peuvent être placé simultanément sur la tapis
			this.numeroTasSuivant = (this.numeroTasSuivant + 1)%5;
		}
	}
	
	public void gererCommande()
	{
		Iterator<Tas> iterator;
		Tas tasCourant;
		int positionLaPlusPetite;
		for(int i = 0; (this.enFonctionnement == BOUCLE_INDETERMINEE || i < this.enFonctionnement) && this.enFonctionnement != BOUCLE_STOPPEE; i++)
		{
			//On informe que l'on vas entrer dans un tick
			this.notifyWaitTick();
			//On attend que le tapis avance
			this.codeur.attendreTick();
			//On met la la plus petite position trés haut
			positionLaPlusPetite = 255;
			iterator = this.tas.iterator();
			//Pour chaque tas
			while(iterator.hasNext())
			{
				tasCourant = iterator.next();
				//On met à jour la position
				tasCourant.majPosition();
				//On met à jour la position la plus petite
				if(tasCourant.lirePositionCourante() < positionLaPlusPetite)
				{
					positionLaPlusPetite = tasCourant.lirePositionCourante();
				}
			}
			//Si la position la plus petite est suffisament loin, on créer un nouveau tas
			if(positionLaPlusPetite > 3 && this.fileDeCommande.lireNombre() > 0)
			{
				creerTas();
			}
			nettoyerTas();
		}
		this.enFonctionnement = BOUCLE_STOPPEE;
		this.notifyWaitTick();
	}

	public void nettoyerTas()
	{
		Iterator<Tas> iterator = this.tas.iterator();
		Tas courant;
		while(iterator.hasNext())
		{
			courant = iterator.next();
			if(courant.lirePositionCourante() > NOMBRE_POSITION)
			{
				this.notifyDeleteTas(courant);
				iterator.remove();
			}
		}
	}
	public void viderTapis()
	{
		this.tas.clear();
		this.notifyClearCarpet();
		
	}
	public FileDeCommande getFileDeCommande() {
		return fileDeCommande;
	}

	public void setFileDeCommande(FileDeCommande fileDeCommande) {
		this.fileDeCommande = fileDeCommande;
	}

	public List<Tas> getTas() {
		return tas;
	}

	public void setTas(List<Tas> tas) {
		this.tas = tas;
	}

	public boolean isEnFonctionnement() {
		if(this.enFonctionnement == BOUCLE_STOPPEE)
		{
			return false;
		}
		return true;
	}

	public void setEnFonctionnement(boolean enFonctionnement) {
		if(enFonctionnement == true)
		{
			this.enFonctionnement = BOUCLE_INDETERMINEE;
		}
		else
		{
			this.enFonctionnement = BOUCLE_STOPPEE;
		}
	}

	public void setNombreRestantDeTick(int i)
	{
		this.enFonctionnement = i;
	}
	public Codeur getCodeur() {
		return codeur;
	}

	public void setCodeur(Codeur codeur) {
		this.codeur = codeur;
	}
}
