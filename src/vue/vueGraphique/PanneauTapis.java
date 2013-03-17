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
	}
	@Override
	public void onWaitTick()
	{
		this.repaint();
	}
	//Peut être optimisé en ne peignant que la zone des tas car il n'y a qu'elle qui change
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(238, 238, 238));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.black);
		//Ceux qui n'aiment pas les maths, sauter ce passage
		int marge = 20;
		//On se raméne à un cadre sans marge
		int reelWidth = this.getWidth() - marge, reelHeight = this.getHeight() - marge;
		//Il y a trois lignes
		int heightCube = reelHeight/3;
		//Il y a 10 cube de large et 9 intercube. cinq intercubes égal un cube ce qui donne l'équation -> 10x + 9y = reelWidth avec 5y = x  donc 59y = reelWidth
		int interCube = reelWidth/59;
		int widthCube = interCube*5;
		int x, y;
		Magasin magasin = Magasin.recupererInstance();
		//getmagasin
			for(int i = 1; i <= 10; i++)
			{
				//On récupére les code de medoc
				String codeDroit, codeGauche;
				codeDroit = magasin.lireCode(i*2);
				codeGauche = magasin.lireCode((i*2) - 1);
				//On calcule le x avec la marge
				x = ((i - 1)*interCube*6) + marge;
				y = marge;
				//On peint
				g.drawRect(x, marge, widthCube, heightCube);
				//Ajoute le code a des coordonnée arbitrairement proportionelles
				g.drawString(codeDroit, x + widthCube/2, y + heightCube/2);
				//On recalcule et on repeint l'autre ejecteur
				y = 2*heightCube + marge;
				g.drawRect(x, y, widthCube, heightCube);
				g.drawString(codeGauche, x + widthCube/2, y + heightCube/2);
			}
			
			List<Tas> listeTas = this.peem1000.getListeCommandeSurTapis();
			
			Tas courant;
			Iterator<Tas> iterator = listeTas.iterator();
			y = heightCube + heightCube/2;
			//On peint les tas
			while(iterator.hasNext())
			{
				courant = iterator.next();
				//On calcule le x
				x = ((courant.lirePositionCourante() - 1)*interCube*6) + marge;
				g.setColor(Color.pink);
				g.fillOval(x, y, widthCube, widthCube);
				g.setColor(Color.BLACK);
				g.drawString(courant.getNumero() + "", x, y);
			}
	}
}
