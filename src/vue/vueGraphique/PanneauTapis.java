package vue.vueGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Magasin;
import modele.Tas;

import controleur.PEEM1000;

import observation.TapisRoulantObserver;

public class PanneauTapis extends JPanel implements TapisRoulantObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PEEM1000 peem1000;
	public PanneauTapis()
	{
		this.peem1000 = PEEM1000.recupererInstance();
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder("Tapis"));
		this.mettreAJour();
	}
	@Override
	public void onWaitTick()
	{
		this.mettreAJour();
		
	}

	public void mettreAJour()
	{
		this.removeAll();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = c.gridy = 0;
		Magasin magasin = Magasin.recupererInstance();
		
		//getmagasin
		for(int i = 1; i <= 10; i++)
		{
			PanneauEjecteur panneauEjecteurDroit, panneauEjecteurGauche;
			String codeDroit, codeGauche;
			int quantiteDroite, quantiteGauche;
			codeDroit = magasin.lireCode(i*2);
			quantiteDroite = magasin.lireQUantite(i*2);
			codeGauche = magasin.lireCode((i*2) - 1);
			quantiteGauche = magasin.lireQUantite((i*2) - 1);
			panneauEjecteurDroit = new PanneauEjecteur(codeDroit, quantiteDroite);
			panneauEjecteurGauche = new PanneauEjecteur(codeGauche, quantiteGauche);

			c.gridx = i;
			c.gridy = 0;
			this.add(panneauEjecteurGauche, c);			
			c.gridy = 2;
			this.add(panneauEjecteurDroit, c);
		}
		
		List<Tas> listeTas = this.peem1000.getListeCommandeSurTapis();
		c.gridy = 1;
		if(listeTas.size() == 0)
		{
			
			this.add(new JPanel(), c);
		}
		else
		{
			JPanel panel;
			JLabel lab;
			Tas courant;
			Iterator<Tas> iterator = listeTas.iterator();
			while(iterator.hasNext())
			{
				courant = iterator.next();
				panel = new JPanel();
				c.gridx = courant.lirePositionCourante();
				lab = new JLabel("(" + courant.getNumero() + ")");
				panel.add(lab);
				this.add(panel, c);
			}
		}
		this.updateUI();
	}

}
