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
	ScrollPane scrollPane = new ScrollPane();
	private JTextArea text;
	private int sauterTick = 0;
	public Journal()
	{
		this.text = new JTextArea();
		
		scrollPane.add(this.text);
		scrollPane.setScrollPosition(this.text.getRows(), 0);
		this.add(scrollPane);
		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createTitledBorder("Log"));
		this.ecrire("");
	}
	public void ecrire(String chaine)
	{
		this.journal += chaine + "\n";
		this.text.setText(this.journal);System.out.println();
		this.scrollPane.setScrollPosition(0, this.text.getText().lastIndexOf('\n'));
		
	}
	
	public void ecrireSansRetour(String chaine)
	{
		this.journal += chaine;
		this.text.setText(this.journal);
	}
	@Override
	public void onWaitTick()
	{
		if(this.sauterTick > 0)
		{
			this.sauterTick--;
			return;
		}
		Tas courant;
		Iterator<Tas> iteratorTapis = peem1000.getListeCommandeSurTapis().iterator();
		Iterator<Map<String, Integer>> iteratorFile = peem1000.getListeCommande().iterator();
		this.ecrire("");
		this.ecrire("*****Attente d'un tick*****");
		if(iteratorFile.hasNext())
		{
			this.ecrire("====Commande dans la file====");
			int i = 0;
			while(iteratorFile.hasNext())
			{
				this.ecrire("[" + i + "] " + AnalyseFichier.ecrireCommande(iteratorFile.next()));
				i++;
			}
		}
		else
		{
			this.ecrire("====> Pas de commande en attente");
		}
		
		if(iteratorTapis.hasNext())
		{
			this.ecrire("####Commande sur le tapis####");
			while(iteratorTapis.hasNext())
			{
				courant = iteratorTapis.next();
				afficherTas(courant);
			}
		}
		else
		{
			this.ecrire("====> Pas de commande sur le tapis");
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
	public void onEject(int nombre, String code, int position)
	{
		this.ecrire("Ejection de " + nombre + " " + code + " à la position " + position);
	}
	
	@Override
	public void onOutOfStock(String code, int position)
	{
			this.ecrire("Le medicament " + code + " en " + position + " est en rupture de stock");	
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

	@Override
	public void onClearCarpet()
	{
		this.ecrire("*****Le tapis à était vidé****");
	}
	@Override
	public void onEndGererCommande()
	{
		this.onWaitTick();
		this.sauterTick++;
	}

	

}
