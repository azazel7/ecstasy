package vue.vueGraphique;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.LinkedList;

import javax.swing.JPanel;

import observation.MagasinObserver;

import modele.Magasin;
import modele.RailMedicament;

//TODO a faire
public class PanneauStock extends JPanel implements MagasinObserver
{
	private Magasin magasin = Magasin.recupererInstance();
	private PanneauEjecteur[] listeEjecteur;

	public PanneauStock()
	{
		RailMedicament[] tabRail = this.magasin.getStock();
		this.listeEjecteur = new PanneauEjecteur[tabRail.length];
		for(int i = 0; i < tabRail.length; i++)
		{
			this.listeEjecteur[i] = new PanneauEjecteur(tabRail[i].getCode(), tabRail[i].getQuantite());
		}
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		for(PanneauEjecteur pan : this.listeEjecteur)
		{
			this.add(pan, c);
			c.gridx++;
			if(c.gridx == this.listeEjecteur.length/4)
			{
				c.gridx = 0;
				c.gridy++;
			}
		}
	}

	@Override
	public void onEject(int nombre, String code)
	{
		for(PanneauEjecteur rail : this.listeEjecteur)
		{
			if(code.equals(rail.getCode()))
			{
				rail.setQuantite(rail.getQuantite() - nombre);
				break;
			}
		}
	}

	@Override
	public void onOutOfStock(String code)
	{
		
		for(PanneauEjecteur rail : this.listeEjecteur)
		{
			if(code.equals(rail.getCode()))
			{
				rail.alerteZeroPourcent();
				break;
			}
		}
	}
}
