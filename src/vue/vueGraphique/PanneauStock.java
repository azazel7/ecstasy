package vue.vueGraphique;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import observation.MagasinObserver;

import modele.Magasin;
import modele.RailMedicament;

public class PanneauStock extends JPanel implements MagasinObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Magasin magasin = Magasin.recupererInstance();
	private PanneauEjecteur[] listeEjecteur;

	public PanneauStock()
	{
		RailMedicament[] tabRail = this.magasin.getStock();
		JScrollPane scrollpane;
		JPanel panel = new JPanel();
		this.listeEjecteur = new PanneauEjecteur[tabRail.length];
		for(int i = 0; i < tabRail.length; i++)
		{
			this.listeEjecteur[i] = new PanneauEjecteur(tabRail[i].getCode(), tabRail[i].getQuantite(), i+1);
		}
		panel.setLayout(new GridLayout(this.listeEjecteur.length/4, 0));
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		for(PanneauEjecteur pan : this.listeEjecteur)
		{
			panel.add(pan, c);
			c.gridx++;
			if(c.gridx == this.listeEjecteur.length/4)
			{
				c.gridx = 0;
				c.gridy++;
			}
		}
		scrollpane = new JScrollPane(panel);
		this.setLayout(new BorderLayout());
		this.add(scrollpane);
	}

	@Override
	public void onEject(int nombre, String code, int position)
	{
		this.listeEjecteur[position].setQuantite(this.listeEjecteur[position].getQuantite() - nombre);
	}

	@Override
	public void onOutOfStock(String code, int position)
	{
		this.listeEjecteur[position].alerteZeroPourcent();
	}
}
