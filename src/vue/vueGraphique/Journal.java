package vue.vueGraphique;

import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controleur.AnalyseFichier;
import controleur.PEEM1000;
import modele.RailMedicament;
import modele.Tas;
import observation.MagasinObserver;
import observation.TapisRoulantObserver;

public class Journal extends JPanel implements TapisRoulantObserver, MagasinObserver
{
	private static final long serialVersionUID = 1L;
	private String journal = new String();
	private PEEM1000 peem1000 = PEEM1000.recupererInstance();
	
	private JTextArea text;
	
	public Journal()
	{
		this.text = new JTextArea();
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(this.text);
		this.add(scrollPane);
		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createTitledBorder("Log"));
	}
	public void ecrire(String chaine)
	{
		this.journal += chaine + "\n";
		this.text.setText(this.journal);
	}
	
	public void ecrireSansRetour(String chaine)
	{
		this.journal += chaine;
		this.text.setText(this.journal);
	}
	@Override
	public void onWaitTick()
	{
		Tas courant;
		Iterator<Tas> iteratorTapis = peem1000.getListeCommandeSurTapis().iterator();
		Iterator<Map<String, Integer>> iteratorFile = peem1000.getListeCommande().iterator();
		this.ecrire("");
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
		this.ecrire("");
		this.ecrire("Nouveau tas N°" + tas.getNumero() + " (" + listeMedocString + ")");
		this.ecrire("");
	}

	@Override
	public void onDeleteTas(Tas tas)
	{
		String listeMedocString = AnalyseFichier.ecrireCommande(tas.getListeMedoc());
		this.ecrire("");
		if(tas.getListeMedocRestante().size() == 0)
		{
			this.ecrire("Destruction du tas: " + listeMedocString);
		}
		else
		{
			String listeMedocRestantString = AnalyseFichier.ecrireCommande(tas.getListeMedocRestante());
			this.ecrire("Destruction du tas: " + listeMedocString + "( " + listeMedocRestantString + " )");
		}
		this.ecrire("");
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
			e.printStackTrace();
		}
	}

	

}
