package vue.vueGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	public void onWaitTick() {
		// TODO Auto-generated method stub
		
	}

	public void mettreAJour()
	{
		this.removeAll();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = c.gridy = 0;
		for(int i = 1; i <= 10; i++)
		{
			JButton bouton = new JButton("" + i);
			JButton bouton2 = new JButton("" + i);
			JLabel label = new JLabel("7");
			label.setBackground(Color.cyan);
			c.gridx = i;
			c.gridy = 0;
			this.add(new PanneauEjecteur("D", 50), c);
			c.gridy = 1;
			if(i %2 == 0)
			{
				this.add(label, c);
			}
			
			c.gridy = 2;
			this.add(bouton2, c);
		}
	}

}
