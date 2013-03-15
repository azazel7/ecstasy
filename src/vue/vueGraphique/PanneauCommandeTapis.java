package vue.vueGraphique;

import controleur.PEEM1000;
import observation.TapisRoulantObserver;

public class PanneauCommandeTapis implements TapisRoulantObserver
{

	private PEEM1000 peem1000;
	
	public PanneauCommandeTapis()
	{
		this.peem1000 = PEEM1000.recupererInstance();
	}
	@Override
	public void onWaitTick()
	{
		// TODO Auto-generated method stub
		
	}

	

}
