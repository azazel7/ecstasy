package vue.vueGraphique;

import controleur.PEEM1000;
import observation.FileCommandeObserver;

public class PanneauCommandeFile implements FileCommandeObserver
{
	private PEEM1000 peem1000;
	
	public PanneauCommandeFile()
	{
		super();
		peem1000 = PEEM1000.recupererInstance();
	}
	@Override
	public void onAddCommande()
	{
				
	}

	@Override
	public void onDeleteCommande() {
		// TODO Auto-generated method stub
		
	}

	

}
