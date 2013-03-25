package modele;

public class RailMedicament
{
	private String code;
	private int quantite;
	
	public RailMedicament(String code, int quantite)
	{
		this.code = code;
		this.quantite = quantite;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite)
	{
		if(quantite >= 0 && quantite <= Magasin.MAX_STOCK)
		{
			this.quantite = quantite;
		}
	}
	
	
}
