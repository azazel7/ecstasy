package controlleur;

import modele.TapisRoulant;

public class ThreadTapisRoulant extends Thread
{
	private TapisRoulant tapis;
	public ThreadTapisRoulant(TapisRoulant tapis)
	{
		this.tapis = tapis;
	}
	
	public void run()
	{
		if(this.tapis != null)
		{
			this.tapis.gererCommande();
		}
	}
}
