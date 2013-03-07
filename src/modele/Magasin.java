package modele;

public class Magasin
{
	private static int NOMBRE_RAIL = 20;
	private static Magasin magasin = null;
	private RailMedicament stock[];
	
	public Magasin()
	{
		this.stock = new RailMedicament[20];
		char lettre[] = {'A'};
		//On remplis les stocks
		for(int i = 0; i < NOMBRE_RAIL; i++)
		{
			this.stock[i] = new RailMedicament(new String(lettre), 50);
			lettre[0] ++;
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
		int retour = 0;;
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
		return retour;
	}
	
	/**
	 * Permet de recharger un stock de medicament
	 * @param position
	 * @param nombre
	 */
	public void rechargerMedoc(int position, int nombre)
	{
		position--;
		if(position < 0 || position >= NOMBRE_RAIL)
		{
			return;
		}
		
		RailMedicament courant = this.stock[position];
		courant.setQuantite(courant.getQuantite() + nombre);
	}
	
	/**
	 * Permet de recharger un stock de medicament
	 * @param position
	 * @param nombre
	 */
	public void rechargerMedoc(String code, int nombre)
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
		rechargerMedoc(position, nombre);
	}
	
	public RailMedicament[] getStock() {
		return stock;
	}
	public void setStock(RailMedicament[] stock) {
		this.stock = stock;
	}
	
	
}
