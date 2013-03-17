package vue.vueGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
		//this.mettreAJour();
	}
	@Override
	public void onWaitTick()
	{
		//this.mettreAJour();
		this.repaint();
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
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.black);
		//Ceux qui n'aiment pas les maths, sauter ce passage
		int marge = 20;
		int reelWidth = this.getWidth() - marge, reelHeight = this.getHeight() - marge;
		int heightCube = reelHeight/3;
		int interCube = reelWidth/59;
		int widthCube = interCube*5;
		int x, y;
		Magasin magasin = Magasin.recupererInstance();
		//getmagasin
			for(int i = 1; i <= 10; i++)
			{
				String codeDroit, codeGauche;
				codeDroit = magasin.lireCode(i*2);
				codeGauche = magasin.lireCode((i*2) - 1);
				x = ((i - 1)*interCube*6) + marge;
				y = marge;
				g.drawRect(x, marge, widthCube, heightCube);
				//g.drawString(codeDroit, 70*i + 25, 20);
				y += 2*heightCube;
				g.drawRect(x, y, widthCube, heightCube);
				
			}
			
			List<Tas> listeTas = this.peem1000.getListeCommandeSurTapis();
			if(listeTas.size() == 0)
			{
				
			}
			else
			{
				JPanel panel;
				JLabel lab;
				Tas courant;
				Iterator<Tas> iterator = listeTas.iterator();
				y = heightCube;
				while(iterator.hasNext())
				{
					courant = iterator.next();
					x = ((courant.lirePositionCourante() - 1)*interCube*6) + marge;
					g.fillOval(x, y, widthCube, widthCube);
				}
			
			}
	}
}
