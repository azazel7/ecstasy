package modele;

public class Codeur
{
	private int valeurTick;
	
	public Codeur(int val)
	{
		if(valeurTick >= 0)
		{
			this.valeurTick = val;
		}
		else
		{
			this.valeurTick = 10;
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
	
	
}
