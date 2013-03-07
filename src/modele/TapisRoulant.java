package modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TapisRoulant
{
	private Codeur codeur;
	private FileDeCommande fileDeCommande;
	private List<Tas> tas;
	private boolean enFonctionnement;
	
	public TapisRoulant(FileDeCommande fileDeCommande)
	{
		this.fileDeCommande = fileDeCommande;
		this.tas = new LinkedList<Tas>();
		this.codeur = new Codeur(1000);
		this.enFonctionnement = false;
	}
	
	public void creerTas()
	{
		Map<String, Integer> commande = this.fileDeCommande.lireCommandeSuivante();
		if(commande != null)
		{
			Tas nouveauTas = new Tas(commande, 0);
			nouveauTas.majPosition();
			this.tas.add(nouveauTas);
		}
	}
	
	public void gererCommande()
	{
		Iterator<Tas> iterator;
		Tas tasCourant;
		int positionLaPlusPetite;
		this.enFonctionnement = true;
		
		while(this.enFonctionnement)
		{
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
		}
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
		return enFonctionnement;
	}

	public void setEnFonctionnement(boolean enFonctionnement) {
		this.enFonctionnement = enFonctionnement;
	}

	public Codeur getCodeur() {
		return codeur;
	}

	public void setCodeur(Codeur codeur) {
		this.codeur = codeur;
	}
}
