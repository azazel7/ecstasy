package vue.vueConsole;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import observation.TapisRoulantObservable;
import observation.TapisRoulantObserver;

import modele.RailMedicament;
import modele.Tas;

import controleur.AnalyseFichier;
import controleur.PEEM1000;

public class AffichageConsole implements TapisRoulantObserver
{
	private PEEM1000 peem1000;
	public void principal()
	{
		peem1000 = PEEM1000.recupererInstance();
		peem1000.ajouterCommande("4A");
		peem1000.ajouterCommande("4C,5D");
		peem1000.ajouterCommandeFichier("exempleCommande");
		peem1000.ajouterObserver(this);
		peem1000.demarrer();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		AffichageConsole affiche = new AffichageConsole();
		affiche.principal();
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
	@Override
	public void onWaitTick()
	{
		afficherInstantConsole(peem1000);
		
	}
	@Override
	public void onCreateTas(Tas tas) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDeleteTas(Tas tas) {
		// TODO Auto-generated method stub
		
	}

}
