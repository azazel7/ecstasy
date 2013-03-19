package vue.vueGraphique;

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
	JLabel labelCode, labelQuantitee;
	public PanneauEjecteur(String code, int quantite)
	{
		this.code = code;
		this.quantite = quantite;
		JButton boutonRecharger = new JButton("+"), boutonDecharger = new JButton("-");
		this.labelCode = new JLabel(this.code);
		this.labelQuantitee = new JLabel("" + this.quantite);
		this.setLayout(new GridLayout(2,2));
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.add(boutonRecharger);
		this.add(this.labelCode);
		this.add(boutonDecharger);
		this.add(this.labelQuantitee);
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
				if(stock > 0)
				{
					quantite--;
					labelQuantitee.setText("" + quantite);
					labelQuantitee.updateUI();
				}
			}
			
		}
		
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
		this.labelQuantitee.updateUI();
	}
	
	
}
