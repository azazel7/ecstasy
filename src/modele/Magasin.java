package modele;

import observation.MagasinObservable;

public class Magasin extends MagasinObservable
{
	public static int MAX_STOCK = 50;
	private static int NOMBRE_RAIL = 20;
	private static Magasin magasin = null;
	private RailMedicament stock[];
	
	private Magasin()
	{
		this.stock = new RailMedicament[20];
		char lettre[] = {'A'};
		//On remplis les stocks
		for(int i = 0; i < NOMBRE_RAIL/2; i++)
		{
			lettre[0] = (char)('A' + i);
			this.stock[2*i+1] = new RailMedicament(new String(lettre), 50);
			lettre[0] = (char)('A' + i + NOMBRE_RAIL/2);
			this.stock[2*i] = new RailMedicament(new String(lettre), 50);
			
		}
	}
	/**
	 * Permet de récupérer l'unique instance de magasin
	 * @return
	 */
	public static Magasin recupererInstance()
	{
		if(magasin == null)
		{
			magasin = new Magasin();
		}
		return magasin;
	}
	
	/**
	 * Lit le code medicament d'un ejecteur à la position
	 * @param position
	 * @return
	 */
	public String lireCode(int position)
	{
		position--;
		if(position < 0 || position >= NOMBRE_RAIL)
		{
			return null;
		}
		return this.stock[position].getCode();
	}
	
	/**
	 * Ejecte un nombre de medicament de l'ejecteur à la position
	 * @param position
	 * @param nombre
	 * @return
	 */
	public int ejecterMedoc(int position, int nombre)
	{
		if(nombre > 3)
		{
			nombre = 3;
		}
		else if(nombre < 0)
		{
			nombre = 0;
		}
		int retour = 0;
		RailMedicament rail;
		position--;
		if(position < 0 || position >= NOMBRE_RAIL)
		{
			return 0;
		}
		
		rail = this.stock[position];
		//Il n'y a pas assez de stock alors on donne ce qu'il reste
		if(rail.getQuantite() < nombre)
		{
			retour = rail.getQuantite();
		}
		else
		{
			retour = nombre;
		}
		//on retire au stock
		rail.setQuantite(rail.getQuantite() - retour);
		this.notifyEject(retour, rail.getCode());
		return retour;
	}
	
	/**
	 * Permet de recharger un stock de medicament
	 * @param position
	 * @param nombre
	 */
	public int rechargerMedoc(int position, int nombre)
	{
		position--;
		if(position < 0 || position >= NOMBRE_RAIL)
		{
			return -1;
		}
		
		RailMedicament courant = this.stock[position];
		if(courant.getQuantite() + nombre <= MAX_STOCK)
		{
			courant.setQuantite(courant.getQuantite() + nombre);
		}
		return courant.getQuantite();
	}
	
	/**
	 * Permet de recharger un stock de medicament
	 * @param position
	 * @param nombre
	 */
	public int rechargerMedoc(String code, int nombre)
	{
		String codeCourant;
		int position = -1;
		for(int i = 0; i < NOMBRE_RAIL; i++)
		{
			codeCourant = this.stock[i].getCode();
			if(codeCourant.equals(code))
			{
				position = i;
				break;
			}
		}
		return rechargerMedoc(position+1, nombre);
	}
	public int lireQUantite(int position)
	{
		position--;
		if(position < 0 || position >= NOMBRE_RAIL)
		{
			return -1;
		}
		
		RailMedicament courant = this.stock[position];
		return courant.getQuantite();
	}
	public int lireQuantite(String code)
	{
		String codeCourant;
		int position = -1;
		for(int i = 0; i < NOMBRE_RAIL; i++)
		{
			codeCourant = this.stock[i].getCode();
			if(codeCourant.equals(code))
			{
				position = i;
				break;
			}
		}
		return lireQUantite(position);
	}
	public RailMedicament[] getStock() {
		return stock;
	}
	public void setStock(RailMedicament[] stock) {
		this.stock = stock;
	}
	
	
}
