package vue.vueConsole;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modele.RailMedicament;
import modele.Tas;

import controlleur.AnalyseFichier;
import controlleur.PEEM1000;

public class AffichageConsole {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		PEEM1000 peem1000 = PEEM1000.recupererInstance();
		
		
		System.out.println("Debut");
		peem1000.ajouterCommande("4A,5B");
		peem1000.ajouterCommande("4C,5D");
		peem1000.ajouterCommandeFichier("exempleCommande");
		peem1000.demarrer();
		for(int i = 0; i < 7; i++)
		{
			try
			{
				Thread.sleep(2000);
				afficherInstantConsole(peem1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		peem1000.stopper();
		System.out.println("\n\n");
		RailMedicament[] stock = peem1000.getStock();
		for(int i = 0; i < stock.length; i++)
		{
			if(i%3 == 0 && i != 0)
			{
				System.out.println("");
			}
			System.out.print(stock[i].getCode() + " " + stock[i].getQuantite() + "//");
		}
		System.out.println("");
		System.out.println("Fin");
	}
	
	public static void afficherInstantConsole(PEEM1000 peem1000)
	{
		Tas courant;
		Iterator<Tas> iteratorTapis = peem1000.getListeCommandeSurTapis().iterator();
		Iterator<Map<String, Integer>> iteratorFile = peem1000.getListeCommande().iterator();
		System.out.println("Commande dans la file:");
		while(iteratorFile.hasNext())
		{
			System.out.println(AnalyseFichier.ecrireCommande(iteratorFile.next()));
		}
		System.out.println("Commande sur le tapis:");
		while(iteratorTapis.hasNext())
		{
			courant = iteratorTapis.next();
			afficherTas(courant);
		}
		
		RailMedicament[] stock = peem1000.getStock();
		for(int i = 0; i < stock.length; i++)
		{
			if(i%3 == 0 && i != 0)
			{
				System.out.println("");
			}
			System.out.print(stock[i].getCode() + " " + stock[i].getQuantite() + "//");
		}
		System.out.println("");
	}
	
	public static void afficherTas(Tas tas)
	{
		System.out.println("(" + tas.lirePositionCourante() + ") " + AnalyseFichier.ecrireCommande(tas.getListeMedoc()));
	}

}
