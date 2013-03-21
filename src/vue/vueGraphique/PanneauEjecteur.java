package vue.vueGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Magasin;

public class PanneauEjecteur extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private int quantite;
	JButton labelCode;
	JLabel labelQuantitee;
	public PanneauEjecteur(String code, int quantite, int position)
	{
		this.code = code;
		this.quantite = quantite;
		JButton boutonRecharger = new JButton(" + "), boutonDecharger = new JButton(" - ");
		boutonRecharger.setBorder(BorderFactory.createEmptyBorder());
		boutonDecharger.setBorder(BorderFactory.createEmptyBorder());
		this.labelCode = new JButton(this.code + "(" + position + ")");
		this.labelQuantitee = new JLabel("Qte " + this.quantite);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.add(boutonRecharger, BorderLayout.LINE_END);
		this.add(this.labelCode, BorderLayout.PAGE_START);
		this.add(boutonDecharger, BorderLayout.LINE_START);
		this.add(this.labelQuantitee, BorderLayout.PAGE_END);
		boutonDecharger.addMouseListener(new DechargerStock());
		boutonRecharger.addMouseListener(new RechargerStock());
		
	}
	
	public class RechargerStock extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			if(quantite < Magasin.MAX_STOCK)
			{
				Magasin magasin = Magasin.recupererInstance();
				magasin.rechargerMedoc(code, 1);
				
					quantite++;
					labelQuantitee.setText("" + quantite);
					labelQuantitee.updateUI();
			}
			verifierCouleur();
		}
	}

	public class DechargerStock extends MouseAdapter
	{

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			if(quantite > 0)
			{
				Magasin magasin = Magasin.recupererInstance();
				int stock = magasin.rechargerMedoc(code, -1);
				if(stock >= 0)
				{
					quantite--;
					labelQuantitee.setText("Qte " + quantite);
					labelQuantitee.updateUI();
				}
			}
			verifierCouleur();
		}
	}

	public void verifierCouleur()
	{
		if(this.quantite == 0)
		{
			this.alerteZeroPourcent();
		}
		else if(this.quantite > Magasin.MAX_STOCK/10)
		{
			this.calme();
		}
		else
		{
			this.alerteDixPoucent();
		}
	}
	public void alerteZeroPourcent()
	{
		this.labelCode.setForeground(Color.red);
		this.labelCode.updateUI();
	}
	
	public void calme()
	{
		this.labelCode.setForeground(Color.black);
		this.labelCode.updateUI();
	}
	
	public void alerteDixPoucent()
	{
		this.labelCode.setForeground(Color.orange);
		this.labelCode.updateUI();
	}
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public int getQuantite()
	{
		return quantite;
	}

	public void setQuantite(int quantite)
	{
		this.quantite = quantite;
		this.labelQuantitee.setText("" + this.quantite);
		this.verifierCouleur();
		this.labelQuantitee.updateUI();
	}
	
	
}
