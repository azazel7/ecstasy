package modele;

public class Codeur
{
	private int valeurTick;
	public static final int VITESSE_CODEUR_DEFAUT = 1000;
	public Codeur(int val)
	{
		if(valeurTick >= 0)
		{
			this.valeurTick = val;
		}
		else
		{
			this.valeurTick = VITESSE_CODEUR_DEFAUT;
		}
	}
	public void attendreTick()
	{
		try
		{
			Thread.sleep(this.valeurTick);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public int getValeurTick()
	{
		return valeurTick;
	}

	public void setValeurTick(int valeurTick)
	{
		if(valeurTick >= 0)
		{
			this.valeurTick = valeurTick;
		}
	}
	
	public void unpause()
	{
			synchronized (this)
			{
				this.notify();
			}
	}
	
}
