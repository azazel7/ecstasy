package modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
		this.codeur = new Codeur(50);
		this.enFonctionnement = false;
	}
	
	public void creerTas()
	{
		
	}
	
	public void gererCommande()
	{
		Iterator<Tas> iterator;
		Tas tasCourant;
		this.enFonctionnement = true;
		
		while(this.enFonctionnement)
		{
			this.codeur.attendreTick();
			iterator = tas.iterator();
			while(iterator.hasNext())
			{
				tasCourant = iterator.next();
				tasCourant.majPosition();
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
	
	
}
