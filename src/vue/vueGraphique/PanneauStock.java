package vue.vueGraphique;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.LinkedList;

import javax.swing.JPanel;

import modele.Magasin;
import modele.RailMedicament;

//TODO a faire
public class PanneauStock extends JPanel
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
}
