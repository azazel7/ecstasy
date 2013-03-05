package modele;

import java.util.Map;

public class Tas
{
	
	private int positionCourante;
	private int etat;
	private Map<String, Integer> listeMedoc;
	private Magasin magasin;
	
	public Tas(Map<String, Integer> listeMedoc, int positionInitial, Magasin magasin)
	{
		this.listeMedoc = listeMedoc;
		this.positionCourante = positionInitial;
		this.magasin = Magasin.recupererInstance();
	}
	
	public void majPosition()
	{
		this.positionCourante ++;
		String code;
		int nombre, recu;
		for(int i = 0; i < 2; i++)
		{
			code = this.magasin.lireCode(2*this.positionCourante - i);
			if(this.listeMedoc.containsKey(code))
			{
				nombre = this.listeMedoc.get(code);
				recu = this.magasin.ejecterMedoc(2*this.positionCourante - i, nombre);
			}
		}
	}
	
	public int lirePositionCourante()
	{
		return this.positionCourante;
	}
	
}
