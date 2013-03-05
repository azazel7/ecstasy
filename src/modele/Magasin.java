package modele;

public class Magasin
{
	private static Magasin magasin = null;
	
	public static Magasin recupererInstance()
	{
		if(magasin == null)
		{
			magasin = new Magasin();
		}
		return magasin;
	}
	
	public String lireCode(int position)
	{
		return "A";
	}
	
	public int ejecterMedoc(int position, int nombre)
	{
		return nombre;
	}
}
