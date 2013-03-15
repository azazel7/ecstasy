package vue.vueGraphique;

import javax.swing.JPanel;

import controleur.PEEM1000;

import observation.TapisRoulantObserver;

public class PanneauTapis extends JPanel implements TapisRoulantObserver
{
	private PEEM1000 peem1000;
	public PanneauTapis()
	{
		this.peem1000 = PEEM1000.recupererInstance();
	}
	@Override
	public void onWaitTick() {
		// TODO Auto-generated method stub
		
	}


}
