package vue.vueGraphique;

import java.awt.BorderLayout;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import modele.Tas;

import controleur.AnalyseFichier;
import controleur.PEEM1000;
import observation.TapisRoulantObserver;

public class PanneauCommandeTapis extends JPanel implements TapisRoulantObserver
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PEEM1000 peem1000;
	
	private DefaultMutableTreeNode racine;
	private JTree arbre;
	
	public PanneauCommandeTapis()
	{
		this.peem1000 = PEEM1000.recupererInstance();
		this.initialiserComposant();
	}
	@Override
	public void onWaitTick()
	{
		this.updateArbre();		
	}

	public void initialiserComposant()
	{
		this.racine = new DefaultMutableTreeNode("Commandes");
		this.arbre = new JTree(this.racine);
		this.updateArbre();
		JScrollPane scrollpane = new JScrollPane(this.arbre);
		
		this.setBorder(BorderFactory.createTitledBorder("Commande sur le tapis"));
		this.setLayout(new BorderLayout());
		this.add(scrollpane, BorderLayout.CENTER);
	}
	public void remplirArbre()
	{
		List<Tas> listeTas = this.peem1000.getListeCommandeSurTapis();
		Iterator<Tas> iterator = listeTas.iterator();
		Tas tasCourant;
		Map<String, Integer> listeMedoc;
		Map<String, Integer> listeMedocRestant;
		Branche node;
		Set<String> keys;
		Iterator<String> iteratorKeys;
		String keyCourante;
		int quantiteeInitiale, quantiteeRestante;
		DefaultMutableTreeNode feuille;
		while(iterator.hasNext())
		{
			tasCourant = iterator.next();
			listeMedoc = tasCourant.getListeMedoc();
			listeMedocRestant = tasCourant.getListeMedocRestante();
			node = new Branche("(" + tasCourant.getNumero() + ") " + AnalyseFichier.ecrireCommande(listeMedoc));
			
			keys = listeMedoc.keySet();
			iteratorKeys = keys.iterator();
			while(iteratorKeys.hasNext())
			{
				keyCourante = iteratorKeys.next();
				quantiteeInitiale = listeMedoc.get(keyCourante);
				if(listeMedocRestant.containsKey(keyCourante))
				{
					quantiteeRestante = listeMedocRestant.get(keyCourante);
				}
				else
				{
					quantiteeRestante = 0;
				}
				feuille = new DefaultMutableTreeNode((quantiteeInitiale - quantiteeRestante) + "/" + quantiteeInitiale + " " + keyCourante);
				node.add(feuille);
			}
			this.racine.add(node);
		}
	}
	
	public void updateArbre()
	{
			//On récupére les branches ouvertes
			Enumeration<TreePath> pathExpand = this.arbre.getExpandedDescendants(new TreePath(racine));
			//On vide la racine
			viderNoeud(this.racine);
			//On re remplis l'arbre
			remplirArbre();
			
			this.arbre.setExpandsSelectedPaths(true);
			//On rouvre les branche
			if(pathExpand != null)
			{
				this.redeployerArbre(pathExpand);
			}
			//this.arbre.expandPath(new TreePath(this.racine));
			this.expand();
			//On lance la mise à jour graphique
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					arbre.updateUI();
				}
			});
	}
	
	public void viderNoeud(DefaultMutableTreeNode selectednode)
	{
		int nbchildren = selectednode.getChildCount();

		for (int i = 0; i < nbchildren; i++)
		{
			if (selectednode.getChildAt(0).isLeaf())
			{
				((DefaultMutableTreeNode) selectednode.getChildAt(0)).removeFromParent();
			}
			else
			{
				viderNoeud((DefaultMutableTreeNode) selectednode.getChildAt(0));
			}
		}
		if (!selectednode.isRoot())
		{
			selectednode.removeFromParent();
		}
	}
	
	public void redeployerArbre(Enumeration<TreePath> pathExpand)
	{
		String userObject;
		DefaultMutableTreeNode node;
		TreePath element;
		Object path[] = new Object[2];
		path[0] = racine;
		
		while (pathExpand.hasMoreElements())
		{
			element = pathExpand.nextElement();
			if(element.getLastPathComponent() instanceof Branche)
			{
				DefaultMutableTreeNode tmp = ((DefaultMutableTreeNode) element.getLastPathComponent());
				userObject = (String) tmp.getUserObject();
				node = trouverNoeud(racine, userObject);
				if (node != null)
				{
					path[1] = node;
					this.arbre.expandPath(new TreePath(path));
				}
			}
		}
	}
	public static DefaultMutableTreeNode trouverNoeud(DefaultMutableTreeNode noeud, String userObject)
	{
		Enumeration<?> children = noeud.children();
		DefaultMutableTreeNode tmp;
		while (children.hasMoreElements())
		{
			tmp = (DefaultMutableTreeNode) children.nextElement();
			if (tmp.getUserObject().equals(userObject))
			{
				return tmp;
			}
		}
		return null;
	}
	
	private void expand()
	{
		Enumeration children = this.racine.children();
		Object tmp, tab[] = new Object[2];
		tab[0] = this.racine;
		while (children.hasMoreElements())
		{
			tmp = children.nextElement();
			tab[1] = tmp;
			this.arbre.expandPath(new TreePath(tab));
		}
	}
	
	@Override
	public void onCreateTas(Tas tas) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDeleteTas(Tas tas) {
		// TODO Auto-generated method stub
		
	}

}
