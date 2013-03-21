package modele;

import java.util.HashMap;
import java.util.Map;

public class Tas
{
	
	private int positionCourante;
	private int numero;
	private Map<String, Integer> listeMedoc;
	private Map<String, Integer> listeMedocRestante;
	private Magasin magasin;
	private static int NOMBRE_POSITION = 10;
	
	public Tas(Map<String, Integer> listeMedoc, int positionInitial, int numero)
	{
		this.listeMedoc = listeMedoc;
		this.listeMedocRestante = new HashMap<String, Integer>(this.listeMedoc);
		this.positionCourante = positionInitial;
		this.magasin = Magasin.recupererInstance();
		this.numero = numero;
	}
	
	public void majPosition()
	{
		String code;
		int nombre, recu;
		this.positionCourante ++;
		if(this.positionCourante > NOMBRE_POSITION)
		{
			return;
		}
		for(int i = 0; i < 2; i++)
		{
			code = this.magasin.lireCode(2*this.positionCourante - i);
			if(this.listeMedocRestante.containsKey(code))
			{
				nombre = this.listeMedocRestante.get(code);
				recu = this.demanderEjection(2*this.positionCourante - i, nombre);
				this.listeMedocRestante.remove(code);
				if(recu < nombre)
				{
					this.listeMedocRestante.put(code, nombre - recu);
				}
			}
		}
	}
	
	private int demanderEjection(int position, int nombreTotal)
	{
		int recus = 0;
		recus = this.magasin.ejecterMedoc(position, nombreTotal);
		return recus;
	}
	public int lirePositionCourante()
	{
		return this.positionCourante;
	}

	public Map<String, Integer> getListeMedoc() {
		return listeMedoc;
	}

	public Map<String, Integer> getListeMedocRestante() {
		return listeMedocRestante;
	}

	public int getNumero() {
		return numero;
	}
	
	
}
