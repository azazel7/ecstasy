package modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FileDeCommande
{
	private List<Map<String, Integer>> file = new LinkedList<Map<String,Integer>>();
	
	public int lireNombre()
	{
		return this.file.size();
	}
	
	public Map<String, Integer> lireCommandeSuivante()
	{
		Map<String, Integer> retour = null;
		if (this.file.size() > 0)
		{
			retour = this.file.get(0);
			this.file.remove(0);
			
		}
		return retour;
	}
	
	public void ajouterCommande(Map<String, Integer> nouvelleCommande)
	{
		this.file.add(nouvelleCommande);
	}
}
