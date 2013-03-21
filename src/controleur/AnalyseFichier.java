package controleur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyseFichier
{
	/**
	 * Permet de recupérer une commande
	 * Exemple: 44D,3F,5A -> 44 medoc D, 3 medoc F et 5 medoc A
	 * @param commande
	 * @return
	 */
	public static Map<String, Integer> creerCommande(String commande)
	{
		Map<String, Integer> retour = new HashMap<String, Integer>();
		String regex = "^([1-9][0-9]*[A-Z]*,)*[1-9][0-9]*[A-Z]*,?$", code;
		String[] listeMedoc;
		Pattern pattern = Pattern.compile("^([1-9][0-9]*)([A-Z]{1,})$");
		Matcher matcher;
		int nombre;
		if(Pattern.matches(regex, commande) == true)
		{
			listeMedoc = commande.split(",");
			for(int i = 0; i < listeMedoc.length; i++)
			{
				matcher = pattern.matcher(listeMedoc[i]);
				if(matcher.matches())
				{
					nombre = Integer.parseInt(matcher.group(1));
					if(nombre > 0)
					{
						code = matcher.group(2);
						retour.put(code, nombre);
					}
				}
			}
		}
		return retour;
	}
	
	public static String ecrireCommande(Map<String, Integer> commande)
	{
		String retour = new String();
		String key;
		Set<String> keys = commande.keySet();
		Iterator<String> iteratorKeys = keys.iterator();
		
		while(iteratorKeys.hasNext())
		{
			key = iteratorKeys.next();
			retour += commande.get(key);
			retour += key;
			if(iteratorKeys.hasNext())
			{
				retour += ',';
			}
		}
		return retour;
	}
	/**
	 * Renvoie une liste de commande aprés les avoir chargées depuis un fichier
	 * @param chemin
	 * @return
	 */
	public static List<Map<String, Integer>> lireFichierDeCommande(String chemin)
	{
		List<Map<String, Integer>> retour = new LinkedList<Map<String,Integer>>();
		File fichier = new File(chemin);
		String ligne;
		Map<String, Integer> commande;
		try
		{
			if (fichier.exists())//on verifie que le fichier existe
			{
				//On ouvre un flux sur le fichier
				InputStream in = new FileInputStream(fichier);
				InputStreamReader ipsr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(ipsr);
				//On lit le fichier ligne par ligne
				while( (ligne = br.readLine()) != null)
				{
					//On parse chaque ligne
					commande = AnalyseFichier.creerCommande(ligne);
					//Si c'est une mauvaise ligne alors elle aura une taille de 0
					if(commande.size() > 0)
					{
						retour.add(commande);
					}
				}
				br.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return retour;
	}
	/**
	 * Ce sont des tests
	 * @param args
	 */
 	public static void main(String[] args) 
	{
		System.out.println("Debut");
		 Map<String, Integer> titi;
		 Set<String> keys;
		 Iterator<String> iterator;
		 String commande;
		 
		 commande = "44D,3F,5A,";
		 titi = creerCommande(commande);
		 keys = titi.keySet();
		 iterator = keys.iterator();
		 System.out.println(commande);
		 while(iterator.hasNext())
		 {
			 String key = iterator.next();
			 System.out.println(titi.get(key) + " -> " + key);
		 }
		 
		 commande = "44D3F,5A,";
		 titi = creerCommande(commande);
		 keys = titi.keySet();
		 iterator = keys.iterator();
		 System.out.println(commande);
		 while(iterator.hasNext())
		 {
			 String key = iterator.next();
			 System.out.println(titi.get(key) + " -> " + key);
		 }
		 
		 commande = "44D,3F,5A";
		 titi = creerCommande(commande);
		 keys = titi.keySet();
		 iterator = keys.iterator();
		 System.out.println(commande);
		 while(iterator.hasNext())
		 {
			 String key = iterator.next();
			 System.out.println(titi.get(key) + " -> " + key);
		 }
		 System.out.println("Conversion : " + ecrireCommande(titi));
		 
		 commande = "44D";
		 titi = creerCommande(commande);
		 keys = titi.keySet();
		 iterator = keys.iterator();
		 System.out.println(commande);
		 while(iterator.hasNext())
		 {
			 String key = iterator.next();
			 System.out.println(titi.get(key) + " -> " + key);
		 }
	}
}
