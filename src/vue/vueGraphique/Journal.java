package vue.vueGraphique;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;

import controleur.AnalyseFichier;
import controleur.PEEM1000;
import modele.RailMedicament;
import modele.Tas;
import observation.MagasinObserver;
import observation.TapisRoulantObserver;

public class Journal implements TapisRoulantObserver, MagasinObserver
{
	private String journal = new String();
	private PEEM1000 peem1000 = PEEM1000.recupererInstance();
	
	public void ecrire(String chaine)
	{
		this.journal += chaine + "\n";
	}
	
	public void ecrireSansRetour(String chaine)
	{
		this.journal += chaine;
	}
	@Override
	public void onWaitTick()
	{
		Tas courant;
		Iterator<Tas> iteratorTapis = peem1000.getListeCommandeSurTapis().iterator();
		Iterator<Map<String, Integer>> iteratorFile = peem1000.getListeCommande().iterator();
		this.ecrire("Attente d'un tick");
		this.ecrire("Commande dans la file:");
		while(iteratorFile.hasNext())
		{
			this.ecrire(AnalyseFichier.ecrireCommande(iteratorFile.next()));
		}
		this.ecrire("Commande sur le tapis:");
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
				this.ecrire("");
			}
			this.ecrireSansRetour(stock[i].getCode() + " " + stock[i].getQuantite() + "//");
		}
		this.ecrire("");
	}
	
	public void afficherTas(Tas tas)
	{
		this.ecrire("N°"+ tas.getNumero() + " (" + tas.lirePositionCourante() + ") " + AnalyseFichier.ecrireCommande(tas.getListeMedoc()));
	}

	
	@Override
	public void onCreateTas(Tas tas)
	{
		String listeMedocString = AnalyseFichier.ecrireCommande(tas.getListeMedoc());
		this.ecrire("Nouveau tas N°" + tas.getNumero() + " (" + listeMedocString + ")");
	}

	@Override
	public void onDeleteTas(Tas tas)
	{
		String listeMedocString = AnalyseFichier.ecrireCommande(tas.getListeMedoc());
		if(tas.getListeMedocRestante().size() == 0)
		{
			this.ecrire("Destruction du tas: " + listeMedocString);
		}
		else
		{
			String listeMedocRestantString = AnalyseFichier.ecrireCommande(tas.getListeMedocRestante());
			this.ecrire("Destruction du tas: " + listeMedocString + "( " + listeMedocRestantString + " )");
		}
	}
	@Override
	public void onEject(int nombre, String code)
	{
		this.ecrire("Ejection: " + code + " " + nombre);
	}
	
	@Override
	public void onOutOfStock(String code)
	{
			this.ecrire("Le medicament " + code + " est en rupture de stock");	
	}
	
	public void enregistrerLogs(String chemin)
	{
		File fichier = new File(chemin);
		try {
			FileOutputStream out = new FileOutputStream(fichier);
			out.write(this.journal.getBytes());
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
